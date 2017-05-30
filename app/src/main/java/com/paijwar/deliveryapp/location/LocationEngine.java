package com.paijwar.deliveryapp.location;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.paijwar.deliveryapp.Constants;
import com.paijwar.deliveryapp.network.NetworkManager;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by pradeepkumarpaijwar on 28/05/17.
 */

public class LocationEngine implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {

    GoogleApiClient mGoogleApiClient;
    private final static String TAG = LocationEngine.class.getSimpleName();
    private static LocationEngine ourInstance;
    private Context mContext;
    Location mLastLocation;

    public static void init(Context context) {
        ourInstance = new LocationEngine(context);
    }

    public static LocationEngine getInstance() {
        return ourInstance;
    }

    private LocationEngine(Context context) {
        this.mContext = context;
        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(mContext)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
            startLocationTracking();
        }
    }

    private void startLocationTracking() {
        mGoogleApiClient.connect();
    }

    protected void stopLocationTracking() {
        stopLocationUpdates();
        mGoogleApiClient.disconnect();
    }

    protected void createLocationRequest() {
        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(10000); // 10 seconds
        mLocationRequest.setFastestInterval(5000); // 5 seconds
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        //check for permission
        try {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        }
        catch (SecurityException secEx){
            Log.e(TAG,"Security Exception");
            secEx.printStackTrace();
        }

    }
    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Log.d(TAG,"Locaiton api connected");
        try {
            mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                    mGoogleApiClient);
            if (mLastLocation != null) {
                Map<String, String> params = new HashMap<>();
                params.put("lat", "" + mLastLocation.getLatitude());
                params.put("lng", "" + mLastLocation.getLatitude());
                NetworkManager.getInstance().send("location server api path", params, UUID.randomUUID().toString());
            }
            createLocationRequest();
        }
        catch (SecurityException secEx){

        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        //handle it
        Log.d(TAG,"Locaiton api onConnectionSuspended");
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        //handle it
        Log.d(TAG,"Locaiton api onConnectionFailed");
    }

    @Override
    public void onLocationChanged(Location location) {
        Log.d(TAG,"Locaiton api onLocationChanged");
        mLastLocation = location;
        LocalBroadcastManager.getInstance(mContext).sendBroadcast(new Intent(Constants.LOCATION_UPDATE_AVAILABLE));
        if (mLastLocation != null) {
            Map<String, String> params = new HashMap<>();
            params.put("lat", "" + mLastLocation.getLatitude());
            params.put("lng", "" + mLastLocation.getLatitude());
            NetworkManager.getInstance().send("location server api path", params, UUID.randomUUID().toString());
        }
    }
    protected void stopLocationUpdates() {
        LocationServices.FusedLocationApi.removeLocationUpdates(
                mGoogleApiClient, this);
    }

    public Location getLastLocation(){
        return mLastLocation;
    }
}
