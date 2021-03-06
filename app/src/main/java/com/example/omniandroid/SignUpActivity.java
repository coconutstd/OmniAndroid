package com.example.omniandroid;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobile.client.Callback;
import com.amazonaws.mobile.client.results.SignInResult;
import com.amazonaws.mobile.client.results.SignUpResult;
import com.amazonaws.mobile.client.results.UserCodeDeliveryDetails;
import com.example.omniandroid.fragments.SignUpConfirmFragment;
import com.example.omniandroid.fragments.SignUpFragment;

import java.util.HashMap;
import java.util.Map;

import static com.example.omniandroid.CommonUtil.makeToast;


public class SignUpActivity extends FragmentActivity
        implements com.example.omniandroid.fragments.SignUpFragment.OnFragmentInteractionListener,
        com.example.omniandroid.fragments.SignUpConfirmFragment.OnFragmentInteractionListener {

    private static final String TAG = com.example.omniandroid.SignUpActivity.class.getSimpleName();
    private String userName, password;
    private Context context;
    private static String userId;
    private static int identityCheckedId_;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        setSignUpFragment();
        context = this;

    }

    private void setSignUpFragment() {
        com.example.omniandroid.fragments.SignUpFragment signUpFragment = new com.example.omniandroid.fragments.SignUpFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.signup_layout, signUpFragment);
        transaction.commit();
    }

    private void setSignUpConfirmFragment() {
        com.example.omniandroid.fragments.SignUpConfirmFragment signUpConfirmFragment = new com.example.omniandroid.fragments.SignUpConfirmFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.signup_layout, signUpConfirmFragment);
        transaction.commit();
    }

    @Override
    public void signUp(String email, String password) {
        userName = email;
        this.password = password;

        // Add code here
        final Map<String, String> attributes = new HashMap<>();
        attributes.put("email", email);
        AWSMobileClient.getInstance().signUp(userName, password, attributes, null, new Callback<SignUpResult>() {
            @Override
            public void onResult(final SignUpResult signUpResult) {
                runOnUiThread(() -> {
                    if (!signUpResult.getConfirmationState()) {
                        final UserCodeDeliveryDetails details = signUpResult.getUserCodeDeliveryDetails();
                        makeToast(context, "Confirm sign-up with: " + details.getDestination());
                        setSignUpConfirmFragment();
                    } else {
                        makeToast(context, "Sign-up done.");
                    }
                });
            }

            @Override
            public void onError(Exception e) {
                Log.e(TAG, "Sign-up error", e);
                runOnUiThread(() -> {
                    if (e instanceof AmazonServiceException)
                        makeToast(context, ((AmazonServiceException) e).getErrorMessage());
                });
            }
        });
    }

    @Override
    public void confirmSignUp(String code) {
        // Add code here
        AWSMobileClient.getInstance().confirmSignUp(userName, code, new Callback<SignUpResult>() {
            @Override
            public void onResult(final SignUpResult signUpResult) {
                runOnUiThread(() -> {
                    Log.d(TAG, "Sign-up callback state: " + signUpResult.getConfirmationState());
                    if (!signUpResult.getConfirmationState()) {
                        final UserCodeDeliveryDetails details = signUpResult.getUserCodeDeliveryDetails();
                        makeToast(context,"Confirm sign-up with: " + details.getDestination());
                    } else {
                        makeToast(context, "Sign-up done.");
                        // SignIn and move to MainActivity
                        _signIn(userName, password, identityCheckedId_);
                    }
                });
            }

            @Override
            public void onError(Exception e) {
                Log.e(TAG, "Confirm sign-up error", e);
                runOnUiThread(() -> {
                    if (e instanceof AmazonServiceException)
                        makeToast(context, ((AmazonServiceException) e).getErrorMessage());
                });
            }
        });
    }

    private void _signIn(String username, String password, int identityCheckedId) {
        // Add code here
        userId = username;
        identityCheckedId_ = identityCheckedId;
        AWSMobileClient.getInstance().signIn(username, password, null, new Callback<SignInResult>() {
            @Override
            public void onResult(final SignInResult signInResult) {
                runOnUiThread(() -> {
                    Log.d(TAG, "Sign-in callback state: " + signInResult.getSignInState());
                    switch (signInResult.getSignInState()) {
                        case DONE:
                            makeToast(context, "Sign-in done.");
                            CommonAction.openMain(context, userId, identityCheckedId);
                            break;
                        case SMS_MFA:
                            makeToast(context, "Please confirm sign-in with SMS.");
                            break;
                        case NEW_PASSWORD_REQUIRED:
                            makeToast(context, "Please confirm sign-in with new password.");
                            break;
                        default:
                            makeToast(context, "Unsupported sign-in confirmation: " + signInResult.getSignInState());
                            break;
                    }
                });
            }

            @Override
            public void onError(Exception e) {
                Log.e(TAG, "Sign-in error", e);
                runOnUiThread(() -> {
                    if (e instanceof AmazonServiceException)
                        makeToast(context, ((AmazonServiceException) e).getErrorMessage());
                });
            }
        });
    }
}
