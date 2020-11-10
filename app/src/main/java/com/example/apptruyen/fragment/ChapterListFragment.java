package com.example.apptruyen.fragment;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.apptruyen.R;
import com.example.apptruyen.utils.VolleySingleton;
import com.example.apptruyen.adapter.ChapterListAdapter;
import com.example.apptruyen.entities.Chapter;

import java.util.ArrayList;
import java.util.List;

public class ChapterListFragment extends Fragment {
    private ImageButton btnClose;
    private ListView lvChapterList;
    private ChapterListAdapter chapterListAdapter;
    private EditText edtSearch;
    private Bundle getBundle;
    private List<Chapter> chapterList = new ArrayList<>();
    private ChapterListFragment chapterListFragment;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chapter_list,container,false);
        mapping(view);
        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                VolleySingleton.getInstance(getActivity()).getChapterList(getBundle.getInt("idStory"),chapterList,chapterListAdapter);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                List<Chapter> chapterList1 =  new ArrayList<>();
                chapterListAdapter = new ChapterListAdapter(getActivity(),R.layout.row_chapter_list,chapterList1,chapterList1.size());
                lvChapterList.setAdapter(chapterListAdapter);
                VolleySingleton.getInstance(getActivity()).getChapterList(getBundle.getInt("idStory"),chapterList1,chapterListAdapter,edtSearch.getText().toString());
            }
        });
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle sendBundle = new Bundle();
                sendBundle.putBoolean("close",true);
                if(chapterListFragment!=null) {
                    FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction().remove(chapterListFragment);
                    fragmentTransaction.commit();
                }
            }
        });

        setListChapter();
        return view;
    }
    private void mapping(View view){
        btnClose = (ImageButton) view.findViewById(R.id.btnClose);
        edtSearch = (EditText) view.findViewById(R.id.edtSearch);
        lvChapterList = (ListView)view.findViewById(R.id.lvChapterList);
        getBundle = getArguments();
        chapterListFragment = (ChapterListFragment) getParentFragmentManager().findFragmentById(R.id.listChapter);

    }

    private void setListChapter(){
        List<Chapter> chapterList = new ArrayList<>();
        chapterListAdapter = new ChapterListAdapter(getActivity(),R.layout.row_chapter_list,chapterList,chapterList.size());
        lvChapterList.setAdapter(chapterListAdapter);
        VolleySingleton.getInstance(getActivity()).getChapterList(getBundle.getInt("idStory"),chapterList,chapterListAdapter);
    }
}
