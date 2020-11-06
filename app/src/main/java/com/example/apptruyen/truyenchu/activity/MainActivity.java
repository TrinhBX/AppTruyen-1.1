package com.example.apptruyen.truyenchu.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.apptruyen.R;
import com.example.apptruyen.truyenchu.fragment.ListHorizontalFragment;
import com.example.apptruyen.truyenchu.fragment.ListVerticalFragment;
import com.example.apptruyen.truyenchu.fragment.SearchStoryFragment;


public class MainActivity extends AppCompatActivity {
    private TextView txtMenuCategory;
    private Toolbar toolbar;
    private ImageButton btnChangeView;
    private boolean changed = false;
    private boolean isSearch = false;
    private boolean isRemove = false;
    private SearchStoryFragment searchStoryFragment;
    ListVerticalFragment listVerticalFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);
        super.onCreate(savedInstanceState);
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mapping();
        txtMenuCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CategoriesActivity.class);
                startActivity(intent);
            }
        });
        listVerticalFragment = new ListVerticalFragment("","");
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction().add(R.id.listStory,listVerticalFragment);
        fragmentTransaction.commit();
        //setChangeView();
    }

    private void mapping(){
        txtMenuCategory = (TextView) findViewById(R.id.txtMenuCategory);
        btnChangeView = (ImageButton) findViewById(R.id.btnChangeView);
        searchStoryFragment = new SearchStoryFragment();
    }

    private void setChangeView(){
        btnChangeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                Fragment fragment = null;
                if(changed){
                    fragment = new ListVerticalFragment("","");
                    btnChangeView.setImageResource(R.drawable.column_view);
                    changed = false;
                }else {
                    fragment= new ListHorizontalFragment("","");
                    btnChangeView.setImageResource(R.drawable.row_view);
                    changed = true;
                }
                fragmentTransaction.replace(R.id.listStory,fragment);
                if(!isRemove){

                    fragmentTransaction.remove(listVerticalFragment);
                    isRemove = true;
                }
                fragmentTransaction.commit();
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        if(!isSearch){
            fragmentTransaction.replace(R.id.searchFragment,searchStoryFragment);
            item.setIcon(R.drawable.close_24);
            isSearch = true;
        }else {
            fragmentTransaction.remove(searchStoryFragment);
            item.setIcon(R.drawable.icon_search_24px);
            isSearch = false;
        }
        fragmentTransaction.commit();
        return true;
    }
}
