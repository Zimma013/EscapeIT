package pl.hackyeah.positivedevs.escapeit;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.estimote.cloud_plugin.common.EstimoteCloudCredentials;
import com.estimote.coresdk.common.config.EstimoteSDK;
import com.estimote.coresdk.common.requirements.SystemRequirementsChecker;
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


public class Main2Activity extends AppCompatActivity {

    IndoorLocationView indoorLocationView;
    Location myRoom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        indoorLocationView = (IndoorLocationView) findViewById(R.id.indoor_view);

        SystemRequirementsChecker.checkWithDefaultDialogs(this);
        EstimoteSDK.initialize(getApplicationContext(), "escapeit-kgn", "d1c44810de672d7ca34417ae707b2e4e");


        EstimoteCloudCredentials credentials = new EstimoteCloudCredentials("escapeit-kgn", "d1c44810de672d7ca34417ae707b2e4e");
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


        /*ScanningIndoorLocationManager indoorLocationManager =
                new IndoorLocationManagerBuilder(this, myRoom,credentials)
                        .withDefaultScanner()
                        .build();


        indoorLocationManager.setOnPositionUpdateListener(new OnPositionUpdateListener() {
            @Override
            public void onPositionUpdate(LocationPosition position) {
                Log.d("Location in myRoom",position.toString());
            }

            @Override
            public void onPositionOutsideLocation() {
                Log.d("Location in myRoom","outside the room");
            }
        });

        indoorLocationManager.startPositioning();*/
    }
}
