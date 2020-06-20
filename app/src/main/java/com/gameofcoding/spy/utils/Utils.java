package com.gameofcoding.spy.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.provider.Settings.Secure;
import android.widget.Toast;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Random;

public class Utils {
    private static final String TAG = "Utils";
    private Context mContext;

    public Utils(Context context) {
	mContext = context;
    }

    public String generateDeviceId() {
	String deviceId = Secure.getString(getContext().getContentResolver(), Secure.ANDROID_ID);
	if(deviceId == null || deviceId.isEmpty()) {
	    XLog.w(TAG, "getDeviceUniqueId(), deviceId=null, generating id by other method");
	    // TODO: Save and check if id is saved in pref.
	    deviceId = String.valueOf(new Random().nextInt());
	}

	// Format id
	String deviceModel = Build.MODEL;
	deviceModel = deviceModel.replaceAll("[\\-\\+\\.\\^:,\\s]", "_");
	deviceId = deviceModel + "-" + deviceId;
	return deviceId;
    }

    public void showToast(String msg) {
	Toast.makeText(getContext(), msg, Toast.LENGTH_LONG).show();
    }

    public boolean hasActiveInternetConnection() throws IOException, MalformedURLException {
	// Check if data is on
	ConnectivityManager cm = (ConnectivityManager)
	    getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
	NetworkInfo netInfo = cm.getActiveNetworkInfo();
	if(!(netInfo != null && netInfo.isConnected())) return false;

	// Do ping test
	HttpURLConnection urlc = (HttpURLConnection)
            (new URL("https://clients3.google.com/generate_204").openConnection());
	return (urlc.getResponseCode() == 204 && urlc.getContentLength() == 0);
    }

    private Context getContext() {
	if(mContext == null)
	    XLog.e(TAG, "getContext(): Passed Context is null");
	return mContext;
    }
}