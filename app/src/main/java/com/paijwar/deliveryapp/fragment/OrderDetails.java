package com.paijwar.deliveryapp.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.paijwar.deliveryapp.R;
import com.paijwar.deliveryapp.model.DeliveryOrder;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link OrderDetails#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OrderDetails extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private DeliveryOrder mOrder;

    public OrderDetails() {
        // Required empty public constructor
    }

    public static OrderDetails newInstance(DeliveryOrder order) {
        OrderDetails fragment = new OrderDetails();
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
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_order_details, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView orderId = (TextView) view.findViewById(R.id.orderId);
        TextView restaurantName = (TextView) view.findViewById(R.id.restaurantName);
        TextView restaurantAddress = (TextView) view.findViewById(R.id.restaurantAddress);
        TextView customerName = (TextView) view.findViewById(R.id.customerName);
        TextView customerAddress = (TextView) view.findViewById(R.id.customerAddress);
        TextView customerMobile = (TextView) view.findViewById(R.id.customerMobile);
        TextView billAmount = (TextView) view.findViewById(R.id.billAmount);
        Button bottomButton = (Button) view.findViewById(R.id.bottomButton);

        orderId.setText(mOrder.getOrder_id());
        restaurantName.setText(mOrder.getRestaurantName());
        restaurantAddress.setText(mOrder.getRestaurantAddress());
        customerName.setText(mOrder.getCustomerName());
        customerAddress.setText(mOrder.getDeliveryAddress());
        customerMobile.setText(mOrder.getCustomerMobileNumber());
        billAmount.setText(mOrder.getBillAmount());

        if (mOrder.getStatus().contains("confirm")) {
            bottomButton.setText("Arrived".toUpperCase());
        }
        else if (mOrder.getStatus().contains("reach")){
            bottomButton.setText("Delivered".toUpperCase());
        }
        else if (mOrder.getStatus().contains("deliver")){
            bottomButton.setText("Delivered".toUpperCase());
            bottomButton.setEnabled(false);
        }
    }
}
