package pl.hackyeah.positivedevs.escapeit;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;

/**
 * Created by Michał Kupiec on 2017-10-29.
 */

public class NotificationCreator {
    private String CHANNEL_ID = "ESTIMOTE_SCAN";
    private String CHANNEL_NAME = "Estimote bluetooth scan notifications";
    private String CHANNEL_DESCRIPTION = "Blah blah blah";
    private String NOTIFICATION_TITLE = "Estimote Inc. \u00AE";
    private String NOTIFICATION_TEXT = "Scan is running...";

    Notification create(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
            return createNotificationForOreo(context);
        else
            return createNotificationForPreOreo(context);
    }

    private Notification createNotificationForPreOreo(Context context) {
        return new NotificationCompat.Builder(context)
                //NotificationCompat.Builder(context)
                //.setSmallIcon(R.drawable.beacon_gray)
                .setContentTitle(NOTIFICATION_TITLE)
                .setContentText(NOTIFICATION_TEXT)
                .setChannelId(CHANNEL_ID)
                .build();
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private Notification createNotificationForOreo(Context context) {
        createNotificationChannel(context);
        return new Notification.Builder(context,CHANNEL_ID)
                //Notification.Builder(context, CHANNEL_ID)
                //.setSmallIcon(R.drawable.beacon_gray)
                .setContentTitle(NOTIFICATION_TITLE)
                .setContentText(NOTIFICATION_TEXT)
                .build();
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private void createNotificationChannel(Context context) {
        String id = CHANNEL_ID;
        String name = CHANNEL_NAME;
        String description = CHANNEL_DESCRIPTION;
        int importance = android.app.NotificationManager.IMPORTANCE_HIGH;
        NotificationChannel mChannel = new  NotificationChannel(id, name, importance);
        // mChannel.description = description;
        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.createNotificationChannel(mChannel);
    }

}
