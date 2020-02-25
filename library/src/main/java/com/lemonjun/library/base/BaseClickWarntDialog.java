package com.lemonjun.library.base;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.StringRes;

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

    private TextView mContent;

    private ImageView tagerImg;

    private LinearLayout mainLayout;

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

        mainLayout = findViewById(R.id.ll_main_layout);
        mContent = findViewById(R.id.tv_content);
        tagerImg = findViewById(R.id.img_tips);
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

    public BaseClickWarntDialog setBackground(Drawable background){
        if(mainLayout != null){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                mainLayout.setBackground(background);
            }else{
                mainLayout.setBackgroundDrawable(background);
            }
        }
        return this;
    }

    public BaseClickWarntDialog setBackgroundResource(@DrawableRes int resid){
        if(mainLayout != null){
            mainLayout.setBackgroundResource(resid);
        }
        return this;
    }

    public BaseClickWarntDialog setBackgroundColor(@ColorInt int color){
        if(mainLayout != null){
            mainLayout.setBackgroundColor(color);
        }
        return this;
    }

    public BaseClickWarntDialog setImageDrawable(Drawable drawable){
        if(tagerImg != null){
            tagerImg.setImageDrawable(drawable);
        }
        return this;
    }

    public BaseClickWarntDialog setImageResource(@DrawableRes int resId){
        if(tagerImg != null){
            tagerImg.setImageResource(resId);
        }
        return this;
    }

    public BaseClickWarntDialog setImageBitmap(Bitmap bm){
        if(tagerImg != null){
            tagerImg.setImageBitmap(bm);
        }

        return this;
    }

    public BaseClickWarntDialog setText(CharSequence text){
        if(mContent != null){
            mContent.setText(text);
        }
        return this;
    }

    public BaseClickWarntDialog setText(@StringRes int resid){
        if(mContent != null){
            mContent.setText(resid);
        }
        return this;
    }

    public BaseClickWarntDialog setButtonText(String s){
        if(mConfirm != null){
            mConfirm.setText(s);
        }
        return this;
    }

    public BaseClickWarntDialog setButtonText(@StringRes int resid){
        if(mConfirm != null){
            mConfirm.setText(resid);
        }
        return this;
    }

}
