package com.lemonjun.drawlib;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

public class SmartisanAdLayout extends ViewGroup {

    private Context context;

    private int minHeight ;

    private float lastY = 0;

    private int offset = 0;

    public SmartisanAdLayout(Context context) {
        this(context,null);
    }

    public SmartisanAdLayout(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public SmartisanAdLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        this.context = context;

        setBackgroundColor(Color.parseColor("#226589"));
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int count = getChildCount();

        int offsety = 0;

        if(count > 0){
            for(int i = 0; i < count;i++){
                View view = getChildAt(i);
                if(view.getVisibility() != GONE){
                    LayoutParams lp = view.getLayoutParams();
                    measureChild(view,widthMeasureSpec,heightMeasureSpec);

                    int childWidth = view.getMeasuredWidth();
                    int childHeight = view.getMeasuredHeight();

                    offsety += childHeight;

                   // Log.e("tt","childWidth = " + childWidth + " childHeight = " + childHeight);
                }

            }
        }

        minHeight = offsety;

        if(offset < minHeight) {
            offset = minHeight;
        }
        if(offset > getMeasuredHeight()){
            offset = getMeasuredHeight();
        }
        setMeasuredDimension(widthMeasureSpec,offset);

    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

        int count = getChildCount();

        int offsetHeight = 0;

        for(int i = 0;i < count; i++){
            View view = getChildAt(i);

            int childWidth = view.getMeasuredWidth();
            int childHeight = view.getMeasuredHeight();

            view.layout(0,offsetHeight,childWidth,childHeight + offsetHeight);

            offsetHeight += childHeight;

            if(i == 1 && offset > minHeight){
                view.layout(0,offset - childHeight,childWidth,offset);
            }
        }

    }

    private int dp2px(float dp){
        float scale = context.getResources().getDisplayMetrics().density;

        return (int)(dp * scale + 0.5f);
    }

    float dy = 0;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float y = event.getY();
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                lastY = y;
                dy = 0;
                break;
            case MotionEvent.ACTION_MOVE:
                dy = y - lastY;
                Log.e("tt","dy = " + dy );
                if(offset <= getMeasuredHeight()) {
                    offset += (int) dy;
                }else {
                    offset = getMeasuredHeight();
                }
                requestLayout();
                break;
            case MotionEvent.ACTION_UP:

                return false;
            default:
                return false;
        }
        lastY = y;
        return true;
    }
}
