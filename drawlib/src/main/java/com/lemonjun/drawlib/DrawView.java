package com.lemonjun.drawlib;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
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

    private DrawStyle drawStyle;

    public DrawStyle getDrawStyle() {
        return drawStyle;
    }

    public void setDrawStyle(DrawStyle drawStyle) {
        this.drawStyle = drawStyle;
    }

    public DrawView(Context context) {
        this(context,null);
    }

    public DrawView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public DrawView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;

        setDrawStyle(DrawStyle.DRAW_PEXIT_GRID);
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

        switch (getDrawStyle()){
            case DRAW_TEST:
                drawCircle(canvas);
                break;
            case DRAW_PEXIT_GRID:
                drawGrid(canvas);
                break;
        }

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

        mPaint.setStrokeWidth(5);
        mPaint.setColor(Color.WHITE);
        canvas.drawLine(x + 200,y - 100,x + 260,y - 100,mPaint);
        canvas.drawLine(x + 260,y - 100,x + 300,y - 120,mPaint);

        Shader shader = new LinearGradient(x + 260,y - 140,x + 300,y - 140, Color.parseColor("#E91E63"),
                Color.parseColor("#2196F3"), Shader.TileMode.CLAMP);

        mPaint.setTextSize(24);
        mPaint.setShader(shader);
        mPaint.setShadowLayer(10,0,0,Color.WHITE);
        canvas.drawText("蓝色的 90 %",x + 260,y - 140,mPaint);

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

    private int gridWidthNum = 20;
    private int gridHeightNum = 50;
    private boolean isDraw = false;

    private void drawGrid(Canvas canvas){

        int tWidth = width / gridWidthNum;
        int tHeight = height / gridHeightNum;


        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(50);

        mPaint.setColor(Color.BLUE);

        //canvas.drawPoint(25,25,mPaint);

        for(int i = 0; i < gridHeightNum;i ++){
            if(i % 2 == 0){
                isDraw = false;
            }else{
                isDraw = true;
            }
            for(int j = 0;j < gridWidthNum ; j ++){
                drawR(canvas,tWidth * i,tHeight * j,tWidth * (i + 1),tHeight * (j + 1),isDraw);
            }
        }

    }

    public void drawR(Canvas canvas,int l,int t,int r,int b,Boolean is){
        if(is){
            mPaint.setColor(Color.parseColor("#FFE6ECEC"));
            isDraw = false;
        }else{
            mPaint.setColor(Color.parseColor("#FF8A8E8E"));
            isDraw = true;
        }
        canvas.drawRect(l,t,r,b,mPaint);
    }

    public void setDrawStyle(){

    }

    public enum DrawStyle{
        DRAW_TEST,
        DRAW_PEXIT_GRID
    }

}
