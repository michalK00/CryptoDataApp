package com.example.cryptodataapp;

import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class CoinData {

    private static ArrayList<Coin> listOfCoins = new ArrayList<Coin>();

    public CoinData(){
        connectAndLoadData();
    }

    public void connectAndLoadData(){

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create("https://api.coingecko.com/api/v3/coins/markets?vs_currency=usd&order=market_cap_desc&per_page=50&sparkline=false&price_change_percentage=24h")).build();
        client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .thenAccept(CoinData::parse)
                .join();
    }
    public static void parse(String responseBody){

        ArrayList<Coin> filledList = new ArrayList<>();
        JSONArray albums = new JSONArray(responseBody);

        for(int i = 0; i<albums.length();i++){
            JSONObject album = albums.getJSONObject(i);
            int rank = i+1;
            String id = album.getString("id");
            String symbol = album.getString("symbol");
            String name = album.getString("name");
            String imageFileSourcePath =  album.getString("image");
            float currentPrice = album.getFloat("current_price");
            long marketCap = album.getLong("market_cap");
            float priceChange24h = album.getFloat("price_change_24h");
            float priceChange24hPercentage =  album.getFloat("price_change_percentage_24h");
            long circulatingSupply = album.getLong("circulating_supply");
            float ath = album.getFloat("ath");
            float athChangePercentage = album.getFloat("ath_change_percentage");
            //System.out.println(id+" "+ symbol +" "+ currentPrice + " " + name + " " +imageFileSourcePath + marketCap + " "+priceChange24h+" "+ priceChange24hPercentage+" " + circulatingSupply + " " + ath + " "+ athChangePercentage);
            filledList.add(new Coin(rank, id,symbol,name,imageFileSourcePath,currentPrice,marketCap,priceChange24h,priceChange24hPercentage,circulatingSupply,ath,athChangePercentage));
        }
        listOfCoins=filledList;

    }

    public ArrayList<Coin> getListOfCoins() {
        return listOfCoins;
    }




}
