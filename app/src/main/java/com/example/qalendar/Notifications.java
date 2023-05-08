package com.example.qalendar;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;

import androidx.core.app.NotificationCompat;

public class Notifications {

    // Notification channel values and names
    private static final String CHANNEL_ID = "time_flies_notifs";
    private static final String CHANNEL_NAME = "Time Flies";
    private static final String CHANNEL_DESCRIPTION = "Notifications for your Time Flies Calendar";
    private static final int NOTIFICATION_ID = 1;

    public static void sendNotification(Context context, String title, String message) {

        // Initializing a Notification Manager instance
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        // Creating a Notification Channel (required for Android Oreo and higher)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH);
            channel.setDescription(CHANNEL_DESCRIPTION);
            channel.enableLights(true);
            channel.setLightColor(Color.GREEN);
            channel.enableVibration(true);
            notificationManager.createNotificationChannel(channel);
        }

        // Building Notification
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_notifsmall)
                // For whatever our icon ends up being
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true);

        // Displaying Notification
        notificationManager.notify(NOTIFICATION_ID, builder.build());
    }
}