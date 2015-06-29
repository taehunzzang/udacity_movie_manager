package com.example.taehun.myapps.movies;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.taehun.myapps.R;
import com.squareup.picasso.Picasso;

public class MovieDetail extends AppCompatActivity {

    TextView movieTitle;
    TextView movieLaunch;
    ImageView movieImage;
    TextView movieStroy;
    static final String URL_PRE_FIX = "http://image.tmdb.org/t/p/w185/";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        initUI();
        Bundle extra = getIntent().getExtras();
        movieTitle.setText(extra.getString("title"));
        movieLaunch.setText(extra.getString("releaseDate"));

        Picasso.with(this).load(URL_PRE_FIX+extra.getString("image")).into(movieImage);
        movieStroy.setText(extra.getString("story"));
    }

    private void initUI() {
        movieTitle = (TextView) findViewById(R.id.movieTitle);
        movieLaunch = (TextView) findViewById(R.id.movieLaunch);
        movieImage = (ImageView) findViewById(R.id.movieImage);
        movieStroy = (TextView) findViewById(R.id.movieStroy);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_movie_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
