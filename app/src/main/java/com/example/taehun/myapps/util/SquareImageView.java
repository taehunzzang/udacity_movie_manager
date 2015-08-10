package com.example.taehun.myapps.util;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;
import android.widget.ImageView;

/**
 * Created by taehun on 15. 6. 30..
 */
public class SquareImageView extends ImageView {
    Context mCtx;
    public SquareImageView(Context context) {
        super(context);
        mCtx = context;
    }

    public SquareImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mCtx = context;
    }

    public SquareImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mCtx = context;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int divider = 2;
//        DisplayMetrics metrics = new DisplayMetrics();
//        int width = metrics.widthPixels;
//        int height = metrics.heightPixels;



        Display display = ((WindowManager)mCtx.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        if(display.getWidth()> display.getHeight()){
            divider = 3;
        }
        int width1 = display.getWidth()/divider;
        float tempRate = (float)getMeasuredHeight()/(float)getMeasuredWidth();
        Log.e(""," H : "+getMeasuredHeight()+" W : "+getMeasuredWidth()+" R : "+tempRate);

        setMeasuredDimension(width1 , (int) (width1 * tempRate)); //Snap to width
    }
}
