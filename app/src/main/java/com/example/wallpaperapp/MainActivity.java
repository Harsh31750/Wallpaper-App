package com.example.wallpaperapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String url = "https://pixabay.com/api/?key=5748007-e8acc894e452de677cd21f525&q=fish&image_type=photo";
    private static final String TAG = "MainActivity";
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private WallpaperAdapter adapter;
    private ArrayList<WallpaperData> arrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();



    }


    private void init(){
        recyclerView = findViewById(R.id.recycler_view);
        progressBar = findViewById(R.id.progressbar);
        arrayList = new ArrayList<>();
        adapter = new WallpaperAdapter(arrayList,getApplicationContext());

        GridLayoutManager layoutManager = new GridLayoutManager(this,2);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        loadData(url);

    }

    private void loadData(String url){
        progressBar.setVisibility(View.VISIBLE);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                progressBar.setVisibility(View.GONE);
                Log.d(TAG, "onResponse: REsponse : "+response);
                try {
                    JSONArray jsonArray = response.getJSONArray("hits");
                    Log.d(TAG, "onResponse: JsonArray : " + jsonArray);
                     for (int i = 0;i<jsonArray.length();i++){
                         JSONObject object = jsonArray.getJSONObject(i);

                         String largeImageUrl = object.getString("largeImageURL");
                         int likes = object.getInt("likes");

                         WallpaperData data = new WallpaperData();
                         data.setUrl(largeImageUrl);
                         data.setLikesCount(likes);

                         Log.d(TAG, "onResponse: Position : "+i + " Url is : "+ largeImageUrl);
                         arrayList.add(data);
                         adapter.notifyDataSetChanged();
                     }


                } catch (JSONException e) {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(getApplicationContext(),"Something went wrong",Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.e(TAG, "onErrorResponse: Error is : "+error );

            }
        }) ;

        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(jsonObjectRequest);

    }




}