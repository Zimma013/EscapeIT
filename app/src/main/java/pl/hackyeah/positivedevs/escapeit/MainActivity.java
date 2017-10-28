package pl.hackyeah.positivedevs.escapeit;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.estimote.coresdk.common.config.EstimoteSDK;
import com.estimote.coresdk.common.requirements.SystemRequirementsChecker;
import com.estimote.coresdk.observation.region.RegionUtils;
import com.estimote.coresdk.observation.utils.Proximity;
import com.estimote.coresdk.recognition.packets.EstimoteLocation;
import com.estimote.coresdk.service.BeaconManager;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    BeaconManager beaconManager;

    private boolean notificationAlreadyShown = false;

    public void showNotification(String title, String message) {
        if (notificationAlreadyShown) { return; }

        Intent notifyIntent = new Intent(this, MainActivity.class);
        notifyIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivities(this, 0,
                new Intent[] { notifyIntent }, PendingIntent.FLAG_UPDATE_CURRENT);
        Notification notification = new Notification.Builder(this)
                .setSmallIcon(android.R.drawable.ic_dialog_info)
                .setContentTitle(title)
                .setContentText(message)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
                .build();
        notification.defaults |= Notification.DEFAULT_SOUND;
        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(1, notification);
        notificationAlreadyShown = true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        beaconManager = new BeaconManager(getApplicationContext());

        SystemRequirementsChecker.checkWithDefaultDialogs(this);
        EstimoteSDK.initialize(getApplicationContext(), "escapeit-kgn", "d1c44810de672d7ca34417ae707b2e4e");

        beaconManager.setLocationListener(new BeaconManager.LocationListener() {
            @Override
            public void onLocationsFound(List<EstimoteLocation> beacons) {
                //Log.d("LocationListener", "Nearby beacons: " + beacons.size());

                String beaconId = "[de4540da1bb6a2a33064dbae9fc80d2d]";


                for (EstimoteLocation beacon : beacons) {

                    Log.d("LocationListener", beacon.id.toString() + "  " + RegionUtils.computeProximity(beacon).toString());


                    if (beacon.id.toString().equals(beaconId)
                            && RegionUtils.computeProximity(beacon) == Proximity.NEAR) {

                        Log.d("Send noti", beacon.id.toString() + "  " + RegionUtils.computeProximity(beacon).toString());

                        showNotification("Hello world", "Looks like you're near a beacon.");
                    }
                }
            }
        });

        beaconManager.connect(new BeaconManager.ServiceReadyCallback() {
            @Override public void onServiceReady() {

                beaconManager.startLocationDiscovery();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        SystemRequirementsChecker.checkWithDefaultDialogs(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        beaconManager.disconnect();
    }
}
