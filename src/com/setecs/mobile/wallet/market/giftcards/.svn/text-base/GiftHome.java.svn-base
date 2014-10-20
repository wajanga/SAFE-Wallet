package com.setecs.mobile.wallet.market.giftcards;

import android.app.TabActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;

import com.setecs.mobile.safe.apps.shared.CustomExceptionHandler;
import com.setecs.mobile.walletDev.R;
import com.setecs.mobile.wallet.market.main.HomeActivity;


public class GiftHome extends TabActivity {

	private TabHost mTabHost;
	static boolean appPaused = true;

	private void setupTabHost() {
		mTabHost = (TabHost) findViewById(android.R.id.tabhost);
		mTabHost.setup();
	}

	private Intent setupTabContent(int opt) {
		Intent intent = null;
		switch (opt) {
		case 1:
			intent = new Intent().setClass(this, SavedGifts.class);
			break;
		case 2:
			intent = new Intent().setClass(this, MarketGifts.class);
			break;
		}
		return intent;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Thread.setDefaultUncaughtExceptionHandler(new CustomExceptionHandler(GiftHome.this));

		// construct the tabhost
		setContentView(R.layout.coupons_layout2);
		TextView t = (TextView) findViewById(R.id.widget236);
		t.setText("m-Gift Cards");
		appPaused = true;
		setupTabHost();

		setupTab(new TextView(this), "In the Wallet", 1);
		setupTab(new TextView(this), "On the Market", 2);

	}

	private void setupTab(final View view, final String tag, int tag_opt) {
		View tabview = createTabView(mTabHost.getContext(), tag);
		TabSpec setContent = mTabHost.newTabSpec(tag).setIndicator(tabview).setContent(setupTabContent(tag_opt));
		mTabHost.addTab(setContent);
	}

	private static View createTabView(final Context context, final String text) {
		View view = LayoutInflater.from(context).inflate(R.layout.tabs_bg, null);
		TextView tv = (TextView) view.findViewById(R.id.tabsText);
		tv.setText(text);
		return view;
	}

	@Override
	public void onBackPressed() {
		// do something on back.
		Intent i = new Intent(this, HomeActivity.class);
		startActivity(i);
		return;
	}

}
