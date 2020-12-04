package com.example.omniandroid;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobile.client.Callback;
import com.amazonaws.mobile.client.UserStateDetails;

public class SplashActivity extends AppCompatActivity {

    private final int SPLASH_DISPLAY_TIME = 5000;
    private final static String TAG = com.example.omniandroid.SplashActivity.class.getSimpleName();

    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        context = this;

        _initCognito();
    }

    private void _initCognito() {
        // Add code here
        if (AWSMobileClient.getInstance().getConfiguration() == null) {
            AWSMobileClient.getInstance().initialize(getApplicationContext(), new Callback<UserStateDetails>() {
                @Override
                public void onResult(UserStateDetails userStateDetails) {
                    switch (userStateDetails.getUserState()){
                        case SIGNED_IN:
                            CommonAction.openMain(context);
                            break;
                        case SIGNED_OUT:
                            Log.d(TAG, "Do nothing yet");
                            CommonAction.openAuthMain(context);
                            break;
                        default:
                            AWSMobileClient.getInstance().signOut();
                            break;
                    }
                }
                @Override
                public void onError(Exception e) {
                    Log.e("INIT", e.toString());
                }
            });
        } else if (AWSMobileClient.getInstance().isSignedIn()){
            CommonAction.openMain(context);
        } else {
            CommonAction.openAuthMain(context);
        }
    }


}
