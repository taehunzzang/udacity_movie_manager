package com.example.taehun.myapps;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.GridView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class MovieMain extends AppCompatActivity {

    MoviesVolley helper = MoviesVolley.getInstance();
    final static String MY_API_KEY = "c64cba74ee6e14eae65737f936242f41";
    String sortType="popularity.desc";
    static final String STATE_SORTING = "sortType";


    ArrayList<MovieItem> mData;
    MovieGridAdapter mAdapter;
    GridView mGridview;

    DbHelper mDbHelper;
    SQLiteDatabase mdb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().requestFeature(Window.FEATURE_ACTIVITY_TRANSITIONS);
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_movie_main);

        if (savedInstanceState != null) {
            // Restore value of members from saved state
            sortType = savedInstanceState.getString(STATE_SORTING);
        } else {
            // Probably initialize members with default values for a new instance
            sortType = "popularity.desc";
        }

        initData();
        loadMoviesData();
        mDbHelper = new DbHelper(this);
        mdb = mDbHelper.getWritableDatabase();

        loadGerneData();
    }

    public void insert(String gerneNum, String gerneString) {

        ContentValues values = new ContentValues();
        values.put("gerneNum", Integer.parseInt(gerneNum));
        values.put("gerneString", gerneString);
        mdb.insert("movieDB", null, values); // 테이블/널컬럼핵/데이터(널컬럼핵=디폴트)
        // tip : 마우스를 db.insert에 올려보면 매개변수가 어떤 것이 와야 하는지 알 수 있다.
    }


    private void initData() {
        mData = new ArrayList<MovieItem>();
        mAdapter = new MovieGridAdapter(this,R.layout.movie_row,mData);
        mGridview = (GridView) findViewById(R.id.gridView);
        mGridview.setAdapter(mAdapter);
        mGridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MovieItem item = (MovieItem) parent.getItemAtPosition(position);
//                Intent intent = new Intent(MovieMain.this, MovieDetail.class);
//                intent.putExtra("movieItem", item);
//                startActivity(intent);

                Intent intent = new Intent(MovieMain.this, MovieDetail.class); //DetailActivity.class is the second activity where i show the the movie details
//                intent.putExtra("INTENT_KEY", passedMovie);
                intent.putExtra("movieItem", item);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    ActivityOptionsCompat options = ActivityOptionsCompat.
                            makeSceneTransitionAnimation(MovieMain.this,
                                    view.findViewById(R.id.rowImage), "transition_poster");
                    startActivity(intent, options.toBundle());
                } else {
                    startActivity(intent);
                }
            }
        });

    }
    public void loadGerneData(){
        String RECENT_API_ENDPOINT = "http://api.themoviedb.org/3/genre/movie/list?&api_key="+MY_API_KEY;
        CustomJsonRequest request = new CustomJsonRequest(Request.Method.GET, RECENT_API_ENDPOINT, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                Log.e(""," jsonObject : "+jsonObject);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        });
        request.setPriority(Request.Priority.HIGH);
        helper.add(request);
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

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putString(STATE_SORTING, sortType);
        super.onSaveInstanceState(savedInstanceState);
    }
}
