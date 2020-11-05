package com.example.apptruyen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.apptruyen.truyenchu.activity.MainActivity;
import com.example.apptruyen.truyentranh.Adapter.SlideShowHomePageAdapter;
import com.example.apptruyen.truyentranh.Main2Activity;
import com.example.apptruyen.truyentranh.api.ApiLayAnhSlide;
import com.example.apptruyen.truyentranh.interfaces.LayAnhSlide;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class HomePageActivity extends AppCompatActivity implements LayAnhSlide {
    LinearLayout mDotLayout;
    ViewPager slideViewPager;
    private TextView[] mDots;
    ArrayList<String> arrURLAnhSlide;

    //Handle slide show
    private Handler mHandler = new Handler();
    int mCurrentPage = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        anhxa();
        init();
        setup();
        setClick();
        new ApiLayAnhSlide(this, "1").execute();
        mHandler.postDelayed(mRunnable, 4000);
    }

    void anhxa(){
        mDotLayout = findViewById(R.id.dotsLayout);
        slideViewPager = findViewById(R.id.slideViewPage);
    }
    void init(){
        addDotsIndicator(0);
        slideViewPager.addOnPageChangeListener(viewListener);
    }
    void setup(){

    }
    void setClick(){}
    public void clickTruyenTranh(View view) {
        startActivity(new Intent(HomePageActivity.this, Main2Activity.class));
    }

    public void clickTruyenChu(View view) {
        startActivity(new Intent(HomePageActivity.this, MainActivity.class));
    }

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
    private Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            slideViewPager.setCurrentItem(mCurrentPage +=1);
            mHandler.postDelayed(this, 4000);
        }
    };

    @Override
    public void batDau() {
        Toast.makeText(this, "Loading...", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void ketThuc(String data) {
        arrURLAnhSlide = new ArrayList<>();
        try {
            JSONArray arr = new JSONArray(data);
            for (int i=0; i<arr.length(); i++){
                arrURLAnhSlide.add(arr.getString(i));
            }
            SlideShowHomePageAdapter imgSlideShowHomePage = new SlideShowHomePageAdapter(arrURLAnhSlide, this);
            slideViewPager.setAdapter(imgSlideShowHomePage);
        }catch (JSONException e){

        }

    }

    @Override
    public void biLoi() {
        Toast.makeText(this, "Lỗi kết nối", Toast.LENGTH_SHORT).show();
    }


}
