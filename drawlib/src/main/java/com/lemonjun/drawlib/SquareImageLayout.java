package com.lemonjun.drawlib;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.Nullable;

public class SquareImageLayout extends androidx.appcompat.widget.AppCompatImageView {


    public SquareImageLayout(Context context) {
        super(context);
    }

    public SquareImageLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public SquareImageLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int width = getMeasuredWidth();
        int height = getMeasuredHeight();

        if(width > height){
            width = getMeasuredHeight();
        }else{
            height = getMeasuredWidth();
        }

        setMeasuredDimension(width,height);
    }
}
