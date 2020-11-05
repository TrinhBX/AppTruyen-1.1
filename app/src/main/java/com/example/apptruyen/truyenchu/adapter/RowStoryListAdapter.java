package com.example.apptruyen.truyenchu.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.apptruyen.R;
import com.example.apptruyen.truyenchu.entities.Story;
import com.example.apptruyen.truyenchu.activity.StoryDetailActivity;
import com.example.apptruyen.truyenchu.activity.VolleySingleton;

import java.util.List;


public class RowStoryListAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private List<Story> storyList;
    private int count;

    private static final String GET_TOTAL_CHAPTER_URL = "https://mis58pm.000webhostapp.com/GetCountChapter.php";

    public RowStoryListAdapter(Context context, int layout, List<Story> storyList, int count) {
        this.context = context;
        this.layout = layout;
        this.storyList = storyList;
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
        if(this.count > this.storyList.size()){
            return this.storyList.size();
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(this.layout, null);
//        if(position%2==1){
//            convertView.setBackgroundColor(convertView.getResources().getColor(R.color.colorWhile));
//        }

        //mapping
        TextView txtName = (TextView) convertView.findViewById(R.id.txtStoryName);
        TextView txtAuthor = (TextView) convertView.findViewById(R.id.txtAuthor);
        TextView txtStatus = (TextView) convertView.findViewById(R.id.txtStatus);
        TextView txtType = (TextView) convertView.findViewById(R.id.txtType);
        TextView txtTotalChapter = (TextView) convertView.findViewById(R.id.txtTotalChapter);
        ImageView avatar = (ImageView)convertView.findViewById(R.id.imgAvatar);



        txtName.setText(storyList.get(position).getStoryName());
        txtAuthor.setText("Tác giả: "+ storyList.get(position).getAuthor());
        txtStatus.setText("Tình trạng: "+ storyList.get(position).getStatus());
        txtType.setText("Thể loại: "+storyList.get(position).getType());
        VolleySingleton.getInstance(context).getTotalChapter(GET_TOTAL_CHAPTER_URL,storyList.get(position).getIdStory(),txtTotalChapter);
        VolleySingleton.getInstance(context).setImage(storyList.get(position).getAvatar(),avatar);

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
