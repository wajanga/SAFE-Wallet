package com.setecs.mobile.wallet.paymobile;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;

import com.setecs.mobile.walletDev.R;
import com.setecs.mobile.wallet.mwallet.MWallet;


public class PayOptions extends Activity {

	private RadioButton qr, otc, ota;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pay_options);

		qr = (RadioButton) findViewById(R.id.radio1);
		otc = (RadioButton) findViewById(R.id.radio2);
		ota = (RadioButton) findViewById(R.id.radio3);

		qr.setOnClickListener(myOptionOnClickListener);
		otc.setOnClickListener(myOptionOnClickListener);
		ota.setOnClickListener(myOptionOnClickListener);
	}

	RadioButton.OnClickListener myOptionOnClickListener = new RadioButton.OnClickListener() {
		@Override
		public void onClick(View v) {
			if (qr.isChecked()) {
				startActivity(new Intent(PayOptions.this, QRPayWallet.class));
			}
			else if (otc.isChecked()) {
				startActivity(new Intent(PayOptions.this, OTCPayWallet.class));
			}
			else if (ota.isChecked()) {
				startActivity(new Intent(PayOptions.this, OTAPayWallet.class));
			}
		}
	};

	@Override
	public void onBackPressed() {
		Intent i = new Intent(this, MWallet.class);
		startActivity(i);
		return;
	}

}
