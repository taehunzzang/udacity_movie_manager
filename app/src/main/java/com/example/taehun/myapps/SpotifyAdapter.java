package com.example.taehun.myapps;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

/**
 * Created by taehun on 15. 6. 9..
 */
public class SpotifyAdapter extends ArrayAdapter<SpotifyItem> {
    Context ctx;
    ArrayList<SpotifyItem>items;

    public SpotifyAdapter(Context context, int resource, ArrayList<SpotifyItem>_items) {
        super(context, resource, _items);
        ctx = context;
        items = _items;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return super.getView(position, convertView, parent);
    }
}
