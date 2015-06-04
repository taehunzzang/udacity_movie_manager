package com.example.taehun.myapps;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity implements View.OnClickListener{


    Button button,button2,button3,button4,button5, button6;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setUI();
        initListener();
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
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    @Override
    public void onClick(View v) {

         switch (v.getId()){
            case R.id.button:
                Toast.makeText(MainActivity.this,getString(R.string.media_streamer),Toast.LENGTH_SHORT ).show();
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
