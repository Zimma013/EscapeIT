package pl.hackyeah.positivedevs.escapeit;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
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

    private boolean activityShown1 = false;
    private boolean activityShown2 = false;
    private boolean activityShown3 = false;
    private boolean activityShown4 = false;

    IndoorLocationView indoorLocationView;
    ScanningIndoorLocationManager indoorLocationManager;

    IndoorCloudManager cloudManager;

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

                                if (!activityShown1) {
                                    Intent intent = new Intent(getBaseContext(), OpenQuestionQuest.class);
                                    intent.putExtra("fileName", "zagadka1.json");
                                    startActivityForResult(intent, 1);
                                    activityShown1 = true;
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

        ProximityRule rule2 =
                proximityObserver.ruleBuilder()
                        .forAttachmentKeyAndValue("venue2", "office2")
                        .withOnEnterAction(new Function1<ProximityAttachment, Unit>() {
                            @Override
                            public Unit invoke(ProximityAttachment proximityAttachment) {

                                Log.i("proximity", "Enter");

                                if (!activityShown2) {
                                    Intent intent = new Intent(getBaseContext(), OpenQuestionQuest.class);
                                    intent.putExtra("fileName", "zagadka2.json");
                                    startActivityForResult(intent, 2);
                                    activityShown2 = true;
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

        ProximityRule rule3 =
                proximityObserver.ruleBuilder()
                        .forAttachmentKeyAndValue("venue3", "office3")
                        .withOnEnterAction(new Function1<ProximityAttachment, Unit>() {
                            @Override
                            public Unit invoke(ProximityAttachment proximityAttachment) {

                                Log.i("proximity", "Enter");

                                if (!activityShown3) {
                                    Intent intent = new Intent(getBaseContext(), CloseQuestionQuest.class);
                                    startActivityForResult(intent, 3);
                                    intent.putExtra("fileName", "zagadka3.json");
                                    activityShown3 = true;
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

        EstimoteSDK.initialize(getApplicationContext(), "test-8ly", "5eb18314992a67e64e248a2a8c2e49d8");
        EstimoteSDK.enableDebugLogging(true);

        final EstimoteCloudCredentials cloudCredentials = new EstimoteCloudCredentials("test-8ly", "5eb18314992a67e64e248a2a8c2e49d8");

        cloudManager = new IndoorCloudManagerFactory().create(this, cloudCredentials);
        cloudManager.getLocation("test-j4a", new CloudCallback<Location>() {
            @Override
            public void success(Location location) {

                indoorLocationView = (IndoorLocationView) findViewById(R.id.indoor_view);
                indoorLocationView.setLocation(location);

                indoorLocationManager =
                        new IndoorLocationManagerBuilder(getApplicationContext(), location, cloudCredentials)
                                .withDefaultScanner()
                                .build();

                indoorLocationManager.startPositioning();

                indoorLocationManager.setOnPositionUpdateListener(new OnPositionUpdateListener() {
                    @Override
                    public void onPositionUpdate(LocationPosition locationPosition) {
                        indoorLocationView.updatePosition(locationPosition);
                    }

                    @Override
                    public void onPositionOutsideLocation() {
                        indoorLocationView.hidePosition();
                    }
                });

            }

            @Override
            public void failure(EstimoteCloudException e) {
                // oops!
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        activityShown1 = false;
        activityShown2 = false;
        activityShown3 = false;
        activityShown4 = false;

        initMap();
        initBeaconListner();
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        observationHandler.stop();
        super.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                Log.i("APP", "CLOSED");
            }
        }

        if (requestCode == 2) {
            if (resultCode == Activity.RESULT_OK) {
                Log.i("APP", "CLOSED");
            }
        }

        if (requestCode == 3) {
            if (resultCode == Activity.RESULT_OK) {
                Log.i("APP", "CLOSED");
            }
        }
    }
}
