package com.example.edvardsen.quick.helpers;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.NotificationCompat;

import com.example.edvardsen.quick.main.CardMenuActivity;
import com.example.edvardsen.quick.R;

/**
 * Created by buller on 19/12/2017.
 */
public class Notifications {

    public static void generateNotification(Context context/*, String user*/, String message) {
        Intent notificationIntent = new Intent(context, CardMenuActivity.class);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent intent = PendingIntent.getActivity(context, 0, notificationIntent, 0);

        NotificationCompat.Builder mBuilder = (NotificationCompat.Builder) new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.entropyshitlogo)
                .setContentTitle(/*user + " sent a message!"*/"A new message arrived")
                .setContentIntent(intent)
                .setContentText(message)
                .setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE | Notification.DEFAULT_LIGHTS);
        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(0, mBuilder.build());
    }
}