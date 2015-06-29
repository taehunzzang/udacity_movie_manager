package com.example.taehun.myapps;

import android.app.Application;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by taehun on 15. 6. 29..
 */
public class MoviesVolley extends Application {
    public static final String TAG = MoviesVolley.class.getName();
    private RequestQueue mRequestQueue;
    private static MoviesVolley mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        mRequestQueue = Volley.newRequestQueue(getApplicationContext());

    }

    public static synchronized MoviesVolley getInstance(){
        return mInstance;
    }

    public RequestQueue getRequestQueue(){
        return mRequestQueue;
    }
    public <T> void add(Request<T> req){
        getRequestQueue().add(req);
    }
    public void cancel(){
        mRequestQueue.cancelAll(TAG);
    }
}
