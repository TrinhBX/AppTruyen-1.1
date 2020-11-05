package com.example.apptruyen.truyentranh;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.apptruyen.R;
import com.example.apptruyen.truyentranh.Adapter.ChapTruyenAdapter;
import com.example.apptruyen.truyentranh.api.ApiLayChap;
import com.example.apptruyen.truyentranh.interfaces.LayChapVe;
import com.example.apptruyen.truyentranh.object.ChapTruyen;
import com.example.apptruyen.truyentranh.object.TruyenTranh;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ChapActivity extends AppCompatActivity implements LayChapVe {

    TextView txtTenTruyens;
    ImageView imgAnhTruyens;
    TruyenTranh truyentranh;
    ListView lsvDSChap;
    ArrayList<ChapTruyen> arrChap;
    ChapTruyenAdapter chapTruyenAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chap);
        init();
        anhxa();
        setup();
        setClick();

        new ApiLayChap(this, truyentranh.getId()).execute();
    }

    private void init(){
        Bundle b = getIntent().getBundleExtra("data");
        truyentranh = (TruyenTranh) b.getSerializable("truyen");

        arrChap = new ArrayList<>();
        // arrChap.add(new ChapTruyen("Chapter: "+ i,"19/04/2020" ));
        chapTruyenAdapter = new ChapTruyenAdapter(this,0, arrChap);

    }
    private void anhxa(){
        txtTenTruyens = findViewById(R.id.txvTenTruyens);
        imgAnhTruyens = findViewById(R.id.imgAnhTruyens);
        lsvDSChap = findViewById(R.id.lsvDSChap);
    }
    private void setup(){
        txtTenTruyens.setText(truyentranh.getTenTruyen());
        Glide.with(this).load(truyentranh.getLinkAnh()).into(imgAnhTruyens);

        lsvDSChap.setAdapter(chapTruyenAdapter);
    }
    private void setClick(){
        lsvDSChap.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle b = new Bundle();
                b.putString("idChap", arrChap.get(position).getId());
                Intent intent = new Intent(ChapActivity.this, DocTruyenActivity.class);
                intent.putExtra("data", b);
                startActivity(intent);
            }
        });
    }

    @Override
    public void batDau() {
        Toast.makeText(this, "Đang lấy về", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void ketThuc(String data) {
        try {
            arrChap.clear();
            JSONArray arr = new JSONArray(data);
            for (int i =0;i<arr.length();i++){
                JSONObject o = arr.getJSONObject(i);
                arrChap.add(new ChapTruyen(o));
            }
            chapTruyenAdapter = new ChapTruyenAdapter(this, 0, arrChap);
            lsvDSChap.setAdapter(chapTruyenAdapter);
        }catch (JSONException e){

        }
    }

    @Override
    public void biLoi() {
        Toast.makeText(this, "Lỗi kết nối", Toast.LENGTH_SHORT).show();
    }
}
