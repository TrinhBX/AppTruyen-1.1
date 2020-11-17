package com.example.apptruyen.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.apptruyen.R;
import com.example.apptruyen.adapter.RecyclerAdapter;
import com.example.apptruyen.entities.Story;
import com.example.apptruyen.utils.DatabaseHandler;

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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_book_case, container, false);
        databaseHandler = new DatabaseHandler(getContext());
        listBookcase = new ArrayList<>();
        for(Story story:databaseHandler.getAllStories()){
            listBookcase.add((Object) story);
        }
        recyclerView = view.findViewById(R.id.book_case_recycler);
        adapter = new RecyclerAdapter(getContext(),listBookcase,R.layout.row_story_list, "ROW_FULL");
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        return view;
    }
}