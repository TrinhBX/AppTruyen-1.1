package com.example.apptruyen.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.util.List;

public class Validator {
    public static boolean checkInternetConnection(Context context){
        ConnectivityManager connManager =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connManager.getActiveNetworkInfo();
        if (networkInfo == null || !networkInfo.isConnected() || !networkInfo.isAvailable()) {
            return false;
        }
        return true;
    }
    public static String cutString(String string){
        if(string.length()>25){
            int i = 25;
            while (true){
                if(string.charAt(i)!= ' '){
                    i--;
                }else {
                    string = string.substring(0,i)+"\n"+string.substring(i+1);
                    break;
                }
            }
        }
        return string;
    }
}
