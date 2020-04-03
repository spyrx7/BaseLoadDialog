package com.lemonjun.loadingdialog;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;

import com.lemonjun.explosionfied.ExplosionField;
import com.lemonjun.library.base.BaseClickWarntDialog;
import com.lemonjun.library.base.BaseLoadingDialog;
import com.lemonjun.library.base.BaseWarntDialog;
import com.lemonjun.library.tools.BaseDialogTools;
import com.lemonjun.library.view.LoadView;

public class MainActivity extends AppCompatActivity {

    private ExplosionField mExplosionField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       /* mExplosionField = ExplosionField.attach2Window(this);
        addListener(findViewById(R.id.root));

        LoadView loadView = findViewById(R.id.bd_load);
        loadView.setStatus(LoadView.State.STATUS_IDLE);*/

    }

   /* public void onClick(View view){
        switch (view.getId()){
            case R.id.btn_load:
                BaseDialogTools.getInstance().createLoadDialog(this,"加载中。。。");

                Handler h = new Handler();
                Runnable r = new Runnable() {
                    @Override
                    public void run() {
                        BaseDialogTools.getInstance().createWarntDialog(MainActivity.this).setText("服务器返回异常");
                    }
                };

                h.postDelayed(r,5000);

                break;
            case R.id.btn_want:
                BaseDialogTools.getInstance().createWarntDialog(this).show();
                break;
            case R.id.btn_want_btn:
                BaseClickWarntDialog clickWarntDialog = new BaseClickWarntDialog(this);
                clickWarntDialog.show();
                clickWarntDialog.setOnClickListener(new BaseClickWarntDialog.OnClickListener() {
                    @Override
                    public void onClick(Dialog dialog) {
                        dialog.dismiss();
                    }
                });
                break;
        }
    }*/

    private void addListener(View root){
        if(root instanceof ViewGroup) {
            ViewGroup parent = (ViewGroup) root;
            for(int i = 0; i < parent.getChildCount(); i++){
                addListener(parent.getChildAt(i));
            }
        }else{
            root.setClickable(true);
            root.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mExplosionField.explode(v);
                    v.setOnClickListener(null);
                }
            });
        }
    }

}
