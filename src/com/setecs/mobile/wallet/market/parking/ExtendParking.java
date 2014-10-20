package com.setecs.mobile.wallet.market.parking;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.setecs.mobile.walletDev.R;
import com.setecs.mobile.wallet.market.utility.WalletActivity;


public class ExtendParking extends WalletActivity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.extend_parking);

		TextView t = (TextView) findViewById(R.id.widget236);
		t.setText("Extend Parking");
	}

	public void extendParking(View v) {
		showDialog("Alert", "This function will be coming in future versions");
	}

}
