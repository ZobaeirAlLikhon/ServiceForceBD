package com.sfbd.serviceforcebd.activity;

import androidx.appcompat.app.AppCompatActivity;

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
        silkview.loadUrl("https://www.serviceforcebd.com/");
        WebSettings webSettings = silkview.getSettings();
        webSettings.setJavaScriptEnabled(true);
    }
}