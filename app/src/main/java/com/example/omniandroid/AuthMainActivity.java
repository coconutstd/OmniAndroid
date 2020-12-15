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
import com.amplifyframework.AmplifyException;
import com.amplifyframework.api.aws.AWSApiPlugin;
import com.amplifyframework.api.rest.RestOperation;
import com.amplifyframework.api.rest.RestOptions;
import com.amplifyframework.auth.cognito.AWSCognitoAuthPlugin;
import com.amplifyframework.core.Amplify;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AuthMainActivity extends AppCompatActivity {

    private static final String TAG = com.example.omniandroid.AuthMainActivity.class.getSimpleName();

    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth_main);

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

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://4cxysyupk7.execute-api.ap-northeast-2.amazonaws.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        SensorService service1 = retrofit.create(SensorService.class);

        Call<SensorResult> call = service1.getSensors("sensor");

        call.enqueue(new retrofit2.Callback<SensorResult>() {
            @Override
            public void onResponse(Call<SensorResult> call, Response<SensorResult> response) {
                if(response.isSuccessful()){
                    SensorResult result = response.body();
                    Log.d(TAG, "onResponse: 성공, 결과\n"+ response.body());
                    Log.d(TAG, "onResponse: 성공, 결과\n"+ result.toString());
                } else {
                    Log.d(TAG, "onResponse: 실패");
                }
            }

            @Override
            public void onFailure(Call<SensorResult> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.getMessage());
            }
        });


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

    public void openFacebookLogin(View view) {
        _openFacebookLogin();
    }

}
