package cryptodataapp.predictionStrategy;

import cryptodataapp.currentData.Coin;

public class PredictionMethod2 implements PredictionStrategyInterface {


    @Override
    public double predictionStrategyMethod(Coin coin) {
        double output = coin.getPriceChange24hPercentage()*100-coin.getAtlChangePercentage()/100;
        return output;
    }
}
