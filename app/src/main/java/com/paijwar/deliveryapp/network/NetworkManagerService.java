package com.paijwar.deliveryapp.network;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class NetworkManagerService extends Service {
    public NetworkManagerService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
    }

    @Override
    public int onStartCommand(Intent intent,  int flags, int startId) {
        //process all the request from network manager in a thread
        return START_STICKY;
    }
}
