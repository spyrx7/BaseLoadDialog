package com.lemonjun.library.base;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.lemonjun.library.R;

import java.util.Date;

public class BaseLoadingDialog extends Dialog {

    /**
     * 用于管理对话框显示的handler
     */
    private Handler timehandler = new Handler();

    /**
     *  时间长度 单位毫秒
     */
    private long timeLenger = 3000;

    private Runnable timeRunnable = new Runnable() {
        @Override
        public void run() {
            dismiss();
        }
    };

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
    }

    @Override
    public void show() {
        super.show();
        timehandler.postDelayed(timeRunnable,timeLenger);
    }

    @Override
    public void dismiss() {
        super.dismiss();
        // 对话框关闭时 还原
        timehandler.removeCallbacks(timeRunnable);
    }

    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }
}