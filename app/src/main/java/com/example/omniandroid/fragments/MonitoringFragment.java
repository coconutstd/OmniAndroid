package com.example.omniandroid.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.amazonaws.auth.CognitoCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.example.omniandroid.Mqtt;
import com.example.omniandroid.R;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class MonitoringFragment extends Fragment {

    private final String SUB_TOPIC = "토픽명";
    private final String SERVER_URI = "tcp://192.168.0.125:1883";
    private Mqtt mqttClient;
    CognitoCredentialsProvider credentials = new CognitoCredentialsProvider(
            "ap-northeast-2:96bceff2-c9b2-4ac0-9712-bd71504708e8",
            Regions.AP_NORTHEAST_2
    );

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
        mqttClient = new Mqtt(getContext(), SERVER_URI);
        try {
            String topics[] = new String[10];
            for(int i = 0; i < topics.length; i++) {
                topics[i] = SUB_TOPIC;
                mqttClient.connect(topics);
            }
            //mqttClient.setCallback(onReceived(topics, ));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return inflater.inflate(R.layout.fragment_monitoring, container, false);
    }

    //토픽 수신 처리
    public void onReceived(String topic, MqttMessage message) {
        byte[] msg = message.getPayload();
    }
}