package com.example.taehun.myapps;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.taehun.myapps.detail.DetailActivity;
import com.example.taehun.myapps.detail.MovieDetail;

/**
 * Created by taehun on 15. 8. 13..
 */
public class MainActivity extends AppCompatActivity implements MovieMain.Callback{
    public static boolean mTwoPane;
    private static final String DETAILFRAGMENT_TAG = "DFTAG";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        if (findViewById(R.id.detail_container) != null) {
            mTwoPane = true;
            Log.e("","mTwoPane 1 "+mTwoPane);
            if (savedInstanceState == null) {
                getSupportFragmentManager().beginTransaction().replace(R.id.detail_container, new MovieDetail(), DETAILFRAGMENT_TAG).commit();
            }
        } else {
            mTwoPane = false;
            Log.e("","mTwoPane 2 "+mTwoPane);
            getSupportActionBar().setElevation(0f);
        }
        Log.e("","mTwoPane 3 "+mTwoPane);
        //getSupportFragmentManager().beginTransaction().replace(R.id.detail_container, new MovieDetail(), DETAILFRAGMENT_TAG).commit();
        MovieMain forecastFragment =  ((MovieMain)getSupportFragmentManager().findFragmentById(R.id.gridView));


    }

    @Override
    protected void onResume() {
        super.onResume();

        MovieMain ff = (MovieMain)getSupportFragmentManager().findFragmentById(R.id.gridView);
        MovieDetail df = (MovieDetail)getSupportFragmentManager().findFragmentByTag(DETAILFRAGMENT_TAG);

    }

    @Override
    public void onItemSelected(MovieItem item) {
        Log.e("","mTwoPane onItemSelected ~~~~ : "+mTwoPane);
        if(mTwoPane){
            Bundle args = new Bundle();
            args.putParcelable("movieItem", item);
//            args.putParcelable(DetailFragment.DETAIL_URI, contentUri);

            MovieDetail fragment = new MovieDetail();
            fragment.setArguments(args);
            getSupportFragmentManager().beginTransaction().replace(R.id.detail_container, fragment, DETAILFRAGMENT_TAG).commit();

        }else{
            Intent intent = new Intent(this, DetailActivity.class); //DetailActivity.class is the second activity where i show the the movie details
            intent.putExtra("movieItem", item);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(this, findViewById(R.id.rowImage), "transition_poster");
                startActivity(intent, options.toBundle());
            } else {
                startActivity(intent);
            }
        }

    }
}
