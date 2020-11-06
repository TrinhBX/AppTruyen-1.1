package com.example.apptruyen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.apptruyen.truyenchu.activity.MainActivity;
import com.example.apptruyen.truyenchu.activity.VolleySingleton;
import com.example.apptruyen.truyenchu.adapter.RecycleAdapter;
import com.example.apptruyen.truyenchu.entities.Story;
import com.example.apptruyen.truyenchu.utils.Validator;

import java.util.ArrayList;
import java.util.List;

public class StartActivity extends AppCompatActivity {
    private List<Story> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        if(!Validator.checkInternetConnection(this)){
            Toast.makeText(this,"No internet",Toast.LENGTH_SHORT).show();
        } else {
            RecyclerView recyclerView = (RecyclerView)findViewById(R.id.reviewLayout);
            list = new ArrayList<>();
            RecycleAdapter recycleAdapter = new RecycleAdapter(this,list,R.layout.row_story_list);
            //VolleySingleton.getInstance(this).getList(this, MainActivity.class);
            recyclerView.setAdapter(recycleAdapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            VolleySingleton.getInstance(this).getList(recycleAdapter,list);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e("MAIN","onStart");
    }
}