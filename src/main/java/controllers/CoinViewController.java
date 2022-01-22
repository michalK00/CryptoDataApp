package controllers;

import cryptodataapp.Coin;
import cryptodataapp.CoinData;
import cryptodataapp.CryptoApplication;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import com.jfoenix.controls.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ResourceBundle;

public class CoinViewController implements Initializable{

    CoinData data = new CoinData();

    @FXML
    private ImageView coinImage;
    @FXML
    private Label coinNameLabel;
    @FXML
    private JFXButton goBackButton;

    public CoinViewController() {
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
//    public Coin getChosenCoinDataSet(){
//        return data.getListOfCoins().get(controller.index);
//    }
//    public void setAllParametersBasedOnChosenCoin(){
//        String img = getChosenCoinDataSet().getImageFileSourcePath();
//        String name = getChosenCoinDataSet().getName();
//        coinImage.setImage(new Image(img));
//        coinNameLabel.setText(name);
//    }

    @Override
    public void initialize(URL url, ResourceBundle rb){



    }


}

