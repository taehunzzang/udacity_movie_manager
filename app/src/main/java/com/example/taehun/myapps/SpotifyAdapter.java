package com.example.taehun.myapps;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by taehun on 15. 6. 9..
 */
public class SpotifyAdapter extends ArrayAdapter<SpotifyItem> {
    Context ctx;
    ArrayList<SpotifyItem>items;
    LayoutInflater mInflater;

    public SpotifyAdapter(Context context, int resource, ArrayList<SpotifyItem>_items) {
        super(context, resource, _items);
        ctx = context;
        items = _items;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView == null){
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.spotify_main_item,null);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        return convertView;
    }
    class ViewHolder {

        TextView contactname;
        ImageView addfriendbtn;

    }
}
