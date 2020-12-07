package com.example.apptruyen.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.apptruyen.R;
import com.example.apptruyen.activity.ChapterContentActivity;
import com.example.apptruyen.activity.StoryDetailActivity;
import com.example.apptruyen.entities.Chapter;
import com.example.apptruyen.interfaces.ILoadMore;
import com.example.apptruyen.utils.TypeItems;
import com.example.apptruyen.utils.VolleySingleton;
import com.example.apptruyen.entities.Story;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
    private Context context;
    private List<Object> lists;
    private int itemLayout;
    private String itemTag;
    private TypeItems typeItems;


//    public RecyclerAdapter(Context context, List<Object> lists, int itemLayout, String itemTag) {
//        this.context = context;
//        this.lists = lists;
//        this.itemLayout = itemLayout;
//        this.itemTag = itemTag;
//    }
    public RecyclerAdapter(Context context, List<Object> lists, int itemLayout, TypeItems typeItems) {
        this.context = context;
        this.lists = lists;
        this.itemLayout = itemLayout;
        this.typeItems = typeItems;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View itemView = inflater.inflate(itemLayout, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(itemView,typeItems);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        //Story story = lists.get(position);
        //Log.e("A", TypeItems.CARD_STORY+"");
        switch (typeItems){
            case ROW_STORY_FULL:
                setRowFull(holder,(Story)lists.get(position));
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
            case ROW_STORY_COLLAPSE:
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
            case ROW_CHAPTER_DETAIL:
                setChapterList(holder,(Chapter)lists.get(position));
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Chapter chapter = (Chapter)lists.get(position);
                        Intent intent = new Intent(context, ChapterContentActivity.class);
                        intent.putExtra("chapter",chapter);
                        context.startActivity(intent);
                    }
                });
                break;
            case ROW_CHAPTER_CONTENT:
                setChapterList(holder,(Chapter)lists.get(position));
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Chapter chapter = (Chapter)lists.get(position);
                        Intent intent = new Intent(context, ChapterContentActivity.class);
                        intent.putExtra("chapter",chapter);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        context.startActivity(intent);
                    }
                });
                break;
            case CARD_STORY:
                setCardView(holder,(Story)lists.get(position));
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(context, StoryDetailActivity.class);
                        Story story = (Story)lists.get(position);
                        intent.putExtra("story",story);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        context.startActivity(intent);
                    }
                });
                break;
        }

    }

    @Override
    public int getItemCount() {
        return lists.size();
    }

    private void setRowFull(ViewHolder holder,Story story){
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
    }

    private void setCardView(ViewHolder cardView, Story story ){
        ImageView imgAvatarCardView = (ImageView) cardView.imgAvatarCardView;
        VolleySingleton.getInstance(context).setImage(story.getAvatar(),imgAvatarCardView);

        TextView txtNameCardView = (TextView)cardView.txtNameCardView;
        txtNameCardView.setText(story.getStoryName());
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

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

        //View of CardView
        public ImageView imgAvatarCardView;
        public TextView txtNameCardView;

        public ViewHolder(View itemView,TypeItems typeItems) {
            super(itemView);
            switch (typeItems){
                case ROW_STORY_FULL:
                    mappingRow(itemView);
                    break;
                case ROW_STORY_COLLAPSE:
                    mappingRowCollapse(itemView);
                    break;
                case ROW_CHAPTER_DETAIL:
                case ROW_CHAPTER_CONTENT:
                    mappingChapterList(itemView);
                    break;
                case CARD_STORY:
                    mappingCardView(itemView);
                    break;
            }

        }
        private void mappingRow(@NotNull View itemView){
            txtName = (TextView) itemView.findViewById(R.id.txtStoryName);
            txtAuthor = (TextView) itemView.findViewById(R.id.txtAuthor);
            txtStatus = (TextView) itemView.findViewById(R.id.txtStatus);
            txtType = (TextView) itemView.findViewById(R.id.txtType);
            txtTotalChapter = (TextView) itemView.findViewById(R.id.txtTotalChapter);
            avatar = (ImageView)itemView.findViewById(R.id.imgAvatar);
        }
        private void mappingRowCollapse(@NotNull View itemView){
            txtSearchStoryName =(TextView) itemView.findViewById(R.id.txt_search_story_name);
            txtSearchAuthor = (TextView) itemView.findViewById(R.id.txt_search_author);
        }

        private void mappingChapterList(@NotNull View itemView){
            txtChapterName = (TextView) itemView.findViewById((R.id.txtChapterName));
        }

        private void mappingCardView(@NotNull View itemView){
            imgAvatarCardView = (ImageView) itemView.findViewById(R.id.imgAvatarCardView);
            txtNameCardView =(TextView) itemView.findViewById(R.id.txtNameCardView);
        }
    }
}
