package com.example.apptruyen.truyentranh.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.apptruyen.R;
import com.example.apptruyen.truyentranh.object.ChapTruyen;

import java.util.ArrayList;
import java.util.List;

public class ChapTruyenAdapter extends ArrayAdapter<ChapTruyen> {
    private Context ct;
    private ArrayList<ChapTruyen> arr;
    public ChapTruyenAdapter(@NonNull Context context, int resource, @NonNull List<ChapTruyen> objects) {
        super(context, resource, objects);
        this.ct = context;
        this.arr = new ArrayList<>(objects);

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater)ct.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_chap_truyen, null);
        }
        if(arr.size() > 0){
            TextView txvTenChaps, txvNgayNhaps;
            txvTenChaps = convertView.findViewById(R.id.txvTenChaps);
            txvNgayNhaps = convertView.findViewById(R.id.txvNgayNhap);

            ChapTruyen chaptruyen = arr.get(position);
            txvTenChaps.setText(chaptruyen.getTenChap());
            txvNgayNhaps.setText(chaptruyen.getNgayDang());
        }
        return  convertView;
    }
}
