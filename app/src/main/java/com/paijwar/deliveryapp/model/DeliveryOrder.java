package com.paijwar.deliveryapp.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by pradeepkumarpaijwar on 28/05/17.
 */

public class DeliveryOrder implements Serializable {

    private String order_id;
    private String status;
    private String billAmount;
    private Integer payAmount;
    private Integer amountToCollect;
    private String restaurantName;
    private String restaurantAddress;
    private String deliveryAddress;
    private String customerMobileNumber;
    private String customerName;

    private String customerLocationLat;
    private String customerLocationLng;

    private String restaurantLocationLat;
    private String restaurantLocationLng;

    private ArrayList<OrderItem> itemList;
    private long timestamp;


    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getBillAmount() {
        return billAmount;
    }

    public void setBillAmount(String billAmount) {
        this.billAmount = billAmount;
    }

    public Integer getPayAmount() {
        return payAmount;
    }

    public void setPayAmount(Integer payAmount) {
        this.payAmount = payAmount;
    }

    public Integer getAmountToCollect() {
        return amountToCollect;
    }

    public void setAmountToCollect(Integer amountToCollect) {
        this.amountToCollect = amountToCollect;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public String getRestaurantAddress() {
        return restaurantAddress;
    }

    public void setRestaurantAddress(String restaurantAddress) {
        this.restaurantAddress = restaurantAddress;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public String getCustomerMobileNumber() {
        return customerMobileNumber;
    }

    public void setCustomerMobileNumber(String customerMobileNumber) {
        this.customerMobileNumber = customerMobileNumber;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerLocationLat() {
        return customerLocationLat;
    }

    public void setCustomerLocationLat(String customerLocationLat) {
        this.customerLocationLat = customerLocationLat;
    }

    public String getCustomerLocationLng() {
        return customerLocationLng;
    }

    public void setCustomerLocationLng(String customerLocationLng) {
        this.customerLocationLng = customerLocationLng;
    }

    public String getRestaurantLocationLat() {
        return restaurantLocationLat;
    }

    public void setRestaurantLocationLat(String restaurantLocationLat) {
        this.restaurantLocationLat = restaurantLocationLat;
    }

    public String getRestaurantLocationLng() {
        return restaurantLocationLng;
    }

    public void setRestaurantLocationLng(String restaurantLocationLng) {
        this.restaurantLocationLng = restaurantLocationLng;
    }

    public ArrayList<OrderItem> getItemList() {
        return itemList;
    }

    public void setItemList(ArrayList<OrderItem> itemList) {
        this.itemList = itemList;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
