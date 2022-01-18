package com.example.cryptodataapp;

public class Coin {

    private int rank;
    private String id;
    private String symbol;
    private String name;
    private String imageFileSourcePath;
    private double currentPrice;
    private long marketCap;
    private double priceChange24h;
    private double priceChange24hPercentage;
    private long circulatingSupply;
    private float ath;
    private float athChangePercentage;

    public Coin(int rank, String id, String symbol, String name, String imageFileSourcePath, double currentPrice, long marketCap, double priceChange24h, double priceChange24hPercentage, long circulatingSupply, float ath, float athChangePercentage) {
        this.rank = rank;
        this.id = id;
        this.symbol = symbol;
        this.name = name;
        this.imageFileSourcePath = imageFileSourcePath;
        this.currentPrice = currentPrice;
        this.marketCap = marketCap;
        this.priceChange24h = priceChange24h;
        this.priceChange24hPercentage = priceChange24hPercentage;
        this.circulatingSupply = circulatingSupply;
        this.ath = ath;
        this.athChangePercentage = athChangePercentage;
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
}
