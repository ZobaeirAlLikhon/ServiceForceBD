
package com.sfbd.serviceforcebd.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.sfbd.serviceforcebd.R;

public class Goru extends AppCompatActivity {

    WebView goruview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goru);

        goruview = findViewById(R.id.goruview);
        goruview.setWebViewClient(new WebViewClient());
        goruview.loadUrl("https://www.serviceforcebd.com/");
        WebSettings webSettings = goruview.getSettings();
        webSettings.setJavaScriptEnabled(true);

    }
}