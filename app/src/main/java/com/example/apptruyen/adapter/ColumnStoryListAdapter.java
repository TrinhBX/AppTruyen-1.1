package com.example.apptruyen.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.apptruyen.R;
import com.example.apptruyen.entities.Story;
import com.example.apptruyen.activity.StoryDetailActivity;
import com.example.apptruyen.utils.VolleySingleton;

import java.util.List;

public class ColumnStoryListAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private List<Story> storyList;
    private int count;

    public ColumnStoryListAdapter(Context context, int layout, List<Story> storyList, int count) {
        this.context = context;
        this.layout = layout;
        this.storyList = storyList;
        this.count =count;
    }

    public List<Story> getStoryList() {
        return storyList;
    }

    public void setStoryList(List<Story> storyList) {
        this.storyList = storyList;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public int getCount() {
        if(this.count>this.storyList.size())
            return storyList.size();
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.card_item,null);

        //Anh xa
        //ImageView avatar = (ImageView) convertView.findViewById(R.id.imgColAvatar);
        //TextView txtColStoryName = (TextView) convertView.findViewById(R.id.txtColStoryName);

        //Set value
        //imageView.setImageResource(storyList.get(position).getImage());
        //txtColStoryName.setText(storyList.get(position).getStoryName());
        //VolleySingleton.getInstance(context).setImage(storyList.get(position).getAvatar(),avatar);

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, StoryDetailActivity.class);
                Story story = (Story)storyList.get(position);
                intent.putExtra("story",story);
                context.startActivity(intent);
            }
        });

        return convertView;
    }
}
