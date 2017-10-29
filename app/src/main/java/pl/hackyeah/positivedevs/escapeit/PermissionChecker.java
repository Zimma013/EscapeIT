package pl.hackyeah.positivedevs.escapeit;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by Michał Kupiec on 2017-10-29.
 */

public class PermissionChecker {
    Integer REQUEST_PERMISSIONS_CODE = 0;

    protected void checkPermissions(Activity activity) {
        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},REQUEST_PERMISSIONS_CODE);
        }
    }
}
