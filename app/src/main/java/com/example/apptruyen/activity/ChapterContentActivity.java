package com.example.apptruyen.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.apptruyen.R;
import com.example.apptruyen.fragment.ChapterListFragment;
import com.example.apptruyen.entities.Chapter;
import com.example.apptruyen.utils.URLManager;
import com.example.apptruyen.utils.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ChapterContentActivity extends AppCompatActivity {
    private Intent intent;
    private TextView txtStoryName,txtChapterName,txtUploader, txtChapterContent,txtChapterList;
    private TextView txtBackHome;
    private Chapter chapterCurrent;
    private ImageButton btnNextChapter,btnPreviousChapter;
    private ScrollView scrollView;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private static final String TAG = "FRAGMENT_CHAPTER_LIST";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapter_content);


        intent = getIntent();
        chapterCurrent = (Chapter) intent.getSerializableExtra("chapter");
        sharedPreferences = getSharedPreferences("infor", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        txtStoryName = (TextView)findViewById(R.id.txtStoryName);
        txtChapterName = (TextView)findViewById(R.id.txtChapterName);
        txtUploader = (TextView)findViewById(R.id.txtUploader);
        txtChapterContent = (TextView)findViewById(R.id.txtChapterContent);
        btnNextChapter = (ImageButton) findViewById(R.id.btnNextChapter);
        btnPreviousChapter = (ImageButton) findViewById(R.id.btnPreviousChapter);
        txtBackHome = (TextView)findViewById(R.id.txtBackHome);
        txtChapterList = (TextView)findViewById(R.id.txtChapterList);
        scrollView =(ScrollView)findViewById(R.id.content);

        //Set story name
        SharedPreferences data = getSharedPreferences("STORY_NAME",MODE_PRIVATE);
        txtStoryName.setText(data.getString("storyName",""));

        //VolleySingleton.getInstance(this).getStoryName(chapterCurrent.getIdStory(),txtStoryName);

        txtBackHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startActivity(new Intent(ChapterContentActivity.this, StartActivity.class));
                Intent intent = new Intent(ChapterContentActivity.this, StartActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        if(chapterCurrent!= null){
            getChapterContent(chapterCurrent.getIdStory(),chapterCurrent.getIdChapter());
        }

        btnNextChapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getChapterContent(chapterCurrent.getIdStory(),sharedPreferences.getInt("next",0));
            }
        });

        btnPreviousChapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getChapterContent(chapterCurrent.getIdStory(),sharedPreferences.getInt("previous",0));
            }
        });

        txtChapterList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent = new Intent(ChapterContentActivity.this, ChapterListActivity.class);
                //intent.putExtra("idStory",chapterCurrent.getIdStory());
                //startActivity(intent);
                ChapterListFragment chapterListFragment = new ChapterListFragment();
                Bundle sendBundle = new Bundle();
                chapterListFragment.setArguments(sendBundle);
                sendBundle.putInt("idStory",chapterCurrent.getIdStory());
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction().add(R.id.listChapter,chapterListFragment,TAG);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

    }


    private void getChapterContent(final int idStory, final int idChapter){
        StringRequest getContent = new StringRequest(Request.Method.POST, URLManager.GET_CHAPTER_CONTENT.getUrl(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    editor.clear().apply();
                    //editor.commit();
                    final JSONArray array = new JSONArray(response);
                    Log.e("LENGTH",""+array.length());
                    //Log.e("LENGTH", "next ")
                    JSONObject next;
                    JSONObject current;
                    JSONObject previous;
//                    switch (array.length()){
//                        case 1:
//                            current = array.getJSONObject(0);
//                            setContent(current);
//                            break;
//                        case 2:
//                            if(idChapter==array.getJSONObject(0).getInt("IDChapter")){
//                                current = array.getJSONObject(0);
//                                setContent(current);
//                                next = array.getJSONObject(1);
//                                editor.putInt("next",next.getInt("IDChapter"));
//                            }else {
//                                current = array.getJSONObject(1);
//                                setContent(current);
//                                previous = array.getJSONObject(0);
//                                editor.putInt("previous",previous.getInt("IDChapter"));
//                            }
//                            editor.commit();
//                            break;
//                        case 3:
//                            current = array.getJSONObject(1);
//                            setContent(current);
//                            previous = array.getJSONObject(0);
//                            next = array.getJSONObject(2);
//                            editor.putInt("previous",previous.getInt("IDChapter"));
//                            editor.putInt("next",next.getInt("IDChapter"));
//                            editor.commit();
//                            break;
//                    }

                    for (int i=0;i<array.length();i++){
                        JSONObject chapter = array.getJSONObject(i);
                        if(chapter.getInt("IDChapter")==idChapter){
                            if(i==0){
                                setContent(chapter);
                                next = array.getJSONObject(i+1);
                                editor.putInt("next",next.getInt("IDChapter"));
                                editor.commit();
                                btnPreviousChapter.setImageResource(R.drawable.back_gray24);
                                scrollView.scrollTo(0,0);

                            }else if(i==array.length()-1){
                                setContent(chapter);
                                previous = array.getJSONObject(i-1);
                                editor.putInt("previous",previous.getInt("IDChapter"));
                                editor.commit();
                                btnNextChapter.setImageResource(R.drawable.next_gray_24);
                                scrollView.scrollTo(0,0);
                            }else {
//                                txtChapterName.setText(chapter.getString("ChapterName"));
//                                txtUploader.setText("Người đăng: "+chapter.getString("Uploader"));
//                                txtChapterContent.setText(chapter.getString("Content")+"\n\n\n");
                                setContent(chapter);
                                previous = array.getJSONObject(i-1);
                                next = array.getJSONObject(i+1);
                                editor.putInt("next",next.getInt("IDChapter"));
                                editor.putInt("previous",previous.getInt("IDChapter"));
                                editor.commit();
                                btnPreviousChapter.setImageResource(R.drawable.back_arrow24);
                                btnNextChapter.setImageResource(R.drawable.next_24);
                                scrollView.scrollTo(0,0);
                            }
                        }
                    }

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
                params.put("idStory",""+idStory);
                params.put("idChapter",""+idChapter);
                return params;
            }
        };

        VolleySingleton.getInstance(this).addToRequestQueue(getContent);
    }

    private void setContent(JSONObject chapter){
        try {
            txtChapterName.setText(chapter.getString("ChapterName"));
            txtUploader.setText("Người đăng: "+chapter.getString("Uploader"));
            txtChapterContent.setText(chapter.getString("Content")+"\n\n");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        intent = getIntent();
        intent.getData();
        Chapter newChapter = (Chapter) intent.getSerializableExtra("chapter");

        Log.e("INTENT","idStory ="+newChapter.getIdStory()+", "+newChapter.getIdChapter());
        Log.e("DATA",""+intent.getData());
        chapterCurrent = (Chapter) intent.getSerializableExtra("chapter");
        Toast.makeText(ChapterContentActivity.this,intent.getDataString(),Toast.LENGTH_LONG);
        getChapterContent(chapterCurrent.getIdStory(),chapterCurrent.getIdChapter());
    }

    @Override
    protected void onPause() {
        super.onPause();
        ChapterListFragment chapterListFragment = (ChapterListFragment)getSupportFragmentManager().findFragmentById(R.id.listChapter);
        if(chapterListFragment !=null){
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction().remove(chapterListFragment);
            fragmentTransaction.commit();
        }
        editor.clear().apply();
    }
}
