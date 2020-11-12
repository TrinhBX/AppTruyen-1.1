package com.example.apptruyen.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.apptruyen.R;
import com.example.apptruyen.adapter.ColumnStoryListAdapter;
import com.example.apptruyen.fragment.ChapterListFragment;
import com.example.apptruyen.entities.Story;
import com.example.apptruyen.utils.VolleySingleton;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StoryDetailActivity extends AppCompatActivity {
    private TextView txtStoryName,txtAuthor,txtStatus,txtType, txtChapterTotal;
    private TextView txtLatestChapter, txtReviewContent;
    private ImageView avatar;
    private LinearLayout chapterListLayout;
    private Toolbar toolbar;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private static final String URL = "https://mis58pm.000webhostapp.com/GetChapterList.php";
    private static final String TAG = "FRAGMENT_CHAPTER_LIST";
    private ImageButton btnBookmark;
    private boolean isBookmark = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story_detail);

        Intent intent = getIntent();
        final Story story =(Story)intent.getSerializableExtra("story");

        mapping();
        setSupportActionBar(toolbar);

        if(story!=null){
            collapsingToolbarLayout.setTitle(story.getStoryName()); //set name story
            //txtStoryName.setText(story.getStoryName());
            txtAuthor.setText("Tác giả: "+story.getAuthor());
            txtStatus.setText("Tình Trạng: "+story.getStatus());
            txtType.setText("Thể loại: "+story.getType());
            txtReviewContent.setText(story.getReview());
            txtChapterTotal.setText("Số chương:"+story.getNumberOfChapter());
            //avatar.setImageResource(R.drawable.image_broken);
            VolleySingleton.getInstance(StoryDetailActivity.this).setImage(story.getAvatar(),avatar);

        }

        chapterListLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle sendBundle = new Bundle();
                sendBundle.putInt("idStory",story.getIdStory());

                ChapterListFragment chapterListFragment = new ChapterListFragment();
                chapterListFragment.setArguments(sendBundle);
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction().add(R.id.listChapter,chapterListFragment,TAG);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        btnBookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String s = "";
                if(!isBookmark){
                    s= "Bookmarking";
                    btnBookmark.setImageResource(R.drawable.on_bookmark_24);
                    isBookmark = true;
                } else {
                    s= "Un-Bookmarking";
                    btnBookmark.setImageResource(R.drawable.bookmark_24);
                    isBookmark = false;
                }
                Toast.makeText(StoryDetailActivity.this,s,Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void mapping(){
        txtStoryName = (TextView) findViewById(R.id.nameStory);
        txtAuthor = (TextView)findViewById(R.id.txtAuthor);
        txtStatus = (TextView)findViewById(R.id.txtStatus);
        txtType = (TextView)findViewById(R.id.txtType);
        txtChapterTotal =(TextView) findViewById(R.id.txtChapterTotal);
        txtLatestChapter = (TextView)findViewById(R.id.txtLatestChapter);
        txtReviewContent = (TextView)findViewById(R.id.txtReviewContent);
        chapterListLayout =(LinearLayout)findViewById(R.id.chapterListLayout);
        avatar = (ImageView)findViewById(R.id.imgAvatar);
        toolbar = (Toolbar) findViewById(R.id.storyDetailToolbar);
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapse);
        btnBookmark = (ImageButton) findViewById(R.id.btn_bookmark);
    }



    @Override
    protected void onPause() {
        super.onPause();
        ChapterListFragment chapterListFragment = (ChapterListFragment)getSupportFragmentManager().findFragmentById(R.id.listChapter);
        if(chapterListFragment !=null){
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction().remove(chapterListFragment);
            fragmentTransaction.commit();
        }
    }
}
