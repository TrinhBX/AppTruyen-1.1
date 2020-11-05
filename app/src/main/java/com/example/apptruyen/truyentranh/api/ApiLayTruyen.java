package com.example.apptruyen.truyentranh.api;

import android.os.AsyncTask;

import com.example.apptruyen.truyentranh.interfaces.LayTruyenVe;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class ApiLayTruyen extends AsyncTask<Void, Void, Void> {
    String data;
    LayTruyenVe layTruyenVe;

    public ApiLayTruyen(LayTruyenVe layTruyenVe) {
        this.layTruyenVe = layTruyenVe;
        this.layTruyenVe.batDau();
    }

    @Override
    protected Void doInBackground(Void... voids) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://mis58pm.000webhostapp.com/layTruyen.php")
                .build();
        data = null;
        try {
            Response response = client.newCall(request).execute();
            ResponseBody body = response.body();
            data = body.string();
        }catch (IOException e){
            data = null;
        }
        return null;

    }

    @Override
    protected void onPostExecute(Void aVoid) {
        if(data == null){
            this.layTruyenVe.biLoi();
        }else {
            this.layTruyenVe.ketThuc(data);
        }
    }
}
