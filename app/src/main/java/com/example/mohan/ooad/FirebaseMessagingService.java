package com.example.mohan.ooad;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

import com.google.firebase.messaging.RemoteMessage;

import java.util.Objects;

import static android.support.v4.app.NotificationCompat.DEFAULT_VIBRATE;
import static android.support.v4.app.NotificationCompat.PRIORITY_MAX;

public class FirebaseMessagingService extends com.google.firebase.messaging.FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        String message = remoteMessage.getData().get("message");

        SharedPreferences prefs = getSharedPreferences("lockedApps", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        if(Objects.equals(message, "request")){
            showRequestNotification(message);
        } else if(Objects.equals(message, "open")){
            editor.putBoolean("open", true);
            editor.apply();
        }else if(Objects.equals(message, "lock")){
            editor.putBoolean("open", false);
            editor.apply();
        }
    }

    private void showRequestNotification(String message) {

        Intent intent = new Intent(this, Main2Activity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setAutoCancel(true)
                .setContentTitle("Request to play game!!")
                .setContentText(message)
                .setColor(getResources().getColor(R.color.colorPrimary))
                .setSmallIcon(R.drawable.splash)
                .setLargeIcon(BitmapFactory.decodeResource(this.getResources(), R.drawable.splash))
                .setContentIntent(pendingIntent)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(message));

        Notification n = builder.build();
        n.flags |= Notification.FLAG_SHOW_LIGHTS;

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(0, n);
    }
}