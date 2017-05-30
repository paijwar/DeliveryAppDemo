package com.paijwar.deliveryapp.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.paijwar.deliveryapp.R;
import com.paijwar.deliveryapp.model.DeliveryOrder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pradeepkumarpaijwar on 30/05/17.
 */

public class OrderHistoryAdapter extends RecyclerView.Adapter<OrderHistoryAdapter.OrderHistoryItem> {

    List<DeliveryOrder> mOrderList;

    OnItemClickListener mListener;

    public OrderHistoryAdapter(OnItemClickListener listener) {
        mOrderList = new ArrayList<>();
        this.mListener = listener;
    }

    public void updateOrderHistory(ArrayList<DeliveryOrder> orderList) {
        mOrderList.clear();
        mOrderList.addAll(orderList);
        notifyDataSetChanged();
    }

    @Override
    public OrderHistoryItem onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_list_item, parent, false);
        return new OrderHistoryItem(view);
    }

    @Override
    public void onBindViewHolder(final OrderHistoryItem holder, int position) {
        holder.orderId.setText(mOrderList.get(position).getOrder_id());
        holder.customerName.setText(mOrderList.get(position).getCustomerName());
        holder.restaurantName.setText(mOrderList.get(position).getRestaurantName());
        holder.status.setText(mOrderList.get(position).getStatus().toUpperCase());
        holder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mListener != null) {
                    mListener.onItemClicked(mOrderList.get(holder.getAdapterPosition()));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mOrderList.size();
    }


    class OrderHistoryItem extends RecyclerView.ViewHolder {
        TextView orderId, customerName, restaurantName, status;
        View container;

        public OrderHistoryItem(View itemView) {
            super(itemView);
            container = itemView;
            orderId = (TextView) itemView.findViewById(R.id.orderId);
            customerName = (TextView) itemView.findViewById(R.id.customerName);
            restaurantName = (TextView) itemView.findViewById(R.id.restaurantName);
            status = (TextView) itemView.findViewById(R.id.status_text);
        }
    }

    public interface OnItemClickListener {
        void onItemClicked(DeliveryOrder orderItem);
    }
}
