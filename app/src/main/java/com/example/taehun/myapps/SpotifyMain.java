package com.example.taehun.myapps;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;


public class SpotifyMain extends ActionBarActivity {

    ListView itemListiew;
    ArrayList<SpotifyItem> items;
    SpotifyAdapter adapter;

    EditText artistKeyword;
    Button artistSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.spotify_main);
        initUI();
        setVaues();//f
    }

    private void setVaues() {
        items = new ArrayList<SpotifyItem>();
        for (int i = 0; i < 10; i++) {
            items.add(new SpotifyItem());
        }
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
    }
}
