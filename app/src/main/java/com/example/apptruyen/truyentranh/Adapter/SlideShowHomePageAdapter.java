package com.example.apptruyen.truyentranh.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.example.apptruyen.R;
import com.example.apptruyen.truyentranh.object.TruyenTranh;

import java.util.ArrayList;

public class SlideShowHomePageAdapter extends PagerAdapter {
    Context context;
    LayoutInflater layoutInflater;

    public ArrayList<String> urls;
    public SlideShowHomePageAdapter(ArrayList<String> urls, Context ct){
        this.urls = urls;
        this.context = ct;
    }
    @Override
    public int getCount() {
        return urls.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == (RelativeLayout) object;
    }
    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.slide_show, container, false);
        ImageView slideimgView = (ImageView) view.findViewById(R.id.imgSlideShow);

        Glide.with(this.context).load(urls.get(position)).into(slideimgView);
        container.addView(view);

        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((RelativeLayout)object);
    }
}
