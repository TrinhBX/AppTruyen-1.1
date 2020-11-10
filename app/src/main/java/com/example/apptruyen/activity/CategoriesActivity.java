package com.example.apptruyen.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.apptruyen.R;
import com.example.apptruyen.fragment.ListHorizontalFragment;
import com.example.apptruyen.fragment.ListVerticalFragment;
import com.google.android.material.tabs.TabLayout;

public class CategoriesActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private TabLayout categoryTabs;
    private boolean changed = false;
    private boolean isRemove = false;
    ListVerticalFragment listVerticalFragment;
    String condition = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);

        toolbar = (Toolbar) findViewById(R.id.categoryToolbar);
        setSupportActionBar(toolbar);
        categoryTabs = (TabLayout)findViewById(R.id.categoryTabs);

        listVerticalFragment = new ListVerticalFragment("type","tien hiep");
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction().add(R.id.listView,listVerticalFragment);
        fragmentTransaction.commit();

        categoryTabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                condition = tab.getText().toString();
                ListVerticalFragment listVerticalFragment = new ListVerticalFragment("type",tab.getText().toString());
                ListHorizontalFragment listHorizontalFragment = new ListHorizontalFragment("type",tab.getText().toString());
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                if(!changed){
                    fragmentTransaction.replace(R.id.listView,listVerticalFragment);
                } else {
                    fragmentTransaction.replace(R.id.listView,listHorizontalFragment);
                }

                fragmentTransaction.commit();
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu,menu);
        menu.findItem(R.id.app_bar_search).setIcon(R.drawable.column_view);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        Fragment fragment = null;
        if(changed){
            fragment = new ListVerticalFragment("type",condition);
            item.setIcon(R.drawable.column_view);
            changed = false;
        }else {
            fragment= new ListHorizontalFragment("type",condition);
            item.setIcon(R.drawable.row_view);
            changed = true;
        }
        fragmentTransaction.replace(R.id.listView,fragment);
        if(!isRemove){
            fragmentTransaction.remove(listVerticalFragment);
            isRemove = true;
        }
        fragmentTransaction.commit();
        return true;
    }
}
