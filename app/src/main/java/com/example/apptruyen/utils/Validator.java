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
}
