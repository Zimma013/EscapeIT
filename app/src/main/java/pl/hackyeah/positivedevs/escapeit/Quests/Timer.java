package pl.hackyeah.positivedevs.escapeit.Quests;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * Created by motek on 29.10.17.
 */

public class Timer extends Service {
    int time = 1000000;


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
