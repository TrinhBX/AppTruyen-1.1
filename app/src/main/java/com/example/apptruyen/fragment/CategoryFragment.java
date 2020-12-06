package com.example.apptruyen.fragment;

import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.apptruyen.R;
import com.example.apptruyen.utils.VolleySingleton;
import com.example.apptruyen.adapter.RecyclerAdapter;
import com.example.apptruyen.entities.Story;
import com.example.apptruyen.utils.URLManager;
import com.google.android.material.tabs.TabLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CategoryFragment extends Fragment {
    private Toolbar categoryToolbar;
    private TabLayout categoryTabs;
    private RecyclerView recyclerView;
    RecyclerAdapter recyclerAdapter;
    private List<Object> storyList;

    public CategoryFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view =  inflater.inflate(R.layout.fragment_category, container, false);
        //categoryToolbar = (Toolbar)view.findViewById(R.id.category_toolbar);

        //set default
        storyList = new ArrayList<>();
        recyclerView = (RecyclerView)view.findViewById(R.id.category_list);
        recyclerAdapter = new RecyclerAdapter(getContext(),storyList,R.layout.row_story_list,"ROW_FULL");
        recyclerView.setAdapter(recyclerAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        getStoryList("Tien hiep");

        categoryTabs = (TabLayout)view.findViewById(R.id.category_tabs);
        categoryTabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                getStoryList(tab.getText().toString());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        return view;
    }

    private void getStoryList(final String condition){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLManager.GET_STORY_LIST_URL.getUrl(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                storyList.clear();
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for(int i = 0; i<jsonArray.length();i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        int id = jsonObject.getInt("ID");
                        String name = jsonObject.getString("Name");
                        String author = jsonObject.getString("Author");
                        String status = jsonObject.getString("Status");
                        String type = jsonObject.getString("Type");
                        String avatar = jsonObject.getString("Avatar");
                        String review = jsonObject.getString("Review");
                        int numberOfChapters = jsonObject.getInt("NumberOfChapters");
                        storyList.add(new Story(id,name,author,status,type,avatar,numberOfChapters,review));
                    }
                    recyclerAdapter.notifyDataSetChanged();
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
                params.put("column","type");
                params.put("condition",condition);
                return params;
            }
        };
        VolleySingleton.getInstance(getContext()).addToRequestQueue(stringRequest);
    }
}