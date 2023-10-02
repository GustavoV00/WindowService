package com.example.helloworld.windowService;

import static android.Manifest.permission.INTERNET;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class Permissions {
    private static final int REQUEST_CODE_PERMISSIONS = 1;
    private static final String[] REQUIRED_PERMISSIONS = {
            INTERNET,
    };

    private Context context;
    private Activity activity;

    public Permissions(Context context, Activity activity) {
        this.context = context;
        this.activity = activity;
    }

    public boolean hasCorrectPermissions() {
        return ContextCompat.checkSelfPermission(this.context, INTERNET) == PackageManager.PERMISSION_GRANTED;
    }

    public void requestCorrectPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ActivityCompat.requestPermissions(this.activity, REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS);
        } else {
            // Show error message (If SDK version is under 23)
            Toast.makeText(this.context, "Permission to access the internet", Toast.LENGTH_SHORT).show();
        }
    }

}
