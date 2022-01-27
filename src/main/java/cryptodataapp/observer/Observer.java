package cryptodataapp.observer;

import controllers.CoinScreenController;
import cryptodataapp.currentData.Coin;

public interface Observer {
    void update(Coin coin, CoinScreenController controller);
}
