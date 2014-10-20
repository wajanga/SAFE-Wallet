package com.setecs.mobile.wallet.market.parking;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.setecs.mobile.walletDev.R;
import com.setecs.mobile.wallet.market.main.BaseClass;
import com.setecs.mobile.wallet.market.main.HomeActivity;


public class ParkingHome extends BaseClass {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// construct the tabhost
		setContentView(R.layout.m_parking);
		TextView t = (TextView) findViewById(R.id.widget236);
		t.setText("m-Parking");

		/*TextView message = (TextView) findViewById(R.id.textView1);
		message.setText("This Application requires connection to Parking Authority");*/
	}

	public void searchParking(View v) {
		Intent i = new Intent(this, SearchParking.class);
		startActivity(i);
	}

	public void payParking(View v) {
		Intent i = new Intent(this, PayParking.class);
		startActivity(i);
	}

	public void extendParking(View v) {
		Intent i = new Intent(this, ExtendParking.class);
		startActivity(i);
	}

	public void payParkingTicket(View v) {
		Intent i = new Intent(this, PayParkingTicket.class);
		startActivity(i);
	}

	@Override
	public void onBackPressed() {
		// do something on back.
		Intent i = new Intent(this, HomeActivity.class);
		startActivity(i);
		return;
	}

}
