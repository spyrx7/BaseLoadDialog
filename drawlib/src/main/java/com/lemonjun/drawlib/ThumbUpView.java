package com.lemonjun.drawlib;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

public class ThumbUpView extends View implements View.OnClickListener {


    private int minWidth = 90;

    private int minHeight = 60;

    private int width = 0;

    private int height = 0;

    private String text = "0.0";

    private Context context;

    public ThumbUpView(Context context) {
        this(context,null);
    }

    public ThumbUpView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public ThumbUpView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        this.context = context;

        TypedArray ta = context.obtainStyledAttributes(attrs,R.styleable.TapeView);

        setOnClickListener(this);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        width = getThisDefaultSize(minWidth,widthMeasureSpec);
        height = getThisDefaultSize(minHeight,heightMeasureSpec);

        setMeasuredDimension(getThisDefaultSize(minWidth,widthMeasureSpec)
                ,getThisDefaultSize(minHeight,heightMeasureSpec));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setTextSize(dp2px(context,18f));
        canvas.drawText(text,width / 2,height - paint.descent() ,paint);
    }

    public int getThisDefaultSize(int size, int measureSpec) {
        int result = size;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);

        switch (specMode) {
            case MeasureSpec.UNSPECIFIED:
                result = size;
                break;
            case MeasureSpec.AT_MOST:
                result = Math.min(size,specSize);
                break;
            case MeasureSpec.EXACTLY:
                result = specSize;
                break;
        }
        return result;
    }

    @Override
    public void onClick(View v) {
        Log.e("tt","Click ");
    }

    private int dp2px(Context context, float dp){
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    private int dx2dp(Context context,float px){
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (px / scale + 0.5f);
    }

}
