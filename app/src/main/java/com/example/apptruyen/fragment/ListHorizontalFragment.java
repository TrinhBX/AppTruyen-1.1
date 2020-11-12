package com.example.apptruyen.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.apptruyen.R;
import com.example.apptruyen.entities.Story;

import java.util.ArrayList;
import java.util.List;

public class ListHorizontalFragment extends Fragment {
    //private ColumnStoryListAdapter columnStoryListAdapter;
    //private GridView listHorizontal;
    private List<Story> storyList;
    private String type;
    private String column;

    public ListHorizontalFragment(String column, String type) {
        this.type = type;
        this.column = column;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_horizontal, container, false);
        storyList = new ArrayList<>();
        //listHorizontal = (GridView)view.findViewById(R.id.listHorizontal);

        //columnStoryListAdapter = new ColumnStoryListAdapter(getActivity(),R.layout.card_item,storyList,storyList.size());
        //listHorizontal.setAdapter(columnStoryListAdapter);
        //VolleySingleton.getInstance(getActivity()).getStoryList(column,type,storyList,columnStoryListAdapter);
        return view;
    }
}
