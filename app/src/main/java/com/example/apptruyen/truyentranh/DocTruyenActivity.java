package com.example.apptruyen.truyentranh;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.apptruyen.R;
import com.example.apptruyen.truyentranh.api.ApiLayAnh;
import com.example.apptruyen.truyentranh.interfaces.LayAnhVe;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class DocTruyenActivity extends AppCompatActivity implements LayAnhVe {
    ImageView imgAnh;
    TextView txvSoTrang;
    ArrayList<String> arrURLAnh;
    int soTrang, soTrangDangDoc;
    String idChap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doc_truyen);
        init();
        anhxa();
        setUp();
        setClick();
        new ApiLayAnh(this, idChap).execute();
    }

    private void init(){
        Bundle b = getIntent().getBundleExtra("data");
        idChap =  b.getString("idChap");
    }
    private void anhxa(){
        imgAnh = findViewById(R.id.imgAnh);
        txvSoTrang = findViewById(R.id.txvSoTrang);
    }
    private void setUp(){
        //docTheoTrang(0);
    }
    private void setClick(){

    }
    public void left(View view) {
        docTheoTrang(-1);
    }

    public void right(View view) {
        docTheoTrang(1);
    }

    private void docTheoTrang(int i){
        soTrangDangDoc += i;
        if(soTrangDangDoc == 0){
            soTrangDangDoc = 1;
        }
        if(soTrangDangDoc > soTrang){
            soTrangDangDoc = soTrang;
        }
        txvSoTrang.setText(soTrangDangDoc + " / "+soTrang);
        Glide.with(this).load(arrURLAnh.get(soTrangDangDoc-1)).into(imgAnh);
    }

    @Override
    public void batDau() {
        Toast.makeText(this, "Loading...", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void ketThuc(String data) {
        //arrURLAnh.clear();
        arrURLAnh = new ArrayList<>();
        try {
            JSONArray arr = new JSONArray(data);
            for (int i=0; i<arr.length(); i++){
                arrURLAnh.add(arr.getString(i));
            }
            soTrangDangDoc = 1;
            soTrang = arrURLAnh.size();
            docTheoTrang(0);
        }catch (JSONException e){

        }

    }

    @Override
    public void biLoi() {
        Toast.makeText(this, "Lỗi kết nối", Toast.LENGTH_SHORT).show();
    }
}
