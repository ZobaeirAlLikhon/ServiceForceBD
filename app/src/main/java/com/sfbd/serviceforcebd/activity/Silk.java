package com.sfbd.serviceforcebd.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.sfbd.serviceforcebd.R;

public class Silk extends AppCompatActivity {
     WebView silkview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_silk);


        silkview = findViewById(R.id.silkview);
        silkview.setWebViewClient(new WebViewClient());
        silkview.loadUrl("https://docs.google.com/forms/d/e/1FAIpQLScdRGm7Gwor7qPaCdUny3v8v5RDA640lqYaMHtgHMbZMpyfQw/viewform");
        WebSettings webSettings = silkview.getSettings();
        webSettings.setJavaScriptEnabled(true);

    }
}