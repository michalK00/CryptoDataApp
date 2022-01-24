package cryptodataapp.predictionStrategy;

import cryptodataapp.currentData.Coin;

public abstract class CoinMarket {

    protected PredictionStrategyInterface coinPrediction;

    public double runCalculations(Coin coin){
        return  coinPrediction.predictionStrategyMethod(coin);
    }

}
