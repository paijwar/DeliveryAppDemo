package com.paijwar.deliveryapp.location;

import android.os.AsyncTask;
import android.util.Log;

import com.google.android.gms.maps.GoogleMap;

import static com.paijwar.deliveryapp.location.MapDirectionUtils.downloadUrl;

/**
 * Created by pradeepkumarpaijwar on 30/05/17.
 */

public class DownloadTask extends AsyncTask<String, Void, String> {

    GoogleMap mMap;

    public DownloadTask(GoogleMap map) {
        this.mMap = map;
    }

    @Override
    protected String doInBackground(String... url) {

        String data = "";

        try {
            data = downloadUrl(url[0]);
        } catch (Exception e) {
            Log.d("Background Task", e.toString());
        }
        return data;
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);

        ParserTask parserTask = new ParserTask(mMap);
        parserTask.execute(result);

    }
}
