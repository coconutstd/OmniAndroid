package com.example.omniandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;

public class NotificationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        NotificationManager notificationManager = (NotificationManager)NotificationActivity.this.getSystemService(NotificationActivity.this.NOTIFICATION_SERVICE);
        Intent intent = new Intent(NotificationActivity.this.getApplicationContext(), NotificationActivity.class);

        Notification.Builder builder = new Notification.Builder(getApplicationContext());
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP| Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingNotificationIntent = PendingIntent.getActivity(NotificationActivity.this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setSmallIcon(R.drawable.cute).setTicker("OMNI").setWhen(System.currentTimeMillis())
                .setNumber(1).setContentTitle("센서 알림~~!~!!!!").setContentText("학습하기에 온도가 너무 높습니다!!!!")
                .setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE).setContentIntent(pendingNotificationIntent)
                .setAutoCancel(true).setOngoing(true);
        notificationManager.notify(1, builder.build());
    }
}