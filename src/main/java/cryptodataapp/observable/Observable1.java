package cryptodataapp.observable;

import controllers.CoinScreenController;
import cryptodataapp.currentData.Coin;
import cryptodataapp.observer.Observer;

import java.util.ArrayList;

public class Observable1 implements Subject {

    ArrayList<Observer> ObserverList = new ArrayList<>();
    private Coin coin;

    @Override
    public void registerObserver(Observer observer) {
        ObserverList.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        ObserverList.remove(observer);
    }

    @Override
    public void notifyObservers(CoinScreenController controller) {
        for(int x = 0; x<ObserverList.size(); x++) {
            ObserverList.get(x).update(coin, controller);
        }
    }

    public void dataChanged(CoinScreenController controller){
        notifyObservers(controller);
    }
    public void setData(Coin coin, CoinScreenController controller){
        this.coin = coin;
        dataChanged(controller);
    }
}
