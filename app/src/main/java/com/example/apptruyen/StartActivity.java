package com.example.apptruyen;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.apptruyen.truyenchu.activity.VolleySingleton;
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
            list = new ArrayList<>();
            //list = VolleySingleton.getInstance(this).getList();
//            while (true){
//                if(!list.isEmpty()){
//                    Toast.makeText(this,list.size(),Toast.LENGTH_SHORT).show();
//                    break;
//                }
//            }
            Log.e("MAIN",""+list.size());
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e("MAIN","onStart");
    }
}