package controllers;

import com.example.cryptodataapp.Coin;
import com.example.cryptodataapp.CoinData;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import com.jfoenix.controls.*;
import javafx.scene.layout.AnchorPane;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class StartScreenController implements Initializable{
    CoinData data = new CoinData();

    @FXML
    private JFXButton cryptoDashboardButton;
    @FXML
    private JFXButton portfolioButton;
    @FXML
    private JFXButton refreshButton;
    @FXML
    private AnchorPane cryptoDashboard;
    @FXML
    private AnchorPane portfolioDashboard;

    @FXML
    private TableView<Coin> table;
    @FXML
    private TableColumn<Coin, Integer> rankColumn;
    @FXML
    private TableColumn<Coin, String> nameColumn;
    @FXML
    private TableColumn<Coin, Float> priceColumn;
    @FXML
    private TableColumn<Coin, Float> changeColumn;
    @FXML
    private TableColumn<Coin, Long> marketCapColumn;

    ObservableList<Coin> list = FXCollections.observableArrayList(

    );




//    public void fillColumnsWithData(){
//
//        TableColumn<Coin, Integer> rankColumn = new TableColumn<>("#");
//        rankColumn.setCellValueFactory(new PropertyValueFactory<>("rank"));
//
//        TableColumn<Coin, String> nameColumn = new TableColumn<>("Name");
//        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
//
//        TableColumn<Coin, Float> priceColumn = new TableColumn<>("Price");
//        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
//
//        TableColumn<Coin, String> changeColumn = new TableColumn<>("24h");
//        changeColumn.setCellValueFactory(new PropertyValueFactory<>("priceChange24hPercentage"));
//
//        TableColumn<Coin, String> marketCapColumn = new TableColumn<>("Market Cap");
//        marketCapColumn.setCellValueFactory(new PropertyValueFactory<>("marketCap"));
//
//
//
//
//    }




    @FXML
    protected void cryptoDashboardButtonClicked() {
        portfolioDashboard.setVisible(false);
        cryptoDashboard.setVisible(true);
    }
    @FXML
    protected void portfolioButtonClicked() {
        cryptoDashboard.setVisible(false);
        portfolioDashboard.setVisible(true);

    }
    @FXML
    protected void refreshButtonClicked() {

    }
    public void getAllCryptoListedOnTable(){
        ObservableList<Coin> coinList = FXCollections.observableArrayList();
        coinList.addAll(data.getListOfCoins());

        rankColumn.setCellValueFactory(new PropertyValueFactory<Coin,Integer>("rank"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<Coin,String>("name"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<Coin,Float>("currentPrice"));
        changeColumn.setCellValueFactory(new PropertyValueFactory<Coin,Float>("priceChange24hPercentage"));
        marketCapColumn.setCellValueFactory(new PropertyValueFactory<Coin,Long>("marketCap"));

        table.setItems(coinList);
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        getAllCryptoListedOnTable();
    }
}