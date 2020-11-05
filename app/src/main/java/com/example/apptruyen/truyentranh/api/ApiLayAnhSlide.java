package com.example.apptruyen.truyentranh.api;

import android.os.AsyncTask;

import com.example.apptruyen.truyentranh.interfaces.LayAnhSlide;
import com.example.apptruyen.truyentranh.interfaces.LayAnhVe;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class ApiLayAnhSlide extends AsyncTask<Void, Void, Void> {
    String data;
    LayAnhSlide layAnhSlide;
    String idAnh;

    public ApiLayAnhSlide(LayAnhSlide layAnhSlide, String idAnh) {
        this.layAnhSlide = layAnhSlide;
        this.layAnhSlide.batDau();
        this.idAnh = idAnh;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://mis58pm.000webhostapp.com/layAnhSlide.php?idAnh="+idAnh)
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
            this.layAnhSlide.biLoi();
        }else {
            this.layAnhSlide.ketThuc(data);
        }
    }
}
