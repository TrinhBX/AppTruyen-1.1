package com.example.apptruyen.truyenchu.adapter;

import android.content.Context;
import android.content.Intent;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

import com.example.apptruyen.R;
import com.example.apptruyen.truyenchu.activity.StoryDetailActivity;
import com.example.apptruyen.truyenchu.activity.VolleySingleton;
import com.example.apptruyen.truyenchu.entities.Story;

import java.util.List;

public class RecycleAdapter extends RecyclerView.Adapter<RecycleAdapter.ViewHolder> {
    private Context context;
    private List<Story> lists;
    private int itemLayout;

    public RecycleAdapter(Context context, List<Story> lists, int itemLayout) {
        this.context = context;
        this.lists = lists;
        this.itemLayout = itemLayout;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View storyView = inflater.inflate(itemLayout, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(storyView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        Story story = lists.get(position);

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

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView txtName;
        public TextView txtAuthor;
        public TextView txtStatus;
        public TextView txtType;
        public TextView txtTotalChapter;
        public ImageView avatar;
        public ViewHolder(View itemView) {
            super(itemView);
            txtName = (TextView) itemView.findViewById(R.id.txtStoryName);
            txtAuthor = (TextView) itemView.findViewById(R.id.txtAuthor);
            txtStatus = (TextView) itemView.findViewById(R.id.txtStatus);
            txtType = (TextView) itemView.findViewById(R.id.txtType);
            txtTotalChapter = (TextView) itemView.findViewById(R.id.txtTotalChapter);
            avatar = (ImageView)itemView.findViewById(R.id.imgAvatar);
        }
    }
}
