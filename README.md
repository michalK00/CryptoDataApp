# CryptoDataApp

This is an app for cryptocurrency data and wallet tracking. 

It also allows you to add your own strategies for coin price prediction.

You can add one simply by: adding new file to predictionStrategy package and implementing the interface inside this package called PredictionStrategyInterface.
Then you can apply your own logic and making your own prediction strategy. 
To apply your method you need to modify or add new Observer in the observer package

```java
public Observer1(Subject sub){
    this.sub=sub;
    this.sub.registerObserver(this);
    coinPredicion = new "yourFileName"();
}
```
You have access to the following variables:
```java
coin.getCurrentPrice(); 
coin.getMarketCap(); 
coin.getHigh24h(); 
coin.getLow24h();
coin.getPriceChange24h();
coin.getPriceChange24hPercentage();
coin.getCirculatingSupply();
coin.getAth();
coin.getAthChangePercentage();
coin.getAthDate();
coin.getAtl();
coin.getAtlChangePercentage();
coin.getAtlDate();
coin.getTradingVolume();
```
All of these variables are fields of Coin class located in currentData package
