package com.lemonjun.drawlib;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.Scroller;

import androidx.annotation.Nullable;


public class TapeView extends View {

    private int bgColor = Color.parseColor("#FF80D8FF");
    // 自身宽度
    private int width;
    // 自身高度
    private int height;
    // 自身最低高度
    private int minHeight;

    // 标准刻度宽度
    private int scaleWidht;

    // 刻度数量
    private int scaleCount = 40;

    //
    private int count = 200;

    private float offset = 0;

    private float startX = 0;

    private float minValue = 0;

    private float maxValue = 200;

    private float endX = 0;

    private int textColor = Color.WHITE;

    private float textSize = 12;

    private Context context;

    private OnValueChangeListener onValueChangeListener;

    private VelocityTracker velocityTracker;

    private Scroller scroller;

    //被认为是快速滑动的最小速度
    private float minFlingVelocity;

    public TapeView(Context context) {
        this(context,null);
    }

    public TapeView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public TapeView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        this.context = context;
        minHeight = dp2px(context,60f);

        minFlingVelocity = ViewConfiguration.get(context).getScaledMinimumFlingVelocity();
        scroller = new Scroller(context);

        TypedArray ta = context.obtainStyledAttributes(attrs,R.styleable.TapeView);

        bgColor = ta.getColor(R.styleable.TapeView_bgColor,bgColor);
        scaleCount = ta.getInt(R.styleable.TapeView_sacleCount,scaleCount);
        minValue = ta.getFloat(R.styleable.TapeView_minValue,minValue);
        maxValue = ta.getFloat(R.styleable.TapeView_maxValue,maxValue);

        textColor = ta.getColor(R.styleable.TapeView_textColor,textColor);
        textSize = ta.getDimension(R.styleable.TapeView_textSize,dp2px(context,textSize));

    }

    public interface OnValueChangeListener{
        void onChange(float value);
    }

    public void setOnValueChangeListener(OnValueChangeListener onValueChangeListener) {
        this.onValueChangeListener = onValueChangeListener;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        width = MeasureSpec.getSize(widthMeasureSpec);
        height = getTapeDefaultSize(heightMeasureSpec);

        // 计算刻度宽度
        measuredScaleWidht(width);

        setMeasuredDimension(width,height);
    }

    private int getTapeDefaultSize(int measureSpec){
        int result = minHeight;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);

        switch (specMode) {
            case MeasureSpec.UNSPECIFIED:
                result = minHeight;
                break;
            case MeasureSpec.AT_MOST:
                result = Math.min(minHeight,specSize);
                break;
            case MeasureSpec.EXACTLY:
                result = specSize;
                break;
        }
        return result;
    }

    private void measuredScaleWidht(int width){

        offset = width / 2;

        scaleWidht = width / scaleCount;

        startX = width / 2;

        count = (int)(maxValue - minValue);

        endX = startX - scaleWidht * count;

        Log.d("tt","startX = " + startX);
        Log.d("tt","endX = " + endX);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // 绘制背景
        canvas.drawColor(bgColor);

        drawScale(canvas);

        drawScaleTips(canvas);
    }

    private void drawScale(Canvas canvas){
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStrokeWidth(dp2px(context,1f));
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.WHITE);

        for(int i = 0; i <= count; i++){
            int tagerHeight = dp2px(context,20);

            if(i % 5 == 0){
                tagerHeight = dp2px(context,25);
            }

            if(i % 10 == 0){
                tagerHeight = dp2px(context,30);
            }

            canvas.drawLine(i * scaleWidht + offset,0,i * scaleWidht + offset,tagerHeight,paint);

            paint.setTextSize(textSize);
            paint.setTextAlign(Paint.Align.CENTER);
            paint.setColor(textColor);
            if(i % 5 == 0) {
                canvas.drawText(i + minValue + "", i * scaleWidht + offset, dp2px(context, 50), paint);
            }
        }
    }

    private void drawScaleTips(Canvas canvas){
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

        paint.setStrokeWidth(dp2px(context,1.5f));
        paint.setColor(Color.RED);
        canvas.drawLine(width / 2,0,width / 2,dp2px(context,20f),paint);
    }

    private int dp2px(Context context, float dp){
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    private int dx2dp(Context context,float px){
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (px / scale + 0.5f);
    }

    float lastX;
    float dx = 0;

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if(velocityTracker == null){
            velocityTracker = VelocityTracker.obtain();
        }
        velocityTracker.addMovement(event);

        float x = event.getX();
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                lastX = x;
                dx = 0;
                break;
            case MotionEvent.ACTION_MOVE:
                dx = lastX - x;
                //offset -= (int) dx;
                float target = offset;
                target -=  dx;
                if(target < startX && target >= endX){
                    offset = target;
                }

                if(onValueChangeListener != null){
                    float value = ((startX - offset) / scaleWidht + minValue);
                    value = (float) (Math.round(value * 10) / 10);
                    onValueChangeListener.onChange(value);
                }

                postInvalidate();
                break;
            case MotionEvent.ACTION_UP:
                smoothMoveToCalibration();
                calculateVelocity();
               return false;
            default:
                return false;
        }
        lastX = x;
        return true;
    }

    private void smoothMoveToCalibration(){
        //offset = (float) (Math.round(offset / 10) * 10);

        //float value = ((startX - offset) / scaleWidht + minValue);
        //Log.e("tt","offset = " + offset + " value = " + value);

        //postInvalidate();
    }

    private void calculateVelocity(){
        velocityTracker.computeCurrentVelocity(1000);
        float xVelocity = velocityTracker.getXVelocity();
       // Log.e("tt", "xVelocity: " + xVelocity);
        if(Math.abs(xVelocity) > minFlingVelocity){
            scroller.fling(0,0,(int)xVelocity,0,Integer.MIN_VALUE, Integer.MAX_VALUE, 0, 0);
            invalidate();
        }
    }

    @Override
    public void computeScroll() {
        super.computeScroll();

        if(scroller.computeScrollOffset()){
            if(scroller.getCurrX() == scroller.getFinalX()){
                Log.d("tt","computeScroll end");
                smoothMoveToCalibration();
            }else{
                int x = scroller.getCurrX();
                dx = lastX - x;
                float target = offset;
                target -=  dx;
                if(target < startX && target >= endX){
                    offset = target;
                }

                if(onValueChangeListener != null){
                    float value = ((startX - offset) / scaleWidht + minValue);
                    value = (float) (Math.round(value * 10) / 10);
                    onValueChangeListener.onChange(value);
                }

                lastX = x;
                postInvalidate();
            }
        }

    }
}
