package com.setecs.mobile.wallet.market.main;

import android.os.Bundle;
import android.preference.PreferenceActivity;

import com.setecs.mobile.walletDev.R;


public class MyPreferences extends PreferenceActivity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.preferences);
	}

}
