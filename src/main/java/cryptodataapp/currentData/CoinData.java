package cryptodataapp.currentData;

import org.json.JSONArray;
import org.json.JSONObject;
import java.net.*;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Instant;
import java.util.ArrayList;

public class CoinData {

    private static ArrayList<Coin> listOfCoins = new ArrayList<Coin>();

    public CoinData() {
        connectAndLoadData();
    }

    public void connectAndLoadData(){

        try{
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder().uri(URI.create("https://api.coingecko.com/api/v3/coins/markets?vs_currency=usd&order=market_cap_desc&per_page=50&sparkline=false&price_change_percentage=24h")).build();
            client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                    .thenApply(HttpResponse::body)
                    .thenAccept(CoinData::parse)
                    .join();
        } catch (RuntimeException e){
            e.printStackTrace();
        }
    }
    public static void parse(String responseBody){

        ArrayList<Coin> filledList = new ArrayList<>();
        JSONArray albums = new JSONArray(responseBody);
        String athDateString;
        String atlDateString;
        Instant athDate;
        Instant atlDate;

        for(int i = 0; i<albums.length();i++){
            JSONObject album = albums.getJSONObject(i);
            int rank = i+1;
            String id = album.getString("id");
            String symbol = album.getString("symbol");
            String name = album.getString("name");
            String imageFileSourcePath =  album.getString("image");
            double currentPrice = album.getDouble("current_price");
            long marketCap = album.getLong("market_cap");
            long tradingVolume = album.getLong("total_volume");
            double high24h = album.getFloat("high_24h");
            double low24h = album.getFloat("low_24h");
            double priceChange24h = Math.round(album.getFloat("price_change_24h")*100.0)/100.0;
            double priceChange24hPercentage =  Math.round(album.getFloat("price_change_percentage_24h")*100.0)/100.0;
            long circulatingSupply = album.getLong("circulating_supply");
            float ath = album.getFloat("ath");
            float athChangePercentage = album.getFloat("ath_change_percentage");
            athDateString = album.getString("ath_date");
            float atl = album.getFloat("atl");
            float atlChangePercentage = album.getFloat("atl_change_percentage");
            atlDateString = album.getString("atl_date");

            athDate = Instant.parse(athDateString);
            atlDate = Instant.parse(atlDateString);

            filledList.add(new Coin(rank, id,symbol,name,imageFileSourcePath,currentPrice,marketCap,tradingVolume,
                    high24h,low24h,priceChange24h,priceChange24hPercentage,circulatingSupply,ath,athChangePercentage,
                    athDate,atl, atlChangePercentage,atlDate));
        }
        listOfCoins=filledList;

    }

    public ArrayList<Coin> getListOfCoinsAndUpdate() {
        connectAndLoadData();
        return listOfCoins;
    }
    public ArrayList<Coin> getListOfCoins() {
        return listOfCoins;
    }




}
