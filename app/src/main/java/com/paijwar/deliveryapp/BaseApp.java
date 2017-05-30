package com.paijwar.deliveryapp;

import android.os.Handler;
import android.support.multidex.MultiDexApplication;

import com.paijwar.deliveryapp.location.LocationEngine;
import com.paijwar.deliveryapp.network.NetworkManager;
import com.paijwar.deliveryapp.storage.LocalStorage;
import com.paijwar.deliveryapp.utils.HelperUtils;

/**
 * Created by pradeepkumarpaijwar on 29/05/17.
 */

public class BaseApp extends MultiDexApplication {
    Handler mHandler;
    @Override
    public void onCreate() {
        super.onCreate();
        LocalStorage.init(this);
        HelperUtils.init(this);
        NetworkManager.init(this);
        LocationEngine.init(this);
    }
}
