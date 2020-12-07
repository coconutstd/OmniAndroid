package com.example.omniandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;

import com.amazonaws.mobile.client.AWSMobileClient;

public class MeetingActivity extends AppCompatActivity {

    private WebView mWebView;
    private WebSettings mWebSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meeting);

        mWebView = (WebView)this.findViewById(R.id.webview_meeting);
        mWebView.setWebChromeClient(new WebChromeClient());
        mWebSettings = mWebView.getSettings();
        mWebSettings.setJavaScriptEnabled(true);
        mWebView.loadUrl("https://main.d2va81juaem8l3.amplifyapp.com/videochat");

//      앱 캐시 활성화 코드(비추천...)
//      mWebView.getSettings().setAppCacheEnabled(true);

    }
}