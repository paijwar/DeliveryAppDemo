package com.paijwar.deliveryapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.paijwar.deliveryapp.fragment.ConfirmAcceptFragment;
import com.paijwar.deliveryapp.fragment.OrderDetails;
import com.paijwar.deliveryapp.model.DeliveryOrder;
import com.paijwar.deliveryapp.utils.HelperUtils;

import java.util.Stack;

public class Home extends AppCompatActivity implements HomeFragment.OrderItemClickListener {

    private static final String TAG = Home.class.getSimpleName();
    Stack<Fragment> fragmentStack = new Stack<>();
    Fragment homeFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        homeFragment = getSupportFragmentManager().findFragmentById(R.id.fragment);
        fragmentStack.push(homeFragment);
    }

    @Override
    public void onOrderItemClicked(DeliveryOrder order) {

        if ( order.getStatus().contains("reached") ||order.getStatus().contains("deliver")){
            FragmentManager fragmentManager = getSupportFragmentManager();
            Fragment fragment = OrderDetails.newInstance(order);
            fragmentStack.push(fragment);
            fragmentManager.beginTransaction().replace(R.id.fragment, fragment).commitAllowingStateLoss();
        }
        else if (order.getStatus().contains("confirm") ||order.getStatus().contains("arrived")|| order.getStatus().contains("pick") ) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            Fragment fragment = ConfirmAcceptFragment.newInstance(order);
            fragmentStack.push(fragment);
            fragmentManager.beginTransaction().replace(R.id.fragment, fragment).commitAllowingStateLoss();
        } else {
            HelperUtils.getInstance().showMessage("order status " + order.getStatus());
        }

        Log.e(TAG,"Stack size "+fragmentStack.size());
    }

    @Override
    public void onBackPressed() {

//        if (!fragmentStack.isEmpty())
//            fragmentStack.pop();

        if (fragmentStack.size() == 1) {
            super.onBackPressed();
        } else {
            Fragment fragment = fragmentStack.pop();
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().remove(fragment).replace(R.id.fragment, homeFragment).commitAllowingStateLoss();
        }
        Log.e(TAG, "onBackPressed");
    }
}
