# CryptoDataApp

This is an app for cryptocurrency data tracking. It allows you to add your own strategies for coin price prediction.

You can add one simply by: adding new file to predictionStrategy package and implementing the interface inside this package called PredictionStrategyInterface.
Then you can apply your own logic by overwriting predictionStrategyMethod. 
To apply your method you need to modify or add new Observer in the observer package

```java
public Observer1(Subject sub){
    this.sub=sub;
    this.sub.registerObserver(this);
    coinPredicion = new "yourFileName"();
}
```
