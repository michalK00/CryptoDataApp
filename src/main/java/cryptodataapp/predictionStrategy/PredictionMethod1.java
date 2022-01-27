package cryptodataapp.predictionStrategy;

import cryptodataapp.currentData.Coin;

public class PredictionMethod1 implements PredictionStrategyInterface {


    @Override
    public double predictionStrategyMethod(Coin coin) {
        double output = -coin.getAthChangePercentage()-coin.getPriceChange24hPercentage();
        return output;
    }
}
