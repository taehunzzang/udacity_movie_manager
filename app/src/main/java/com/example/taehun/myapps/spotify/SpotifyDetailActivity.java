package com.example.taehun.myapps.spotify;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;

import com.example.taehun.myapps.R;

import kaaes.spotify.webapi.android.SpotifyApi;
import kaaes.spotify.webapi.android.SpotifyService;
import kaaes.spotify.webapi.android.models.Album;
import kaaes.spotify.webapi.android.models.AlbumsPager;
import kaaes.spotify.webapi.android.models.Tracks;
import kaaes.spotify.webapi.android.models.TracksPager;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class SpotifyDetailActivity extends AppCompatActivity {
    String uniqueId;
    ProgressDialog progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.spotify_detail_activity);
        try {
            uniqueId = getIntent().getStringExtra("uniqueId");
        }catch (Exception e){

        }
        new InternetAccess().execute();

    }

    class InternetAccess extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
//            setProgressBarIndeterminateVisibility(true);

            progressBar = new ProgressDialog(SpotifyDetailActivity.this);
            progressBar.setOnKeyListener(new DialogInterface.OnKeyListener() {
                @Override
                public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                    if (keyCode == KeyEvent.KEYCODE_BACK) {
                        progressBar.dismiss();
                    }
                    return false;
                }
            });
            progressBar.setCancelable(false);
            progressBar.setTitle("title");
            progressBar.setMessage("wait");
            progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressBar.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            SpotifyApi api = new SpotifyApi();
            try {


            SpotifyService spotify = api.getService();
//          final TracksPager results= spotify.searchTracks(uniqueId);
//          Tracks test1 =  spotify.getArtistTopTrack(uniqueId);
//            spotify.getAlbum("2dIGnmEIy1WZIcZCFSj6i8", new Callback<Album>() {
//                @Override
//                public void success(Album album, Response response) {
//                    Log.d("Album success", album.name);
//                }
//
//                @Override
//                public void failure(RetrofitError error) {
//                    Log.d("Album failure", error.toString());
//                }
//            });
                Log.e("", "start_getAlbum"+uniqueId);
                spotify.getAlbum("2dIGnmEIy1WZIcZCFSj6i8", new Callback<Album>() {
                    @Override
                    public void success(Album album, Response response) {
                        Log.e("", "getAlbum_response : " + response);
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        Log.e("", "error.toString(): " + error.toString());
                    }
                });
                Log.e("", "end_getAlbum");
//            Log.e("", " resutl 1 : " + results.artists.limit);


//            items.clear();
//            if (results.artists.total > 0) {
//                for (int i = 0; i < results.artists.items.size(); i++) {
//                    Log.e("", " results.artists : " + results.artists.items.get(i).name);
//                    SpotifyItem item = new SpotifyItem();
//                    item.setArtistName(results.artists.items.get(i).name);
//                    item.setArtistEtc(results.artists.items.get(i).uri);
//                    item.setUniqueId(results.artists.items.get(i).id);
//                    for (int j = 0; j < results.artists.items.get(i).images.size(); j++) {
//                        item.setImgName(results.artists.items.get(i).images.get(j).url);
//                    }
////                    items.add(item);
//                }
//            }
            }catch (Exception e){
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            progressBar.dismiss();
//            setProgressBarIndeterminateVisibility(false);
//            progressBar.dismiss();
//            adapter.notifyDataSetChanged();
//            if (adapter.getCount()==0){
//                noResultLL.setVisibility(View.VISIBLE);
//                itemListiew.setVisibility(View.GONE);
//            }else{
//                noResultLL.setVisibility(View.GONE);
//                itemListiew.setVisibility(View.VISIBLE);
//            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_spotify_detail, menu);
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
