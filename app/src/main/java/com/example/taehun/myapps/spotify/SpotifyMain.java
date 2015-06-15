package com.example.taehun.myapps.spotify;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
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
    }
    public void searchClick(View v){
        Toast.makeText(this,"" ,Toast.LENGTH_SHORT ).show();


        new InternetAccess().execute();

//        spotify.getAlbum("Beyonce", new Callback<Album>() {
//            @Override
//            public void success(Album album, Response response) {
//                Log.d("Album success", album.name);
////                album.images;
//            }
//
//            @Override
//            public void failure(RetrofitError error) {
//                Log.d("Album failure", error.toString());
//            }
//        });


    }
    class InternetAccess extends AsyncTask<Void,Void,Void>{

        @Override
        protected Void doInBackground(Void... params) {
            SpotifyApi api = new SpotifyApi();
            SpotifyService spotify = api.getService();
            final ArtistsPager results = spotify.searchArtists("coldplay");
            Log.e("", " results.artists : " + results);
            int loopCnt = 10;
            if(results.artists.total<10){
                loopCnt = results.artists.total;
            }
            for (int i =0;i<loopCnt;i++){
                Log.e("", " results.artists : " + results.artists.items.get(i).name);
                SpotifyItem item = new SpotifyItem();
                item.setArtistName(results.artists.items.get(i).name);
                item.setArtistEtc(results.artists.items.get(i).uri);
                for(int j =0;j<results.artists.items.get(i).images.size();j++){

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
