package controllers;

import com.example.cryptodataapp.Coin;
import com.example.cryptodataapp.CoinData;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import com.jfoenix.controls.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;

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
    private TableColumn<Coin, Double> priceColumn;
    @FXML
    private TableColumn<Coin, Double> changeColumn;
    @FXML
    private TableColumn<Coin, Long> marketCapColumn;

    ObservableList<Coin> list = FXCollections.observableArrayList(

    );

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
    @FXML
    protected void tableClicked(){

    }
    public void getAllCryptoListedOnTable(){
        ObservableList<Coin> coinList = FXCollections.observableArrayList();
        coinList.addAll(data.getListOfCoins());

        rankColumn.setCellValueFactory(new PropertyValueFactory<Coin,Integer>("rank"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<Coin,String>("name"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<Coin,Double>("currentPrice"));
        changeColumn.setCellValueFactory(new PropertyValueFactory<Coin,Double>("priceChange24hPercentage"));
        marketCapColumn.setCellValueFactory(new PropertyValueFactory<Coin,Long>("marketCap"));
        //rankColumn.setStyle("-fx-background-color:  #384D52; -fx-text-fill:  #F2E9D5");


        table.setRowFactory(tableView -> {
            final TableRow<Coin> row = new TableRow<>();
            row.hoverProperty().addListener((observable) -> {
                final Coin coin = row.getItem();
                if (row.isHover() && coin != null) {

                    //row.setStyle("-fx-background-color:  #384D52");
                    row.setStyle("-fx-text-fill:  #F2E9D5");

                } else{
                    //row.setStyle("-fx-background-color:  #ffffff; -fx-text-fill:  #384D52");

                    row.setStyle("-fx-text-fill:  #384D52");
                }
            });

            return row;
        });
        table.setItems(coinList);
    }





    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        getAllCryptoListedOnTable();
    }
}