package com.example.apptruyen.fragment;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.apptruyen.R;
import com.example.apptruyen.adapter.RecyclerAdapter;
import com.example.apptruyen.utils.TypeItems;
import com.example.apptruyen.utils.URLManager;
import com.example.apptruyen.utils.VolleySingleton;
import com.example.apptruyen.entities.Chapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChapterListFragment extends Fragment {
    private ImageButton btnClose;
    private EditText edtSearch;
    private int idStory;
    private List<Object> chapterList;
    private RecyclerView recyclerView;
    private RecyclerAdapter adapter;
    private TextView txtDeleteTextChapter;
    private static final String TAG = "FRAGMENT_CHAPTER_LIST";


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chapter_list,container,false);
        mapping(view);

        chapterList = new ArrayList<>();
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview_chapter_list);
        TypeItems typeItems = (getActivity().getLocalClassName().equals("activity.ChapterContentActivity"))?TypeItems.ROW_CHAPTER_CONTENT:TypeItems.ROW_CHAPTER_DETAIL;
        adapter = new RecyclerAdapter(getActivity(),chapterList,R.layout.row_chapter_list,typeItems);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        DividerItemDecoration dividerHorizontal = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerHorizontal);
        getChapterList("");


        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                chapterList.clear();
                adapter.notifyDataSetChanged();
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(!s.toString().isEmpty()){
                    txtDeleteTextChapter.setBackground(getActivity().getResources().getDrawable(R.drawable.round_close_black));
                    txtDeleteTextChapter.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            edtSearch.setText("");
                        }
                    });
                    getChapterList("Chương "+s.toString());

                } else {
                    txtDeleteTextChapter.setBackground(getActivity().getResources().getDrawable(R.drawable.icon_search));
                    chapterList.clear();
                    getChapterList("");
                }
            }
        });
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ChapterListFragment chapterListFragment = (ChapterListFragment)getActivity().getSupportFragmentManager().findFragmentByTag(TAG);
                if(chapterListFragment!=null){
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction().remove(chapterListFragment);
                    fragmentTransaction.commit();
                    fragmentManager.popBackStack();
                }
            }
        });

        return view;
    }
    private void mapping(View view){
        btnClose = (ImageButton) view.findViewById(R.id.btnClose);
        edtSearch = (EditText) view.findViewById(R.id.edtSearchChapter);
        idStory = getArguments().getInt("idStory");
        txtDeleteTextChapter = (TextView)view.findViewById(R.id.txtDeleteTextChapter);
    }

    private void getChapterList(final String condition){
        chapterList.clear();
        StringRequest request = new StringRequest(Request.Method.POST, URLManager.GET_CHAPTER_LIST_URL.getUrl(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for(int i=0;i<jsonArray.length();i++){
                        JSONObject chapter = jsonArray.getJSONObject(i);
                        chapterList.add(new Chapter(chapter.getInt("IDStory"),chapter.getInt("IDChapter"),chapter.getString("ChapterName")));
                    }
                    adapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("idStory",""+idStory);
                params.put("condition",condition);
                return params;
            }
        };
        VolleySingleton.getInstance(getContext()).addToRequestQueue(request);
    }
}
