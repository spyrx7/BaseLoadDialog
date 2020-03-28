package com.lemonjun.loadingdialog;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class AliPayActivity extends AppCompatActivity {

    WebView webView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_ali_main);

        findViewById(R.id.btn_open).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAli();
            }
        });


        webView = findViewById(R.id.webview);

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
// 设置可以访问文件
        webSettings.setAllowFileAccess(true);
// 设置支持缩放
        webSettings.setBuiltInZoomControls(true);
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
// webSettings.setDatabaseEnabled(true);

// 使用localStorage则必须打开
        webSettings.setDomStorageEnabled(true);

        webSettings.setGeolocationEnabled(true);


        webView.setWebViewClient(new WebViewClient(){

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (parseScheme(url)) {
                    try {
                        Uri uri = Uri.parse(url);
                        Intent intent;
                        intent = Intent.parseUri(url,
                                Intent.URI_INTENT_SCHEME);
                        intent.addCategory("android.intent.category.BROWSABLE");
                        intent.setComponent(null);
                        // intent.setSelector(null);
                        startActivity(intent);

                    } catch (Exception e) {

                    }
                } else {
                    view.loadUrl(url);
                }

                return true;
            }


        });



    }

    public boolean parseScheme(String url) {

        if (url.contains("platformapi/startapp")) {

            return true;
        } else {

            return false;
        }
    }


    private void openAli(){
        webView.loadUrl("https://qr.alipay.com/bax05351pgjhc4yegd2y2084");
    }
}
