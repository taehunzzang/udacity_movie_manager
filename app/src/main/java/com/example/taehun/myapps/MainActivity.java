package com.example.taehun.myapps;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.taehun.myapps.spotify.SpotifyMain;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    Button button,button2,button3,button4,button5, button6;
    ImageView testPicasso;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setUI();
        initListener();
        testImageView();
    }
    private void testImageView() {
//        Picasso.with(this).load("http://i.imgur.com/DvpvklR.png").into(testPicasso);
    }
    private void initListener() {
        button.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
        button4.setOnClickListener(this);
        button5.setOnClickListener(this);
        button6.setOnClickListener(this);
    }

    private void setUI() {
        button = (Button) findViewById(R.id.button);
        button2 = (Button) findViewById(R.id.button2);
        button3 = (Button) findViewById(R.id.button3);
        button4 = (Button) findViewById(R.id.button4);
        button5 = (Button) findViewById(R.id.button5);
        button6 = (Button) findViewById(R.id.button6);
//        testPicasso = (ImageView)findViewById(R.id.testPicasso);
    }

    @Override
    public void onClick(View v) {
         switch (v.getId()){
            case R.id.button:
                startActivity(new Intent(this,SpotifyMain.class));
//                Toast.makeText(MainActivity.this,getString(R.string.media_streamer),Toast.LENGTH_SHORT ).show();
                break;
            case R.id.button2:
                Toast.makeText(MainActivity.this,getString(R.string.super_duo1),Toast.LENGTH_SHORT ).show();
                break;
            case R.id.button3:
                Toast.makeText(MainActivity.this,getString(R.string.super_duo2),Toast.LENGTH_SHORT ).show();
                break;
            case R.id.button4:
                Toast.makeText(MainActivity.this,getString(R.string.ant_terminator),Toast.LENGTH_SHORT ).show();
                break;
            case R.id.button5:
                Toast.makeText(MainActivity.this,getString(R.string.materialize),Toast.LENGTH_SHORT ).show();
                break;
            case R.id.button6:
                Toast.makeText(MainActivity.this,getString(R.string.capstone),Toast.LENGTH_SHORT ).show();
                break;
        }
    }
}
