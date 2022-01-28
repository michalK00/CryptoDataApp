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

    int firstThreshold = 14;
    int secondThreshold = 29;
    int thirdThreshold = 50;
    int fourthThreshold = 75;



    public Observer1(Subject sub){
        this.sub=sub;
        this.sub.registerObserver(this);

        coinPrediction = new PredictionMethod1();
    }


    @Override
    public void update(Coin coin, CoinScreenController controller) {


            this.coin = coin;
            if(analize()>fourthThreshold){
                controller.prediction1Label.setText("Strong buy");
            }else if(analize()>thirdThreshold){
            controller.prediction1Label.setText("Buy");
            }else if(analize()>secondThreshold){
                controller.prediction1Label.setText("Not good, not bad");
            }else if(analize()>firstThreshold){
            controller.prediction1Label.setText("Don't buy");
            } else {
                controller.prediction1Label.setText("Strong don't buy");;
            }
            //System.out.println(analize());
    }

    public double analize(){
        return this.runCalculations(coin);
    }
}
