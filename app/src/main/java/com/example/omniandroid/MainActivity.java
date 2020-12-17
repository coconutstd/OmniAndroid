package com.example.omniandroid;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.amazonaws.mobile.client.AWSMobileClient;
import com.amplifyframework.core.category.CategoryConfiguration;
import com.example.omniandroid.fragments.CalendarFragment;
import com.example.omniandroid.fragments.MainFragment;
import com.example.omniandroid.fragments.MonitoringFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private static final int MY_PERMISSION_CAMERA = 1111;
    private static final String TAG = "tko-main";
    private RecyclerView recyclerView;
    //private PostAdapter mAdapter;
    BottomNavigationView bottomNavigationView;

    MainFragment fragment1;
    MonitoringFragment fragment2;
    CalendarFragment fragment3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnLogout = findViewById(R.id.btnLogout);

        CommonAction.checkSession(this, false);
        checkPermission();

        bottomNavigationView = findViewById(R.id.bottomNavigationView);

        Intent intent = getIntent();
        String userId = intent.getStringExtra("user_id");

        //프래그먼트 생성
        fragment1 = new MainFragment();
        fragment2 = new MonitoringFragment();
        fragment3 = new CalendarFragment();

        Bundle bundle = new Bundle();
        bundle.putString("user_Id", "user_id");
        fragment3.setArguments(bundle);

        //처음 띄워줄 뷰
        getSupportFragmentManager().beginTransaction().replace(R.id.main_layout, fragment1).commitAllowingStateLoss();

        //아이콘 선택했을 때 원하는 프래그먼트 띄워지도록 리스너
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
               switch (item.getItemId()) {
                   case R.id.tab1:{
                       getSupportFragmentManager().beginTransaction().replace(R.id.main_layout, fragment1).commitAllowingStateLoss();
                       return true;
                   }
                   case R.id.tab2:{
                       getSupportFragmentManager().beginTransaction().replace(R.id.main_layout, fragment2).commitAllowingStateLoss();
                       return true;
                   }
                   case R.id.tab3:{
                       getSupportFragmentManager().beginTransaction().replace(R.id.main_layout, fragment3).commitAllowingStateLoss();
                       return true;
                   }

                   default:return false;
               }
            }
        });

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AWSMobileClient.getInstance().signOut();
                CommonAction.openAuthMain(com.example.omniandroid.MainActivity.this);
            }
        });
    }

    private void checkPermission(){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            if ((ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE))) {
                Log.e(TAG, "permission error - ");
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, MY_PERMISSION_CAMERA);
            }
        }
        if (Build.VERSION.SDK_INT >= 23) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.INTERNET}, 1);
        }
    }

    protected void onResume() {
        super.onResume();
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSION_CAMERA:
                for (int i = 0; i < grantResults.length; i++) {
                    if (grantResults[i] < 0) {
                        return;
                    }
                }
                break;
        }
    }
}
