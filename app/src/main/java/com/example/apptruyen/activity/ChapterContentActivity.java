package com.example.apptruyen.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.apptruyen.R;
import com.example.apptruyen.fragment.ChapterListFragment;
import com.example.apptruyen.entities.Chapter;
import com.example.apptruyen.fragment.ContentChapterFragment;

public class ChapterContentActivity extends AppCompatActivity {
    private Intent intent;
    private TextView txtStoryName,txtChapterList;
    private TextView txtBackHome;
    private Chapter chapterCurrent;
    private static final String TAG = "FRAGMENT_CHAPTER_LIST";
    private ContentChapterFragment contentChapterFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapter_content);


        intent = getIntent();
        chapterCurrent = (Chapter) intent.getSerializableExtra("chapter");
        txtStoryName = (TextView)findViewById(R.id.txtStoryName);
        txtBackHome = (TextView)findViewById(R.id.txtBackHome);
        txtChapterList = (TextView)findViewById(R.id.txtChapterList);

        //set fragment content
        contentChapterFragment = new ContentChapterFragment();
        Bundle sendBundle = new Bundle();
        contentChapterFragment.setArguments(sendBundle);
        sendBundle.putSerializable("current",chapterCurrent);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction().replace(R.id.contentFragment,contentChapterFragment);
        fragmentTransaction.commit();

        //Set story name
        SharedPreferences data = getSharedPreferences("STORY_NAME",MODE_PRIVATE);
        txtStoryName.setText(data.getString("storyName",""));

        txtBackHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChapterContentActivity.this, StartActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        txtChapterList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
