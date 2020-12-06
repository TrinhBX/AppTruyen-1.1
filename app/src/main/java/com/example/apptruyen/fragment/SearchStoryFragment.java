package com.example.apptruyen.fragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SearchStoryFragment extends Fragment {
    private RecyclerView recyclerView;
    private RecyclerAdapter recyclerAdapter;
    private List<Object> storyList;
    private EditText edtSearch;
    private TextView txtDeleteText;
    private TextView txtDeleteContext;

    @SuppressLint({"ClickableViewAccessibility", "WrongViewCast"})
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_search_story,container,false);
        txtDeleteContext = (TextView) view.findViewById(R.id.txtDeleteContext);
        edtSearch = (EditText) view.findViewById(R.id.edtSearch);
        txtDeleteText = (TextView)view.findViewById(R.id.txtDeleteText);

        storyList = new ArrayList<>();
        recyclerView = (RecyclerView) view.findViewById(R.id.search_story_list);
        recyclerView.setHasFixedSize(true);
        recyclerAdapter =  new RecyclerAdapter(getContext(),storyList,R.layout.row_collapse_item,"ROW_COLLAPSE");
        recyclerView.setAdapter(recyclerAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        DividerItemDecoration dividerHorizontal =
                new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);

        recyclerView.addItemDecoration(dividerHorizontal);



        //Event
        txtDeleteContext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                edtSearch.setText("");
                Fragment searchStoryFragment =  getActivity().getSupportFragmentManager().findFragmentById(R.id.frame_container2);
                if(searchStoryFragment!=null){
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction().remove(searchStoryFragment);
                    fragmentManager.popBackStack();
                    fragmentTransaction.commit();
                    edtSearch.clearFocus();
                }
            }
        });



        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
            @Override
            public void afterTextChanged(Editable s) {
                if(!s.toString().isEmpty()){
                    txtDeleteText.setBackground(getActivity().getResources().getDrawable(R.drawable.round_close_black));
                    txtDeleteText.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            edtSearch.setText("");
                        }
                    });
                    getStoryList(edtSearch.getText().toString());
                }else {
                    txtDeleteText.setBackground(getActivity().getResources().getDrawable(R.drawable.icon_search));
                    storyList.clear();
                    recyclerAdapter.notifyDataSetChanged();
                }
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
                params.put("column","name");
                params.put("condition",condition);
                return params;
            }
        };
        VolleySingleton.getInstance(getContext()).addToRequestQueue(stringRequest);
    }
}
