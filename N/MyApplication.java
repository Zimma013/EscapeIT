package com.estimote.notification;

import android.app.Application;

import com.estimote.coresdk.common.config.EstimoteSDK;
import com.estimote.coresdk.common.requirements.SystemRequirementsChecker;
import com.estimote.coresdk.observation.region.RegionUtils;
import com.estimote.coresdk.observation.utils.Proximity;
import com.estimote.coresdk.recognition.packets.EstimoteLocation;
import com.estimote.coresdk.service.BeaconManager;
import com.estimote.notification.estimote.BeaconID;
import com.estimote.notification.estimote.BeaconNotificationsManager;

import android.os.Bundle;
import android.provider.Settings.Secure;
import android.util.Log;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

public class MyApplication extends Application {

    private boolean beaconNotificationsEnabled = false;


    // Added by Mikh
    //<---->


    BeaconID A =  new BeaconID("B9407F30-F5F8-466E-AFF9-25556B57FE6D", 59448  , 8542);
    BeaconID B = new BeaconID("B9407F30-F5F8-466E-AFF9-25556B57FE6D", 33856  , 35765);
    BeaconID C = new BeaconID("B9407F30-F5F8-466E-AFF9-25556B57FE6D", 26902  , 37659);
    BeaconID D = new BeaconID("B9407F30-F5F8-466E-AFF9-25556B57FE6D", 27669  , 8618);



    //<---->

    @Override
    public void onCreate() {
        super.onCreate();

        EstimoteSDK.initialize(getApplicationContext(), "escapeit-kgn", "d1c44810de672d7ca34417ae707b2e4e");

        // Added by Mikh
        // <---->
        // SystemRequirementsChecker requirementsChecker = new SystemRequirementsChecker();
        // requirementsChecker.checkWithDefaultDialogs();
        // RelativeLayout layoutView = (RelativeLayout) findViewById(R.id.relative_layout);


        /*
        beaconNotificationsManager.addNotification(
                B, "Wszedles w obszar BEACONA NUMER 2",
                "Goodbye, world.");
        beaconNotificationsManager.addNotification(
                C, "Wszedles w obszar BEACONA NUMER 3",
                "Goodbye, world.");
        beaconNotificationsManager.addNotification(
                D, "Wszedles w obszar BEACONA NUMER 4",
                "Goodbye, world.");


        final BeaconManager beaconManager = new BeaconManager(getApplicationContext());

        beaconManager.setLocationListener(new BeaconManager.LocationListener() {
            @Override
            public void onLocationsFound(List<EstimoteLocation> beacons) {

                // Wyprowadzić BeaconID na zewnątrz (??)
                String beaconId = "[de4540da1bb6a2a33064dbae9fc80d2d]";
                //BeaconID LocationA = new BeaconID("B9407F30-F5F8-466E-AFF9-25556B57FE6D", 59448  , 8542);
                //Boolean chk = A.getProximityUUID().toString().equals(beacons.indexOf(1));
                //chk.toString().
                //BeaconNotificationsManager beaconNotificationsManager = new BeaconNotificationsManager(this);
                //beaconNotificationsManager.startMonitoring();

                beaconNotificationsManager.addNotification(
                        A,"Wszedles w obszar BEACONA NUMER 1",
                        "Goodbye, world.");
                // ------ //
                for (EstimoteLocation beacon : beacons) {

                    if(A.getProximityUUID().toString().equals(beacon.id.toString()) && RegionUtils.computeProximity(beacon) == Proximity.IMMEDIATE){
                        // zadanie 1
                        // teoretycznie zadanie jest jako nowa Activity więc można:

                        // po rozwiązaniu zadania odblokować (dodać notif) dla kolejnej lokalizacji
                         beaconNotificationsManager.addNotification(
                                 B, "Wszedles w obszar BEACONA NUMER 2",
                                 "Goodbye, world.");
                    }

                    Log.d("LocationListener", beacon.id.toString() + "  " + RegionUtils.computeProximity(beacon).toString());


                    if (beacon.id.toString().equals(beaconId)
                            && RegionUtils.computeProximity(beacon) == Proximity.NEAR) {

                        Log.d("Send noti", beacon.id.toString() + "  " + RegionUtils.computeProximity(beacon).toString());

                    }
                }
            }
        });
        // <---->*/


    }


    public void enableBeaconNotifications() {
        if (beaconNotificationsEnabled) { return; }

        BeaconNotificationsManager beaconNotificationsManager = new BeaconNotificationsManager(this);

        BeaconID A =  new BeaconID("B9407F30-F5F8-466E-AFF9-25556B57FE6D", 59448  , 8542);
        BeaconID B = new BeaconID("B9407F30-F5F8-466E-AFF9-25556B57FE6D", 33856  , 35765);
        BeaconID C = new BeaconID("B9407F30-F5F8-466E-AFF9-25556B57FE6D", 26902  , 37659);
        BeaconID D = new BeaconID("B9407F30-F5F8-466E-AFF9-25556B57FE6D", 27669  , 8618);


        beaconNotificationsManager.addNotification(
        A,"Wszedles w obszar BEACONA NUMER 1",
                "Goodbye, world.");
        beaconNotificationsManager.addNotification(
        B, "Wszedles w obszar BEACONA NUMER 2",
                "Goodbye, world.");
        beaconNotificationsManager.addNotification(
        C, "Wszedles w obszar BEACONA NUMER 3",
                "Goodbye, world.");
        beaconNotificationsManager.addNotification(
        D, "Wszedles w obszar BEACONA NUMER 4",
                "Goodbye, world.");
        beaconNotificationsManager.startMonitoring();

        beaconNotificationsEnabled = true;
    }

    public boolean isBeaconNotificationsEnabled() {
        return beaconNotificationsEnabled;
    }
}
