package cryptodataapp.wallet;

import java.io.*;
import java.util.ArrayList;

public class WalletCoinsSerialize{

    public static ArrayList<WalletCoin> arrayList;


    public void save(){

            try{

                ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("listOfCoinsInWallet.ser"));
                out.writeObject(arrayList);
                out.close();

                System.out.println("Serialized");

            }catch (IOException e){
                e.printStackTrace();
            }

    }
    public ArrayList<WalletCoin> read(){

        ArrayList<WalletCoin> simpleArrayList;
        simpleArrayList = null;

        try {
            ObjectInputStream read = new ObjectInputStream(new FileInputStream("listOfCoinsInWallet.ser"));

            simpleArrayList = (ArrayList<WalletCoin>) read.readObject();

            read.close();
            System.out.println("Deserialized");

        }catch (EOFException eofe){

        } catch (IOException ioe)
        {
            ioe.printStackTrace();
        }
        catch (ClassNotFoundException c)
        {
            System.out.println("Class not found");
            c.printStackTrace();
        }
        return simpleArrayList;

    }

    public ArrayList<WalletCoin> getWalletCoinArrayList() {
        return arrayList;
    }

    public void setWalletCoinArrayList(ArrayList<WalletCoin> walletCoinArrayList) {
        arrayList = walletCoinArrayList;
    }

    public ArrayList<WalletCoin> readAndGetWalletCoinArrayList(){
        if(read()!=null){
            arrayList = read();

        }
        return arrayList;
    }
}
