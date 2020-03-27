package com.lemonjun.drawlib;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import androidx.annotation.Nullable;

public class DrawView extends View {

    private Paint mPaint;

    private Context context;

    private int width;

    private int height;


    public DrawView(Context context) {
        this(context,null);
    }

    public DrawView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public DrawView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        this.context = context;

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        width = MeasureSpec.getSize(widthMeasureSpec);
        height = MeasureSpec.getSize(heightMeasureSpec);

        Log.e("tt","this width = " + width);
        Log.e("tt","this height = " + height);

        int mWidth = context.getResources().getDisplayMetrics().widthPixels;
        int mHeight = context.getResources().getDisplayMetrics().heightPixels;

        Log.e("tt","moble width = " + mWidth);
        Log.e("tt","moble height = " + mHeight);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        drawCircle(canvas);
    }

    /**
     * 画圆
     * @param canvas
     */
    public void drawCircle(Canvas canvas){
        // 先清空
        canvas.drawColor(Color.BLACK);

        mPaint = new Paint();


        mPaint.setColor(Color.RED);
        mPaint.setStyle(Paint.Style.FILL);
        canvas.drawRect(0,0,mProgress,height,mPaint);

        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(10);
        mPaint.setColor(Color.WHITE);
        mPaint.setAntiAlias(true);

        int x = width / 2;
        int y = height / 2;

        canvas.drawCircle(x,y,200,mPaint);

        RectF rectF = new RectF(x - 200,y - 200,x + 200,y + 200);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(Color.parseColor("#952674"));
       // canvas.drawRect(rectF,mPaint);

        mPaint.setColor(Color.BLUE);
        canvas.drawArc(rectF,0,45f,true,mPaint);
        mPaint.setColor(Color.parseColor("#00BCD4"));
        canvas.drawArc(rectF,270f,90,true,mPaint);
        mPaint.setColor(Color.parseColor("#955574"));
        canvas.drawArc(rectF,45,225,true,mPaint);

        if(mProgress == 10){
            handler.post(r);
        }
    }

    int mProgress = 10;

    Handler handler = new Handler();
    Runnable r = new Runnable() {
        @Override
        public void run() {
            if(mProgress < width){
                mProgress += 10;
                handler.postDelayed(this,30);
                invalidate();
            }else{
                handler.removeCallbacks(this);
            }
        }
    };

}
