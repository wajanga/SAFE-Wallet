package com.setecs.mobile.wallet.market.main;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.c2dm.C2DMessaging;
import com.setecs.mobile.safe.apps.shared.CustomExceptionHandler;
import com.setecs.mobile.walletDev.R;
import com.setecs.mobile.wallet.market.advertisement.AdHome;
import com.setecs.mobile.wallet.market.bills.BillHome;
import com.setecs.mobile.wallet.market.coupons.CouponsHome;
import com.setecs.mobile.wallet.market.giftcards.GiftHome;
import com.setecs.mobile.wallet.market.parking.ParkingHome;
import com.setecs.mobile.wallet.market.promotions.Home;
import com.setecs.mobile.wallet.market.telecom.TelecomHome;
import com.setecs.mobile.wallet.market.tickets.TicketsHome;
import com.setecs.mobile.wallet.safe.wallet.WalletMain;


public class HomeActivity extends BaseClass {
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Thread.setDefaultUncaughtExceptionHandler(new CustomExceptionHandler(HomeActivity.this));

		//setContentView(R.layout.server_message);
		setContentView(R.layout.dashboard2);
	}

	public void onActionBarHomeClick(View v) {
		Intent i = new Intent(this, WalletMain.class);
		i.putExtra("pinflag", false);
		startActivity(i);
	}

	@Override
	public void onBackPressed() {
		// do something on back.
		Intent i = new Intent().setClass(HomeActivity.this, WalletMain.class);
		Bundle bundle = new Bundle(); //bundle is like the letter
		bundle.putBoolean("pinflag", false);
		bundle.putBoolean("setRunned", false);
		i.putExtras(bundle);//actually it's bundle who carries the content u wanna pass
		i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(i);
		return;
	}

	public void onActionOneClick(View v) {
		Intent i = new Intent(this, Home.class);
		startActivity(i);
	}

	public void onActionTwoClick(View v) {
		Intent i = new Intent(this, CouponsHome.class);
		startActivity(i);
	}

	public void onActionThreeClick(View v) {
		Intent i = new Intent(this, TelecomHome.class);
		startActivity(i);
	}

	public void onActionFourClick(View v) {
		Intent i = new Intent(this, TicketsHome.class);
		startActivity(i);
	}

	public void onActionFiveClick(View v) {
		Intent i = new Intent(this, ParkingHome.class);
		startActivity(i);
	}

	public void onActionSixClick(View v) {
		Intent i = new Intent(this, GiftHome.class);
		startActivity(i);
	}

	public void onActionSevenClick(View v) {
		Intent i = new Intent(this, BillHome.class);
		startActivity(i);
	}

	public void m_Advertising(View v) {
		Intent i = new Intent(this, AdHome.class);
		startActivity(i);
	}

	public void onSearchButtonClick(View v) {
		Log.e("Super", "Starting registration");
		C2DMessaging.register(this, "LocBasedSecApp@gmail.com");
		Toast.makeText(this, "Registration sent", Toast.LENGTH_SHORT).show();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.settings_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.settings_id:
			Intent i = new Intent(this, MyPreferences.class);
			startActivity(i);
			return true;
		case R.id.push_reg_id:
			SettingsHandler my_settings = new SettingsHandler(this.getApplicationContext());
			Toast.makeText(this, my_settings.getC2DMRegID(), Toast.LENGTH_SHORT).show();
			return true;
		}
		return false;
	}

}
