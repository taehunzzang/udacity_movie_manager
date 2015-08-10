package com.example.taehun.myapps.detail;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.taehun.myapps.R;

import java.util.ArrayList;

/**
 * Created by taehun on 15. 8. 10..
 */
public class TrailerAdapter extends ArrayAdapter<ItemTrailers>{
    Context mContext;
    ArrayList<ItemTrailers> mItems;
    LayoutInflater mInflater;
    public TrailerAdapter(Context context, int resource, ArrayList<ItemTrailers> _item) {
        super(context, resource, _item);
        mContext  = context;
        mItems = _item;
        mInflater = LayoutInflater.from(context);

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder mViewHolder;
        if(convertView == null){
            convertView = mInflater.inflate(R.layout.item_trailer,null);
            mViewHolder = new ViewHolder();
            mViewHolder.contents = (TextView) convertView.findViewById(R.id.movieTitle);
            convertView.setTag(mViewHolder);
        }else{
            mViewHolder = (ViewHolder) convertView.getTag();
        }

        mViewHolder.contents.setText(mItems.get(position).getName());


        return convertView;
    }
    class ViewHolder{
        TextView contents;
    }
}
