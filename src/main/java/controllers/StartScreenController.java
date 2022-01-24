package controllers;

import cryptodataapp.currentData.Coin;
import cryptodataapp.currentData.CoinData;
import cryptodataapp.CryptoApplication;
import cryptodataapp.historicalData.CoinHistoricalData;
import cryptodataapp.wallet.WalletCoin;
import cryptodataapp.wallet.WalletCoinsSerialize;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
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

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class StartScreenController implements Initializable{
    CoinData data = new CoinData();

    @FXML
    private JFXButton cryptoDashboardButton;
    @FXML
    private JFXButton portfolioButton;

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

    @FXML
    private TextField searchBox;

    @FXML
    void searchBoxAction(ActionEvent event) {

    }



    @FXML
    protected void cryptoDashboardButtonClicked() {
        portfolioDashboard.setVisible(false);
        cryptoDashboard.setVisible(true);
        portfolioButton.setButtonType(JFXButton.ButtonType.RAISED);
        cryptoDashboardButton.setButtonType(JFXButton.ButtonType.FLAT);
    }
    @FXML
    protected void portfolioButtonClicked() {
        cryptoDashboard.setVisible(false);
        portfolioDashboard.setVisible(true);
        portfolioButton.setButtonType(JFXButton.ButtonType.FLAT);
        cryptoDashboardButton.setButtonType(JFXButton.ButtonType.RAISED);

    }
    @FXML
    protected void refreshButtonClicked() {
        getAllCryptoListedOnTable();
    }

    public static int index;
    @FXML
    protected void tableClicked(){
        //funkcja anonimowa
        table.setOnMouseClicked((MouseEvent event) -> {
            if(event.getButton().equals(MouseButton.PRIMARY)){
                if(event.getClickCount() == 2 && table.getSelectionModel().getSelectedItem() !=  null){

                    index = rankColumn.getCellData(table.getSelectionModel().getSelectedItem())-1;
                    try {
                        Stage stage = new Stage();
                        FXMLLoader fxmlLoader = new FXMLLoader(CryptoApplication.class.getResource("CoinScreen-view.fxml"));
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
            }
        });
    }


    public void noInterentConnection(){

        Label l = new Label("No internet connection");
        l.setFont(new Font("Calibri", 26));
        l.setTextFill(Color.web("#435C61"));

        ClassLoader classLoader = getClass().getClassLoader();
        Image image = null;
        try {
            File file = new File(classLoader.getResource("noWifi.png").getFile());
            InputStream inputStream = new FileInputStream(file);
            image = new Image(inputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
        ImageView view = new ImageView(image);
        view.setFitHeight(50);
        view.setFitWidth(50);
        view.setPreserveRatio(true);
        l.setGraphic(view);

        table.setPlaceholder(l);

    }
    public void setCellTextColorDependingOnTheirValue(){
        //https://stackoverflow.com/questions/51988663/tableview-modify-style-per-cell-only

        changeColumn.setCellFactory(col -> new TableCell<>() {
            @Override
            protected void updateItem(Double item, boolean empty) {
                super.updateItem(item,  empty);
                if (empty || item == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    setText(item.toString());
                    if (item < 0.0) {
                        setTextFill(Color.RED); // or use setStyle(String)
                    } else {
                        setTextFill(Color.GREEN); // or use setStyle(String)
                    }
                }
            }
        });

    }
    public void rowHoveringFunction(){
        //https://stackoverflow.com/questions/26269940/how-do-i-make-something-happen-on-hover-of-a-row-in-a-javafx-tableview
        table.setRowFactory(tableView -> {
            final TableRow<Coin> row = new TableRow<>();
            row.hoverProperty().addListener((observable) -> {
                final Coin coin = row.getItem();
                if (row.isHover() && coin != null) {
                    row.setStyle("-fx-background-color:  #D1C9B8");
                } else{
                    row.setStyle("");
                }
            });

            return row;
        });

    }

    public void getAllCryptoListedOnTable(){

        ObservableList<Coin> coinList = FXCollections.observableArrayList();

        if(!data.getListOfCoinsAndUpdate().isEmpty()){
            coinList.addAll(data.getListOfCoinsAndUpdate());
            rankColumn.setCellValueFactory(new PropertyValueFactory<Coin,Integer>("rank"));
            nameColumn.setCellValueFactory(new PropertyValueFactory<Coin,String>("name"));
            priceColumn.setCellValueFactory(new PropertyValueFactory<Coin,Double>("currentPrice"));
            changeColumn.setCellValueFactory(new PropertyValueFactory<Coin,Double>("priceChange24hPercentage"));
            marketCapColumn.setCellValueFactory(new PropertyValueFactory<Coin,Long>("marketCap"));

            setCellTextColorDependingOnTheirValue();
            rowHoveringFunction();
            table.setItems(coinList);
        } else{
            noInterentConnection();
        }



    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        getAllCryptoListedOnTable();


        // Starting postion (that may change) for all components
        cryptoDashboardButton.setButtonType(JFXButton.ButtonType.FLAT);
        initPortfolioWindow();
    }

    //===========================(Portfolio window)============================//

    @FXML
    private JFXButton addButton;

    @FXML
    private TextField amountTextField;

    @FXML
    private TableView<WalletCoin> walletCoinTableView;

    @FXML
    private JFXComboBox<String> currencyComboBox;

    @FXML
    private PieChart pieChart;

    @FXML
    private Label totalPortfolioValueLabel;

    @FXML
    private JFXButton saveButton;

    @FXML
    private TableColumn<WalletCoin, String> walletNameColumn;

    @FXML
    private TableColumn<WalletCoin, Double> walletAmountColumn;

    @FXML
    private TableColumn<WalletCoin, WalletCoin> walletTotalValueColumn;

    @FXML
    private TableColumn<WalletCoin, WalletCoin> walletDeleteColumn;

    ArrayList<WalletCoin> walletCoinsList = new ArrayList<>();
    WalletCoinsSerialize serializedWalletCoinsList = new WalletCoinsSerialize();

    @FXML
    void saveButtonClicked(ActionEvent event) {
        serializedWalletCoinsList.setWalletCoinArrayList(walletCoinsList);
        serializedWalletCoinsList.save();

    }

    @FXML
    void addButtonClicked(ActionEvent event) {
        String chosenCoin = currencyComboBox.getValue();
        String amount = amountTextField.getText();
        String regex = "^[0-9]+([.]+[0-9]{1,6})?+$";

        double price = 0;
        for(int x = 0; x<data.getListOfCoins().size();x++){
            if(data.getListOfCoins().get(x).getName().equals(chosenCoin)){
                price = data.getListOfCoins().get(x).getCurrentPrice();
            }
        }
        if(chosenCoin != null && amount.matches(regex) && Double.parseDouble(amount)*price>1){
            boolean doWeAdd=true;
            for(int x = 0; x<walletCoinsList.size();x++){
                if(walletCoinsList.get(x).getName().equals(chosenCoin)){
                    walletCoinsList.get(x).setAmount(walletCoinsList.get(x).getAmount()+Double.parseDouble(amount));
                    doWeAdd=false;
                }
            }
            if (doWeAdd){
                walletCoinsList.add(new WalletCoin(chosenCoin, Double.parseDouble(amount), price));
            }
            coinListTableViewInitAndUpdate();

        }
    }
    public void loadObjectsFromSerializedFile(){
        if(serializedWalletCoinsList.readAndGetWalletCoinArrayList()!=null){
            walletCoinsList = serializedWalletCoinsList.getWalletCoinArrayList();
        }
    }


    public void initPortfolioWindow(){
        loadObjectsFromSerializedFile();
        addComboBoxOptions();
        coinListTableViewInitAndUpdate();
    }
    public void addComboBoxOptions(){
        for(int x = 0; x<data.getListOfCoins().size();x++){
            currencyComboBox.getItems().add(data.getListOfCoins().get(x).getName());
        }
    }

    public void setTotalPortfolioValueLabel(){
        calculateTotalValue();
        totalPortfolioValueLabel.setText("$"+Math.round(calculateTotalValue()*100.0)/100.0);
    }
    public void coinListTableViewInitAndUpdate(){
        ObservableList<WalletCoin> coinList = FXCollections.observableArrayList();

        coinList.addAll(walletCoinsList);

        Label l = new Label("No coins in portfolio");
        l.setFont(new Font("Calibri", 22));
        l.setTextFill(Color.web("#435C61"));
        walletCoinTableView.setPlaceholder(l);

        walletNameColumn.setCellValueFactory(new PropertyValueFactory<WalletCoin,String>("name"));
        walletAmountColumn.setCellValueFactory(new PropertyValueFactory<WalletCoin,Double>("amount"));
        walletTotalValueColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue()));
        walletTotalValueColumn.setCellFactory(param -> new TableCell<WalletCoin, WalletCoin>(){
            @Override
            protected void updateItem(WalletCoin item, boolean empty){
                super.updateItem(item, empty);
                double price = 0;

                if(!empty){
                    for(int x = 0; x<data.getListOfCoins().size();x++){
                        if(data.getListOfCoins().get(x).getName().equals(item.getName())){
                            price = data.getListOfCoins().get(x).getCurrentPrice();
                            setText(String.valueOf(Math.round(price*item.getAmount()*100.0)/100.0));
                        }
                    }

                }
            }
        });
        walletDeleteColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue()));
        walletDeleteColumn.setCellFactory(param -> new TableCell<WalletCoin, WalletCoin>(){
            Button button = createDeleteButton();
            @Override
            protected void updateItem(WalletCoin item, boolean empty){
                super.updateItem(item, empty);
                if(!empty){
                    setGraphic(button);
                    }
                    button.setOnAction(event -> {
                        walletCoinsList.remove(item);
                        coinListTableViewInitAndUpdate();
                        walletCoinTableView.refresh();
                        setTotalPortfolioValueLabel();

                    });
                }
            });

        drawPieChart();
        setTotalPortfolioValueLabel();
        walletCoinTableView.setItems(coinList);
        walletCoinTableView.refresh();
    }


    private Button createDeleteButton(){
        Button button = new Button();
        ClassLoader classLoader = getClass().getClassLoader();
        Image image = null;
        try {
            File file = new File(classLoader.getResource("deleteIcon.png").getFile());
            InputStream inputStream = new FileInputStream(file);
            image = new Image(inputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(20);
        imageView.setFitWidth(20);
        button.setGraphic(imageView);
        return button;
    }

    public void drawPieChart(){
        pieChart.getData().clear();
        double percentValue;
        ObservableList<PieChart.Data> pieData = FXCollections.observableArrayList();
        double totalValue = calculateTotalValue();
        for(int x = 0; x<walletCoinsList.size();x++){
            for(int y = 0; y<data.getListOfCoins().size();y++){
                if(data.getListOfCoins().get(y).getName().equals(walletCoinsList.get(x).getName())){
                    double price = data.getListOfCoins().get(y).getCurrentPrice();
                    percentValue = Math.round(price*walletCoinsList.get(x).getAmount()/totalValue*100);
                    pieData.add(new PieChart.Data(walletCoinsList.get(x).getName(),percentValue));
                }
            }
        }

        for(int x = 0; x<walletCoinsList.size();x++){

        }
        pieChart.setData(pieData);
        pieChart.setAnimated(false);
    }

    public double calculateTotalValue() {
        double totalValue = 0;
        for(int x = 0; x<walletCoinsList.size();x++){
            for(int y = 0; y<data.getListOfCoins().size();y++){
                if(data.getListOfCoins().get(y).getName().equals(walletCoinsList.get(x).getName())){
                    double price = data.getListOfCoins().get(y).getCurrentPrice();
                    totalValue+=Math.round(price*walletCoinsList.get(x).getAmount()*100.0)/100.0;
                }
            }
        }
        return totalValue;
    }


}