package com.example.apptruyen.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.apptruyen.R;
import com.example.apptruyen.adapter.ChapterListAdapter;
import com.example.apptruyen.adapter.ColumnStoryListAdapter;
import com.example.apptruyen.adapter.RecycleAdapter;
import com.example.apptruyen.adapter.RowStoryListAdapter;
import com.example.apptruyen.entities.Chapter;
import com.example.apptruyen.entities.Story;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VolleySingleton {
    private static VolleySingleton instance;
    private RequestQueue requestQueue;
    private static Context staticContext;
    private static final String GET_CHAPTER_LIST_URL = "https://mis58pm.000webhostapp.com/GetChapterList.php";
    private static final String GET_STORY_LIST_URL= "https://mis58pm.000webhostapp.com/GetListStory.php";

    private VolleySingleton(Context context) {
        staticContext = context;
        requestQueue = getRequestQueue();
    }

    public static synchronized VolleySingleton getInstance(Context context) {
        if (instance == null) {
            instance = new VolleySingleton(context);
        }
        return instance;
    }

    public RequestQueue getRequestQueue() {
        if (requestQueue == null) {
            // getApplicationContext() is key, it keeps you from leaking the
            // Activity or BroadcastReceiver if someone passes one in.
            requestQueue = Volley.newRequestQueue(staticContext.getApplicationContext());
        }
        return requestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req) {
        getRequestQueue().add(req);
    }

    public void setImage(String url, final ImageView avatar){
        ImageRequest imageRequest = new ImageRequest(url, new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap response) {
                avatar.setImageBitmap(response);
            }
        }, 0, 0, null, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                avatar.setImageResource(R.drawable.image_broken);
            }
        });
        getRequestQueue().add(imageRequest);
    }

    public void getList(final RecycleAdapter adapter, final List<Story> list){
        StringRequest rq = new StringRequest(Request.Method.POST, GET_STORY_LIST_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
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
                        list.add(new Story(id,name,author,status,type,avatar,numberOfChapters,review));
                    }
                    Log.e("REPO",""+list.size());
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
                Map<String, String> params = new HashMap<>();
                    params.put("type", "");
                    params.put("column", "");
                return params;
            }
        };
        getRequestQueue().add(rq);
    }
    public void getStoryList(final String column, final String type, final List<Story> storyList, final RowStoryListAdapter rowStoryListAdapter, final int count){
        storyList.clear();
        StringRequest rqListStoryByType = new StringRequest(Request.Method.POST, GET_STORY_LIST_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
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
                        storyList.add(new Story(id,name,author,status,type,avatar,review));
                    }
                    rowStoryListAdapter.setCount(count);
                    rowStoryListAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    Toast.makeText(staticContext,"Exception!",Toast.LENGTH_LONG).show();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(staticContext,"Error",Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                if(column!= null){
                    params.put("type", type);
                    params.put("column", column);
                }
                return params;
            }
        };
        getRequestQueue().add(rqListStoryByType);
    }

 public void getStoryList(final String column, final String type, final List<Story> storyList, final RowStoryListAdapter rowStoryListAdapter){
        StringRequest rqListStoryByType = new StringRequest(Request.Method.POST, GET_STORY_LIST_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
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
                        storyList.add(new Story(id,name,author,status,type,avatar,review));
                    }
                    rowStoryListAdapter.setCount(storyList.size());
                    rowStoryListAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    Toast.makeText(staticContext,"Exception!",Toast.LENGTH_LONG).show();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(staticContext,"Error",Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                if(column!= null){
                    params.put("type", type);
                    params.put("column", column);
                }
                return params;
            }
        };
        getRequestQueue().add(rqListStoryByType);
    }

    public void getStoryList(final String column, final String type, final List<Story> storyList, final ColumnStoryListAdapter columnStoryListAdapter){
        StringRequest rqListStoryByType = new StringRequest(Request.Method.POST, GET_STORY_LIST_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
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
                        storyList.add(new Story(id,name,author,status,type,avatar,review));
                    }
                    columnStoryListAdapter.setCount(storyList.size());
                    columnStoryListAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(staticContext,"Exception!",Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(staticContext,"Error",Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams(){
                Map<String, String> params = new HashMap<>();
                if(column!= null){
                    params.put("type", type);
                    params.put("column", column);
                }
                return params;
            }
        };
        getRequestQueue().add(rqListStoryByType);
    }

    public void getStoryList(final String column, final String type, final List<Story> storyList, final ColumnStoryListAdapter columnStoryListAdapter, final int count){
        StringRequest rqListStoryByType = new StringRequest(Request.Method.POST, GET_STORY_LIST_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
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
                        storyList.add(new Story(id,name,author,status,type,avatar,review));
                    }
                    columnStoryListAdapter.setCount(count);
                    columnStoryListAdapter.notifyDataSetChanged();
//                    Toast.makeText(staticContext,"size "+storyList.size(),Toast.LENGTH_LONG).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(staticContext,"Exception!",Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(staticContext,"Error",Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams(){
                Map<String, String> params = new HashMap<>();
                if(column!= null){
                    params.put("type", type);
                    params.put("column", column);
                }
                return params;
            }
        };
        getRequestQueue().add(rqListStoryByType);
    }

    public void getTotalChapter(String url, final int idStory, final TextView totalChapter){
        StringRequest rqGetTotalChapter = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                String n = response;
                totalChapter.setText("Số chương: "+n);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                totalChapter.setText("Số chương: 0");
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("idStory",""+idStory);
                return params;
            }
        };
        getRequestQueue().add(rqGetTotalChapter);
    }

    public  void getChapterList(final int idStory, final List<Chapter> chapterList, final ChapterListAdapter chapterListAdapter){
        StringRequest rqChapterList = new StringRequest(Request.Method.POST, GET_CHAPTER_LIST_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray chapterArray = new JSONArray(response);
                    for(int i = 0; i<chapterArray.length();i++){
                        JSONObject chapter = chapterArray.getJSONObject(i);
                        int idChapter = chapter.getInt("IDChapter");
                        int idStory = chapter.getInt("IDStory");
                        String chapterName = chapter.getString("ChapterName");
                        chapterList.add(new Chapter(idChapter,idStory,chapterName));
                    }
                    chapterListAdapter.setCount(chapterList.size());
                    chapterListAdapter.notifyDataSetChanged();
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
                params.put("idStory", ""+idStory);
                return params;
            }
        };
        getRequestQueue().add(rqChapterList);
    }

    public  void getChapterList(final int idStory, final List<Chapter> chapterList, final ChapterListAdapter chapterListAdapter, final String number){
        StringRequest rqChapterList = new StringRequest(Request.Method.POST, GET_CHAPTER_LIST_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray chapterArray = new JSONArray(response);
                    for(int i = 0; i<chapterArray.length();i++){
                        JSONObject chapter = chapterArray.getJSONObject(i);
                        if(chapter.getString("ChapterName").contains("Chương "+number)){
                            int idChapter = chapter.getInt("IDChapter");
                            int idStory = chapter.getInt("IDStory");
                            String chapterName = chapter.getString("ChapterName");
                            chapterList.add(new Chapter(idChapter,idStory,chapterName));
                        }
                    }
                    chapterListAdapter.setCount(chapterList.size());
                    chapterListAdapter.notifyDataSetChanged();
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
                params.put("idStory", ""+idStory);
                return params;
            }
        };
        getRequestQueue().add(rqChapterList);
    }

    public void getStoryName(final int idStory, final TextView txtNameStory){
        StringRequest rqListStoryByType = new StringRequest(Request.Method.POST, GET_STORY_LIST_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for(int i = 0; i<jsonArray.length();i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        if(jsonObject.getInt("ID")==idStory){
                            String name = jsonObject.getString("Name");
                            txtNameStory.setText(name);
                        }

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(staticContext,"Exception!",Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(staticContext,"Error",Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams(){
                Map<String, String> params = new HashMap<>();
                    params.put("type", ""+idStory);
                    params.put("column", "id");
                return params;
            }
        };
        getRequestQueue().add(rqListStoryByType);
    }
}