package com.example.omniandroid;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobile.client.Callback;
import com.amazonaws.mobile.client.HostedUIOptions;
import com.amazonaws.mobile.client.SignInUIOptions;
import com.amazonaws.mobile.client.UserStateDetails;
import com.amplifyframework.AmplifyException;
import com.amplifyframework.api.aws.AWSApiPlugin;
import com.amplifyframework.api.rest.RestOperation;
import com.amplifyframework.api.rest.RestOptions;
import com.amplifyframework.auth.cognito.AWSCognitoAuthPlugin;
import com.amplifyframework.core.Amplify;
import com.google.android.material.tabs.TabLayout;
import com.myhome.siviewpager.SIViewPager;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AuthMainActivity extends AppCompatActivity {

    private static final String TAG = com.example.omniandroid.AuthMainActivity.class.getSimpleName();

    private Context context;
    private ArrayList<Integer> imageList;
    private static final int DP = 24;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth_main);

        this.initializeDate();

        ViewPager viewPager = findViewById(R.id.viewpager);
        viewPager.setClipToPadding(false);

        float density = getResources().getDisplayMetrics().density;
        int margin = (int) (DP*density);
        viewPager.setPadding(margin, 0, margin, 0);
        viewPager.setPageMargin(margin/2);
        viewPager.setAdapter(new ViewPagerAdapter(this, imageList));

//        CircleIndicator circleIndicator = findViewById(R.id.circleIndicator);
//
//        circleIndicator.createDotPanel(3, R.drawable.indicator_dot_off, R.drawable.indicator_dot_on, 0);
        context = this;
//        try{
////            Amplify.addPlugin(new AWSCognitoAuthPlugin());
////            Amplify.addPlugin(new AWSApiPlugin());
////            Amplify.configure(getApplicationContext());
//            Log.i(TAG, "Initialized Amplify");
//            RestOptions options = RestOptions.builder()
//                    .addPath("/sensor")
//                    .build();
//
//            Amplify.API.get(options,
//                    restResponse -> Log.i(TAG, "GET success: " + restResponse.getData()),
//                    apiFailure -> Log.e("MyAmplifyApp", "GET failed.", apiFailure)
//                    );
//        } catch (AmplifyException error) {
//            Log.e(TAG, "Could not initialize Amplify", error);
//        }


        CommonAction.checkSession(this, true);
    }

    public void initializeDate() {
        imageList = new ArrayList();

        imageList.add(R.drawable.meeting);
        imageList.add(R.drawable.studying);
        imageList.add(R.drawable.detector);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "호출!!!!!!!!!!!!!");
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



}
