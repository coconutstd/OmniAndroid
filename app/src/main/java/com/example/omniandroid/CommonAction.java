package com.example.omniandroid;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobile.client.UserStateDetails;
import com.amazonaws.mobile.client.UserStateListener;

public class CommonAction {
    public static void openMain(Context context){
        com.example.omniandroid.CommonAction.openActivityOnTop(context, com.example.omniandroid.MainActivity.class);
    }

    public static void openAuthMain(Context context){
        com.example.omniandroid.CommonAction.openActivityOnTop(context, com.example.omniandroid.AuthMainActivity.class);
    }

    public static void openSplash(Context context){
        com.example.omniandroid.CommonAction.openActivityOnTop(context, com.example.omniandroid.SplashActivity.class);
    }

    public static void openActivityOnTop(Context context, Class targetClass) {
        Intent intent=new Intent(context, targetClass);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);
    }

    public static void checkSession(Context context, boolean moveToMain) {
        // Add code here
        AWSMobileClient.getInstance().addUserStateListener(new UserStateListener() {
            @Override
            public void onUserStateChanged(UserStateDetails userStateDetails) {
                switch(userStateDetails.getUserState()){
                    case SIGNED_IN:
                        Log.i("checkSession", "user signed in");
                        if (moveToMain)
                            com.example.omniandroid.CommonAction.openMain(context);
                        break;
                    default:
                        Log.i("checkSession", "unsupported");
                        com.example.omniandroid.CommonAction.openSplash(context);
                        break;
                }
            }
        });
    }

}
