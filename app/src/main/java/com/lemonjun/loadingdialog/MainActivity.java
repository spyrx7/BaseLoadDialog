package com.lemonjun.loadingdialog;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.lemonjun.library.base.BaseLoadingDialog;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BaseLoadingDialog dialog = new BaseLoadingDialog(this);

        dialog.show();

    }
}
