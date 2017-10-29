package pl.hackyeah.positivedevs.escapeit.Bluetooth;

import android.bluetooth.BluetoothSocket;

import java.io.IOException;

/**
 * Created by Krzysiek on 2017-10-29.
 */

public class BluetoothMessanger {
    public static void sendMessage(BluetoothSocket bSocket, int data) throws IOException {
        ManageConnectThread manage = new ManageConnectThread();
        manage.sendData(bSocket,data);
    }

    public static int receiveMessage(BluetoothSocket bSocket) throws IOException {
        ManageConnectThread manage = new ManageConnectThread();
        return manage.receiveData(bSocket);
    }
}
