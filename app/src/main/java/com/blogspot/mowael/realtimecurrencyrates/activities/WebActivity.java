package com.blogspot.mowael.realtimecurrencyrates.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;

import com.blogspot.mowael.realtimecurrencyrates.R;

public class WebActivity extends AppCompatActivity {

    private WebView wvAbout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);

        wvAbout = (WebView) findViewById(R.id.wvNews);
        Intent intent = getIntent();
        Log.d("link", intent.getStringExtra("link"));
        wvAbout.loadUrl(intent.getStringExtra("link"));
    }
}
