package com.lemonjun.drawlib;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class OnTouchMangerLayout extends ViewGroup {

    private int widht = 0;
    private int height = 0;

    public OnTouchMangerLayout(@NonNull Context context) {
        this(context,null);
    }

    public OnTouchMangerLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public OnTouchMangerLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int count = getChildCount();

        if(count < 2){
            throw new ExceptionInInitializerError();
        }

        for(int i = 0; i < count; i ++){
            View view = getChildAt(i);

            if(view instanceof ViewGroup){
                measureChild((ViewGroup)view,widthMeasureSpec,heightMeasureSpec);
            }

            int width = view.getMeasuredWidth();
            int height = view.getMeasuredHeight();
            measureChild(view,widthMeasureSpec,heightMeasureSpec);
        }

        setMeasuredDimension(getMeasuredWidth(),getMeasuredHeight());
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

        int count = getChildCount();

        int offsetHeight = 0;

        for(int i = 0;i < count; i++){
            View view = getChildAt(i);

            int childWidth = view.getMeasuredWidth();
            int childHeight = view.getMeasuredHeight();

            if(view instanceof ViewGroup) {

                MarginLayoutParams lp = (MarginLayoutParams) view.getLayoutParams();

                view.layout(0 + lp.leftMargin, offsetHeight + lp.topMargin, childWidth + lp.rightMargin, childHeight + offsetHeight + lp.bottomMargin);

                offsetHeight += childHeight;
            }
        }

    }


}
