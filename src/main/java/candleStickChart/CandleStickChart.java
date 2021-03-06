package candleStickChart;

import javafx.animation.FadeTransition;
import javafx.beans.InvalidationListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.chart.Axis;
import javafx.scene.chart.XYChart;
import javafx.scene.shape.Line;
import javafx.scene.shape.Path;
import javafx.util.Duration;

import java.util.*;

public class CandleStickChart<X, Y> extends XYChart<X, Y> {


    private double candleWidth;

    private ObservableList<Data<X, Y>> horizontalMarkers;

    public CandleStickChart(Axis<X> xAxis, Axis<Y> yAxis, double candleWidth) {
        super(xAxis, yAxis);
        getStylesheets().add(getClass().getResource("/cryptodataapp/css/CandleStickChartStyles.css").toExternalForm());
        this.candleWidth = candleWidth;
        horizontalMarkers = FXCollections.observableArrayList();
        horizontalMarkers.addListener((InvalidationListener) observable -> layoutPlotChildren());
    }

    @Override
    protected void dataItemAdded(Series<X, Y> series, int itemIndex, Data<X, Y> item) {
        Node candle = createCandle(getData().indexOf(series), item, itemIndex);
        if (shouldAnimate()) {
            candle.setOpacity(0);
            getPlotChildren().add(candle);
            // fade in new candle
            FadeTransition ft = new FadeTransition(Duration.millis(500), candle);
            ft.setToValue(1);
            ft.play();
        } else {
            getPlotChildren().add(candle);
        }
        // always draw average line on top
        if (series.getNode() != null) {
            series.getNode().toFront();
        }
    }


    private Node createCandle(int seriesIndex, final Data item, int itemIndex) {
        Node candle = item.getNode();
        // check if candle has already been created
        if (candle instanceof Candle) {
            ((Candle) candle).setSeriesAndDataStyleClasses("series" + seriesIndex, "data" + itemIndex);
        } else {
            candle = new Candle("series" + seriesIndex, "data" + itemIndex);
            item.setNode(candle);
        }
        return candle;
    }

    @Override
    protected void dataItemRemoved(Data<X, Y> item, Series<X, Y> series) {
        final Node candle = item.getNode();
        if (shouldAnimate()) {
            // fade out old candle
            FadeTransition ft = new FadeTransition(Duration.millis(500), candle);
            ft.setToValue(0);
            ft.setOnFinished(actionEvent -> getPlotChildren().remove(candle));
            ft.play();
        } else {
            getPlotChildren().remove(candle);
        }
    }

    @Override
    protected void dataItemChanged(Data<X, Y> item) {
    }

    @Override
    protected void seriesAdded(Series<X, Y> series, int seriesIndex) {
        // handle any data already in series
        for (int j = 0; j < series.getData().size(); j++) {
            Data item = series.getData().get(j);
            Node candle = createCandle(seriesIndex, item, j);
            if (shouldAnimate()) {
                candle.setOpacity(0);
                getPlotChildren().add(candle);
                // fade in new candle
                FadeTransition ft = new FadeTransition(Duration.millis(500), candle);
                ft.setToValue(1);
                ft.play();
            } else {
                getPlotChildren().add(candle);
            }
        }
        // create series path
        Path seriesPath = new Path();
        seriesPath.getStyleClass().setAll("candlestick-average-line", "series" + seriesIndex);
        series.setNode(seriesPath);
        getPlotChildren().add(seriesPath);
    }

    @Override
    protected void seriesRemoved(Series<X, Y> series) {
        // remove all candle nodes
        for (XYChart.Data<X, Y> d : series.getData()) {
            final Node candle = d.getNode();
            if (shouldAnimate()) {
                // fade out old candle
                FadeTransition ft = new FadeTransition(Duration.millis(500), candle);
                ft.setToValue(0);
                ft.setOnFinished(actionEvent -> getPlotChildren().remove(candle));
                ft.play();
            } else {
                getPlotChildren().remove(candle);
            }
        }
    }

    @Override
    protected void layoutPlotChildren() {
        if (getData() == null) return;
        for (int seriesIndex = 0; seriesIndex < getData().size(); seriesIndex++) {
            Series<X, Y> series = getData().get(seriesIndex);
            Iterator<Data<X, Y>> iterator = getDisplayedDataIterator(series);
            while (iterator.hasNext()) {
                Data<X, Y> item = iterator.next();
                double x = getXAxis().getDisplayPosition(getCurrentDisplayedXValue(item));
                double y = getYAxis().getDisplayPosition(getCurrentDisplayedYValue(item));
                Node itemNode = item.getNode();
                CandleStickExtraValues<Y> extra = (CandleStickExtraValues<Y>) item.getExtraValue();
                if (itemNode instanceof Candle) {
                    Candle candle = (Candle) itemNode;

                    double close = getYAxis().getDisplayPosition(extra.getClose());
                    double high = getYAxis().getDisplayPosition(extra.getHigh());
                    double low = getYAxis().getDisplayPosition(extra.getLow());

                    candle.update(close - y, high - y, low - y, candleWidth);
                    candle.setLayoutX(x);
                    candle.setLayoutY(y);
                }
            }
        }
        for (Data<X, Y> horizontalMarker : horizontalMarkers) {
            Line line = (Line) horizontalMarker.getNode();
            line.setStartX(0);
            line.setEndX(getBoundsInLocal().getWidth());
            line.setStartY(getYAxis().getDisplayPosition(horizontalMarker.getYValue()) + 0.5); // 0.5 for crispness
            line.setEndY(line.getStartY());
            line.toFront();
        }
    }


    @Override

    protected void updateAxisRange() {
        // For candle stick chart we need to override this method as we need to let the axis know that they need to be able
        // to cover the whole area occupied by the high to low range not just its center data value
        final Axis<X> xa = getXAxis();
        final Axis<Y> ya = getYAxis();
        List<X> xData = null;
        List<Y> yData = null;
        if (xa.isAutoRanging()) {
            xData = new ArrayList<>();
        }
        if (ya.isAutoRanging()) {
            yData = new ArrayList<>();
        }
        if (xData != null || yData != null) {
            for (Series<X, Y> series : getData()) {
                for (Data<X, Y> data : series.getData()) {
                    if (xData != null) {
                        xData.add(data.getXValue());
                    }
                    if (yData != null) {
                        CandleStickExtraValues<Y> extras = (CandleStickExtraValues<Y>) data.getExtraValue();
                        if (extras != null) {
                            yData.add(extras.getHigh());
                            yData.add(extras.getLow());
                        } else {
                            yData.add(data.getYValue());
                        }
                    }
                }
            }
            if (xData != null) {
                xa.invalidateRange(xData);
            }
            if (yData != null) {
                ya.invalidateRange(yData);
            }
        }
    }

    public static class CandleStickExtraValues<Y> {
        private Y close;
        private Y high;
        private Y low;

        public CandleStickExtraValues(Y close, Y high, Y low) {
            this.close = close;
            this.high = high;
            this.low = low;
        }

        public Y getClose() {
            return close;
        }

        public Y getHigh() {
            return high;
        }

        public Y getLow() {
            return low;
        }
    }

}
