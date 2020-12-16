package com.example.omniandroid.fragments;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.omniandroid.R;

import java.io.FileInputStream;
import java.io.FileOutputStream;

import static android.app.Activity.RESULT_OK;
import static android.content.ContentValues.TAG;
import static android.content.Context.MODE_NO_LOCALIZED_COLLATORS;

public class CalendarFragment extends Fragment {

    public String fname = null;
    public String str = null;
    public CalendarView calendarView;
    public Button add_Btn, del_Btn;
    public TextView diaryTextView, textView2, textView3;
    public EditText contextEditText;

    public CalendarFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_calendar, container, false);

        CalendarView calendarView = (CalendarView)view.findViewById(R.id.calendarView);

        diaryTextView = (TextView)view.findViewById(R.id.diaryTextView);
        add_Btn = (Button)view.findViewById(R.id.add_Btn);
        del_Btn = (Button)view.findViewById(R.id.del_Btn);

        textView2 = (TextView)view.findViewById(R.id.textView2);

        String userID = getArguments().getString("user_id");

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                add_Btn.setVisibility(View.VISIBLE);
                textView2.setVisibility(View.VISIBLE);
                checkDay(year, month, dayOfMonth, userID);
            }
        });
        add_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder ad = new AlertDialog.Builder(getContext());
                ad.setTitle("메모 수정 및 추가");
                ad.setMessage("내용을 입력하세요~!");

                final EditText et = new EditText((getContext()));
                ad.setView(et);
                ad.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(et.getText().toString().equals("")) {
                            Toast.makeText(getContext(), "작성하셈", Toast.LENGTH_LONG).show();
                        }
                        else {
                            saveDiary(fname, et.getText().toString());
                            textView2.setVisibility(View.VISIBLE);
                        }
                    }
                });
                ad.setNegativeButton("취소", null);
                ad.show();
            }
        });
        del_Btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                textView2.setText("");
                removeDiary(fname);
            }
        });

        return view;
    }

    public void checkDay(int cYear, int cMonth, int cDay, String userID) {
        fname=""+ userID + cYear + "-" + (cMonth+1) + "" + "-" + cDay + ".txt";//저장할 파일 이름설정
        FileInputStream fis=null;//FileStream fis 변수

        try{
            fis = getActivity().openFileInput(fname);

            byte[] fileData=new byte[fis.available()];

            if(fileData.length == 0)
                textView2.setVisibility(View.INVISIBLE);

            fis.read(fileData);

            str = new String(fileData);

            textView2.setVisibility(View.VISIBLE);
            textView2.setText(str);

            fis.close();

        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @SuppressLint("WrongConstant")
    public void removeDiary(String readDay){

        AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
        alert.setTitle("일정 삭제");
        alert.setMessage("정말 삭제할건가요??????");
        alert.setPositiveButton("yes!", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try{
                    FileOutputStream fos=null;
                    fos = getActivity().openFileOutput(readDay,MODE_NO_LOCALIZED_COLLATORS);
                    String content="";
                    fos.write((content).getBytes());
                    fos.close();

                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
        alert.setNeutralButton("취소", null);

        alert.create().show();

    }
    @SuppressLint("WrongConstant")
    public void saveDiary(String readDay, String et){
        FileOutputStream fos=null;

        try{
            fos = getActivity().openFileOutput(readDay,MODE_NO_LOCALIZED_COLLATORS);
            String content=et;
            fos.write((content).getBytes());
            fos.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
