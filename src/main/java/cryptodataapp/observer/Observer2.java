package cryptodataapp.observer;

import controllers.CoinScreenController;
import cryptodataapp.currentData.Coin;
import cryptodataapp.observable.Subject;
import cryptodataapp.predictionStrategy.CoinMarket;
import cryptodataapp.predictionStrategy.PredictionMethod2;
import javafx.scene.Parent;


public class Observer2 extends CoinMarket implements Observer{


    private Coin coin;
    private Subject sub;

    private final double someMysteriousValueCalculatedByScientists = -300;

    public Observer2(Subject sub){
        this.sub=sub;
        this.sub.registerObserver(this);

        coinPrediction = new PredictionMethod2();
    }


    @Override
    public void update(Coin coin, CoinScreenController controller) {

            this.coin = coin;
            if(analize()<someMysteriousValueCalculatedByScientists){
                controller.prediction2Label.setText("Buy");
            } else {
                controller.prediction2Label.setText("Don't buy");;
            }
            //System.out.println(analize());
    }

    public double analize(){
        return this.runCalculations(coin);
    }
}