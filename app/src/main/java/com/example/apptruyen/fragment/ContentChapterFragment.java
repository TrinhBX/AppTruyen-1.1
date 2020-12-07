package com.example.apptruyen.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.apptruyen.R;
import com.example.apptruyen.entities.Chapter;
import com.example.apptruyen.utils.SharedPreferencesKey;
import com.example.apptruyen.utils.URLManager;
import com.example.apptruyen.utils.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ContentChapterFragment extends Fragment {
    private Chapter chapterCurrent;
    private TextView txtChapterName,txtUploader, txtChapterContent;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    public ContentChapterFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_content_chapter, container, false);

        sharedPreferences = getActivity().getSharedPreferences(SharedPreferencesKey.ID_THREE_CHAPTER.name(), Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        chapterCurrent = (Chapter) getArguments().getSerializable("current");

        mapping(view);

        getChapterContent(chapterCurrent.getIdStory(),chapterCurrent.getIdChapter());
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(SharedPreferencesKey.ID_CHAPTER_FIRST_LAST.name(),Context.MODE_PRIVATE);
        if(sharedPreferences.getInt("id_last_chapter",0)!=chapterCurrent.getIdChapter()||sharedPreferences.getInt("id_first_chapter",0)!=chapterCurrent.getIdChapter()){
            ImageButton btnNextChapter,btnPreviousChapter;
            btnNextChapter = (ImageButton) getActivity().findViewById(R.id.btnNextChapter);
            btnPreviousChapter = (ImageButton) getActivity().findViewById(R.id.btnPreviousChapter);
            if(sharedPreferences.getInt("id_last_chapter",0)==chapterCurrent.getIdChapter()){
                btnNextChapter.setImageResource(R.drawable.next_gray_24);
                btnNextChapter.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                });
                setOnPreviousChapter(btnPreviousChapter);
            }
            else if(sharedPreferences.getInt("id_first_chapter",0)==chapterCurrent.getIdChapter()){
                btnPreviousChapter.setImageResource(R.drawable.back_gray24);
                btnPreviousChapter.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                });
                setOnNextChapter(btnNextChapter);
            } else {
                setOnNextChapter(btnNextChapter);
                setOnPreviousChapter(btnPreviousChapter);
            }
        }

        return view;
    }

    private void mapping(View view){
        txtChapterName = (TextView)view.findViewById(R.id.txtChapterName);
        txtUploader = (TextView)view.findViewById(R.id.txtUploader);
        txtChapterContent = (TextView)view.findViewById(R.id.txtChapterContent);
    }

    private void setContent(JSONObject chapter){
        try {
            txtChapterName.setText(chapter.getString("ChapterName"));
            txtUploader.setText("Người đăng: "+chapter.getString("Uploader"));
            txtChapterContent.setText(chapter.getString("Content")+"\n\n");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void setOnNextChapter(ImageButton btnNextChapter){
        btnNextChapter.setImageResource(R.drawable.next_24);
        btnNextChapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ContentChapterFragment contentChapterFragment = new ContentChapterFragment();
                Bundle sendBundle = new Bundle();
                sendBundle.putSerializable("current",new Chapter(chapterCurrent.getIdStory(),sharedPreferences.getInt("next",0)));
                contentChapterFragment.setArguments(sendBundle);
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.contentFragment,contentChapterFragment);
                fragmentTransaction.commit();
            }
        });
    }
    private void setOnPreviousChapter(ImageButton btnPreviousChapter){
        btnPreviousChapter.setImageResource(R.drawable.back_arrow24);
        btnPreviousChapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ContentChapterFragment contentChapterFragment = new ContentChapterFragment();
                Bundle sendBundle = new Bundle();
                sendBundle.putSerializable("current",new Chapter(chapterCurrent.getIdStory(),sharedPreferences.getInt("previous",0)));
                contentChapterFragment.setArguments(sendBundle);
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.contentFragment,contentChapterFragment);
                fragmentTransaction.commit();
            }
        });
    }

    private void getChapterContent(final int idStory, final int idChapter){
        StringRequest rq = new StringRequest(Request.Method.POST, URLManager.GET_CHAPTER_CONTENT.getUrl(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray array = new JSONArray(response);

                    switch (array.length()){
                        case 1:
                            setContent(array.getJSONObject(0));
                            break;
                        case 2:
                            if(chapterCurrent.getIdChapter()==array.getJSONObject(0).getInt("IDChapter")){
                                setContent(array.getJSONObject(0));
                                editor.putInt("next",array.getJSONObject(1).getInt("IDChapter"));
                            } else {
                                setContent(array.getJSONObject(1));
                                editor.putInt("previous",array.getJSONObject(0).getInt("IDChapter"));
                            }
                            break;
                        case 3:
                            setContent(array.getJSONObject(1));
                            editor.putInt("next",array.getJSONObject(2).getInt("IDChapter"));
                            editor.putInt("previous",array.getJSONObject(0).getInt("IDChapter"));
                            break;
                    }
                    editor.apply();

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
                params.put("idChapter",""+idChapter);
                return params;
            }
        };
        VolleySingleton.getInstance(getContext()).addToRequestQueue(rq);
    }
}