package com.example.apptruyen.fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.apptruyen.R;
import com.example.apptruyen.adapter.RecyclerAdapter;
import com.example.apptruyen.entities.Story;
import com.example.apptruyen.utils.DatabaseHandler;
import com.example.apptruyen.utils.TypeItems;

import java.util.ArrayList;
import java.util.List;

public class BookCaseFragment extends Fragment {

    private RecyclerView recyclerView;
    private List<Object> listBookcase;
    private RecyclerAdapter adapter;
    private DatabaseHandler databaseHandler;
    public BookCaseFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        Log.e("BOOKCASE","onAttach");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("BOOKCASE","onAttach");
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.e("BOOKCASE","onCreateView");
        View view = inflater.inflate(R.layout.fragment_book_case, container, false);
        databaseHandler = new DatabaseHandler(getContext());
        listBookcase = new ArrayList<>();
        for(Story story:databaseHandler.getAllStories()){
            listBookcase.add((Object) story);
        }
        recyclerView = view.findViewById(R.id.book_case_recycler);
        adapter = new RecyclerAdapter(getContext(),listBookcase,R.layout.row_story_list, TypeItems.ROW_STORY_FULL);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.e("BOOKCASE","onActivityCreated");
    }

    @Override
    public void onStart() {
        super.onStart();
        if(listBookcase.size()!=databaseHandler.getStoryCount()){
            listBookcase.clear();
            for(Story story:databaseHandler.getAllStories()){
                listBookcase.add((Object) story);
            }
            adapter.notifyDataSetChanged();
        }
        Log.e("BOOKCASE","onStart");
        Log.e("BOOK_CASE: ",listBookcase.size()+ ", reset: = "+databaseHandler.getStoryCount());
    }
    @Override
    public void onResume() {
        super.onResume();
        Log.e("BOOKCASE","onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.e("BOOKCASE","onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.e("BOOKCASE","onStop");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.e("BOOKCASE","onDestroyView");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e("BOOKCASE","onDestroy");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.e("BOOKCASE","onDetach");
    }

    private List<Story> getLists(){
        List<Story> list = new ArrayList<>();
        return list;
    }
}