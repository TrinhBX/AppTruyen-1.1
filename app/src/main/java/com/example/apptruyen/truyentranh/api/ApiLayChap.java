package com.example.apptruyen.truyentranh.api;

import android.os.AsyncTask;

import com.example.apptruyen.truyentranh.interfaces.LayChapVe;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class ApiLayChap extends AsyncTask<Void, Void, Void> {
    String data;
    LayChapVe layChapVe;
    String idTruyen;

    public ApiLayChap(LayChapVe layChapVe, String idTruyen) {
        this.layChapVe = layChapVe;
        this.layChapVe.batDau();
        this.idTruyen = idTruyen;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://mis58pm.000webhostapp.com/layChap.php?id="+idTruyen)
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
            this.layChapVe.biLoi();
        }else {
            this.layChapVe.ketThuc(data);
        }
    }
}
