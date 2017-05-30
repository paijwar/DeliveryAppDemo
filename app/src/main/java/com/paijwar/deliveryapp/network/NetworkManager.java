package com.paijwar.deliveryapp.network;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.support.v4.content.LocalBroadcastManager;

import com.paijwar.deliveryapp.BuildConfig;
import com.paijwar.deliveryapp.Constants;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.PriorityBlockingQueue;

/**
 * Created by pradeepkumarpaijwar on 28/05/17.
 */

public class NetworkManager {

    private static final String TAG = NetworkManager.class.getSimpleName();
    private static NetworkManager ourInstance = null;
    private Context mContext = null;
    private final static int MAX_PENDING_REQUESTS = 10;
    Queue<Request> mPendingQueue = new PriorityBlockingQueue<>(MAX_PENDING_REQUESTS);
    Map<String, String> dummyResponse = new HashMap<>();
    //Request types
    // 0 - GET
    // 1 - POST

    public static void init(Context context) {
        ourInstance = new NetworkManager(context);
    }

    public static NetworkManager getInstance() {
        return ourInstance;
    }

    private NetworkManager(Context context) {
        this.mContext = context;
        AssetManager am = context.getAssets();
        try {
            InputStream is = am.open("order_list.json");
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is));
            StringBuffer sb = new StringBuffer();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line);
            }
            bufferedReader.close();

            dummyResponse.put(Constants.ORDER_LIST_UPDATED, sb.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void getPastOrders() {
        if (BuildConfig.BASE_URL.contains("localhost")) {
            //local
            LocalBroadcastManager.getInstance(mContext).sendBroadcast(new Intent(Constants.ORDER_LIST_UPDATED));
        } else {
            //network
        }
    }

    public void doGetRequest(String serviceUrl, Map<String, String> parameters, String requestId) {
        if (BuildConfig.BASE_URL.contains("localhost")) {

        } else {
            Request request = new Request();
            request.url = serviceUrl;
            request.params = parameters;
            request.requestId = requestId;
            request.requestType = 0;// GET request
            //add to processing queue
            mPendingQueue.add(request);
        }
    }

    public void doPostRequest(String serviceUrl, Map<String, String> parameters, String requestId) {
        if (BuildConfig.BASE_URL.contains("localhost")) {

        } else {

            Request request = new Request();
            request.url = serviceUrl;
            request.params = parameters;
            request.requestId = requestId;
            request.requestType = 1;// POST request
            mPendingQueue.add(request);
        }
    }

    //WebSocket request
    public void send(String serviceUrl, Map<String, String> parameters, String requestId) {
        //send data using network manager service
        if (BuildConfig.BASE_URL.contains("localhost")) {

        } else {

        }
    }

    //Cancel any pending request
    public void cancel(String requestId) {
        //issue cancel request to network manager service
        if (BuildConfig.BASE_URL.contains("localhost")) {

        } else {

        }
    }

    class Request {
        String requestId;
        int requestType;
        Map<String, String> params;
        String url;
    }

    public String getRequestStatus(String requestId) {
        //get status code from database
        //status code to status string mapped
        if (BuildConfig.BASE_URL.contains("localhost")) {
            return "completed";
        } else {
        }
        return "Current status";
    }

    byte[] getResponse(String requestId) {
        //get response from database
        return null;
    }

    public String getResults(String requestId) {
        String resultToReturn = "";
        if (BuildConfig.BASE_URL.contains("localhost")) {
            resultToReturn = dummyResponse.get(requestId);
        } else {

        }
        return resultToReturn;
    }


    //NetworkManager service to process requests
}
