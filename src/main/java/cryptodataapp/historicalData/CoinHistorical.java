package cryptodataapp.historicalData;

public class CoinHistorical {

    private double price;
    private String date;
    private double dayHigh, dayLow, dayOpen,dayClose;

    public CoinHistorical(double price, String date){
        this.price = price;
        this.date = date;
    }

    public CoinHistorical(String date, double dayOpen,double dayHigh, double dayLow,  double dayClose) {
        this.date=date;
        this.dayHigh = dayHigh;
        this.dayLow = dayLow;
        this.dayOpen = dayOpen;
        this.dayClose = dayClose;
    }

    @Override
    public String toString(){
        return "price: " + price + " date: " + date +  " day open: "+ dayOpen+ " day high: "+ dayHigh + " day low: "+ dayLow+" day close: "+ dayClose;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getDayHigh() {
        return dayHigh;
    }

    public void setDayHigh(double dayHigh) {
        this.dayHigh = dayHigh;
    }

    public double getDayLow() {
        return dayLow;
    }

    public void setDayLow(double dayLow) {
        this.dayLow = dayLow;
    }

    public double getDayOpen() {
        return dayOpen;
    }

    public void setDayOpen(double dayOpen) {
        this.dayOpen = dayOpen;
    }

    public double getDayClose() {
        return dayClose;
    }

    public void setDayClose(double dayClose) {
        this.dayClose = dayClose;
    }
}
