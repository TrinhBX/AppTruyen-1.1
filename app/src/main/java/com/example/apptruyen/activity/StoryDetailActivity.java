package com.example.apptruyen.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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
import com.example.apptruyen.adapter.RecyclerAdapter;
import com.example.apptruyen.entities.Chapter;
import com.example.apptruyen.fragment.ChapterListFragment;
import com.example.apptruyen.entities.Story;
import com.example.apptruyen.utils.DatabaseHandler;
import com.example.apptruyen.utils.SharedPreferencesKey;
import com.example.apptruyen.utils.TypeItems;
import com.example.apptruyen.utils.URLManager;
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
    private static final String TAG = "FRAGMENT_CHAPTER_LIST";
    private ImageButton btnBookmark;
    private Button btnReading;
    private Story story;
    private DatabaseHandler databaseHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story_detail);

        Intent intent = getIntent();
        story =(Story)intent.getSerializableExtra("story");

        mapping();
        setSupportActionBar(toolbar);
        //toolbar.

        databaseHandler= new DatabaseHandler(this);
        if(databaseHandler.exitsStory(story.getIdStory())){
            btnBookmark.setImageResource(R.drawable.on_bookmark_24);
            databaseHandler.updateStory(story);
            btnReading.setText("Đọc tiếp");
        }

        btnReading.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int idStory = story.getIdStory();
                int idChapter;
                if(databaseHandler.exitsStory(story.getIdStory())){
                    Story newStory = (Story) databaseHandler.getStory(story.getIdStory());
                    idChapter = newStory.getIdCurrentChapter();
                } else {
                    SharedPreferences sharedPreferences = getSharedPreferences(SharedPreferencesKey.ID_CHAPTER_FIRST_LAST+"",MODE_PRIVATE);
                    idChapter = sharedPreferences.getInt("id_first_chapter",0);
                }
                Intent sentIntent = new Intent(StoryDetailActivity.this,ChapterContentActivity.class);
                Chapter chapter = new Chapter(idStory,idChapter);
                sentIntent.putExtra("chapter",chapter);
                startActivity(sentIntent);
            }
        });

        if(story!=null){
            collapsingToolbarLayout.setTitle(story.getStoryName()); //set name story
            txtStoryName.setText(story.getStoryName());
            txtAuthor.setText("Tác giả: "+story.getAuthor());
            txtStatus.setText("Tình Trạng: "+story.getStatus());
            txtType.setText("Thể loại: "+story.getType());
            txtReviewContent.setText(story.getReview());
            txtChapterTotal.setText("Số chương: "+story.getNumberOfChapter());
            VolleySingleton.getInstance(StoryDetailActivity.this).setImage(story.getAvatar(),avatar);
            getStoryDetail(story.getIdStory());

        }

        chapterListLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle sendBundle = new Bundle();
                sendBundle.putInt("idStory",story.getIdStory());
                sendBundle.putString("nameStory",story.getStoryName());
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
                String s;
                if(!databaseHandler.exitsStory(story.getIdStory())){
                    SharedPreferences sharedPreferences = getSharedPreferences(SharedPreferencesKey.ID_CHAPTER_FIRST_LAST+"",MODE_PRIVATE);
                    s= "Bookmarking";
                    btnBookmark.setImageResource(R.drawable.on_bookmark_24);
                    story.setIdCurrentChapter(sharedPreferences.getInt("id_first_chapter",0));
                    databaseHandler.addStory(story);
                } else {
                    s= "Un-Bookmarking";
                    btnBookmark.setImageResource(R.drawable.bookmark_24);
                    databaseHandler.deleteStory(story.getIdStory());
                    btnReading.setText("Đọc ngay");
                }
                Toast.makeText(StoryDetailActivity.this,s,Toast.LENGTH_SHORT).show();
            }
        });

        if(databaseHandler.exitsStory(story.getIdStory())){

        }
        setListStoryOfAuthor();

        SharedPreferences sharedPreferences = getSharedPreferences(""+SharedPreferencesKey.STORY_NAME,MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("storyName",story.getStoryName());
        editor.apply();
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
        btnReading = (Button) findViewById(R.id.btn_reading);
    }

    private void getStoryDetail(final int idStory){
        StringRequest rq = new StringRequest(Request.Method.POST, URLManager.GET_STORY_DETAIL.getUrl(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject object = new JSONObject(response);
                    txtLatestChapter.setText(object.getString("NameLastChapter"));
                    SharedPreferences sharedPreferences = getSharedPreferences(SharedPreferencesKey.ID_CHAPTER_FIRST_LAST+"",MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putInt("id_last_chapter",object.getInt("IDLastChapter"));
                    editor.putInt("id_first_chapter",object.getInt("IDFirstChapter"));
                    editor.commit();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() {
                Map<String,String> params = new HashMap<>();
                params.put("idStory",""+idStory);
                return params;
            }
        };
        VolleySingleton.getInstance(this).addToRequestQueue(rq);
    }



    private void setListStoryOfAuthor(){
        final List<Object> objectList = new ArrayList<>();
        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.gvStoryOfAuthor);
        final RecyclerAdapter recyclerAdapter = new RecyclerAdapter(this,objectList,R.layout.card_item, TypeItems.CARD_STORY);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setAdapter(recyclerAdapter);
        StringRequest rq = new StringRequest(Request.Method.POST, URLManager.GET_STORY_LIST_URL.getUrl(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray array = new JSONArray(response);
                    for(int i = 0; i<array.length();i++){
                        JSONObject jsonObject = array.getJSONObject(i);
                        if(jsonObject.getInt("ID")!=story.getIdStory()){
                            int id = jsonObject.getInt("ID");
                            String name = jsonObject.getString("Name");
                            String author = jsonObject.getString("Author");
                            String status = jsonObject.getString("Status");
                            String type = jsonObject.getString("Type");
                            String avatar = jsonObject.getString("Avatar");
                            String review = jsonObject.getString("Review");
                            int numberOfChapters = jsonObject.getInt("NumberOfChapters");
                            objectList.add(new Story(id,name,author,status,type,avatar,numberOfChapters,review));
                        }
                    }
                    recyclerAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("column","author");
                params.put("condition",story.getAuthor());
                return params;
            }
        };
        VolleySingleton.getInstance(this).addToRequestQueue(rq);
    }
    @Override
    protected void onPause() {
        super.onPause();
        ChapterListFragment chapterListFragment = (ChapterListFragment)getSupportFragmentManager().findFragmentById(R.id.listChapter);
        if(chapterListFragment !=null){
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction().remove(chapterListFragment);
            fragmentTransaction.commit();
            fragmentManager.popBackStack();
        }
    }
}
