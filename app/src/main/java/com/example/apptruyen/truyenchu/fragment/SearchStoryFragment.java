package com.example.apptruyen.truyenchu.fragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.apptruyen.R;
import com.example.apptruyen.truyenchu.adapter.RowStoryListAdapter;
import com.example.apptruyen.truyenchu.entities.Story;
import com.example.apptruyen.truyenchu.activity.VolleySingleton;

import java.util.ArrayList;
import java.util.List;

public class SearchStoryFragment extends Fragment {
    private ListView listView;
    private RowStoryListAdapter rowStoryListAdapter;
    private EditText edtSearch;
    private TextView txtDeleteText;

    @SuppressLint("ClickableViewAccessibility")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_search_story,container,false);
        listView = (ListView) view.findViewById(R.id.listView);
        edtSearch = (EditText) view.findViewById(R.id.edtSearch);
//        if(edtSearch.getText() != null){
//            Toast.makeText(getContext(),"null",Toast.LENGTH_SHORT).show();
//            edtSearch.setOnTouchListener(new View.OnTouchListener() {
//                @Override
//                public boolean onTouch(View view, MotionEvent motionEvent) {
//                    if(motionEvent.getAction() == MotionEvent.ACTION_UP){
//                        if(motionEvent.getRawX() >= edtSearch.getRight() - edtSearch.getTotalPaddingRight()){
//                            Toast.makeText(getContext(),"click",Toast.LENGTH_SHORT).show();
//                            edtSearch.setText("");
//                            return true;
//                        }
//                    }
//                    return false;
//                }
//            });
//        }


        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(edtSearch.getText().toString().trim().length()>2){
                    List<Story> storyList = new ArrayList<>();
                    rowStoryListAdapter = new RowStoryListAdapter(getActivity(), R.layout.row_story_list, storyList, storyList.size());
                    listView.setAdapter(rowStoryListAdapter);
                    VolleySingleton.getInstance(getActivity()).getStoryList("name", edtSearch.getText().toString(), storyList, rowStoryListAdapter);
                }else {
                    List<Story> storyList = new ArrayList<>();
                    rowStoryListAdapter = new RowStoryListAdapter(getActivity(), R.layout.row_story_list, storyList, storyList.size());
                    listView.setAdapter(rowStoryListAdapter);
                }

                txtDeleteText = (TextView) view.findViewById(R.id.txtDeleteText);
                if(!edtSearch.getText().toString().isEmpty()){
                    txtDeleteText.setBackground(getActivity().getResources().getDrawable(R.drawable.round_close_black));
                    txtDeleteText.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            edtSearch.setText("");
                        }
                    });
                } else  txtDeleteText.setBackground(getActivity().getResources().getDrawable(R.drawable.icon_search));

            }
        });
        return view;
    }
}
