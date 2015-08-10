package com.example.taehun.myapps.detail;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.taehun.myapps.MovieItem;
import com.example.taehun.myapps.R;
import com.example.taehun.myapps.util.CustomJsonRequest;
import com.example.taehun.myapps.util.MoviesVolley;
import com.example.taehun.myapps.util.ValuesStatic;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MovieDetail extends AppCompatActivity {

    TextView movieTitle;
    TextView movieLaunch;
    TextView movieRuningTime;
    TextView movieDate;
    ImageView movieImage;
    ImageView movieMark;
    TextView movieStroy;
    MoviesVolley helper = MoviesVolley.getInstance();
    static final String URL_PRE_FIX = "http://image.tmdb.org/t/p/w185/";

    CustomJsonRequest request;
    private String movieID = "";
    private String MOVIE_TRAILERS;
    private String MOVIE_REVIES;
    ArrayList<ItemReviews> mItemReviews;
    ArrayList<ItemTrailers> mItemTrailers;

    ListView listTrailer;
    ArrayAdapter<ItemTrailers> mTrailerAdapter;
    ListView listReview;
    ArrayAdapter<ItemReviews> mReviewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        initUI();
        Bundle extra = getIntent().getExtras();
        MovieItem mItem = extra.getParcelable("movieItem");
        Log.e("", "mItem : " + mItem);
//

        movieTitle.setText(mItem.getTitle());
        movieTitle.setSelected(true);
        movieLaunch.setText(mItem.getRelease_date());
        movieRuningTime.setText("Rate : "+mItem.getVote_average());
        movieDate.setText("Vote  : " + mItem.getVote_count());
        if(mItem.getPoster_path() != null) {

            Picasso.with(this).load(URL_PRE_FIX+mItem.getPoster_path()).into(movieImage);
        }
        movieID = mItem.getId();
        MOVIE_TRAILERS = "http://api.themoviedb.org/3/movie/"+movieID+"/videos?api_key="+ ValuesStatic.MY_API_KEY;
        MOVIE_REVIES = "http://api.themoviedb.org/3/movie/"+movieID+"/reviews?api_key="+ValuesStatic.MY_API_KEY;
        movieStroy.setText(mItem.getOverview());
        fatchVideo();
        fatchReviews();


        Log.e("", "review : " + MOVIE_REVIES);
        Log.e("", "review 2 : " + MOVIE_TRAILERS);
        movieMark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        listTrailer.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ItemTrailers item = (ItemTrailers) parent.getItemAtPosition(position);
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?v="+item.getKey())));
            }
        });

    }



    private void fatchReviews() {
        mItemReviews = new ArrayList<ItemReviews>();
        request = new CustomJsonRequest(Request.Method.GET, MOVIE_REVIES, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                Log.e("", " jsonObject_Reviews : " + jsonObject);

                try {
                    JSONArray jArray = jsonObject.getJSONArray("results");
                    ItemReviews item = new ItemReviews();
                    for (int i = 0; i<jArray.length();i++){
                        JSONObject jObj = jArray.getJSONObject(i);
                        Log.e(""," Text : "+jObj.optString(""));
                        item.setId(jObj.optString("id"));
                        item.setAuthor(jObj.optString("author"));
                        item.setContent(jObj.optString("content"));
                        mItemReviews.add(item);
                        Log.e("","1 : "+item.getId()+", "+item.getAuthor()+", "+item.getContent());
                    }

                    mReviewAdapter = new ReviewAdapter(MovieDetail.this,R.layout.review_detail_item,mItemReviews);
                    listReview.setAdapter(mReviewAdapter);

                } catch (JSONException e) {
                    e.printStackTrace();
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

    private void fatchVideo() {

        mItemTrailers = new ArrayList<ItemTrailers>();
        request = new CustomJsonRequest(Request.Method.GET, MOVIE_TRAILERS, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                Log.e("", " jsonObject_Trailers : " + jsonObject);

                try {
                    JSONArray jArray = jsonObject.getJSONArray("results");
                    for (int i  = 0 ;i <jArray.length();i++){
                        ItemTrailers item = new ItemTrailers();
                        JSONObject jObj  = jArray.getJSONObject(i);
                        item.setId(jObj.optString("id"));
                        item.setKey(jObj.optString("key"));
                        item.setName(jObj.optString("name"));
                        mItemTrailers.add(item);

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                mTrailerAdapter = new TrailerAdapter(MovieDetail.this,R.layout.item_trailer,mItemTrailers);
                listTrailer.setAdapter(mTrailerAdapter);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        });
        request.setPriority(Request.Priority.HIGH);
        helper.add(request);
    }

    private void initUI() {
        movieTitle = (TextView) findViewById(R.id.movieTitle);
        movieLaunch = (TextView) findViewById(R.id.movieLaunch);
        movieRuningTime = (TextView) findViewById(R.id.movieRuningTime);
        movieDate = (TextView) findViewById(R.id.movieDate);
        movieImage = (ImageView) findViewById(R.id.movieImage);
        movieMark = (ImageView) findViewById(R.id.movieMark);
        movieStroy = (TextView) findViewById(R.id.movieStroy);

        listTrailer = (ListView) findViewById(R.id.listTrailer);
        listReview = (ListView) findViewById(R.id.listReview);


    }


}
