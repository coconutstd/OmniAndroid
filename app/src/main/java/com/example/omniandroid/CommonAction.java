package com.example.omniandroid;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobile.client.UserStateDetails;
import com.amazonaws.mobile.client.UserStateListener;

public class CommonAction {
    private static String userId_;
    private static int identityCheckedId_;

    public static void openMain(Context context, String userId, int identityCheckedId){
        com.example.omniandroid.CommonAction.openActivityOnTop(context, com.example.omniandroid.MainActivity.class, userId, identityCheckedId);
    }

    public static void openAuthMain(Context context, String userId, int identityCheckedId){
        com.example.omniandroid.CommonAction.openActivityOnTop(context, com.example.omniandroid.AuthMainActivity.class, userId, identityCheckedId);
    }

    public static void openSplash(Context context, String userId, int identityCheckedId){
        com.example.omniandroid.CommonAction.openActivityOnTop(context, com.example.omniandroid.SplashActivity.class, userId, identityCheckedId);
    }

    public static void openActivityOnTop(Context context, Class targetClass, String userId, int identityCheckedId) {
        userId_ = userId;
        identityCheckedId_ = identityCheckedId;

        Intent intent=new Intent(context, targetClass);
        intent.putExtra("user_id", userId);
        intent.putExtra("checkedId", identityCheckedId);
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
                            com.example.omniandroid.CommonAction.openMain(context, userId_, identityCheckedId_);
                        break;
                    default:
                        Log.i("checkSession", "unsupported");
                        com.example.omniandroid.CommonAction.openSplash(context, userId_, identityCheckedId_);
                        break;
                }
            }
        });
    }

}
