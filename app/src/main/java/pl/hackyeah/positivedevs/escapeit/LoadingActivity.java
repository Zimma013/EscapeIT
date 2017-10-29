package pl.hackyeah.positivedevs.escapeit;

import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothSocket;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.util.UUID;

import pl.hackyeah.positivedevs.escapeit.Bluetooth.ConnectThread;
import pl.hackyeah.positivedevs.escapeit.Bluetooth.ServerConnectThread;
import pl.hackyeah.positivedevs.escapeit.Quests.CloseQuestionQuest;

import static pl.hackyeah.positivedevs.escapeit.Main2Activity.REQUEST_BLUETOOTH;

public class LoadingActivity extends AppCompatActivity {

    BluetoothSocket bSocket;

    public void connectClientOnClick(View v) {
        BluetoothAdapter BTAdapter = BluetoothAdapter.getDefaultAdapter();

        if (!BTAdapter.isEnabled()) {
            Intent enableBT = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBT, REQUEST_BLUETOOTH);
        }

        ConnectThread client = new ConnectThread();
        BluetoothSocket bSocket = client.connect(BTAdapter.getRemoteDevice("78:02:F8:E5:D7:88"), new UUID(123, 456));

        showStartDialog();

    }

    public void connectHostOnClick(View v) {
        BluetoothAdapter BTAdapter = BluetoothAdapter.getDefaultAdapter();

        if (!BTAdapter.isEnabled()) {
            Intent enableBT = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBT, REQUEST_BLUETOOTH);
        }

        ServerConnectThread server = new ServerConnectThread();
        BluetoothSocket bSocket = server.acceptConnect(BTAdapter, new UUID(123, 456));

        showStartDialog();

    }

    public void showStartDialog() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Jesteś w pokoju, w którym wszystko jest kontrolowane przez zbuntowane AI, Skynet, które pragnie zniszczenia ludzkości.\n" +
                "A mówili, że SmartHomes i Internet-of-Things ułatwią życie.\n" +
                "\n" +
                "Jednak możesz uratować zarówno siebie jak i osobę z [drugiego] pokoju.\n" +
                "Musisz jedynie rozwiązać kilka zagadek, które zostały pozostawione wewnątrz twojego więzienia.\n" +
                "\n" +
                "Aby otrzymać pierwszą z nich podejdź do [Beacona A].\n" +
                "Kolejną otrzymasz po poprawnym rozwiązaniu obecnej.");
        alertDialogBuilder.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        Intent intent = new Intent(getBaseContext(), MainActivity.class);
                        startActivity(intent);
                    }
                });

        alertDialogBuilder.show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);
    }


    @Override
    protected void onStart() {
        super.onStart();

    }
}
