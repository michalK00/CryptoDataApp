package controllers;

import cryptodataapp.CoinData;
import cryptodataapp.CryptoApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import com.jfoenix.controls.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CoinScreenController implements  Initializable{

    CoinData data = new CoinData();


    @FXML
    private Label priceLabel;

    @FXML
    private ImageView coinImage;

    @FXML
    private Label coinNameLabel;

    @FXML
    private JFXButton goBackButton;

    @FXML
    private Label dayHighLabel;

    @FXML
    private ProgressBar dayHighLowProgressBar;

    @FXML
    private Label dayLowLabel;


    public CoinScreenController() {
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



//    StartScreenController controller = new StartScreenController();

    public void setAllParametersBasedOnChosenCoin(int index){

        System.out.println(data.getListOfCoins().get(index).getName());
        String img = data.getListOfCoins().get(index).getImageFileSourcePath();
        String name = data.getListOfCoins().get(index).getName();
        String price = String.valueOf(data.getListOfCoins().get(index).getCurrentPrice());
        String high24h = "$"+ data.getListOfCoins().get(index).getHigh24h();
        String low24h = "$"+ data.getListOfCoins().get(index).getLow24h();

        coinImage.setImage(new Image(img));
        coinNameLabel.setText(name);
        priceLabel.setText(price);
        dayHighLabel.setText(high24h);
        dayLowLabel.setText(low24h);
        System.out.println(calculatePriceProgress());
        dayHighLowProgressBar.setProgress(calculatePriceProgress());


    }

    public double calculatePriceProgress(){
        double high = data.getListOfCoins().get(StartScreenController.index).getHigh24h();
        double low = data.getListOfCoins().get(StartScreenController.index).getLow24h();
        double range = high-low;
        double average = (high+low)/2.0;
        return (range/average);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        setAllParametersBasedOnChosenCoin(StartScreenController.index);

    }




}

