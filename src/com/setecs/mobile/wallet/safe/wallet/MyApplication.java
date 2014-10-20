package com.setecs.mobile.wallet.safe.wallet;

import org.acra.ACRA;
import org.acra.ReportingInteractionMode;
import org.acra.annotation.ReportsCrashes;

import android.app.Application;

import com.setecs.mobile.walletDev.R;


//(formKey = "dHV3ZUVDZ2xCbDlucE1MYThQaF9wVlE6MQ", mode = ReportingInteractionMode.TOAST, forceCloseDialogAfterToast = false, // optional, default false
//resToastText = R.string.crash_toast_text)
@ReportsCrashes(formKey = "", // will not be used
mailTo = "behzadpr@gmail.com", mode = ReportingInteractionMode.TOAST, resToastText = R.string.crash_toast_text)
public class MyApplication extends Application {

	@Override
	public void onCreate() {
		// The following line triggers the initialization of ACRA
		ACRA.init(this);
		super.onCreate();
	}
}