package com.gameofcoding.spy.utils;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import com.gameofcoding.spy.R;

public class NotificationUtils {
    private Context mContext;
    
    public NotificationUtils(Context context) {
	mContext = context;
    }
    
    public boolean createDefaultNotifChannel() {
	String channelId = getDefaultNotifChannelId();
	String channelName = mContext.getString(R.string.default_notif_channel_name);
	String channelDesc = mContext.getString(R.string.default_notif_channel_desc);;
	return createNotificationChannel(channelId, channelName, channelDesc,
					 NotificationManager.IMPORTANCE_MIN);
    }
    
    public String getDefaultNotifChannelId() {
	return mContext.getString(R.string.default_notif_channel_id);
    }

    public boolean createHighPriorityNotifChannel() {
	String channelId = getHighPriorityNotifChannelId();
	String channelName =
	    mContext.getString(R.string.high_priority_notif_channel_name);
	String channelDesc =
	    mContext.getString(R.string.high_priority_notif_channel_desc);
	return createNotificationChannel(channelId, channelName, channelDesc,
				  NotificationManager.IMPORTANCE_HIGH);
    }
    
    public String getHighPriorityNotifChannelId() {
	return mContext.getString(R.string.high_priority_notif_channel_id);
    }

    public boolean createNotificationChannel(String channelId, String channelName,
					  String channelDesc, int importance) {
	NotificationManager notifManager =
	    (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
	if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
	    // Create notification channel if we don't already have one
	    if (notifManager.getNotificationChannel(channelId) == null) {
		NotificationChannel channel =
		    new NotificationChannel(channelId,channelName, importance);
		channel.setDescription(channelDesc);
		notifManager.createNotificationChannel(channel);
		return true;
	    }
	}
	return false;
    }
}
