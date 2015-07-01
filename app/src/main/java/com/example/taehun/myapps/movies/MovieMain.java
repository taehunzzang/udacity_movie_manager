package com.example.taehun.myapps.movies;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.taehun.myapps.MoviesVolley;
import com.example.taehun.myapps.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class MovieMain extends AppCompatActivity {

    MoviesVolley helper = MoviesVolley.getInstance();
    final static String MY_API_KEY = "c64cba74ee6e14eae65737f936242f41";
    String sortType="popularity.desc";

    ArrayList<MovieItem> mData;
    MovieGridAdapter mAdapter;
    GridView mGridview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_main);
        initData();
        loadMoviesData();
    }

    private void initData() {
        mData = new ArrayList<MovieItem>();
        mAdapter = new MovieGridAdapter(this,R.layout.movie_row,mData);
        mGridview = (GridView) findViewById(R.id.gridView);
        mGridview.setAdapter(mAdapter);
        mGridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MovieItem item  = (MovieItem) parent.getItemAtPosition(position);
                Intent intent = new Intent(MovieMain.this, MovieDetail.class);
                intent.putExtra("movieItem",item);
                startActivity(intent);
            }
        });

    }

    public void loadMoviesData(){
        mData.clear();
        String RECENT_API_ENDPOINT = "http://api.themoviedb.org/3/discover/movie?sort_by="+sortType+"&api_key="+MY_API_KEY;
        Log.e("","mData_ : "+mData.size()+"url : "+RECENT_API_ENDPOINT);
        CustomJsonRequest request = new CustomJsonRequest
                (Request.Method.GET, RECENT_API_ENDPOINT, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                            Log.e("","movies_response : "+response);
//                            String results = response.optString("results");
                            JSONArray mJsonArryaResults = response.getJSONArray("results");
                            for (int i =0;i<mJsonArryaResults.length();i++){
                                JSONObject mJsonObject  = mJsonArryaResults.getJSONObject(i);
                                MovieItem subItem = new MovieItem();
                                subItem.setTitle(mJsonObject.optString("title"));
                                subItem.setAdult(mJsonObject.optBoolean("adult"));
                                subItem.setBackdrop_path(mJsonObject.optString("backdrop_path"));
//                                subItem.setGenre_ids(mJsonObject.optBoolean("adult"));
                                subItem.setId(mJsonObject.optString("id"));
                                subItem.setOriginal_language(mJsonObject.optString("original_language"));
                                subItem.setOriginal_title(mJsonObject.optString("original_title"));
                                subItem.setOverview(mJsonObject.optString("overview"));
                                subItem.setRelease_date(mJsonObject.optString("release_date"));
                                subItem.setPoster_path(mJsonObject.optString("poster_path"));
                                subItem.setPopularity(mJsonObject.optString("popularity"));
                                subItem.setVideo(mJsonObject.optBoolean("video"));
                                subItem.setVote_average(mJsonObject.optString("vote_average"));
                                subItem.setVote_count(mJsonObject.optString("vote_count"));
                                mData.add(subItem);
                                Log.e("", "mData_ : " + mData.size());


//                                String genre_ids;

                            }
                            mAdapter.notifyDataSetChanged();

                            Log.e("", "  daffta.get(0).getBackdrop_path() " + mData.get(0).getBackdrop_path());

                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                });
        request.setPriority(Request.Priority.HIGH);
        helper.add(request);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_movie_main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_popular) {
//            Toast.makeText(MovieMain.this,"popular",Toast.LENGTH_SHORT).show();;
            sortType="popularity.desc";
            loadMoviesData();
            return true;
        }else if (id == R.id.action_rated) {
//            Toast.makeText(MovieMain.this,"rated",Toast.LENGTH_SHORT).show();;
            sortType="vote_average.desc";
            loadMoviesData();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
