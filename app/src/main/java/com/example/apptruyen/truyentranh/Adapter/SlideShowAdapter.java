package com.example.apptruyen.truyentranh.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.example.apptruyen.R;

public class SlideShowAdapter extends PagerAdapter {
    Context context;
    LayoutInflater layoutInflater;

    public SlideShowAdapter(Context context){
        this.context = context;
    }
    public int [] slide_images ={
            R.drawable.onepiece,
            R.drawable.naruto,
            R.drawable.deathnote,
            R.drawable.itachi,
            R.drawable.zoro
    };
    public int getCount(){
        return slide_images.length;
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
        slideimgView.setImageResource(slide_images[position]);

        container.addView(view);

        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((RelativeLayout)object);
    }
}
