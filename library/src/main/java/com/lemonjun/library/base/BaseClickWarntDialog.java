package com.lemonjun.library.base;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;

import com.lemonjun.library.R;

public class BaseClickWarntDialog extends Dialog {

    /**
     * 用于管理对话框显示的handler
     */
    private Handler timehandler = new Handler();

    /**
     *  时间长度 单位毫秒
     */
    private long timeLenger = Config.waitingTime;

    private Button mConfirm;

    private OnClickListener listener;

    private Runnable timeRunnable = new Runnable() {
        @Override
        public void run() {
            dismiss();
        }
    };

    public BaseClickWarntDialog(@NonNull Context context) {
        this(context,0);
    }

    public BaseClickWarntDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bld_layout_onclick_warnt_view);

        mConfirm = findViewById(R.id.btn_confirm);
        mConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listener != null){
                    listener.onClick(BaseClickWarntDialog.this);
                }
            }
        });
    }

    @Override
    public void show() {
        super.show();
        timehandler.postDelayed(timeRunnable,timeLenger);
    }

    public void setOnClickListener(OnClickListener listener) {
        this.listener = listener;
    }

    public interface OnClickListener {
        void onClick(Dialog view);
    }

}
