package com.example.taehun.myapps.detail;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.taehun.myapps.MovieItem;
import com.example.taehun.myapps.R;
import com.example.taehun.myapps.util.CustomJsonRequest;
import com.example.taehun.myapps.util.DbHelper;
import com.example.taehun.myapps.util.MoviesVolley;
import com.example.taehun.myapps.util.ValuesStatic;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

//import android.support.v7.widget.ShareActionProvider;

public class MovieDetail extends Fragment {

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

    Bundle arguments;
    MovieItem mItem;


    //------
    LinearLayout ll_youtube, ll_reviews;


    DbHelper mDbHelper;
    SQLiteDatabase mdb;
    public int favorMovie= 0 ;





   public MovieDetail(){

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Add this line in order for this fragment to handle menu events.
        //setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_movie_detail, container, false);
        initUI(rootView);
        setDb();
        try {
            arguments = getArguments();
            if (arguments != null) {
                mItem = arguments.getParcelable("movieItem");
            }
//            Bundle bundle_1 = null;
            if (savedInstanceState != null) {
//                bundle_1 = savedInstanceState.getBundle("movieItem");
//                mItem = bundle_1.getParcelable("movieItem");
                mItem = savedInstanceState.getParcelable("key");
                Log.e("","savedInstanceState details : "+mItem.getTitle());
            }


            if(mItem!=null){
                movieTitle.setText(mItem.getTitle());
                movieTitle.setSelected(true);
                movieLaunch.setText(mItem.getRelease_date());
                movieRuningTime.setText("Rate : " + mItem.getVote_average());
                movieDate.setText("Vote  : " + mItem.getVote_count());
                if (mItem.getPoster_path() != null) {

                    Picasso.with(getActivity()).load(URL_PRE_FIX + mItem.getPoster_path()).into(movieImage);
                }
                movieID = mItem.getId();
                MOVIE_TRAILERS = "http://api.themoviedb.org/3/movie/" + movieID + "/videos?api_key=" + ValuesStatic.MY_API_KEY;
                MOVIE_REVIES = "http://api.themoviedb.org/3/movie/" + movieID + "/reviews?api_key=" + ValuesStatic.MY_API_KEY;
                movieStroy.setText(mItem.getOverview());
            }


        } catch (Exception e) {
            e.printStackTrace();
        }


        return rootView;
    }

    private void setDb() {
        mDbHelper = new DbHelper(getActivity());
        mdb = mDbHelper.getWritableDatabase();
        String sql = "select * from Favor ;";
        //updateFavorMovie("");
    }

    private void updateFavorMovie(int s) {

        ContentValues updateRowValue = new ContentValues();
        updateRowValue.put("favor", s);
        mdb.update("FAVOR", updateRowValue, "movie_id=" + movieID, null);
    }

    void insertTrailer(ItemTrailers item){
        ContentValues values = new ContentValues();
        values.put("movie_id", item.getId());
        values.put("url_add", item.getKey());
        values.put("movie_title", item.getName());
        mdb.insert("TRAILER", null, values); // 테이블/널컬럼핵/데이터(널컬럼핵=디폴트)
    }

    void insertReviews(ItemReviews item){
        ContentValues values = new ContentValues();
        values.put("movie_id", item.getId());
        values.put("auth", item.getAuthor());
        values.put("content", item.getContent());
        mdb.insert("REVIEWS", null, values); // 테이블/널컬럼핵/데이터(널컬럼핵=디폴트)
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        fatchVideo();
        fatchReviews();


        Log.e("", "review : " + MOVIE_REVIES);
        Log.e("", "review 2 : " + MOVIE_TRAILERS);
        movieMark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (favorMovie == 1) {
                    updateFavorMovie(0);
                } else {
                    updateFavorMovie(1);
                }
                isFavorMovie();

            }
        });

        isFavorMovie();

    }

    public void setGrayScale(ImageView v){
        ColorMatrix matrix = new ColorMatrix();
        if(favorMovie==1){
            matrix.setSaturation(1); //0 means grayscale
        }else{
            matrix.setSaturation(0); //0 means grayscale
        }

        ColorMatrixColorFilter cf = new ColorMatrixColorFilter(matrix);
        v.setColorFilter(cf);
    }

    private void fatchReviews() {
        request = new CustomJsonRequest(Request.Method.GET, MOVIE_REVIES, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                Log.e("", " jsonObject_Reviews : " + jsonObject);

                try {
                    JSONArray jArray = jsonObject.getJSONArray("results");
                    ItemReviews item = new ItemReviews();
                    for (int i = 0; i < jArray.length(); i++) {
                        JSONObject jObj = jArray.getJSONObject(i);
                        Log.e("", " Text : " + jObj.optString(""));
                        item.setId(jObj.optString("id"));
                        item.setAuthor(jObj.optString("author"));
                        item.setContent(jObj.optString("content"));

                        insertReviews(item);

                        try{
                            LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                            LinearLayout addLinear =  (LinearLayout) inflater.inflate(R.layout.review_detail_item, null);;
                            TextView author, content;
                            author = (TextView) addLinear.findViewById(R.id.author);
                            content = (TextView) addLinear.findViewById(R.id.content);
                            author.setText(jObj.optString("author"));
                            content.setText(jObj.optString("content"));
                            ll_reviews.addView(addLinear);
                        }catch(Exception e){
                            e.printStackTrace();
                        }


                        Log.e("", "1 : " + item.getId() + ", " + item.getAuthor() + ", " + item.getContent());
                    }

//                    mReviewAdapter = new ReviewAdapter(getActivity(), R.layout.review_detail_item, mItemReviews);
//                    listReview.setAdapter(mReviewAdapter);

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

        request = new CustomJsonRequest(Request.Method.GET, MOVIE_TRAILERS, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                Log.e("", " jsonObject_Trailers : " + jsonObject);

                try {
                    JSONArray jArray = jsonObject.getJSONArray("results");
                    for (int i = 0; i < jArray.length(); i++) {
                        ItemTrailers item = new ItemTrailers();
                        JSONObject jObj = jArray.getJSONObject(i);
                        item.setId(jObj.optString("id"));
                        item.setKey(jObj.optString("key"));
                        item.setName(jObj.optString("name"));
                        insertTrailer(item);

                        //mItemTrailers.add(item);
                        final String tempKey = jObj.optString("key");
                        try{
                            LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                            LinearLayout addLinear = null;
                            TextView movieTitle;
                            addLinear  = (LinearLayout) inflater.inflate(R.layout.item_trailer, null);
                            movieTitle = (TextView) addLinear.findViewById(R.id.movieTitle);
                            movieTitle.setText(jObj.optString("name"));
                            addLinear.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
//                                ItemTrailers item = (ItemTrailers) parent.getItemAtPosition(position);
                                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?v=" + tempKey)));
                                }
                            });

                            ll_youtube.addView(addLinear);
                        }catch(Exception e){

                        }
                    }
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

    private void initUI(View view) {
        movieTitle = (TextView) view.findViewById(R.id.movieTitle);
        movieLaunch = (TextView) view.findViewById(R.id.movieLaunch);
        movieRuningTime = (TextView) view.findViewById(R.id.movieRuningTime);
        movieDate = (TextView) view.findViewById(R.id.movieDate);
        movieImage = (ImageView) view.findViewById(R.id.movieImage);
        movieMark = (ImageView) view.findViewById(R.id.movieMark);
        movieStroy = (TextView) view.findViewById(R.id.movieStroy);


        ll_youtube = (LinearLayout) view.findViewById(R.id.ll_youtube);
        ll_reviews = (LinearLayout) view.findViewById(R.id.ll_reviews);


    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putBundle("arguments", arguments);
        savedInstanceState.putParcelable("key", mItem);

        super.onSaveInstanceState(savedInstanceState);
    }

    public void isFavorMovie() {

        Cursor c = mdb.rawQuery("select * from FAVOR WHERE movie_id = '"+movieID+"';",null);
        c.moveToFirst();
        if(c.getCount()>0){
            favorMovie = c.getInt(c.getColumnIndex("favor"));
            setGrayScale(movieMark);
        }
    }



    //    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater){
//        inflater.inflate(R.menu.menu_movie_detail, menu);
//        MenuItem item = menu.findItem(R.id.action_share);
//        ShareActionProvider mShareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(item);
//        if (mShareActionProvider != null) {
//            mShareActionProvider.setShareIntent(createShareIntent());
//        }else{
//        }
//    }
//
//    private Intent createShareIntent() {
//        Intent shareIntent = new Intent(Intent.ACTION_SEND);
//        shareIntent.setType("text/plain");
//        shareIntent.putExtra(Intent.EXTRA_TEXT,
//                "this text will be shared");
//        return shareIntent;
//    }

}
