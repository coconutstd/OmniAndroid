package com.example.omniandroid;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobile.client.Callback;
import com.amazonaws.mobile.client.HostedUIOptions;
import com.amazonaws.mobile.client.SignInUIOptions;
import com.amazonaws.mobile.client.UserStateDetails;

public class AuthMainActivity extends AppCompatActivity {

    private static final String TAG = com.example.omniandroid.AuthMainActivity.class.getSimpleName();

    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth_main);

        context = this;

        CommonAction.checkSession(this, true);
    }

    private void _openFacebookLogin() {
        // Add code here
        HostedUIOptions hostedUIOptions = HostedUIOptions.builder()
                .scopes("openid", "email")
                .identityProvider("Facebook")
                .build();

        SignInUIOptions signInUIOptions = SignInUIOptions.builder()
                .hostedUIOptions(hostedUIOptions)
                .build();

        AWSMobileClient.getInstance().showSignIn((Activity) context, signInUIOptions, new Callback<UserStateDetails>() {
            @Override
            public void onResult(UserStateDetails details) {
                Log.d(TAG, "onResult: " + details.getUserState());
            }

            @Override
            public void onError(Exception e) {
                Log.e(TAG, "onError: ", e);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Add code here
        Intent activityIntent = getIntent();
        if (activityIntent.getData() != null &&
                "omniandroid".equals(activityIntent.getData().getScheme())) {
            Log.i(TAG, activityIntent.getData().getScheme());
            if (AWSMobileClient.getInstance().handleAuthResponse(activityIntent))
                CommonAction.checkSession(this, true);
        } else {
            Log.i(TAG, "getData() is null");
        }

    }

    public void openLogin(View view) {
        Intent intent = new Intent(context, com.example.omniandroid.LoginActivity.class);
        startActivity(intent);
    }

    public void openRegistration(View view) {
        Intent intent = new Intent(context, com.example.omniandroid.SignUpActivity.class);
        startActivity(intent);
    }

    public void openFacebookLogin(View view) {
        _openFacebookLogin();
    }

}
