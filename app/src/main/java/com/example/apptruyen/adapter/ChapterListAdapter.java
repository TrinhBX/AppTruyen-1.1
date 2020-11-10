package com.example.apptruyen.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.apptruyen.R;
import com.example.apptruyen.entities.Chapter;
import com.example.apptruyen.activity.ChapterContentActivity;

import java.util.List;

public class ChapterListAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private List<Chapter> chapterLists;
    private  int count;

    public ChapterListAdapter(Context context, int layout, List<Chapter> chapterLists, int count) {
        this.context = context;
        this.layout = layout;
        this.chapterLists = chapterLists;
        this.count = count;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public int getLayout() {
        return layout;
    }

    public void setLayout(int layout) {
        this.layout = layout;
    }

    public List<Chapter> getChapterLists() {
        return chapterLists;
    }

    public void setChapterLists(List<Chapter> chapterLists) {
        this.chapterLists = chapterLists;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public int getCount() {
        if(this.count > this.chapterLists.size()){
            return this.chapterLists.size();
        }
        return this.count;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, final ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(this.layout,null);

        final Chapter chapter = chapterLists.get(position);

        final TextView txtChapter = (TextView) convertView.findViewById(R.id.txtChapterName);
        txtChapter.setText(chapterLists.get(position).getChapterName());

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ChapterContentActivity.class);
                intent.putExtra("chapter",chapter);
                context.startActivity(intent);
            }
        });
        return convertView;
    }
}
