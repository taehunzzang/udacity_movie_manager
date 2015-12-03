package com.example.taehun.myapps;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

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
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder mViewHolder;
        if(convertView == null){
            convertView = mInflater.inflate(R.layout.movie_row,null);
            mViewHolder = new ViewHolder();
            mViewHolder.rowImage = (ImageView) convertView.findViewById(R.id.rowImage);
            mViewHolder.title = (TextView) convertView.findViewById(R.id.title);
            mViewHolder.genre = (TextView) convertView.findViewById(R.id.genre);
            mViewHolder.rateStar = (RatingBar) convertView.findViewById(R.id.rateStar);
            convertView.setTag(mViewHolder);
        }else{
            mViewHolder = (ViewHolder) convertView.getTag();
        }

        DisplayMetrics metrics = new DisplayMetrics();
        int width = metrics.widthPixels;
        int height = metrics.heightPixels;

//        mViewHolder.rowImage.setMinimumHeight(height / 2);
        Log.e("", " img : " + URL_PRE_FIX + mData.get(position).getPoster_path());
        if(mData.get(position).getPoster_path() != null){
            Picasso.with(context).load(URL_PRE_FIX+mData.get(position).getPoster_path()).into(mViewHolder.rowImage);
        }

        mViewHolder.title.setText(mData.get(position).getTitle());
        mViewHolder.title.setSelected(true);
        mViewHolder.genre.setText(mData.get(position).getGenre_ids());
//        Log.e(""," star : "+Float.parseFloat(mData.get(position).getPopularity()));
        mViewHolder.rateStar.setRating(Float.parseFloat(mData.get(position).getPopularity())/10);

        return convertView;
    }
    class ViewHolder{
        ImageView rowImage;
        TextView title;
        TextView genre;
        RatingBar rateStar;
    }
}
