package cryptodataapp.wallet;

import java.io.Serializable;

public class WalletCoin implements Serializable {

    private String name;
    private double amount;
    private transient double currentPrice;
    private transient double currentValue;

    public WalletCoin(String name, double amount, double currentPrice) {
        this.name = name;
        this.amount =  Math.round(amount*100.0)/100.0;
        this.currentValue = Math.round(currentPrice*amount*100.0)/100.0;
        this.currentPrice = currentPrice;
    }

    public String getName() {
        return name;
    }

    public double getAmount() {
        return amount;
    }

    public double getCurrentPrice() {
        return currentPrice;
    }

    public double getCurrentValue() {
        return Math.round(currentPrice*amount*100.0)/100.0;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAmount(double amount) {
        this.amount = Math.round(amount*100.0)/100.0;
        setCurrentValue();
    }

    public void setCurrentPrice(double currentPrice) {
        this.currentPrice = currentPrice;
    }

    public void setCurrentValue() {
        currentValue = Math.round(currentPrice*amount*100.0)/100.0;
    }
}
