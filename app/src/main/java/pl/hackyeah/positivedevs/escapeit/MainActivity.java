package pl.hackyeah.positivedevs.escapeit;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.estimote.cloud_plugin.common.EstimoteCloudCredentials;
import com.estimote.coresdk.common.config.EstimoteSDK;
import com.estimote.coresdk.common.requirements.SystemRequirementsChecker;
import com.estimote.coresdk.observation.region.RegionUtils;
import com.estimote.coresdk.observation.utils.Proximity;
import com.estimote.coresdk.recognition.packets.EstimoteLocation;
import com.estimote.coresdk.service.BeaconManager;
import com.estimote.indoorsdk.IndoorLocationManagerBuilder;
import com.estimote.indoorsdk_module.algorithm.OnPositionUpdateListener;
import com.estimote.indoorsdk_module.algorithm.ScanningIndoorLocationManager;
import com.estimote.indoorsdk_module.cloud.CloudCallback;
import com.estimote.indoorsdk_module.cloud.EstimoteCloudException;
import com.estimote.indoorsdk_module.cloud.IndoorCloudManager;
import com.estimote.indoorsdk_module.cloud.IndoorCloudManagerFactory;
import com.estimote.indoorsdk_module.cloud.Location;
import com.estimote.indoorsdk_module.cloud.LocationPosition;
import com.estimote.indoorsdk_module.view.IndoorLocationView;
import com.estimote.internal_plugins_api.cloud.CloudCredentials;
import com.estimote.internal_plugins_api.cloud.proximity.ProximityAttachment;
import com.estimote.proximity_sdk.proximity.ProximityObserver;
import com.estimote.proximity_sdk.proximity.ProximityObserverFactory;
import com.estimote.proximity_sdk.proximity.ProximityRule;

import java.util.List;

import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import pl.hackyeah.positivedevs.escapeit.Quests.CloseQuestionQuest;
import pl.hackyeah.positivedevs.escapeit.Quests.OpenQuestionQuest;


public class MainActivity extends AppCompatActivity {

    ProximityObserver.Handler observationHandler;
    BeaconManager beaconManager;

    private boolean beaconNotificationsEnabled = false;
    private boolean activityShown = false;
    Location myRoom;

    IndoorLocationView indoorLocationView;
    ScanningIndoorLocationManager indoorLocationManager;


    private void initBeaconListner() {
        EstimoteCloudCredentials cloudCredentials = new EstimoteCloudCredentials("test-8ly", "5eb18314992a67e64e248a2a8c2e49d8");
        ProximityObserver proximityObserver = new ProximityObserverFactory().create(getApplicationContext(), cloudCredentials);

        ProximityRule rule1 =
                proximityObserver.ruleBuilder()
                        .forAttachmentKeyAndValue("venue", "office")
                        .withOnEnterAction(new Function1<ProximityAttachment, Unit>() {
                            @Override
                            public Unit invoke(ProximityAttachment proximityAttachment) {

                                Log.i("proximity", "Enter");

                                if (!activityShown) {
                                    Intent intent = new Intent(getBaseContext(), CloseQuestionQuest.class);
                                    startActivity(intent);
                                    activityShown = true;
                                }
                                return null;
                            }
                        })
                        .withOnExitAction(new Function0<Unit>() {
                            @Override
                            public Unit invoke() {
                                Log.i("proximity", "Exit");
                                return null;
                            }
                        })
                        .withOnChangeAction(new Function1<List<? extends ProximityAttachment>, Unit>() {
                            @Override
                            public Unit invoke(List<? extends ProximityAttachment> proximityAttachments) {
                                Log.i("proximity", "onChange");
                                return null;
                            }

                            ;
                        })
                        .withDesiredMeanTriggerDistance(2.0)
                        .create();

        observationHandler =
                proximityObserver.addProximityRules(rule1)
                        .withBalancedPowerMode()
                        .withOnErrorAction(new Function1<Throwable, Unit>() {
                            @Override
                            public Unit invoke(Throwable throwable) {
               /* Do something here */
                                return null;
                            }
                        })
                        .startWithSimpleScanner();
    }


    private void initMap() {
        CloudCredentials cloudCredentials = new EstimoteCloudCredentials("test-8ly", "5eb18314992a67e64e248a2a8c2e49d8");
        IndoorCloudManager cloudManager = new IndoorCloudManagerFactory().create(this, cloudCredentials);
        cloudManager.getLocation("test-j4a", new CloudCallback<Location>() {
            @Override
            public void success(Location location) {
                // do something with your Location object here.
                // You will need it to initialise IndoorLocationManager!
                indoorLocationView = (IndoorLocationView) findViewById(R.id.indoor_view);
                indoorLocationView.setLocation(location);
            }

            @Override
            public void failure(EstimoteCloudException e) {
                // oops!
            }
        });
    }

    private void initMap2() {
        SystemRequirementsChecker.checkWithDefaultDialogs(this);
        EstimoteSDK.initialize(getApplicationContext(), "test-8ly", "5eb18314992a67e64e248a2a8c2e49d8");


        EstimoteCloudCredentials credentials = new EstimoteCloudCredentials("test-8ly", "5eb18314992a67e64e248a2a8c2e49d8");
        IndoorCloudManager cloudManager = new IndoorCloudManagerFactory().create(this, credentials);

        cloudManager.getLocation("test-j4a", new CloudCallback<Location>() {
            @Override
            public void success(Location location) {
                Log.i("success", "success");
                myRoom = location;
                indoorLocationView = (IndoorLocationView) findViewById(R.id.indoor_view);
                indoorLocationView.setLocation(location);
            }

            @Override
            public void failure(EstimoteCloudException e) {
                // oops!
                Log.i("fail", "fail");
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        activityShown = false;

        initMap();
        initBeaconListner();

    }

    @Override
    protected void onResume() {
        super.onResume();
        activityShown = false;
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
