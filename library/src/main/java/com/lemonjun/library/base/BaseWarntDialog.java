package com.lemonjun.library.base;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.NonNull;

import com.lemonjun.library.R;

public class BaseWarntDialog extends Dialog {

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
    }

    @Override
    public void show() {
        super.show();
        timehandler.postDelayed(timeRunnable,timeLenger);
    }
}
