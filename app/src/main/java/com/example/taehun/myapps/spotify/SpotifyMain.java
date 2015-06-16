package com.example.taehun.myapps.spotify;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.taehun.myapps.R;

import java.util.ArrayList;
import java.util.logging.Handler;

import kaaes.spotify.webapi.android.SpotifyApi;
import kaaes.spotify.webapi.android.SpotifyService;
import kaaes.spotify.webapi.android.models.ArtistsPager;


public class SpotifyMain extends ActionBarActivity {

    ListView itemListiew;
    ArrayList<SpotifyItem> items;
    SpotifyAdapter adapter;

    EditText artistKeyword;
    Button artistSearch;

    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.spotify_main);
        initUI();
        setVaues();//f
    }

    private void setVaues() {
        items = new ArrayList<SpotifyItem>();
//        for (int i = 0; i < 10; i++) {
//            items.add(new SpotifyItem());
//        }
        adapter = new SpotifyAdapter(this, R.layout.spotify_main_item, items);
        itemListiew.setAdapter(adapter);
    }

    private void initUI() {
        itemListiew = (ListView) findViewById(R.id.itemListiew);
        artistKeyword = (EditText) findViewById(R.id.artistKeyword);
        artistSearch = (Button) findViewById(R.id.artistSearch);

        artistKeyword.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    runSpotifyAPI();
                    return true;
                }
                return false;
            }
        });
    }

    public void searchClick(View v) {
        runSpotifyAPI();
    }

    private void runSpotifyAPI() {
        if (artistKeyword.getText().length() == 0) {
            Toast.makeText(this, getResources().getString(R.string.empty_keyword), Toast.LENGTH_SHORT).show();
        } else {
            new InternetAccess().execute();
            hideKeyboard();
        }
    }

    private void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(artistKeyword.getWindowToken(), 0);
    }

    class InternetAccess extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            SpotifyApi api = new SpotifyApi();
            SpotifyService spotify = api.getService();
            final ArtistsPager results = spotify.searchArtists(artistKeyword.getText().toString());
            Log.e("", " results.artists : " + results);
            int loopCnt = 10;
            if (results.artists.total < 10) {
                loopCnt = results.artists.total;
            }
            for (int i = 0; i < loopCnt; i++) {
                Log.e("", " results.artists : " + results.artists.items.get(i).name);
                SpotifyItem item = new SpotifyItem();
                item.setArtistName(results.artists.items.get(i).name);
                item.setArtistEtc(results.artists.items.get(i).uri);
                for (int j = 0; j < results.artists.items.get(i).images.size(); j++) {

                    item.setImgName(results.artists.items.get(i).images.get(j).url);
                }

                items.add(item);

            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            adapter.notifyDataSetChanged();
        }
    }
}
