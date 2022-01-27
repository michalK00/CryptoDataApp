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
    private static ArrayList<CoinHistorical> historicalDataOfACoinOHLC = new ArrayList<CoinHistorical>();

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
        this.rangeInDays = rangeInDays;
        try{
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder().uri(URI.create("https://api.coingecko.com/api/v3/coins/"+nameOfChosenCoin+"/ohlc?vs_currency=usd&days="+rangeInDays)).build();
            client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                    .thenApply(HttpResponse::body)
                    .thenAccept(CoinHistoricalData::parseohlc)
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
    ArrayList<CoinHistorical> filledList = new ArrayList<>();
    public static void parseohlc(String responseBody){
        ArrayList<CoinHistorical> filledList = new ArrayList<>();

        JSONArray objArray = new JSONArray(responseBody);

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        int interval=0;
        int secondRangeInDays = 0;
        if(rangeInDays==30){
            for (int i = 0; i < 180;i++)
            {
                addDataToList(objArray, i, filledList);
            }
        } else if (rangeInDays==90){
            for (int i = 0; i < 24;i++)
            {
                addDataToList(objArray, i, filledList);
            }
        } else if (rangeInDays==180){
            for (int i = 0; i < 47;i++)
            {
                addDataToList(objArray, i, filledList);
            }
        } else if (rangeInDays==365){
            for (int i = 0; i < 95;i++)
            {
                addDataToList(objArray, i, filledList);
            }
        }

        historicalDataOfACoinOHLC = filledList;
        merge4hCandlesDataIntoDayCandle();
    }
    public static void addDataToList(JSONArray objArray,int i, ArrayList<CoinHistorical> filledList){
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        long unixTimestamp = Long.parseLong(objArray.getJSONArray(i).get(0).toString());
        double dayOpen = Double.parseDouble(objArray.getJSONArray(i).get(1).toString());
        double dayHigh = Double.parseDouble(objArray.getJSONArray(i).get(2).toString());
        double dayLow = Double.parseDouble(objArray.getJSONArray(i).get(3).toString());
        double dayClose = Double.parseDouble(objArray.getJSONArray(i).get(4).toString());
        date.setTime(unixTimestamp);

        filledList.add(new CoinHistorical(dateFormat.format(date),dayOpen,dayHigh,dayLow,dayClose));
    }
    public static void merge4hCandlesDataIntoDayCandle(){
        ArrayList<CoinHistorical> mergedList = new ArrayList<>();
        double dayOpen;
        double dayHigh;
        double dayLow;
        double dayClose;
        String date;

        if(rangeInDays==30){
            for(int i = 0; i<180;){
                date = historicalDataOfACoinOHLC.get(i+5).getDate();
                dayOpen = historicalDataOfACoinOHLC.get(i).getDayOpen();
                dayClose = historicalDataOfACoinOHLC.get(i+5).getDayClose();

                dayHigh= Math.max(Math.max(historicalDataOfACoinOHLC.get(i).getDayHigh(),
                        historicalDataOfACoinOHLC.get(i+1).getDayHigh()),
                        Math.max(historicalDataOfACoinOHLC.get(i+2).getDayHigh(),
                                historicalDataOfACoinOHLC.get(i+3).getDayHigh()));
                dayHigh = Math.max(dayHigh, Math.max(historicalDataOfACoinOHLC.get(i+4).getDayHigh(),historicalDataOfACoinOHLC.get(i+5).getDayHigh()));

                dayLow= Math.min(Math.min(historicalDataOfACoinOHLC.get(i).getDayLow(),
                        historicalDataOfACoinOHLC.get(i+1).getDayLow()),
                        Math.min(historicalDataOfACoinOHLC.get(i+2).getDayLow(),
                                historicalDataOfACoinOHLC.get(i+3).getDayLow()));
                dayLow = Math.min(dayLow, Math.max(historicalDataOfACoinOHLC.get(i+4).getDayLow(),historicalDataOfACoinOHLC.get(i+5).getDayLow()));


                i+=6;
                mergedList.add(new CoinHistorical(date,dayOpen,dayHigh,dayLow,dayClose));
            }
            historicalDataOfACoinOHLC=mergedList;
        }
    }


    public ArrayList<CoinHistorical> getHistoricalDataOfACoin() {
        return historicalDataOfACoin;
    }

    public ArrayList<CoinHistorical> getHistoricalDataOfACoinOHLC() {
        return historicalDataOfACoinOHLC;
    }
}
