package com.example.omniandroid;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobile.client.Callback;
import com.amazonaws.mobile.client.results.SignInResult;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Password;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.omniandroid.CommonUtil.makeToast;


public class LoginActivity extends AppCompatActivity implements Validator.ValidationListener {

    private static final String TAG = com.example.omniandroid.LoginActivity.class.getSimpleName();

    Validator validator;

    @BindView(R.id.etEmail)
    @NotEmpty
    @Email
    EditText etEmail;

    @BindView(R.id.etPassword)
    @Password(min = 8, scheme = Password.Scheme.ANY)
    EditText etPassword;

//    @BindView(R.id.identity)
//    RadioGroup identity;
//
//    @BindView(R.id.teacher)
//    RadioButton teacher;
//
//    @BindView(R.id.student)
//    RadioButton student;


    private Context context;
    public int identityCheckedId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        RadioGroup identity = findViewById(R.id.identity);
        RadioButton teacher = findViewById(R.id.teacher);
        RadioButton student = findViewById(R.id.student);

        boolean check = false;
        teacher.setChecked(false);
        student.setChecked(false);


        identity.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                switch(checkedId) {
                    case R.id.teacher:
                        Log.d("R.id.teacher", String.valueOf(R.id.teacher));
                        AlertDialog.Builder ad = new AlertDialog.Builder(LoginActivity.this);

                        ad.setTitle("선생님 확인 메세지");
                        ad.setMessage("발급받은 인증번호를 입력해주세요.");

                        final EditText et = new EditText(LoginActivity.this);
                        ad.setView(et);
                        Log.d("dialog","들어갔는가요???");
                        ad.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //인증번호랑 et랑 비교 후 일치하면 넘어가기
                                Log.d("dialogValue1", et.getText().toString());
                                if(et.getText().toString().equals("1234")) {
                                    Toast.makeText(context.getApplicationContext(), "인증이 완료되었습니다.", Toast.LENGTH_LONG).show();
                                    Log.d("teacher!!!!", String.valueOf(teacher.isChecked()));
                                    dialog.cancel();
                                }
                                else if(et.getText().toString().equals("")) {
                                    Toast.makeText(context.getApplicationContext(), "인증번호가 입력되지 않았습니다.", Toast.LENGTH_LONG).show();
                                    teacher.setChecked(false);
                                    dialog.cancel();
                                }
                                else {
                                    Toast.makeText(context.getApplicationContext(), "인증에 실패하였습니다.", Toast.LENGTH_LONG).show();
                                    teacher.setChecked(false);
                                    dialog.cancel();
                                }
                                identityCheckedId = R.id.teacher;
                                Log.d("여기는 오니??????? teacher", String.valueOf(identityCheckedId));
                            }
                        });
                        ad.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                teacher.setChecked(false);
                                dialog.cancel();
                            }
                        });
                        ad.show();

                        Log.d("teacherChecked 여기까지 오니?", String.valueOf(identityCheckedId));
                        break;
                    case R.id.student:
                        Log.d("R.id.student", String.valueOf(R.id.student));
                        identityCheckedId = R.id.student;
                        Log.d("studentChecked", String.valueOf(identityCheckedId));
                        break;
                }
            }
        });

        ButterKnife.bind(this);
        validator = new Validator(this);
        validator.setValidationListener(this);

        context = this;

    }

    @Override
    public void onValidationSucceeded() {
        _signIn(etEmail.getText().toString(), etPassword.getText().toString(), etEmail.getText().toString(), identityCheckedId);
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        for (ValidationError error : errors) {
            View view = error.getView();
            String message = error.getCollatedErrorMessage(this);

            // Display error messages ;)
            if (view instanceof EditText) {
                ((EditText) view).setError(message);
            } else {
                Toast.makeText(this, message, Toast.LENGTH_LONG).show();
            }
        }
    }

    private void _signIn(String userName, String password, String userId, int identityCheckedId) {
        AWSMobileClient.getInstance().signIn(userName, password, null, new Callback<SignInResult>() {
            @Override
            public void onResult(final SignInResult signInResult) {
                runOnUiThread(() -> {
                    Log.d(TAG, "Sign-in callback state: " + signInResult.getSignInState());
                    switch (signInResult.getSignInState()) {
                        case DONE:
                            makeToast(context,"로그인 성공");
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

    public void doLogin(View view) {
        if(identityCheckedId == 0)
            Toast.makeText(context.getApplicationContext(), "비어있는 항목을 모두 작성해주세요.", Toast.LENGTH_LONG).show();
        else
            validator.validate();
    }
}
