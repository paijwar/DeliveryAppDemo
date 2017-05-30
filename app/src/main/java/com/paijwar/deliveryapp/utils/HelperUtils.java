package com.paijwar.deliveryapp.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by pradeepkumarpaijwar on 29/05/17.
 */

public class HelperUtils {
    private Context mContext;
    private static HelperUtils instance = null;

    public static void init(Context context) {
        instance = new HelperUtils();
        instance.mContext = context;
    }

    public static HelperUtils getInstance() {
        return instance;
    }

    public void showMessage(String message) {
        Toast.makeText(mContext, message, Toast.LENGTH_LONG).show();
    }
}
