package pl.hackyeah.positivedevs.escapeit;

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

import pl.hackyeah.positivedevs.escapeit.Quests.CloseQuestionQuest;
import pl.hackyeah.positivedevs.escapeit.Quests.OpenQuestionQuest;

public class MainActivity extends AppCompatActivity {

    BeaconManager beaconManager;

    private boolean beaconNotificationsEnabled = false;
    private boolean activityShown = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        activityShown = false;

        beaconManager = new BeaconManager(getApplicationContext());

        EstimoteSDK.initialize(getApplicationContext(), "escapeit-kgn", "d1c44810de672d7ca34417ae707b2e4e");
        beaconManager.setLocationListener(new BeaconManager.LocationListener() {
            @Override
            public void onLocationsFound(List<EstimoteLocation> beacons) {
                //Log.d("LocationListener", "Nearby beacons: " + beacons.size());

                String beaconId = "[dde6403fa0fabbb0854d27dc01d2cd16]";


                for (EstimoteLocation beacon : beacons) {

                    if (beacon.id.toString().equals(beaconId)) {
                        Log.d("LocationListener", beacon.id.toString() + "  " + RegionUtils.computeProximity(beacon).toString());
                        if (RegionUtils.computeProximity(beacon) == Proximity.NEAR) {

                            Log.d("LocationListener", "START");

                            if (!activityShown) {
                                activityShown = true;
                                Intent intent = new Intent(getBaseContext(), OpenQuestionQuest.class);
                                startActivity(intent);
                            }
                        }
                    }
                }
            }
        });

        beaconManager.connect(new BeaconManager.ServiceReadyCallback() {
            @Override public void onServiceReady() {

                beaconManager.startLocationDiscovery();
            }
        });

        Intent intent = new Intent(getBaseContext(), CloseQuestionQuest.class);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();

        /*String tmpTAG = "???";

        if (!SystemRequirementsChecker.checkWithDefaultDialogs(this)) {
            Log.e(tmpTAG, "Can't scan for beacons, some pre-conditions were not met");
            Log.e(tmpTAG, "Read more about what's required at: http://estimote.github.io/Android-SDK/JavaDocs/com/estimote/sdk/SystemRequirementsChecker.html");
            Log.e(tmpTAG, "If this is fixable, you should see a popup on the app's screen right now, asking to enable what's necessary");
        } else if (!isBeaconNotificationsEnabled()) {
            Log.d(tmpTAG, "Enabling beacon notifications");
            enableBeaconNotifications();
        }
        */

        activityShown = false;
    }

    public void enableBeaconNotifications() {
        if (beaconNotificationsEnabled) {
            return;
        }

        BeaconNotificationsManager beaconNotificationsManager = new BeaconNotificationsManager(this);

        BeaconID A = new BeaconID("B9407F30-F5F8-466E-AFF9-25556B57FE6D", 59448, 8542);
        BeaconID B = new BeaconID("B9407F30-F5F8-466E-AFF9-25556B57FE6D", 33856, 35765);
        BeaconID C = new BeaconID("B9407F30-F5F8-466E-AFF9-25556B57FE6D", 26902, 37659);
        BeaconID D = new BeaconID("B9407F30-F5F8-466E-AFF9-25556B57FE6D", 27669, 8618);


        beaconNotificationsManager.addNotification(
                A, "Wszedles w obszar BEACONA NUMER 1",
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
