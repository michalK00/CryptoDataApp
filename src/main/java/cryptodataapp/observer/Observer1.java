package cryptodataapp.observer;

import controllers.CoinScreenController;
import cryptodataapp.CryptoApplication;
import cryptodataapp.currentData.Coin;
import cryptodataapp.observable.Subject;
import cryptodataapp.predictionStrategy.CoinMarket;
import cryptodataapp.predictionStrategy.PredictionMethod1;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.IOException;

public class Observer1 extends CoinMarket implements Observer{

    private Coin coin;
    private Subject sub;
    private Parent root;



    private final double someMysteriousValueCalculatedByScientists = 0;

    public Observer1(Subject sub){
        this.sub=sub;
        this.sub.registerObserver(this);

        coinPrediction = new PredictionMethod1();
    }


    @Override
    public void update(Coin coin, CoinScreenController controller) {


            this.coin = coin;
            if(analize()>someMysteriousValueCalculatedByScientists){
                controller.prediction1Label.setText("BUY");
            } else {
                controller.prediction1Label.setText("DON'T BUY");;
            }
            //System.out.println(analize());
    }

    public double analize(){
        return this.runCalculations(coin);
    }
}
