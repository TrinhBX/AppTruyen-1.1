package com.example.apptruyen.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.apptruyen.R;
import com.example.apptruyen.entities.Story;
import com.example.apptruyen.fragment.BookCaseFragment;
import com.example.apptruyen.fragment.CategoryFragment;
import com.example.apptruyen.fragment.LibraryFragment;
import com.example.apptruyen.fragment.SearchStoryFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;

import java.util.List;

public class StartActivity extends AppCompatActivity {
    private List<Story> list;
    private Toolbar toolbar;
    private FragmentTransaction transaction;
    private LibraryFragment libraryFragment;
    private BookCaseFragment bookCaseFragment;
    private CategoryFragment categoryFragment;
    private TextView title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        title = (TextView)findViewById(R.id.title_start_activity);
        //set bottom bar
        libraryFragment = new LibraryFragment();
        bookCaseFragment = new BookCaseFragment();
        categoryFragment = new CategoryFragment();

        transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container,libraryFragment);
        transaction.commit();

        final BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.bottom_navigation);

        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.library_bottom_bar:
                        loadFragment(libraryFragment,"Thư viện");
                        return true;
                    case R.id.bookcase_bottom_bar:
                        loadFragment(bookCaseFragment,"Tủ sách");
                        return true;
                    case R.id.category_bottom_bar:
                        loadFragment(categoryFragment,"Thể loại");
                        return true;
                }
                return false;
            }
        });
    }

    private void loadFragment(Fragment fragment, String titleString){
        FragmentTransaction transactionCategory = getSupportFragmentManager().beginTransaction();
        transactionCategory.replace(R.id.frame_container, fragment);
        transactionCategory.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        transactionCategory.commit();
        title.setText(titleString);

    }
    @Override
    protected void onStart() {
        super.onStart();
        Log.e("MAIN","onStart");
    }
}