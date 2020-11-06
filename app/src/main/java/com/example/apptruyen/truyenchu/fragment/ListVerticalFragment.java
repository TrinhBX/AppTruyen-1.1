package com.example.apptruyen.truyenchu.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.apptruyen.R;
import com.example.apptruyen.truyenchu.adapter.RecycleAdapter;
import com.example.apptruyen.truyenchu.adapter.RowStoryListAdapter;
import com.example.apptruyen.truyenchu.entities.Story;
import com.example.apptruyen.truyenchu.activity.VolleySingleton;

import java.util.ArrayList;
import java.util.List;

public class ListVerticalFragment extends Fragment {
    private String type;
    private String column;

    public ListVerticalFragment(String column, String type) {
        this.type = type;
        this.column = column;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_vertical,container,false);
        RecyclerView listVertical = (RecyclerView) view.findViewById(R.id.listVertical);
        List<Story> storyList = new ArrayList<>();
        RecycleAdapter recycleAdapter = new RecycleAdapter(getContext(),storyList,R.layout.row_story_list);
        listVertical.setAdapter(recycleAdapter);
        listVertical.setLayoutManager(new LinearLayoutManager(getContext()));
        VolleySingleton.getInstance(getActivity()).getList(recycleAdapter,storyList);
        return view;
    }
}
