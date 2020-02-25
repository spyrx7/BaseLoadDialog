package com.lemonjun.library.base;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.ColorInt;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.StringRes;

import com.lemonjun.library.R;

public class BaseWarntDialog extends Dialog {

    /**
     * 用于管理对话框显示的handler
     */
    private Handler timehandler = new Handler();

    /**
     *  时间长度 单位毫秒
     */
    private long timeLenger = Config.waitingTime;

    private Runnable timeRunnable = new Runnable() {
        @Override
        public void run() {
            dismiss();
        }
    };

    private TextView mContent;

    private LinearLayout mainLayout;

    private ImageView targetImg;

    public BaseWarntDialog(@NonNull Context context) {
        this(context,0);
    }

    public BaseWarntDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bld_layout_warnt_view);

        mainLayout = findViewById(R.id.ll_main_layout);
        targetImg = findViewById(R.id.img_tips);
        mContent = findViewById(R.id.tv_content);
    }

    @Override
    public void show() {
        super.show();
        timehandler.postDelayed(timeRunnable,timeLenger);
    }

    public BaseWarntDialog setBackground(Drawable background){
        if(mainLayout != null){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                mainLayout.setBackground(background);
            }else{
                mainLayout.setBackgroundDrawable(background);
            }
        }
        return this;
    }

    public BaseWarntDialog setBackgroundResource(@DrawableRes int resid){
        if(mainLayout != null){
            mainLayout.setBackgroundResource(resid);
        }
        return this;
    }

    public BaseWarntDialog setBackgroundColor(@ColorInt int color){
        if(mainLayout != null){
            mainLayout.setBackgroundColor(color);
        }
        return this;
    }

    public BaseWarntDialog setImageDrawable(Drawable drawable){
        if(targetImg != null){
            targetImg.setImageDrawable(drawable);
        }
        return this;
    }

    public BaseWarntDialog setImageResource(@DrawableRes int resId){
        if(targetImg != null){
            targetImg.setImageResource(resId);
        }
        return this;
    }

    public BaseWarntDialog setImageBitmap(Bitmap bm){
        if(targetImg != null){
            targetImg.setImageBitmap(bm);
        }

        return this;
    }

    public BaseWarntDialog setText(CharSequence text){
        if(mContent != null){
            mContent.setText(text);
        }
        return this;
    }

    public BaseWarntDialog setText(@StringRes int resid){
        if(mContent != null){
            mContent.setText(resid);
        }
        return this;
    }

}
