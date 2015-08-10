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
public class ReviewAdapter extends ArrayAdapter<ItemReviews>{
    Context mContext;
    ArrayList<ItemReviews> mItems;
    LayoutInflater mInflater;
    public ReviewAdapter(Context context, int resource, ArrayList<ItemReviews> _item) {
        super(context, resource, _item);
        mContext  = context;
        mItems = _item;
        mInflater = LayoutInflater.from(context);

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder mViewHolder;
        if(convertView == null){
            convertView = mInflater.inflate(R.layout.review_detail_item,null);
            mViewHolder = new ViewHolder();
            mViewHolder.auther = (TextView) convertView.findViewById(R.id.author);
            mViewHolder.contents = (TextView) convertView.findViewById(R.id.content);
            convertView.setTag(mViewHolder);
        }else{
            mViewHolder = (ViewHolder) convertView.getTag();
        }

        mViewHolder.auther.setText(mItems.get(position).getAuthor());
        mViewHolder.contents.setText(mItems.get(position).getContent());


        return convertView;
    }
    class ViewHolder{
        TextView contents;
        TextView auther;
    }
}
