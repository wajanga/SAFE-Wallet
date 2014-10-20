package com.setecs.mobile.wallet.market.main;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.google.android.c2dm.C2DMBaseReceiver;
import com.setecs.mobile.walletDev.R;


public class C2DMReceiver extends C2DMBaseReceiver {

	private NotificationManager mgr = null;
	private static final int NOTIFY_ME_ID = 1337;

	public C2DMReceiver() {
		super("LocBasedSecApp@gmail.com");
		
	}

	@Override
	protected void onMessage(Context context, Intent intent) {
		
		mgr = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		Notification note = new Notification(R.drawable.notify_icon, "Status message!", System.currentTimeMillis());
		PendingIntent i = PendingIntent.getActivity(this, 0, new Intent(this, HomeActivity.class), 0);
		note.setLatestEventInfo(this, "Message from Server", "Yay!This is the notification message", i);
		note.defaults |= Notification.DEFAULT_SOUND;
		note.flags |= Notification.FLAG_AUTO_CANCEL;
		mgr.notify(NOTIFY_ME_ID, note);
	}

	@Override
	public void onError(Context context, String errorId) {
		
		Log.e("C2DM", "Error occured!!!");
	}

	@Override
	public void onRegistrered(Context context, String registrationId) {
		Log.w("C2DMReceiver-onRegistered", registrationId);
		SettingsHandler my_settings = new SettingsHandler(this.getApplicationContext());
		my_settings.setC2DMRegID(registrationId);
		my_settings.save();
	}

	@Override
	public void onUnregistered(Context context) {
		Log.w("C2DMReceiver-onUnregistered", "got here!");
	}

}
