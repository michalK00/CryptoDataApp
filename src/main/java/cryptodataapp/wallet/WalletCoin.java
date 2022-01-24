package cryptodataapp.wallet;

import java.io.Serial;
import java.io.Serializable;

public class WalletCoin implements Serializable {

    private String name;
    private double amount;
    @Serial
    private static final long serialVersionUID = 123456789L;

    public WalletCoin(String name, double amount, double currentPrice) {
        this.name = name;
        this.amount =  Math.round(amount*100.0)/100.0;
    }

    public String getName() {
        return name;
    }

    public double getAmount() {
        return amount;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAmount(double amount) {
        this.amount = Math.round(amount*100.0)/100.0;
    }

}
