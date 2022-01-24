package controllers;

import cryptodataapp.currentData.CoinData;
import cryptodataapp.CryptoApplication;
import cryptodataapp.historicalData.CoinHistoricalData;
import cryptodataapp.observable.Observable1;
import cryptodataapp.observer.Observer1;
import cryptodataapp.observer.Observer2;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import com.jfoenix.controls.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class CoinScreenController implements  Initializable{

    static CoinData data = new CoinData();


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
    private ImageView coinImage;

    @FXML
    private Label coinNameLabel;

    @FXML
    private Label dayChangePercentLabel;

    @FXML
    private Label dayHighLabel;

    @FXML
    private ProgressBar dayHighLowProgressBar;

    @FXML
    private Label dayLowLabel;

    @FXML
    private JFXToggleNode days180;

    @FXML
    private JFXToggleNode days30;

    @FXML
    private JFXToggleNode days90;

    @FXML
    private JFXToggleNode days360;

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
    public LineChart<String, Double> lineChart;

    public static Observable1 obs;

    public CoinScreenController() {
    }
    @FXML
    void chartRangeClicked(ActionEvent event) {
        drawChart();
    }

    @FXML
    void chartTypeClicked(ActionEvent event) {
        //drawChart();
    }

    @FXML
    void goBackButtonClicked(ActionEvent event) {
        try {
            Stage stage = new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(CryptoApplication.class.getResource("StartScreen-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 1000, 700);
            stage.setTitle("Crypto Data Application");
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
            ((Node)(event.getSource())).getScene().getWindow().hide();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
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

    public double calculatePriceProgress(){
        double high = data.getListOfCoins().get(StartScreenController.index).getHigh24h();
        double low = data.getListOfCoins().get(StartScreenController.index).getLow24h();
        double range = high-low;
        double average = (high+low)/2.0;
        return (range/average);
    }
    public int getActiveDaysToggleNode(){
        if(chartRange.getSelectedToggle()==null){
            return 0;
        } else if(chartRange.getSelectedToggle().equals(days30)){
            return 30;
        } else if(chartRange.getSelectedToggle().equals(days90)){
            return 90;
        } else if(chartRange.getSelectedToggle().equals(days180)){
            return 180;
        } else if(chartRange.getSelectedToggle().equals(days360)){
            return 360;
        }
            System.out.println("Error while drawing chart (range)");
        return 0;
    }


    public void drawChart() {
        lineChart.getData().clear();
        CoinData data = new CoinData();
        XYChart.Series<String, Double> series = new XYChart.Series<>();
        CoinHistoricalData dataHistorical = new CoinHistoricalData(getActiveDaysToggleNode(), data.getListOfCoins().get(StartScreenController.index).getId());

        for (int x = 0; x < dataHistorical.getHistoricalDataOfACoin().size(); x++) {
            double price = dataHistorical.getHistoricalDataOfACoin().get(x).getPrice();
            String date = dataHistorical.getHistoricalDataOfACoin().get(x).getDate();
            series.getData().add(new XYChart.Data<>(date, price));


        }
        series.setName(data.getListOfCoins().get(StartScreenController.index).getId());
        lineChart.setCreateSymbols(false);
        lineChart.getData().add(series);
        lineChart.setAnimated(false);
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
    public static void setObs(Observable1 o){
        obs=o;
    }
    public static void sendDataToModel(CoinScreenController controller){
        obs.setData(data.getListOfCoins().get(StartScreenController.index), controller);
    }
    public void dataToView(String advise, String observer_info) {

        if (observer_info.equals("Observer 1")) {

            prediction1Label.setText(advise);
        }
        else if (observer_info.equals("Observer 2")) {

            prediction2Label.setText(advise);
        }

    }







}

