package com.lemonjun.library.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class LoadView extends View {

    private Paint mPaint;

    private int mCenterX,mCenterY;

    private int mRadio = 150;

    private RectF mRectArc = new RectF();

    private final float mDeltaAngle = 10;

    private final float mMaxSwipeAngle = 200;

    private float mCurrentSwipeAngle = 0;

    private float mStartAngle = 0;

    private ProgressTag mProgressTag = ProgressTag.PROGRESS_0;

    private float x1, y1 , x2, y2,x3 ,y3;

    private float mDeltaLineDis = 10;

    private Line line1 , line2;

    private float xDis3tol;

    private float xDis2tol;

    private float mCurrentXDis;

    private State mCurrentStatus = State.STATUS_IDLE;


    public void setStatus(State mCurrentStatus) {
        this.mCurrentStatus = mCurrentStatus;
        invalidate();
    }

    public LoadView(Context context) {
        this(context,null);
    }

    public LoadView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        mCenterX = w / 2;
        mCenterY = h / 2;
        mRectArc.set(mCenterX - mRadio, mCenterY - mRadio,mCenterX + mRadio , mCenterY + mRadio);

        x1 = mCenterX - mRadio / 3 * 2;
        y1 = mCenterY + mRadio / 8;
        x2 = mCenterX - mRadio / 5 ;
        y2 = mCenterY + mRadio / 3 * 2;
        x3 = mCenterX + mRadio / 4 * 3;
        y3 = mCenterY - mRadio / 4;

        line1 = new Line(new PointF(x1,y1),new PointF(x2,y2));
        line2 = new Line(new PointF(x2,y2),new PointF(x3,y3));

        xDis3tol = x3 - x1;
        xDis2tol = x2 - x1;

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        switch (mCurrentStatus){
            case STATUS_IDLE:
                reset();
                canvas.drawArc(mRectArc, -90 + mStartAngle,360,false,mPaint);
                canvas.drawLine(line1.startP.x, line1.startP.y, line1.endP.x, line1.endP.y, mPaint);//第1条直线，保持完整
                canvas.drawLine(line2.startP.x, line2.startP.y, line2.endP.x, line2.endP.y, mPaint);//第2条直线，保持完整
                break;
            case STATUS_PROCESS:

                canvas.drawArc(mRectArc, -90 + mStartAngle,mCurrentSwipeAngle,false,mPaint);
                if(mProgressTag == ProgressTag.PROGRESS_0){
                    if(mCurrentSwipeAngle <= mMaxSwipeAngle){
                        mCurrentSwipeAngle += mDeltaAngle;
                    }else{
                        mStartAngle += mDeltaAngle;//那就让起始角度值递增
                    }
                    if(mStartAngle + mCurrentSwipeAngle >= 360){
                        mProgressTag = ProgressTag.PROGRESS_1;
                    }
                }else if(mProgressTag == ProgressTag.PROGRESS_1){
                    mCurrentSwipeAngle -= mDeltaAngle;
                    mStartAngle += mDeltaAngle;

                    if(mCurrentSwipeAngle <= 0){
                        mStartAngle = 0;
                        mProgressTag = ProgressTag.PROGRESS_0;
                    }
                }
                invalidate();
                break;
            case STATUS_FINISH:
                canvas.drawArc(mRectArc, -90 + mStartAngle, mCurrentSwipeAngle, false, mPaint);//以当前两个参数继续画圆弧，直到画完整

                mCurrentSwipeAngle += mDeltaAngle;
                if(mCurrentSwipeAngle <= 360){
                    invalidate();
                }else{
                    mCurrentXDis += mDeltaLineDis;
                    if(mCurrentXDis < xDis2tol){
                        canvas.drawLine(line1.startP.x, line1.startP.y, line1.startP.x + mCurrentXDis
                                , line1.getY(line1.startP.x + mCurrentXDis), mPaint);
                        invalidate();
                    }else if(mCurrentXDis < xDis3tol){
                        canvas.drawLine(line1.startP.x, line1.startP.y, line1.endP.x, line1.endP.y, mPaint);//第一条直线，保持完整
                        canvas.drawLine(
                                line2.startP.x,
                                line2.startP.y, line2.startP.x + mCurrentXDis - xDis2tol,
                                line2.getY(line2.startP.x + mCurrentXDis - xDis2tol),
                                mPaint);//动态画第二条直线
                        invalidate();
                    }else{
                        canvas.drawLine(line1.startP.x, line1.startP.y, line1.endP.x, line1.endP.y, mPaint);//第1条直线，保持完整
                        canvas.drawLine(line2.startP.x, line2.startP.y, line2.endP.x, line2.endP.y, mPaint);//第2条直线，保持完整
                        reset();
                    }
                }
                break;
        }

    }

    private void reset(){
        mCurrentSwipeAngle = 0;
        mStartAngle = 0;
        mCurrentXDis = 0;
    }

    private void init(){
        mPaint = new Paint();
        mPaint.setColor(Color.RED);
        mPaint.setAntiAlias(true);;//抗锯齿
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(10); //画笔宽度
        mPaint.setStrokeCap(Paint.Cap.ROUND); //画直线的时候让线头呈现圆角
    }

    class Line {
        float k;
        float b;

        PointF startP, endP;

        Line(PointF startP,PointF endP){
            this.startP = startP;
            this.endP = endP;

            k = (endP.y - startP.y) / (endP.x - startP.x);
            b = endP.y - k * endP.x;
        }

        float getY(float x) {
            return k * x + b;
        }

    }

    enum ProgressTag {
        PROGRESS_0,
        PROGRESS_1
    }

   public enum State {
        STATUS_IDLE,
        STATUS_PROCESS,
        STATUS_FINISH
    }
}
