package com.paijwar.deliveryapp.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.paijwar.deliveryapp.Constants;
import com.paijwar.deliveryapp.R;
import com.paijwar.deliveryapp.location.DownloadTask;
import com.paijwar.deliveryapp.location.LocationEngine;
import com.paijwar.deliveryapp.location.MapDirectionUtils;
import com.paijwar.deliveryapp.model.DeliveryOrder;

import static com.paijwar.deliveryapp.R.id.map;


public class ConfirmAcceptFragment extends Fragment implements OnMapReadyCallback {

    private static final String ARG_PARAM1 = "param1";
    private DeliveryOrder mOrder;
    private OnFragmentInteractionListener mListener;
    private GoogleMap mMap;

    Button bottomButton;
    public ConfirmAcceptFragment() {
        // Required empty public constructor
    }

    public static ConfirmAcceptFragment newInstance(DeliveryOrder order) {
        ConfirmAcceptFragment fragment = new ConfirmAcceptFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1, order);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mOrder = (DeliveryOrder) getArguments().getSerializable(ARG_PARAM1);
            Log.e("TAG", "");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_confirm_accept, container, false);
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(map);
        mapFragment.getMapAsync(this);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bottomButton = (Button) view.findViewById(R.id.bottomButton);
        if (mOrder.getStatus().contains("confirm")){
            bottomButton.setText("Arrived".toUpperCase());
        }
        if (mOrder.getStatus().contains("arrive")){
            bottomButton.setText("Picked up".toUpperCase());
        }
        else if (mOrder.getStatus().contains("pick")){
            bottomButton.setText("reached".toUpperCase());
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        LocalBroadcastManager.getInstance(getContext()).registerReceiver(locationReceiver, new IntentFilter(Constants.LOCATION_UPDATE_AVAILABLE));
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
    }

    @Override
    public void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(getContext()).unregisterReceiver(locationReceiver);
    }


    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setIndoorEnabled(true);
        mMap.setBuildingsEnabled(true);
        Location currentLocation = LocationEngine.getInstance().getLastLocation();
        LatLng myLocation = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(myLocation, 13));
        driverMarker = new MarkerOptions()
                .title("You are here")
                .position(myLocation).icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_delivery_boy_pin));

        LatLng restaurantLocation = new LatLng(Double.valueOf(mOrder.getRestaurantLocationLat()), Double.valueOf(mOrder.getRestaurantLocationLng()));


        restaurantMarker = new MarkerOptions().title("Restaurant").position(restaurantLocation).
                icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_restaurant_pin));
        if (mOrder.getStatus().contains("confirm")) {
            mMap.addMarker(restaurantMarker).showInfoWindow();
            String url = MapDirectionUtils.getDirectionsUrl(myLocation, restaurantLocation);
            DownloadTask downloadTask = new DownloadTask(mMap);
            downloadTask.execute(url);
        }
        showMyMarker();
        try {
            mMap.setMyLocationEnabled(true);
        } catch (SecurityException secEx) {
            secEx.printStackTrace();
        }
        mMap.getUiSettings().setCompassEnabled(true);

        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        LatLng customerLocation = new LatLng(Double.valueOf(mOrder.getCustomerLocationLat()), Double.valueOf(mOrder.getCustomerLocationLng()));
        customerMarker = new MarkerOptions().title("Customer").position(customerLocation)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_home_pin));
        if (mOrder.getStatus().equalsIgnoreCase("arrived")) {
            mMap.addMarker(customerMarker).showInfoWindow();
            String url = MapDirectionUtils.getDirectionsUrl(myLocation, customerLocation);
            DownloadTask downloadTask = new DownloadTask(mMap);
            downloadTask.execute(url);
        }

//        builder.include(driverMarker.getPosition());
//        builder.include(restaurantMarker.getPosition());
//        builder.include(customerMarker.getPosition());
//        LatLngBounds bounds = builder.build();
//        int width = getResources().getDisplayMetrics().widthPixels;
//        int height = getResources().getDisplayMetrics().heightPixels;
//        int padding = (int) (width * 0.10);
//        CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, padding);
//        mMap.moveCamera(cu);

    }

    MarkerOptions driverMarker;
    MarkerOptions restaurantMarker;
    MarkerOptions customerMarker;

    void showMyMarker() {
        Location currentLocation = LocationEngine.getInstance().getLastLocation();
        LatLng myLocation = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
        driverMarker.position(myLocation);
        mMap.addMarker(driverMarker).showInfoWindow();

    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }


    final BroadcastReceiver locationReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (mMap != null) {
                Log.e("TAG", "Location updated");
                showMyMarker();
            }
        }
    };

}
