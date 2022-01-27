package controllers;

import candleStickChart.CandleStickChart;
import cryptodataapp.currentData.CoinData;
import cryptodataapp.CryptoApplication;
import cryptodataapp.historicalData.CoinHistoricalData;
import cryptodataapp.observable.Observable1;
import cryptodataapp.observer.Observer1;
import cryptodataapp.observer.Observer2;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import com.jfoenix.controls.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import java.net.URL;
import java.util.ResourceBundle;


public class CoinScreenController implements  Initializable{

    @FXML
    private Label coinNameLabel;
    @FXML
    private Label dayChangePercentLabel;
    @FXML
    private Label dayHighLabel;
    @FXML
    private Label dayLowLabel;
    @FXML
    private Label marketCapLabel;
    @FXML
    private Label priceLabel;
    @FXML
    private Label tradingVolumeLabel;
    @FXML
    public Label prediction1Label;
    @FXML
    public Label prediction2Label;
    @FXML
    private Label athDateLabel;
    @FXML
    private Label athLabel;
    @FXML
    private Label athPercentageLabel;
    @FXML
    private Label atlDateLabel;
    @FXML
    private Label atlLabel;
    @FXML
    private Label atlPercentageLabel;
    @FXML
    private ToggleGroup chartRange;
    @FXML
    private ToggleGroup chartType;
    @FXML
    private JFXToggleNode days180;
    @FXML
    private JFXToggleNode days30;
    @FXML
    private JFXToggleNode days90;
    @FXML
    private JFXToggleNode days365;
    @FXML
    private JFXToggleNode candleChart;
    @FXML
    private JFXToggleNode linearChart;
    @FXML
    private AnchorPane lineChartAnchorPane;
    @FXML
    private AnchorPane candleChartAnchorPane;
    @FXML
    public LineChart<String, Double> chart;
    @FXML
    private ProgressBar dayHighLowProgressBar;
    @FXML
    private ImageView coinImage;


    public static Observable1 obs;
    static CoinData data = new CoinData();

    @FXML
    void chartRangeClicked(ActionEvent event) {
        try {
            if (chartType.getSelectedToggle().equals(linearChart)) {
                drawChart();
            } else if (chartType.getSelectedToggle().equals(candleChart)) {
                drawCandleChart(getActiveDaysToggleNode());
            }
        }catch (NullPointerException npe){
            lineChartAnchorPane.setVisible(false);
            candleChartAnchorPane.setVisible(false);
        }

    }
    @FXML
    void chartTypeClicked(ActionEvent event) {
        try{
            if(chartType.getSelectedToggle().equals(linearChart)){
                drawChart();
            } else if (chartType.getSelectedToggle().equals(candleChart)){
                drawCandleChart(getActiveDaysToggleNode());
            }
        }catch (NullPointerException e){
        lineChartAnchorPane.setVisible(false);
        candleChartAnchorPane.setVisible(false);
        }

    }
    @FXML
    void goBackButtonClicked(ActionEvent event) {
            Stage stage = new Stage();
            CryptoApplication.loadScreenViewStage(stage);
            ((Node)(event.getSource())).getScene().getWindow().hide();
    }

    public double calculatePriceProgress(){
        double high = data.getListOfCoins().get(StartScreenController.index).getHigh24h();
        double low = data.getListOfCoins().get(StartScreenController.index).getLow24h();
        double range = high-low;
        double average = (high+low)/2.0;
        return (range/average);
    }
    public int getActiveDaysToggleNode(){
        if(chartRange.getSelectedToggle()==null){
            return 30;
        } else if(chartRange.getSelectedToggle().equals(days30)){
            return 30;
        } else if(chartRange.getSelectedToggle().equals(days90)){
            return 90;
        } else if(chartRange.getSelectedToggle().equals(days180)){
            return 180;
        } else if(chartRange.getSelectedToggle().equals(days365)){
            return 365;
        }
        System.out.println("Error while drawing chart (range)");
        return 0;
    }


    public void drawChart() {
        candleChartAnchorPane.setVisible(false);
        lineChartAnchorPane.setVisible(true);
        chart.getData().clear();
        CoinData data = new CoinData();
        XYChart.Series<String, Double> series = new XYChart.Series<>();
        CoinHistoricalData dataHistorical = new CoinHistoricalData(getActiveDaysToggleNode(), data.getListOfCoins().get(StartScreenController.index).getId());

        for (int x = 0; x < dataHistorical.getHistoricalDataOfACoin().size(); x++) {
            double price = dataHistorical.getHistoricalDataOfACoin().get(x).getPrice();
            String date = dataHistorical.getHistoricalDataOfACoin().get(x).getDate();
            series.getData().add(new XYChart.Data<>(date, price));


        }
        series.setName(data.getListOfCoins().get(StartScreenController.index).getId());
        chart.setCreateSymbols(false);
        chart.getData().add(series);
        chart.setAnimated(false);

    }

    private void drawCandleChart(int rangeOfDays) {

        lineChartAnchorPane.setVisible(false);
        candleChartAnchorPane.setVisible(true);

        CoinHistoricalData dataHistorical = new CoinHistoricalData(getActiveDaysToggleNode(), data.getListOfCoins().get(StartScreenController.index).getId());
        int updatedRangeOfdays =0;
        double v2 =0;
        double a = 0;
        double candleWidth = 20;
        double dataInterval=0;
        String xAxisText="";

        if(rangeOfDays==30){
            updatedRangeOfdays=30;
            dataInterval=1;
            a=1;
            candleWidth = 20;
            xAxisText = "(Day - 4h interval)";
        } else if(rangeOfDays==90){
            updatedRangeOfdays=97;
            dataInterval=4;
            a=4;
            candleWidth = 25;
            xAxisText = "(Day - 4 day interval)";
        }else if(rangeOfDays==180){
            updatedRangeOfdays =185;
            dataInterval=4;
            a=4;
            candleWidth = 12;
            xAxisText = "(Day - 4 day interval)";
        }else if(rangeOfDays==365){
            updatedRangeOfdays =377;
            dataInterval=4;
            a=4;
            candleWidth = 6.5;
            xAxisText = "(Day - 4 day interval)";
        }

        final NumberAxis xAxis = new NumberAxis(1,updatedRangeOfdays,dataInterval);
        xAxis.setMinorTickCount(0);
        final NumberAxis yAxis = new NumberAxis();
        yAxis.setForceZeroInRange(false);

        final CandleStickChart candleChart = new CandleStickChart(xAxis,yAxis, candleWidth);

        xAxis.setLabel(xAxisText);
        yAxis.setLabel("Price(USD)");
        // add starting data

        XYChart.Series<Number,Number> series = new XYChart.Series<Number,Number>();
        candleChart.setAnimated(false);


        ObservableList<XYChart.Series<Number,Number>> chartData = candleChart.getData();
        if (chartData == null) {
            chartData = FXCollections.observableArrayList(series);
            candleChart.setData(chartData);
        } else {
            candleChart.getData().add(series);
        }
        //wprowadzanie danych

        for (int i=0; i<dataHistorical.getHistoricalDataOfACoinOHLC().size();) {
            double dayOpen =dataHistorical.getHistoricalDataOfACoinOHLC().get(i).getDayOpen();
            double dayHigh =dataHistorical.getHistoricalDataOfACoinOHLC().get(i).getDayHigh();
            double dayLow = dataHistorical.getHistoricalDataOfACoinOHLC().get(i).getDayLow();
            double dayClose =dataHistorical.getHistoricalDataOfACoinOHLC().get(i).getDayClose();
            series.getData().add(new XYChart.Data<Number,Number>(a*i,dayOpen, new CandleStickChart.CandleStickExtraValues(dayClose,dayHigh,dayLow)));
            i++;
        }
        lineChartAnchorPane.setVisible(false);
        candleChartAnchorPane.getChildren().add(candleChart);
        candleChartAnchorPane.setVisible(true);

        candleChart.setLayoutX(-49.0);
        candleChart.setLayoutY(14.0);
        candleChart.setPrefWidth(698.0);
        candleChart.setPrefHeight(472.0);

    }

    public void setAllParametersBasedOnChosenCoin(int index){

        String img = data.getListOfCoins().get(index).getImageFileSourcePath();
        String name = data.getListOfCoins().get(index).getName();
        String price = "$"+data.getListOfCoins().get(index).getCurrentPrice();
        String high24h = "$"+ Math.round(data.getListOfCoins().get(index).getHigh24h()*100.0)/100.0;
        String low24h = "$"+ Math.round(data.getListOfCoins().get(index).getLow24h()*100.0)/100.0;
        String change24h = Math.round(data.getListOfCoins().get(index).getPriceChange24hPercentage()*100.0)/100.0+"%";

        String tradingVolume = "$"+String.format("%,d", data.getListOfCoins().get(index).getTradingVolume());
        String marketCap = "$"+String.format("%,d", data.getListOfCoins().get(index).getMarketCap());

        String athPrice = "$"+Math.round(data.getListOfCoins().get(index).getAth()*100.0)/100.0;
        String[] partsAthDate = data.getListOfCoins().get(index).getAthDate().toString().split("T");
        String athDate = partsAthDate[0];
        String athPercentage = Math.round(data.getListOfCoins().get(index).getAthChangePercentage() * 100.0) / 100.0 +"%";

        String atlPrice = "$"+Math.round(data.getListOfCoins().get(index).getAtl()*100.0)/100.0;
        String[] partsAtlDate = data.getListOfCoins().get(index).getAtlDate().toString().split("T");
        String atlDate = partsAtlDate[0];
        String atlPercentage = Math.round(data.getListOfCoins().get(index).getAtlChangePercentage() * 100.0) / 100.0 +"%";

        coinImage.setImage(new Image(img));
        coinNameLabel.setText(name);
        priceLabel.setText(price);
        dayHighLabel.setText(high24h);
        dayLowLabel.setText(low24h);
        dayHighLowProgressBar.setProgress(calculatePriceProgress());
        dayChangePercentLabel.setText(change24h);
        if(Math.round(data.getListOfCoins().get(index).getPriceChange24hPercentage()*100.0)/100.0>=0){
            dayChangePercentLabel.setTextFill(Color.web("GREEN"));
        } else{
            dayChangePercentLabel.setTextFill(Color.web("RED"));
        }
        tradingVolumeLabel.setText(tradingVolume);
        marketCapLabel.setText(marketCap);
        athLabel.setText(athPrice);
        athDateLabel.setText(athDate);
        athPercentageLabel.setText(athPercentage);
        if(Math.round(data.getListOfCoins().get(index).getAthChangePercentage()*100.0)/100.0>=0){
            athPercentageLabel.setTextFill(Color.web("GREEN"));
        } else{
            athPercentageLabel.setTextFill(Color.web("RED"));
        }
        atlLabel.setText(atlPrice);
        atlDateLabel.setText(atlDate);
        atlPercentageLabel.setText(atlPercentage);
        if(Math.round(data.getListOfCoins().get(index).getAtlChangePercentage()*100.0)/100.0>=0){
            atlPercentageLabel.setTextFill(Color.web("GREEN"));
        } else{
            atlPercentageLabel.setTextFill(Color.web("RED"));
        }

    }

    public static void setObs(Observable1 o){
        obs=o;
    }
    public static void sendDataToModel(CoinScreenController controller){
        obs.setData(data.getListOfCoins().get(StartScreenController.index), controller);
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        Observable1 observable1 = new Observable1();
        CoinScreenController.setObs(observable1);
        Observer1 observer1 = new Observer1(observable1);
        Observer2 observer2 = new Observer2(observable1);

        setAllParametersBasedOnChosenCoin(StartScreenController.index);
        drawChart();

    }

}

