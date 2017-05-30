package com.paijwar.deliveryapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.paijwar.deliveryapp.storage.LocalStorage;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            Dexter.withActivity(this)
//                    .withPermissions(
//                            Manifest.permission.ACCESS_FINE_LOCATION,
//                            Manifest.permission.ACCESS_COARSE_LOCATION,
//                            Manifest.permission.WRITE_EXTERNAL_STORAGE,
//                            Manifest.permission.READ_SMS,
//                            Manifest.permission.SEND_SMS
//
//
//                    ).withListener(new MultiplePermissionsListener() {
//                @Override
//                public void onPermissionsChecked(MultiplePermissionsReport report) {
//                    if (report.areAllPermissionsGranted()) {
//                        processToNextScreen();
//                    } else {
//                        Toast.makeText(MainActivity.this, "Please grant the permission to continue", Toast.LENGTH_LONG).show();
//                        finish();
//                        startActivity(new Intent(MainActivity.this, MainActivity.class));
//                    }
//                }
//
//                @Override
//                public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
//                    token.continuePermissionRequest();
//                }
//            }).check();
//        } else {
//            processToNextScreen();
//        }

        processToNextScreen();

    }

    private void processToNextScreen() {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                finish();
                String token = LocalStorage.getInstance().get("token");
                if (token == null) {
                    startActivity(new Intent(MainActivity.this, SignupOrSignIn.class));
                } else {
                    startActivity(new Intent(MainActivity.this, Home.class));
                }
            }
        }, 1000);
    }
}
