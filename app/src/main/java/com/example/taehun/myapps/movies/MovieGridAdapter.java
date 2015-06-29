package com.example.taehun.myapps.movies;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.example.taehun.myapps.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by taehun on 15. 6. 30..
 */
public class MovieGridAdapter extends ArrayAdapter<MovieItem> {
    static final String URL_PRE_FIX = "http://image.tmdb.org/t/p/w185/";
    Context context;
    ArrayList<MovieItem>mData;
    LayoutInflater mInflater;
    public MovieGridAdapter(Context context, int resource, ArrayList<MovieItem>data) {
        super(context, resource, data);
        this.context = context;
        mData = data;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        mInflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder mViewHolder;
        if(convertView == null){
//            convertView = mInflater.inflate(R.layout.spotify_main_item,null);
            convertView = mInflater.inflate(R.layout.movie_row,null);
            mViewHolder = new ViewHolder();
            mViewHolder.rowImage = (ImageView) convertView.findViewById(R.id.rowImage);
            convertView.setTag(mViewHolder);
        }else{
            mViewHolder = (ViewHolder) convertView.getTag();
        }

        DisplayMetrics metrics = new DisplayMetrics();
        int width = metrics.widthPixels;
        int height = metrics.heightPixels;

        mViewHolder.rowImage.setMinimumHeight(height/2);


        Picasso.with(context).load(URL_PRE_FIX+mData.get(position).getPoster_path()).into(mViewHolder.rowImage);


        return convertView;
    }
    class ViewHolder{
        ImageView rowImage;
    }
}
