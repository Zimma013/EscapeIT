package pl.hackyeah.positivedevs.escapeit;

import android.app.Notification;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import com.estimote.cloud_plugin.common.EstimoteCloudCredentials;
import com.estimote.coresdk.observation.utils.Proximity;
import com.estimote.internal_plugins_api.cloud.proximity.ProximityAttachment;
import com.estimote.cloud_plugin.common.EstimoteCloudCredentials;
import com.estimote.proximity_sdk.monitoring.EstimoteMonitor;
//import com.estimote.proximity_sdk.monitoring.MonitoringUseCase;
//import com.estimote.proximity_sdk.monitoring.MonitoringUseCaseFactory;
import com.estimote.proximity_sdk.monitoring.MonitoringZone;
import com.estimote.proximity_sdk.proximity.ProximityObserver;
import com.estimote.proximity_sdk.proximity.ProximityObserverFactory;
//import com.estimote.proximity_sdk.proximity.ProximityZone;


import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Created by Michał Kupiec on 2017-10-29.
 */

public class TmpMainActivity extends AppCompatActivity {
    // In order to run this example you need to create an App in your Estimote Cloud account and put here your AppId and AppToken
    //private EstimoteCloudCredentials cloudCredentials = new EstimoteCloudCredentials("test-8ly", "5eb18314992a67e64e248a2a8c2e49d8");
    // Actions to trigger when proximity conditions are met.
    /*
        private val makeMintDeskColorFilled: (ProximityAttachment) -> Unit = { _ -> mint_image.setImageResource(R.color.mint_cocktail) }
        private val makeMintDeskColorWhite: () -> Unit = { mint_image.setImageResource(R.color.primary) }
        private val makeBlueberryDeskFilled: (ProximityAttachment) -> Unit = { _ -> blueberry_image.setImageResource(R.color.blueberry_muffin) }
        private val makeBlueberryDeskWhite: () -> Unit = { blueberry_image.setImageResource(R.color.primary) }
        private val makeVenueFilled: (ProximityAttachment) -> Unit = { _ -> venue_image.setImageResource(R.color.icy_marshmallow) }
        private val makeVenueWhite: () -> Unit = { venue_image.setImageResource(R.color.primary) }
        private val displayInfoAboutChangeInVenue: (List<ProximityAttachment>) -> Unit = { attachmentsNearby -> Toast.makeText(this, "Venue - current nearby attachments: ${attachmentsNearby.size}", Toast.LENGTH_SHORT).show() }
    */
    // Notification about pending BLE scanning to display in notification bar
    private Notification notification;
    // Estimote's main object for doing proximity observations
    private ProximityObserver proximityObserver;
    private ProximityObserver.Handler observationHandler;
    private EstimoteMonitor estimoteMonitor;
    private EstimoteMonitor.Handler monitorHandler;
    /*
    private MonitoringUseCaseFactory monitoringUseCaseFactory;
    private MonitoringUseCase monitoringUseCase;
    */


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Take a look at NotificationCreator class which handles different OS versions
        notification = new NotificationCreator().create(this);
        // Check Location permissions
        new PermissionChecker().checkPermissions(this);

        // NullPointerException detected
        //monitorHandler = estimoteMonitor.startWithSimpleScanner();
        //EstimoteMonitor.EstimoteMonitoringZoneModeBuilder zone = estimoteMonitor.zoneBuilder();

        EstimoteCloudCredentials cloudCredentials = new EstimoteCloudCredentials("test-8ly", "5eb18314992a67e64e248a2a8c2e49d8");
        ProximityObserver proximityObserver = new ProximityObserverFactory().create(getApplicationContext(), cloudCredentials);


    }

/*
    private void startProximityObservation() {

        // Create ProximityObserver - don't forget to put your APP ID and APP TOKEN.
        // Also make sure that all your beacons have attachments assigned in Estimote Cloud.
        proximityObserver = new ProximityObserverFactory().create(applicationContext, cloudCredentials);
        // The first rule is for the venue in general.
        // All devices in this venue will have the same key,
        // and the actions will be triggered when entering/changing/exiting the venue.
        val venueRule = proximityObserver.ruleBuilder()
                .forAttachmentKey("venue")
                .withOnEnterAction(makeVenueFilled)
                .withOnExitAction(makeVenueWhite)
                .withOnChangeAction(displayInfoAboutChangeInVenue)
                .withDesiredMeanTriggerDistance(2.0)
                .create()
        // The next rule is defined for single desk in your venue - let's call it "Mint desk".
        val mintDeskRule = proximityObserver.ruleBuilder()
                .forAttachmentKey("mint_desk")
                .withOnEnterAction(makeMintDeskColorFilled)
                .withOnExitAction(makeMintDeskColorWhite)
                .withDesiredMeanTriggerDistance(1.0)
                .create()
        // The last rule is defined for another single desk in your venue - the "Blueberry desk".
        val blueberryDeskRule = proximityObserver.ruleBuilder()
                .forAttachmentKey("blueberry_desk")
                .withOnEnterAction(makeBlueberryDeskFilled)
                .withOnExitAction(makeBlueberryDeskWhite)
                .withDesiredMeanTriggerDistance(1.0)
                .create()

        // Ok, now let's talk to our ProximityObserver about its future task...
        observationHandler = proximityObserver
                // Rules to observe
                .addProximityRules(venueRule, mintDeskRule, blueberryDeskRule)
                // Scan power mode - you can play with three different - low latency, low power and balanced.
                // The default mode is balanced. If you have used our old SDK before (1.0.13 or so),
                // you might notice that we no longer allow to setup exact scan time periods.
                // This caused many misconceptions and from now on we will handle the proper scan setup for you.
                // This is cool, isn't it? Tell us what you think about it!
                .withBalancedPowerMode()
                // And now go ahead and launch the observation process!
                // Also, notice that we used here .startWithForegroundScanner method.
                // It takes your notification and will handle scanning in the foreground service,
                // so that the system won't kill your scanning as long as user is playing with the app.
                .startWithForegroundScanner(notification)
        // You can also use .startWithSimpleScanner() which won't use any service for scan.
        // This will cause your scan to stop when user exits your app (or shortly after).
        // But you can use this method to implement some custom logic - maybe using your own service?
    }
*/
    @Override
    protected void onDestroy() {
        super.onDestroy();
        // After starting your scan, the Proximity Observer will return you a handler to stop the scanning process.
        // We will use it here to stop the scan when activity is destroyed.
        // IMPORTANT:
        // If you don't stop the scan here, the foreground service will remain active EVEN if the user kills your APP.
        // You can use it to retain scanning when app is killed, but you will need to handle actions properly
        observationHandler.stop();
        monitorHandler.stop();
    }

}
