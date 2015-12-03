package com.example.taehun.myapps;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.GridView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.taehun.myapps.detail.MovieDetail;
import com.example.taehun.myapps.util.CustomJsonRequest;
import com.example.taehun.myapps.util.DbHelper;
import com.example.taehun.myapps.util.MoviesVolley;
import com.example.taehun.myapps.util.ValuesStatic;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MovieMain extends Fragment {

    MoviesVolley helper = MoviesVolley.getInstance();
    String sortType = "popularity.desc";
    static final String STATE_SORTING = "sortType";
    int optionMenuPosition = 0;


    //-Genre Data------
    ArrayList<MovieGenresItems> mGenreItems;
    //-------
    ArrayList<MovieItem> mData;
    MovieGridAdapter mAdapter;
    GridView mGridview;

    DbHelper mDbHelper;
    SQLiteDatabase mdb;

    public MovieMain() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Add this line in order for this fragment to handle menu events.
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_movie_main, container, false);

        mDbHelper = new DbHelper(getActivity());
        mdb = mDbHelper.getWritableDatabase();
        mGenreItems = new ArrayList<MovieGenresItems>();
        initData(rootView);
        if (savedInstanceState != null) {
            sortType = savedInstanceState.getString(STATE_SORTING);
            mData = savedInstanceState.getParcelableArrayList("key");
            optionMenuPosition = savedInstanceState.getInt("menuPosition");
            mAdapter.addAll(mData);
            mAdapter.notifyDataSetChanged();
        } else {
            sortType = "popularity.desc";
            optionMenuPosition = 0;
            loadGerneData();
            loadMoviesData();
        }

        return rootView;
    }


    public void insert(String gerneNum, String gerneString) {
        ContentValues values = new ContentValues();
        values.put("gerneNum", Integer.parseInt(gerneNum));
        values.put("gerneString", gerneString);
        mdb.insert("movieDB", null, values); // 테이블/널컬럼핵/데이터(널컬럼핵=디폴트)
        // tip : 마우스를 db.insert에 올려보면 매개변수가 어떤 것이 와야 하는지 알 수 있다.
    }

    public void insertMovie(MovieItem subItem) {
        ContentValues values = new ContentValues();
        Log.e("","@@@subItem.getTitle() : "+subItem.getTitle());
        values.put("title", subItem.getTitle());
        values.put("genre_ids", subItem.getGenre_ids());
        values.put("movie_id", subItem.getId());
        values.put("overview", subItem.getOverview());
        values.put("release_date", subItem.getRelease_date());
        values.put("poster_path", subItem.getPoster_path());
        values.put("popularity", subItem.getPopularity());
        values.put("vote_average", subItem.getVote_average());
        values.put("vote_count", subItem.getVote_count());
        values.put("favor", 0);//0is false 1 is true
        mdb.insert("FAVOR", null, values); // 테이블/널컬럼핵/데이터(널컬럼핵=디폴트)
        // tip : 마우스를 db.insert에 올려보면 매개변수가 어떤 것이 와야 하는지 알 수 있다.
    }


    private void initData(View view) {
        mData = new ArrayList<MovieItem>();
        mAdapter = new MovieGridAdapter(getActivity(), R.layout.movie_row, mData);
        mGridview = (GridView) view.findViewById(R.id.gridView);
        int divider = 2;
        Display display = ((WindowManager) getActivity().getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        if (display.getWidth() > display.getHeight()) {
            divider = 3;
        }
        mGridview.setNumColumns(divider);
        mGridview.setAdapter(mAdapter);
        mGridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MovieItem item = (MovieItem) parent.getItemAtPosition(position);
                ((Callback) getActivity()).onItemSelected(item);
            }
        });

    }

    public void loadGerneData() {
        String RECENT_API_ENDPOINT = "http://api.themoviedb.org/3/genre/movie/list?&api_key=" + ValuesStatic.MY_API_KEY;
        CustomJsonRequest request = new CustomJsonRequest(Request.Method.GET, RECENT_API_ENDPOINT, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                Log.e("", " jsonObject_Gerne : " + jsonObject);

                try {
                    JSONArray jArray = jsonObject.getJSONArray("genres");
                    for (int i = 0; i < jArray.length(); i++) {
                        MovieGenresItems item = new MovieGenresItems();
                        JSONObject jObj = jArray.getJSONObject(i);
                        item.setGenreName(jObj.optString("name"));
                        item.setId(jObj.optInt("id"));
                        mGenreItems.add(item);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


                for (MovieGenresItems item1 : mGenreItems) {
                    Log.e("", " item name : " + item1.getId() + "name : " + item1.getGenreName());
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        });
        request.setPriority(Request.Priority.HIGH);
        helper.add(request);
    }

    public void loadMoviesData() {
        mData.clear();
        String RECENT_API_ENDPOINT = "http://api.themoviedb.org/3/discover/movie?sort_by=" + sortType + "&api_key=" + ValuesStatic.MY_API_KEY;
        Log.e("", "mData_ : " + mData.size() + "url : " + RECENT_API_ENDPOINT);
        CustomJsonRequest request = new CustomJsonRequest
                (Request.Method.GET, RECENT_API_ENDPOINT, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                            Log.e("", "movies_response : " + response);
//                            String results = response.optString("results");
                            JSONArray mJsonArryaResults = response.getJSONArray("results");
                            for (int i = 0; i < mJsonArryaResults.length(); i++) {
                                JSONObject mJsonObject = mJsonArryaResults.getJSONObject(i);
                                MovieItem subItem = new MovieItem();
                                subItem.setTitle(mJsonObject.optString("title"));
                                mJsonObject.optString("genre_ids");
                                subItem.setGenre_ids(getGenreValue(mJsonObject.getJSONArray("genre_ids")));
                                subItem.setId(mJsonObject.optString("id"));//movie_id
                                subItem.setOverview(mJsonObject.optString("overview"));
                                subItem.setRelease_date(mJsonObject.optString("release_date"));
                                subItem.setPoster_path(mJsonObject.optString("poster_path"));
                                subItem.setPopularity(mJsonObject.optString("popularity"));
                                subItem.setVote_average(mJsonObject.optString("vote_average"));
                                subItem.setVote_count(mJsonObject.optString("vote_count"));

                                insertMovie(subItem);
                                mData.add(subItem);
                                Log.e("", "mData_ : " + mData.size());

                            }
                            mAdapter = new MovieGridAdapter(getActivity(), R.layout.movie_row, mData);
                            mGridview.setAdapter(mAdapter);
//                            mAdapter.notifyDataSetChanged();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        if(MainActivity.mTwoPane && mData.size()>0){
                            Bundle args = new Bundle();
                            args.putParcelable("movieItem", mData.get(0));
                            MovieDetail fragment = new MovieDetail();
                            fragment.setArguments(args);
                            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.detail_container, fragment, "").commit();
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

    private String getGenreValue(JSONArray genre_ids) {
        int tempGenre = 0;
        String returnGenre = "";
        for (int i = 0; i < genre_ids.length(); i++) {
            try {
//                tempGenre = genre_ids.get(i).toString();
                tempGenre = Integer.parseInt(genre_ids.get(i).toString());

                for (int j = 0; j < mGenreItems.size(); j++) {
                    if (tempGenre == mGenreItems.get(j).getId()) {
                        returnGenre = mGenreItems.get(j).getGenreName();
                        break;
                    }
                }
                if (returnGenre.length() != 0) {
                    break;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
//        mGenreItems
        Log.e("", "select_genre" + returnGenre);
        return returnGenre;
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_movie_main, menu);
    }


    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
//        if(sortType.equals("popularity.desc")){
//            menu.getItem(0).setChecked(true);
//        }else if(sortType.equals("revenue.desc")){
//            menu.getItem(1).setChecked(true);
//        }else{
//            menu.getItem(2).setChecked(true);
//        }

        menu.getItem(optionMenuPosition).setChecked(true);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_popular) {
            item.setChecked(true);
            sortType = "popularity.desc";
            optionMenuPosition = 0;
            loadMoviesData();
        } else if (id == R.id.action_rated) {
            item.setChecked(true);
            sortType = "revenue.desc";
            optionMenuPosition = 1;
            loadMoviesData();
        } else if (id == R.id.action_favorites) {
            item.setChecked(true);
            loadMovesLocal();
            optionMenuPosition = 2;
        }
        return super.onOptionsItemSelected(item);
    }

    private void loadMovesLocal() {
        String sql = "select * from FAVOR WHERE favor = '1' ;";
//        mdb.query("",);
        try {


            Cursor c = mdb.rawQuery(sql, null);
            c.moveToFirst();
            mData.clear();
            while (!c.isAfterLast()) {
                MovieItem subItem = new MovieItem();
                Log.e("","@@@@ : "+c.getString(c.getColumnIndex("title"))+" ,  "+c.getColumnIndex("title"));

                subItem.setTitle(c.getString(c.getColumnIndex("title")));
                subItem.setGenre_ids(c.getString(c.getColumnIndex("genre_ids")));
                subItem.setId(c.getString(c.getColumnIndex("movie_id")));
                subItem.setOverview(c.getString(c.getColumnIndex("overview")));
                subItem.setRelease_date(c.getString(c.getColumnIndex("release_date")));
                subItem.setPoster_path(c.getString(c.getColumnIndex("poster_path")));
                subItem.setPopularity(c.getString(c.getColumnIndex("popularity")));
                subItem.setVote_average(c.getString(c.getColumnIndex("vote_average")));
                subItem.setVote_count(c.getString(c.getColumnIndex("vote_count")));

                insertMovie(subItem);
                mData.add(subItem);
                c.moveToNext();
            }
//            mAdapter.notifyDataSetChanged();
            mAdapter = new MovieGridAdapter(getActivity(), R.layout.movie_row, mData);
            mGridview.setAdapter(mAdapter);

            if(MainActivity.mTwoPane && mData.size()>0){
                Bundle args = new Bundle();
                args.putParcelable("movieItem", mData.get(0));
                MovieDetail fragment = new MovieDetail();
                fragment.setArguments(args);
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.detail_container, fragment, "").commit();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putString(STATE_SORTING, sortType);
        savedInstanceState.putInt("menuPosition", optionMenuPosition);

        savedInstanceState.putParcelableArrayList("key", mData);

        super.onSaveInstanceState(savedInstanceState);
    }


    public interface Callback {
        public void onItemSelected(MovieItem item);
    }
}
