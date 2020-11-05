package com.example.apptruyen.truyentranh;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.apptruyen.R;
import com.example.apptruyen.truyentranh.Adapter.SlideShowAdapter;
import com.example.apptruyen.truyentranh.Adapter.TruyenTranhAdapter;
import com.example.apptruyen.truyentranh.api.ApiLayTruyen;
import com.example.apptruyen.truyentranh.interfaces.LayTruyenVe;
import com.example.apptruyen.truyentranh.object.TruyenTranh;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Main2Activity extends AppCompatActivity implements LayTruyenVe {
    GridView gdvDSTruyen;
    EditText edtTimKiem;
    TruyenTranhAdapter adapter;
    ArrayList<TruyenTranh> truyenTranhArrayList;
    LinearLayout mDotLayout;
    ViewPager slideViewPager;
    private TextView[] mDots;
    private SlideShowAdapter slideShowAdapter;

    //Handle slide show
    private Handler mHandler = new Handler();
    int mCurrentPage = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        anhXa();
        init();
        setClick();
        setUp();
        new ApiLayTruyen(this).execute();
        mHandler.postDelayed(mRunnable, 4000);
    }
    private void anhXa(){
        gdvDSTruyen = findViewById(R.id.gdvDSTruyen);
        edtTimKiem = findViewById(R.id.edtTimKiem);
        mDotLayout = findViewById(R.id.dotsLayout);
        slideViewPager = findViewById(R.id.slideViewPage);
    }

    private void init(){
        truyenTranhArrayList = new ArrayList<>();
        adapter = new TruyenTranhAdapter(this, 0, truyenTranhArrayList);
        slideShowAdapter = new SlideShowAdapter(this);

        addDotsIndicator(0);
        slideViewPager.addOnPageChangeListener(viewListener);
    }

    private void setUp(){
        //gdvDSTruyen.setAdapter(adapter);
        slideViewPager.setAdapter(slideShowAdapter);
        /*btnRight.setText(Html.fromHtml("&#10151;"));
        btnLeft.setText(Html.fromHtml("&#10151;"));*/
    }

    private void setClick(){
        //Bắt sự kiện text change của view tìm kiếm
        edtTimKiem.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String s = edtTimKiem.getText().toString();
                adapter.sortTruyen(s);
            }
        });

        //Bắt sự kiện click vào item chuyển trang, gửi dữ liệu bằng bundle
        gdvDSTruyen.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //mHandler.removeCallbacks(mRunnable);
                TruyenTranh truyentranh = truyenTranhArrayList.get(position);
                Bundle b = new Bundle();
                b.putSerializable("truyen", truyentranh);
                Intent intent = new Intent(Main2Activity.this, ChapActivity.class);
                intent.putExtra("data", b);
                startActivity(intent);
            }
        });
    }

    //start
    @Override
    public void batDau() {
        Toast.makeText(this, "Đang lấy về", Toast.LENGTH_SHORT).show();
    }

    //Định nghĩa hàm thực thi lấy dữ liệu json
    @Override
    public void ketThuc(String data) {
        try {
            truyenTranhArrayList.clear();
            JSONArray arr = new JSONArray(data);
            for (int i =0;i<arr.length();i++){
                JSONObject o = arr.getJSONObject(i);
                truyenTranhArrayList.add(new TruyenTranh(o));
            }
            adapter = new TruyenTranhAdapter(this, 0, truyenTranhArrayList);
            gdvDSTruyen.setAdapter(adapter);
        }catch (JSONException e){

        }
    }

    //error
    @Override
    public void biLoi() {
        Toast.makeText(this, "Lỗi kết nối", Toast.LENGTH_SHORT).show();
    }

    //Hàm bắt sự kiện click của update
    public void update(View view) {
        new ApiLayTruyen(this).execute();
    }

    //Hàm tạo ra dots slide
    public void addDotsIndicator(int position){
        mDots = new TextView[5];
        mDotLayout.removeAllViews();
        for (int i = 0; i < mDots.length; i++){
            mDots[i] = new TextView(this);
            mDots[i].setText(Html.fromHtml("&#8226;"));
            mDots[i].setTextSize(35);
            mDots[i].setTextColor(getResources().getColor(R.color.colorTransparentWhite));

            mDotLayout.addView(mDots[i]);
        }

        if(mDots.length > 0){
            mDots[position].setTextColor(getResources().getColor(R.color.colorAccent));
        }
    }

    // Bắt sự kiện Slide thay đổi
    ViewPager.OnPageChangeListener viewListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            addDotsIndicator(position);
            mCurrentPage = position;
            if(mCurrentPage >= 4){
                mCurrentPage = -1;
            }

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    //Hàm runable để handle slide
    private Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            slideViewPager.setCurrentItem(mCurrentPage +=1);
            mHandler.postDelayed(this, 4000);
        }
    };
}
