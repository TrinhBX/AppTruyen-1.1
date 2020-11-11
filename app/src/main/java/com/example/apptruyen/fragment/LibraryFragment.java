package com.example.apptruyen.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.apptruyen.R;
import com.example.apptruyen.utils.VolleySingleton;
import com.example.apptruyen.adapter.RecycleAdapter;
import com.example.apptruyen.entities.Story;
import com.example.apptruyen.utils.URLManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LibraryFragment extends Fragment {
    private Toolbar toolbar;
    private TextView txtMenuCategory;
    RecyclerView recyclerView;
    public LibraryFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_library, container, false);
        toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);

        //set click event for button Category (TextView)
        txtMenuCategory = (TextView)view.findViewById(R.id.txtMenuCategory); //mapping
        txtMenuCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_container2, new CategoryFragment());
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
        //set story list for recycleview
        recyclerView = (RecyclerView)view.findViewById(R.id.library_story_list); //mapping
        //RecycleAdapter recycleAdapter = new RecycleAdapter(getActivity(),)
        getStoryList();

        return view;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }
        @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.frame_container2, new SearchStoryFragment(),"SEARCH_FRAGMENT");
        transaction.addToBackStack(null);
        transaction.commit();
        return true;
    }

    private void getStoryList(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLManager.GET_STORY_LIST_URL.getUrl(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                List<Story> stories = new ArrayList<>();
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
                        stories.add(new Story(id,name,author,status,type,avatar,numberOfChapters,review));
                    }
                    RecycleAdapter adapter = new RecycleAdapter(getContext(),stories,R.layout.row_story_list,"ROW");
                    recyclerView.setAdapter(adapter);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
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
                params.put("column","");
                params.put("condition","");
                return params;
            }
        };
        VolleySingleton.getInstance(getContext()).addToRequestQueue(stringRequest);
    }
}