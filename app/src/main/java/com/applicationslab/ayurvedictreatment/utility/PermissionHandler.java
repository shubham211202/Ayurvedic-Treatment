package com.applicationslab.ayurvedictreatment.utility;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.Settings;
import android.view.View;

import androidx.core.content.ContextCompat;

import com.google.android.material.snackbar.Snackbar;
import com.applicationslab.ayurvedictreatment.R;

public class PermissionHandler {

    private static final int REQUEST_SETTINGS = 444;

    private Activity activity;

    public PermissionHandler(Context context) {
        this.activity = (Activity) context;
    }

    public boolean hasAccessFineLocationPermission() {
        return ContextCompat.checkSelfPermission(activity,
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED;
    }

    public boolean hasWriteExternalStoragePermission() {
        return ContextCompat.checkSelfPermission(activity,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED;
    }

    public void showPermissionSettingsSnackbar(String message) {

        View view = activity.findViewById(android.R.id.content);

        Snackbar.make(view, message, Snackbar.LENGTH_LONG)
                .setAction("Settings", v -> {
                    Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    intent.setData(Uri.parse("package:" + activity.getPackageName()));
                    activity.startActivityForResult(intent, REQUEST_SETTINGS);
                })
                .setActionTextColor(ContextCompat.getColor(activity, R.color.colorPrimary))
                .show();
    }
}