package com.example.omniandroid.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.omniandroid.Mqtt;
import com.example.omniandroid.R;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttClient;



public class MonitoringFragment extends Fragment {

    private MqttAndroidClient mqttAndroidClient;
    private final String SUB_TOPIC = "토픽명";
    private final String SERVER_URI = "tcp://192.168.0.125:1883";
    private Mqtt mqttClient;

    public MonitoringFragment() {
        // Required empty public constructor
    }

    public static MonitoringFragment newInstance(String param1, String param2) {
        MonitoringFragment fragment = new MonitoringFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mqttClient = new Mqtt(this, SERVER_URI);
        try {
            mqttClient.setCallback(topic);
        }

        return inflater.inflate(R.layout.fragment_monitoring, container, false);
    }
}