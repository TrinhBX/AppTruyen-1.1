package com.example.apptruyen.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.apptruyen.R;
import com.example.apptruyen.activity.ChapterContentActivity;
import com.example.apptruyen.activity.StoryDetailActivity;
import com.example.apptruyen.entities.Chapter;
import com.example.apptruyen.utils.VolleySingleton;
import com.example.apptruyen.entities.Story;

import java.util.List;

public class RecycleAdapter extends RecyclerView.Adapter<RecycleAdapter.ViewHolder> {
    private Context context;
    private List<Object> lists;
    private int itemLayout;
    private String itemTag;
    private List<Object> chapterList;

    public RecycleAdapter(Context context, List<Object> lists, int itemLayout,String itemTag) {
        this.context = context;
        this.lists = lists;
        this.itemLayout = itemLayout;
        this.itemTag = itemTag;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View storyView = inflater.inflate(itemLayout, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(storyView,itemTag);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        //Story story = lists.get(position);
        switch (itemTag){
            case "ROW_FULL":
                setRow(holder,(Story)lists.get(position));
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(context, StoryDetailActivity.class);
                        Story story = (Story)lists.get(position);
                        intent.putExtra("story",story);
                        context.startActivity(intent);
                    }
                });
                break;
            case "ROW_COLLAPSE":
                setRowCollapse(holder,(Story)lists.get(position));
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(context, StoryDetailActivity.class);
                        Story story = (Story)lists.get(position);
                        intent.putExtra("story",story);
                        context.startActivity(intent);
                    }
                });
                break;
            case "CHAPTER_LIST":
                setChapterList(holder,(Chapter)lists.get(position));
                break;
        }

    }

    @Override
    public int getItemCount() {
        return lists.size();
    }

    private void setRow(ViewHolder holder,Story story){
        TextView txtName = (TextView) holder.txtName;
        txtName.setText(story.getStoryName());
        TextView txtAuthor = (TextView) holder.txtAuthor;
        txtAuthor.setText(story.getAuthor());
        TextView txtStatus = (TextView) holder.txtStatus;
        txtStatus.setText(story.getStatus());
        TextView txtType = (TextView) holder.txtType;
        txtType.setText(story.getType());
        TextView txtTotalChapter = (TextView) holder.txtTotalChapter;
        txtTotalChapter.setText(""+story.getNumberOfChapter());
        ImageView avatar = (ImageView)holder.avatar;
        VolleySingleton.getInstance(context).setImage(story.getAvatar(),avatar);
    }
    private void setRowCollapse(ViewHolder holder,Story story){
        TextView txtSearchStoryName = (TextView)holder.txtSearchStoryName;
        txtSearchStoryName.setText(story.getStoryName());
        TextView txtSearchAuthor = (TextView)holder.txtSearchAuthor;
        txtSearchAuthor.setText(story.getAuthor());
    }

    private void setChapterList(ViewHolder holder, final Chapter chapter){
        TextView txtChapterName = (TextView) holder.txtChapterName;
        txtChapterName.setText(chapter.getChapterName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("CHAPTER", "id Story = "+chapter.getIdStory()+ ", id chapter = "+chapter.getIdChapter());
                Intent intent = new Intent(context, ChapterContentActivity.class);
                intent.putExtra("chapter",chapter);
                context.startActivity(intent);
            }
        });
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        //View of ROW_FULL
        public TextView txtName;
        public TextView txtAuthor;
        public TextView txtStatus;
        public TextView txtType;
        public TextView txtTotalChapter;
        public ImageView avatar;

        //View of ROW_COLLAPSE
        public TextView txtSearchStoryName;
        public TextView txtSearchAuthor;

        //View of CHAPTER_LIST
        public TextView txtChapterName;

        public ViewHolder(View itemView,String itemTag) {
            super(itemView);
            switch (itemTag){
                case "ROW_FULL":
                    mappingRow();
                    break;
                case "ROW_COLLAPSE":
                    mappingRowCollapse();
                    break;
                case "CHAPTER_LIST":
                    mappingChapterList();
                    break;
            }

        }
        private void mappingRow(){
            txtName = (TextView) itemView.findViewById(R.id.txtStoryName);
            txtAuthor = (TextView) itemView.findViewById(R.id.txtAuthor);
            txtStatus = (TextView) itemView.findViewById(R.id.txtStatus);
            txtType = (TextView) itemView.findViewById(R.id.txtType);
            txtTotalChapter = (TextView) itemView.findViewById(R.id.txtTotalChapter);
            avatar = (ImageView)itemView.findViewById(R.id.imgAvatar);
        }
        private void mappingRowCollapse(){
            txtSearchStoryName =(TextView) itemView.findViewById(R.id.txt_search_story_name);
            txtSearchAuthor = (TextView) itemView.findViewById(R.id.txt_search_author);
        }

        private void mappingChapterList(){
            txtChapterName = (TextView) itemView.findViewById((R.id.txtChapterName));
        }
    }
}
