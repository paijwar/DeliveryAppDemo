package com.paijwar.deliveryapp.storage;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by pradeepkumarpaijwar on 29/05/17.
 */

public class LocalStorage {
    private static LocalStorage ourInstance;
    private Context mContext;

    public static void init(Context context) {
        ourInstance = new LocalStorage(context);
    }

    public static LocalStorage getInstance() {
        return ourInstance;
    }

    SharedPreferences mPrefs;

    private LocalStorage(Context context) {
        this.mContext = context;
        mPrefs = mContext.getSharedPreferences(mContext.getPackageName(), Context.MODE_PRIVATE);
    }

    public String get(String key) {
        return mPrefs.getString(key, null);
    }

    public void put(String key, String value) {
        mPrefs.edit().putString(key, value).apply();
    }

}
