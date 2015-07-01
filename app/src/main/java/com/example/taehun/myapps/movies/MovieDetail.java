package com.example.taehun.myapps.movies;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.taehun.myapps.R;
import com.squareup.picasso.Picasso;

public class MovieDetail extends AppCompatActivity {

    TextView movieTitle;
    TextView movieLaunch;
    TextView movieRuningTime;
    TextView movieDate;
    ImageView movieImage;
    TextView movieStroy;
    static final String URL_PRE_FIX = "http://image.tmdb.org/t/p/w185/";
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
        movieLaunch.setText(mItem.getRelease_date());
        movieRuningTime.setText("Rate : "+mItem.getVote_average());
        movieDate.setText("Vote  : "+mItem.getVote_count());
        Picasso.with(this).load(URL_PRE_FIX+mItem.getPoster_path()).into(movieImage);
        movieStroy.setText(mItem.getOverview());
    }

    private void initUI() {
        movieTitle = (TextView) findViewById(R.id.movieTitle);
        movieLaunch = (TextView) findViewById(R.id.movieLaunch);
        movieRuningTime = (TextView) findViewById(R.id.movieRuningTime);
        movieDate = (TextView) findViewById(R.id.movieDate);
        movieImage = (ImageView) findViewById(R.id.movieImage);
        movieStroy = (TextView) findViewById(R.id.movieStroy);

    }

}
