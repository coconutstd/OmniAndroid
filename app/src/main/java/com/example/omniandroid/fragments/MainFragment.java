package com.example.omniandroid.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;

import com.amazonaws.mobile.client.AWSMobileClient;
import com.example.omniandroid.CameraActivity;
import com.example.omniandroid.CommonAction;
import com.example.omniandroid.MeetingActivity;
import com.example.omniandroid.R;

import butterknife.ButterKnife;

public class MainFragment extends Fragment {
    
    private WebView mWebView;
    private WebSettings mWebSettings;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        Button btn_startmeeting = (Button)view.findViewById(R.id.btnStartmeeting);
        Button camera = (Button)view.findViewById(R.id.photo);

        btn_startmeeting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MeetingActivity.class);
                startActivity(intent);
            }
        });

        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CameraActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }

}