package controllers;

import cryptodataapp.Coin;
import cryptodataapp.CoinData;
import cryptodataapp.CryptoApplication;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.UnknownHostException;
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

    public StartScreenController()  {
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
                    System.out.println();

                    System.out.println(rankColumn.getCellData(table.getSelectionModel().getSelectedItem()));
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
                //w momencie klikniecia na nazwe tabeli pokazuje się null;
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

    }

}