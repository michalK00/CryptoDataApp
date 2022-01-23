package cryptodataapp.historicalData;


import java.security.Timestamp;
import java.util.Date;

public class CoinHistorical {

    private double price;
    private String date;

    public CoinHistorical(double price, String date){
        this.price = price;
        this.date = date;
    }
    @Override
    public String toString(){
        return "price: " + price + " date: " + date;
    }

    public double getPrice() {
        return price;
    }

    public String getDate() {
        return date;
    }
}
