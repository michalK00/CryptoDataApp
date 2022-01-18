package com.example.cryptodataapp;

public class Coin {

    private String id;
    private String symbol;
    private String name;
    private String imageFileSourcePath;
    private float currentPrice;
    private long marketCap;
    private float priceChange24h;
    private float priceChange24hPercentage;
    private long circulatingSupply;
    private float ath;
    private float athChangePercentage;

    public Coin(String id, String symbol, String name, String imageFileSourcePath, float currentPrice, long marketCap, float priceChange24h, float priceChange24hPercentage, long circulatingSupply, float ath, float athChangePercentage) {
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
        return "Name: " + name + " symbol: " + symbol + " price: " + currentPrice;
    }
}
