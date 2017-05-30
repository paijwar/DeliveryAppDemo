package com.paijwar.deliveryapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.paijwar.deliveryapp.adapter.OrderHistoryAdapter;
import com.paijwar.deliveryapp.model.DeliveryOrder;
import com.paijwar.deliveryapp.network.NetworkManager;

import java.util.ArrayList;
import java.util.Arrays;

import static com.paijwar.deliveryapp.Constants.ORDER_LIST_UPDATED;

/**
 * A placeholder fragment containing a simple view.
 */
public class HomeFragment extends Fragment implements OrderHistoryAdapter.OnItemClickListener {

    RecyclerView mListView;
    OrderHistoryAdapter mListAdapter;
    OrderItemClickListener mClickListener;

    public HomeFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mListView = (RecyclerView) view.findViewById(R.id.orderList);
        mListView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        mListAdapter = new OrderHistoryAdapter(this);
        mListView.setAdapter(mListAdapter);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OrderItemClickListener) {
            mClickListener = (OrderItemClickListener) context;
        } else {
            mClickListener = null;
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(getContext()).unregisterReceiver(mOrderUpdateReceiver);
    }

    @Override
    public void onResume() {
        super.onResume();
        LocalBroadcastManager.getInstance(getContext()).registerReceiver(mOrderUpdateReceiver, new IntentFilter(ORDER_LIST_UPDATED));
        NetworkManager.getInstance().getPastOrders();
    }

    BroadcastReceiver mOrderUpdateReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (ORDER_LIST_UPDATED.equalsIgnoreCase(intent.getAction())) {
                String result = NetworkManager.getInstance().getResults(ORDER_LIST_UPDATED);
                if (result != null || result.length() > 0) {
                    Gson gson = new GsonBuilder().create();
                    OrderList orderList = gson.fromJson(result, OrderList.class);
                    ArrayList<DeliveryOrder> orders = new ArrayList<>();
                    orders.addAll(Arrays.asList(orderList.order_list));
                    mListAdapter.updateOrderHistory(orders);
                }
            }
        }
    };

    @Override
    public void onItemClicked(DeliveryOrder orderItem) {
        if (mClickListener != null) {
            mClickListener.onOrderItemClicked(orderItem);
        }
    }

    class OrderList {
        DeliveryOrder[] order_list;
    }


    public interface OrderItemClickListener {
        void onOrderItemClicked(DeliveryOrder order);
    }
}
