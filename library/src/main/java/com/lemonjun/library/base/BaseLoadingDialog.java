package com.lemonjun.library.base;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.StringRes;

import com.lemonjun.library.R;
public class BaseLoadingDialog extends Dialog {

    /**
     * 用于管理对话框显示的handler
     */
   // private Handler timehandler = new Handler();

    /**
     *  时间长度 单位毫秒
     */
    private long timeLenger = Config.waitingTime;

 /*   private Runnable timeRunnable = new Runnable() {
        @Override
        public void run() {
            dismiss();
        }
    };*/

    /**
     * 对话框背景颜色
     * @param context
     */
     private String dialogBgColor = "#ffffff";

    /**
     * 主要字体颜色
     */
    private int mainTextColor;

    private LinearLayout mainLayout;

    private TextView loadText;

    private ProgressBar progressBar;

    public BaseLoadingDialog(@NonNull Context context) {
        this(context,0);
    }

    public BaseLoadingDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        setCancelable(true);
        setCanceledOnTouchOutside(false);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bld_layout_loading_view);
        loadText = findViewById(R.id.tv_content);
        mainLayout = findViewById(R.id.ll_main_layout);
        progressBar = findViewById(R.id.progressBar);
    }

    @Override
    public void show() {
        super.show();
        //timehandler.postDelayed(timeRunnable,timeLenger);
    }

    @Override
    public void dismiss() {
        super.dismiss();
        // 对话框关闭时 还原
        //timehandler.removeCallbacks(timeRunnable);
    }

    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }

    public BaseLoadingDialog setLoadText(String c){
        if(loadText != null){
            loadText.setText(c);
        }
        return this;
    }

    public BaseLoadingDialog setLoadText(@StringRes int resid){
        if(loadText != null ){
            loadText.setText(resid);
        }
        return this;
    }

    public BaseLoadingDialog setMainBackground(Drawable background){
        if(mainLayout != null){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                mainLayout.setBackground(background);
            }else{
                mainLayout.setBackgroundDrawable(background);
            }
        }
        return this;
    }

    public BaseLoadingDialog setMainBackgroundColor(@ColorInt int color){
        if(mainLayout != null){
            mainLayout.setBackgroundColor(color);
        }
        return this;
    }

    public BaseLoadingDialog setMainBackgroundResource(@DrawableRes int resid){
        if(mainLayout != null){
            mainLayout.setBackgroundResource(resid);
        }
        return this;
    }

}
