package cryptodataapp.wallet;

import java.io.*;
import java.util.ArrayList;

public class WalletCoinsSerialize {

    public static ArrayList<WalletCoin> walletCoinArrayList;


    public void save(){
        try(ObjectOutputStream save = new ObjectOutputStream(new FileOutputStream("listOfCoinsInWallet.ser"))){

            save.writeObject(walletCoinArrayList);

        }catch (IOException e){
            e.printStackTrace();
        }
    }
    public void read(){
        try (ObjectInputStream read = new ObjectInputStream(new FileInputStream("listOfCoinsInWallet.ser"))){

            walletCoinArrayList = (ArrayList<WalletCoin>) read.readObject();

        }catch (IOException e){
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<WalletCoin> getWalletCoinArrayList() {
        return walletCoinArrayList;
    }

    public void setWalletCoinArrayList(ArrayList<WalletCoin> walletCoinArrayList) {
        WalletCoinsSerialize.walletCoinArrayList = walletCoinArrayList;
    }
}
