package com.lemonjun.animatorlibrary;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class SportsView extends View {

    private float progress = 0;


    public SportsView(Context context) {
        this(context,null);
    }

    public SportsView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public SportsView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public float getProgress() {
        return progress;
    }

    public void setProgress(float progress) {
        this.progress = progress;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Paint mPaint = new Paint();
        mPaint.setStrokeWidth(30);
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setColor(Color.RED);

        RectF arcRectf = new RectF(0,0,400,400);
        canvas.drawArc(arcRectf,135,progress * 2.7f,false,mPaint);

        mPaint.setColor(Color.BLACK);
        mPaint.setTextSize(60);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setTextAlign(Paint.Align.CENTER);
        canvas.drawText(progress + "%",200 ,200 - (mPaint.ascent() + mPaint.descent()) / 2,mPaint);

    }

}
