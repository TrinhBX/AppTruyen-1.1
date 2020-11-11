package com.example.apptruyen.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.apptruyen.R;
import com.example.apptruyen.activity.StoryDetailActivity;
import com.example.apptruyen.utils.VolleySingleton;
import com.example.apptruyen.entities.Story;

import java.util.List;

public class RecycleAdapter extends RecyclerView.Adapter<RecycleAdapter.ViewHolder> {
    private Context context;
    private List<Story> lists;
    private int itemLayout;
    private String tag;

    public RecycleAdapter(Context context, List<Story> lists, int itemLayout,String tag) {
        this.context = context;
        this.lists = lists;
        this.itemLayout = itemLayout;
        this.tag = tag;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View storyView = inflater.inflate(itemLayout, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(storyView,tag);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        //Story story = lists.get(position);
        switch (tag){
            case "ROW":
                setRow(holder,lists.get(position));
                break;
            case "ROW_COLLAPSE":
                setRowCollapse(holder,lists.get(position));
                break;
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, StoryDetailActivity.class);
                Story story = (Story)lists.get(position);
                intent.putExtra("story",story);
                context.startActivity(intent);
            }
        });
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

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView txtName;
        public TextView txtAuthor;
        public TextView txtStatus;
        public TextView txtType;
        public TextView txtTotalChapter;
        public ImageView avatar;
        public ViewHolder(View itemView,String tag) {
            super(itemView);
            switch (tag){
                case "ROW":
                    mappingRow();
                    break;
                case "ROW_COLLAPSE":
                    mappingRowCollapse();
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

        }
    }
}
