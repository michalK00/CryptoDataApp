package cryptodataapp;

import controllers.CoinScreenController;
import cryptodataapp.historicalData.CoinHistoricalData;
import cryptodataapp.observable.Observable1;
import cryptodataapp.observer.Observer1;
import cryptodataapp.observer.Observer2;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;
import java.io.IOException;
import java.security.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class CryptoApplication extends Application {


    @Override
    public void start(Stage stage){
        loadScreenViewStage(stage);


    }

    public static void loadScreenViewStage(Stage stage){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(CryptoApplication.class.getResource("StartScreen-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 1000, 700);
            stage.setTitle("Crypto Data Application");
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
        } catch(IOException e){
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
//        CoinHistoricalData data = new CoinHistoricalData(30, "bitcoin");
//        for(int x = 0; x<data.getHistoricalDataOfACoinOHLC().size();x++){
//            System.out.println(data.getHistoricalDataOfACoinOHLC().get(x));
//        }
        launch();
    }
}