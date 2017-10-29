package pl.hackyeah.positivedevs.escapeit;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;

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

import java.util.ArrayList;
import java.util.UUID;

import pl.hackyeah.positivedevs.escapeit.Bluetooth.BluetoothMessanger;
import pl.hackyeah.positivedevs.escapeit.Bluetooth.ConnectThread;
import pl.hackyeah.positivedevs.escapeit.Bluetooth.DeviceItem;
import pl.hackyeah.positivedevs.escapeit.Bluetooth.ManageConnectThread;
import pl.hackyeah.positivedevs.escapeit.Bluetooth.ServerConnectThread;


public class Main2Activity extends AppCompatActivity {

    public static int REQUEST_BLUETOOTH = 1;
    ArrayList<DeviceItem> mAdapter = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BluetoothAdapter BTAdapter = BluetoothAdapter.getDefaultAdapter();

        if (!BTAdapter.isEnabled()) {
            Intent enableBT = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBT, REQUEST_BLUETOOTH);
        }
        /*
        BroadcastReceiver bReciever = new BroadcastReceiver() {
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                    BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                    // Create a new device item
                    DeviceItem newDevice = new DeviceItem(device.getName(), device.getAddress(), "false");
                    // Add it to our adapter
                    mAdapter.add(newDevice);
                }
            }
        };
        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        getApplicationContext().registerReceiver(bReciever,filter);*/
        // server
        //ServerConnectThread server = new ServerConnectThread();
        //BluetoothSocket bSocket = server.acceptConnect(BTAdapter,new UUID(123,456));

        //client
        ConnectThread client = new ConnectThread();
        BluetoothSocket bSocket = client.connect(BTAdapter.getRemoteDevice("78:02:F8:E5:D7:88"),new UUID(123,456));
    }
}
