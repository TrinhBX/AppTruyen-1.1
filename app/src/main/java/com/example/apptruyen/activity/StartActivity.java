package com.example.apptruyen.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.example.apptruyen.R;
import com.example.apptruyen.entities.Story;
import com.example.apptruyen.fragment.BookCaseFragment;
import com.example.apptruyen.fragment.LibraryFragment;
import com.example.apptruyen.fragment.SearchStoryFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

public class StartActivity extends AppCompatActivity {
    private List<Story> list;
    private Toolbar toolbar;
    MenuItem menuItem;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //set bottom bar
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        loadFragment(new LibraryFragment(),"");
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment;
                switch (item.getItemId()) {
                    case R.id.library_bottom_bar:
                        loadFragment(new LibraryFragment(),"");
                        toolbar.setTitle("Thư viện");
                        return true;
                    case R.id.bookcase_bottom_bar:
                        loadFragment(new BookCaseFragment(),"");
                        toolbar.setTitle("Tủ sách");
                        return true;
                }
                return false;
            }
        });
    }
    private void loadFragment(Fragment fragment, String tag) {
        // load Fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment,tag);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e("MAIN","onStart");
    }
}