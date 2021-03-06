package cryptodataapp.currentData;

import java.time.Instant;

public class Coin {

    private int rank;
    private String id;
    private String symbol;
    private String name;
    private String imageFileSourcePath;
    private double currentPrice;
    private long marketCap;
    private long tradingVolume;
    private double high24h;
    private double low24h;
    private double priceChange24h;
    private double priceChange24hPercentage;
    private long circulatingSupply;
    private float ath;
    private float athChangePercentage;
    private Instant athDate;
    private float atl;
    private float atlChangePercentage;
    private Instant atlDate;

    public Coin(int rank, String id, String symbol, String name, String imageFileSourcePath, double currentPrice,
                long marketCap, long tradingVolume, double high24h, double low24h, double priceChange24h,
                double priceChange24hPercentage, long circulatingSupply, float ath, float athChangePercentage,
                Instant athDate, float atl, float atlChangePercentage, Instant atlDate) {
        this.rank = rank;
        this.id = id;
        this.symbol = symbol;
        this.name = name;
        this.imageFileSourcePath = imageFileSourcePath;
        this.currentPrice = currentPrice;
        this.marketCap = marketCap;
        this.tradingVolume = tradingVolume;
        this.high24h = high24h;
        this.low24h = low24h;
        this.priceChange24h = priceChange24h;
        this.priceChange24hPercentage = priceChange24hPercentage;
        this.circulatingSupply = circulatingSupply;
        this.ath = ath;
        this.athChangePercentage = athChangePercentage;
        this.athDate = athDate;
        this.atl = atl;
        this.atlChangePercentage = atlChangePercentage;
        this.atlDate = atlDate;
    }

    @Override
    public String toString(){
        return "Name: " + name + " symbol: " + symbol + " price: " + currentPrice+ " 24h change: " + priceChange24h;
    }

    public int getRank(){
        return rank;
    }

    public String getId() {
        return id;
    }

    public String getSymbol() {
        return symbol;
    }

    public String getName() {
        return name;
    }

    public String getImageFileSourcePath() {
        return imageFileSourcePath;
    }

    public double getCurrentPrice() {
        return currentPrice;
    }

    public long getMarketCap() {
        return marketCap;
    }

    public double getHigh24h() {
        return high24h;
    }

    public double getLow24h() {
        return low24h;
    }

    public double getPriceChange24h() {
        return priceChange24h;
    }

    public double getPriceChange24hPercentage() {
        return priceChange24hPercentage;
    }

    public long getCirculatingSupply() {
        return circulatingSupply;
    }

    public float getAth() {
        return ath;
    }

    public float getAthChangePercentage() {
        return athChangePercentage;
    }

    public Instant getAthDate() {
        return athDate;
    }

    public float getAtl() {
        return atl;
    }

    public float getAtlChangePercentage() {
        return atlChangePercentage;
    }

    public Instant getAtlDate() {
        return atlDate;
    }

    public long getTradingVolume() {
        return tradingVolume;
    }
}
