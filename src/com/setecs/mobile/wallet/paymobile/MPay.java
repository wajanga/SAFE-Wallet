package com.setecs.mobile.wallet.paymobile;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.setecs.mobile.walletDev.R;
import com.setecs.mobile.wallet.market.utility.WalletActivity;
import com.setecs.mobile.wallet.mbanking.PayBillActivity;
import com.setecs.mobile.wallet.mwallet.MWallet;


public class MPay extends WalletActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

		setContentView(R.layout.m_pay);
		((Button) findViewById(R.id.Pay_Bill)).setOnClickListener(mPayButtonListener);
		((Button) findViewById(R.id.Pay_Merchant)).setOnClickListener(mPayButtonListener);
	}

	protected OnClickListener mPayButtonListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			Integer buttonId = ((Button) v).getId();

			if (buttonId == R.id.Pay_Bill) {
				Pay_Bill();
			}

			if (buttonId == R.id.Pay_Merchant) {
				Pay_Merchant();
			}

		}

	};

	protected void Pay_Bill() {
		startActivity(new Intent(getApplicationContext(), PayBillActivity.class));
	}

	protected void Pay_Merchant() {
		startActivity(new Intent(getApplicationContext(), QRPayWallet.class));
	}

	@Override
	public void onBackPressed() {
		// do something on back.
		Intent i = new Intent(this, MWallet.class);
		startActivity(i);
		return;
	}

}
