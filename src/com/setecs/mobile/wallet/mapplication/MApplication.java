package com.setecs.mobile.wallet.mapplication;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.setecs.mobile.walletDev.R;
import com.setecs.mobile.wallet.market.utility.WalletActivity;
import com.setecs.mobile.wallet.mwallet.MWallet;


public class MApplication extends WalletActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

		setContentView(R.layout.m_application);
		((Button) findViewById(R.id.Register_Card)).setOnClickListener(mApplicationButtonListener);
		((Button) findViewById(R.id.List_Cards)).setOnClickListener(mApplicationButtonListener);
		((Button) findViewById(R.id.List_Transactions)).setOnClickListener(mApplicationButtonListener);
	}

	protected OnClickListener mApplicationButtonListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			Integer buttonId = ((Button) v).getId();

			if (buttonId == R.id.Register_Card) {
				Register_Card();
			}
			if (buttonId == R.id.List_Cards) {
				List_Cards();
			}
			if (buttonId == R.id.List_Transactions) {
				List_Transactions();
			}

		}

	};

	protected void Register_Card() {
		startActivity(new Intent(getApplicationContext(), RegisterCard.class));
	}

	protected void List_Transactions() {
		//startActivity(new Intent(getApplicationContext(), ListTransactions.class));
		startActivity(new Intent(getApplicationContext(), ListTransactionsIntermediate.class));
	}

	protected void List_Cards() {
		startActivity(new Intent(getApplicationContext(), ListCards.class));
	}

	public void checkCardBalance(View v) {
		showDialog("Alert", "This function will be coming in future versions");
	}

	@Override
	public void onBackPressed() {
		// do something on back.
		Intent i = new Intent(this, MWallet.class);
		startActivity(i);
		return;
	}

} // end class

