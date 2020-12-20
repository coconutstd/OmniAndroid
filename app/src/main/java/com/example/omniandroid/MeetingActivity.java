package com.example.omniandroid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.webkit.PermissionRequest;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.Toast;

import com.amazonaws.mobile.client.AWSMobileClient;
import com.example.omniandroid.fragments.MonitoringFragment;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MeetingActivity extends AppCompatActivity {

    private WebView mWebView;
    private WebSettings mWebSettings;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meeting);

        checkDangerousPermissions();
        mWebView = (WebView)this.findViewById(R.id.webview_meeting);
        mWebView.setWebChromeClient(new WebChromeClient() {
            @TargetApi(Build.VERSION_CODES.P)
            @Override
            public void onPermissionRequest(final PermissionRequest request) {
                request.grant(request.getResources());
            }
        });
        mWebSettings = mWebView.getSettings();
        mWebSettings.setJavaScriptEnabled(true); // 웹페이지 자바스클비트 허용 여부
        mWebSettings.setSupportMultipleWindows(true); // 새창 띄우기 허용 여부
        mWebSettings.setJavaScriptCanOpenWindowsAutomatically(true); // 자바스크립트 새창 띄우기(멀티뷰) 허용 여부
        mWebSettings.setLoadWithOverviewMode(true); // 메타태그 허용 여부
        mWebSettings.setUseWideViewPort(true); // 화면 사이즈 맞추기 허용 여부
        mWebSettings.setSupportZoom(false); // 화면 줌 허용 여부
        mWebSettings.setBuiltInZoomControls(false); // 화면 확대 축소 허용 여부
        mWebSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN); // 컨텐츠 사이즈 맞추기
        mWebSettings.setCacheMode(WebSettings.LOAD_NO_CACHE); // 브라우저 캐시 허용 여부
        mWebSettings.setDomStorageEnabled(true); // 로컬저장소 허용 여부
        mWebView.loadUrl("https://omnichat.site/");

        Thread thread = new Thread() {
            Thread thread = new Thread() {
                public void run() {
                    while(true) {
                        try {
                            sleep(5000);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        handler.sendEmptyMessage(0);
                    }
                }
            };
        };
        thread.start();

//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl("https://4cxysyupk7.execute-api.ap-northeast-2.amazonaws.com/")
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//
//        SensorService valueTempService = retrofit.create(SensorService.class);
//        Call<String> value = valueTempService.valueTemp();
//        value.enqueue(new Callback<String>() {
//
//            @Override
//            public void onResponse(Call<String> call, Response<String> response) {
//                Log.d("CallResponse" , "들어왔나요?????????");
////                Log.d("CallValue", call);
//                // 다 가져오지 말고 최근 꺼 하나만 가져와야 됨 - api 수정 필요
//                // 핸들러 실시간으로 계속 실행시키는 거 추가해야 함
//                if(call.equals("26.6"))
//                    getNotificationBuilder();
//            }
//
//            @Override
//            public void onFailure(Call<String> call, Throwable t) {
//                    Log.d("CallFailure", "설마 안들어오나요?!????!?!");
//            }
//        });
    }
    
    private void getNotificationBuilder() {
        
        // 알림 채널 생성
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationManager notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);

            NotificationChannel notificationChannel =
                    new NotificationChannel(
                            "alarm",
                            "테스트",
                            NotificationManager.IMPORTANCE_DEFAULT
                    );
            notificationChannel.setDescription("알람테스트");
            notificationManager.createNotificationChannel(notificationChannel);
        }
        
//        // 알림 클릭 시 모니터링 액티비티로 이동 - 근데 그러면 학습이 나가지니까 그냥 알림만 주는게 나을듯
//        Intent notificationIntent = new Intent(this, MainActivity.class);
//        PendingIntent pendingNotificationIntent = PendingIntent.getActivity(
//                this, 0,
//                notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT
//        );

        // 알림 builder 생성
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "alarm")
                .setSmallIcon(R.drawable.cute)
                .setContentTitle("센서 알림~~!~!!!!")
                .setContentText("학습하기에 온도가 너무 높습니다!!!!")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE)
//                .setContentIntent(pendingNotificationIntent)
                .setAutoCancel(true);
        final NotificationManager nm = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);

        nm.notify("태그????????", 1, builder.build());
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            Log.d("Call", "들어왔나요 핸들러로????");
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://4cxysyupk7.execute-api.ap-northeast-2.amazonaws.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            SensorService valueTempService = retrofit.create(SensorService.class);
            Call<String> value = valueTempService.valueTemp();
            value.enqueue(new retrofit2.Callback<String>() {

                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    Log.d("CallResponse" , "들어왔나요?????????");
//                Log.d("CallValue", call);
                    // 다 가져오지 말고 최근 꺼 하나만 가져와야 됨 - api 수정 필요
                    // 핸들러 실시간으로 계속 실행시키는 거 추가해야 함
                    if(call.toString().equals("26.6"))
                        getNotificationBuilder();
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    Log.d("CallFailure", "설마 안들어오나요?!????!?!");
                }
            });
        }
    };

    private void checkDangerousPermissions() {
        String[] permissions = {
                android.Manifest.permission.READ_EXTERNAL_STORAGE,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                android.Manifest.permission.CAMERA,
                android.Manifest.permission.RECORD_AUDIO
        };

        int permissionCheck = PackageManager.PERMISSION_GRANTED;
        for (int i = 0; i < permissions.length; i++) {
            permissionCheck = ContextCompat.checkSelfPermission(this, permissions[i]);
            if (permissionCheck == PackageManager.PERMISSION_DENIED) {
                break;
            }
        }

        if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "권한 있음", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "권한 없음", Toast.LENGTH_LONG).show();

            if (ActivityCompat.shouldShowRequestPermissionRationale(this, permissions[0])) {
                Toast.makeText(this, "권한 설명 필요함.", Toast.LENGTH_LONG).show();
            } else {
                ActivityCompat.requestPermissions(this, permissions, 1);
            }
        }
    }
}