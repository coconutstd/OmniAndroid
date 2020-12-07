package com.example.omniandroid.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.omniandroid.R;
import com.example.omniandroid.SignUpActivity;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.ConfirmPassword;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Password;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SignUpFragment extends Fragment implements Validator.ValidationListener {

    private static final String TAG = com.example.omniandroid.fragments.SignUpFragment.class.getSimpleName();
    RadioButton teacher, student;

    @BindView(R.id.etEmail)
    @NotEmpty
    @Email
    EditText etEmail;

    @BindView(R.id.identity)
    RadioGroup identity;

    @BindView(R.id.etPassword)
    @Password(min = 8, scheme = Password.Scheme.ANY)
    EditText etPassword;

    @BindView(R.id.etPassword1)
    @ConfirmPassword
    EditText etPassword1;

    Validator validator;

    private OnFragmentInteractionListener mListener;

    public SignUpFragment() {
        validator = new Validator(this);
        validator.setValidationListener(this);

    }

    public static com.example.omniandroid.fragments.SignUpFragment newInstance(String param1, String param2) {
        com.example.omniandroid.fragments.SignUpFragment fragment = new com.example.omniandroid.fragments.SignUpFragment();
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
        View view = inflater.inflate(R.layout.fragment_sign_up, container, false);
        ButterKnife.bind(this, view);

        teacher = view.findViewById(R.id.teacher);
        student = view.findViewById(R.id.student);

        teacher.setChecked(false);
        student.setChecked(false);

        teacher.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(teacher.isChecked()) {
                    // 체크되었을 때 인증번호 확인 팝업 띄우기
                    AlertDialog.Builder ad = new AlertDialog.Builder(getContext());

                    ad.setTitle("진짜 선생님인가요????");
                    ad.setMessage("인증번호를 입력해주세요");

                    final EditText et = new EditText((getContext()));
                    ad.setView(et);
                    ad.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //DB에 있는 인증번호랑 et랑 비교 후 일치하면 넘어가기
                        }
                    });
                    ad.show();
                }
            }
        });
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onValidationSucceeded() {
        mListener.signUp(etEmail.getText().toString(), etPassword.getText().toString(), identity.getCheckedRadioButtonId());
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        for (ValidationError error : errors) {
            View view = error.getView();
            String message = error.getCollatedErrorMessage(getActivity());

            // Display error messages ;)
            if (view instanceof EditText) {
                ((EditText) view).setError(message);
            } else {
                Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
            }
        }
    }


    public interface OnFragmentInteractionListener {
        void signUp(String email, String password, int identity);
    }

    @OnClick(R.id.btnRegistration)
    void doSignUp() {
        Log.d(TAG, "doSignUp");
        validator.validate();
    }
}