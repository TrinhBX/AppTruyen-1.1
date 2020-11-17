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
    private FragmentTransaction transaction;
    private LibraryFragment libraryFragment;
    private BookCaseFragment bookCaseFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //set bottom bar
        libraryFragment = new LibraryFragment();
        bookCaseFragment = new BookCaseFragment();
        transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.frame_container,libraryFragment);
        //transaction.addToBackStack(null);
        transaction.commit();

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        //loadFragment(libraryFragment,"");
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment;
                switch (item.getItemId()) {
                    case R.id.library_bottom_bar:
                        loadFragment(libraryFragment,"");
                        //transaction.replace(R.id.frame_container,libraryFragment);
                        //transaction.addToBackStack(null);
                        //transaction.commit();
                        toolbar.setTitle("Thư viện");
                        return true;
                    case R.id.bookcase_bottom_bar:
                        loadFragment(bookCaseFragment,"");
                        //transaction.replace(R.id.frame_container,bookCaseFragment);
                        //transaction.addToBackStack(null);
                        //transaction.commit();
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
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        transaction.commit();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e("MAIN","onStart");
    }
}