package cryptodataapp.observable;


import controllers.CoinScreenController;
import cryptodataapp.observer.Observer;

public interface Subject {

    public void registerObserver(Observer observer);
    public void removeObserver(Observer observer);
    public void notifyObservers(CoinScreenController controller);

}
