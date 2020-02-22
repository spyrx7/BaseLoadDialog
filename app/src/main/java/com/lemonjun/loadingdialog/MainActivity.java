package com.lemonjun.loadingdialog;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;

import com.lemonjun.library.base.BaseClickWarntDialog;
import com.lemonjun.library.base.BaseLoadingDialog;
import com.lemonjun.library.base.BaseWarntDialog;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClick(View view){
        switch (view.getId()){
            case R.id.btn_load:
                BaseLoadingDialog dialog = new BaseLoadingDialog(this);
                dialog.show();
                break;
            case R.id.btn_want:
                BaseWarntDialog dWarnt = new BaseWarntDialog(this);
                dWarnt.show();
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

    }
}
