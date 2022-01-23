package cryptodataapp.historicalData;

import org.json.JSONArray;
import org.json.JSONObject;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class CoinHistoricalData {

    private static ArrayList<CoinHistorical> historicalDataOfACoin = new ArrayList<CoinHistorical>();

    public CoinHistoricalData(int rangeInInDays, String nameOfChosenCoin) {
        connectAndLoadData(rangeInInDays, nameOfChosenCoin);
    }
    private static int rangeInDays;
    public void connectAndLoadData(int rangeInDays,String nameOfChosenCoin){
        this.rangeInDays = rangeInDays;
        try{
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder().uri(URI.create("https://api.coingecko.com/api/v3/coins/"+nameOfChosenCoin+"/market_chart?vs_currency=usd&days="+rangeInDays+"&interval=daily")).build();
            client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                    .thenApply(HttpResponse::body)
                    .thenAccept(CoinHistoricalData::parse)
                    .join();
        } catch (RuntimeException e){
            e.printStackTrace();
        }


    }
    public static void parse(String responseBody){

        ArrayList<CoinHistorical> filledList = new ArrayList<>();
        JSONObject obj = new JSONObject(responseBody);

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();

        for (int i = 0; i < rangeInDays; i++)
        {
            long unixTimestamp = Long.parseLong(obj.getJSONArray("prices").getJSONArray(i).get(0).toString());
            double price = Math.round(Double.parseDouble(obj.getJSONArray("prices").getJSONArray(i).get(1).toString())*100.0)/100.0;
            date.setTime(unixTimestamp);

            filledList.add(new CoinHistorical(price, dateFormat.format(date)));
        }

        historicalDataOfACoin=filledList;

    }


    public ArrayList<CoinHistorical> getHistoricalDataOfACoin() {
        return historicalDataOfACoin;
    }


}
