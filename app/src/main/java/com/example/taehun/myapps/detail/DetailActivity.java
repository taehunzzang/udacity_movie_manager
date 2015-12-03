package com.example.taehun.myapps.detail;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.taehun.myapps.MovieItem;
import com.example.taehun.myapps.R;

/**
 * Created by taehun on 15. 8. 13..
 */
public class DetailActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_activity);

        if (savedInstanceState == null) {
            Bundle arguments = new Bundle();
            arguments = getIntent().getExtras();
            MovieItem m  = arguments.getParcelable("movieItem");

                Log.e("","m.getTitle() : "+m.getTitle());

            arguments.putParcelable("movieItem", m);
            MovieDetail fragment = new MovieDetail();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction().add(R.id.detail_container, fragment).commit();
        }
    }

}
