package com.setecs.mobile.wallet.mmoney;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;

import com.setecs.mobile.walletDev.R;


public class ViewStoredMoney extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		//		walletmain.setPinFlag(false);

		super.onCreate(savedInstanceState);
		this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

		setContentView(R.layout.view_storedmoney);

	}

}
