package com.setecs.mobile.wallet.mwallet;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.setecs.mobile.walletDev.R;
import com.setecs.mobile.wallet.mapplication.MApplication;
import com.setecs.mobile.wallet.mbanking.MBanking;
import com.setecs.mobile.wallet.mmoney.mMoney;
import com.setecs.mobile.wallet.paymobile.BTPayWallet;
import com.setecs.mobile.wallet.paymobile.MPay;
import com.setecs.mobile.wallet.paymobile.QRPayWallet;
import com.setecs.mobile.wallet.safe.accounts.SafeAccounts;
import com.setecs.mobile.wallet.safe.transactions.SafeTransactions;
import com.setecs.mobile.wallet.safe.wallet.WalletMain;


public class MWallet extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

		setContentView(R.layout.mwallet);
		((Button) findViewById(R.id.m_pay)).setOnClickListener(mwalletButtonListener);
		((Button) findViewById(R.id.m_transactions)).setOnClickListener(mwalletButtonListener);
		((Button) findViewById(R.id.m_banking)).setOnClickListener(mwalletButtonListener);
		((Button) findViewById(R.id.m_accounts)).setOnClickListener(mwalletButtonListener);
		((Button) findViewById(R.id.m_cards)).setOnClickListener(mwalletButtonListener);
		((Button) findViewById(R.id.m_money)).setOnClickListener(mwalletButtonListener);

	}

	protected OnClickListener mwalletButtonListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			Integer buttonId = ((Button) v).getId();

			if (buttonId == R.id.m_pay) {
				payMobile();
				//paymentDialog();
			}
			if (buttonId == R.id.m_transactions) {
				safeTransactions();
			}
			if (buttonId == R.id.m_accounts) {
				safeAccounts();
			}
			if (buttonId == R.id.m_banking) {
				mBanking();
			}
			if (buttonId == R.id.m_cards) {
				mApplications();
			}
			if (buttonId == R.id.m_money) {
				mMoney();
			}

		}
	};

	public void safeTransactions() {
		startActivity(new Intent(this, SafeTransactions.class));

	}

	protected void mMoney() {
		startActivity(new Intent(this, mMoney.class));
	}

	protected void safeAccounts() {
		startActivity(new Intent(this, SafeAccounts.class));

	}

	protected void mBanking() {
		startActivity(new Intent(this, MBanking.class));

	}

	protected void mApplications() {
		startActivity(new Intent(this, MApplication.class));

	}

	protected void payMobile() {
		//startActivity(new Intent(this, PayOptions.class));
		startActivity(new Intent(this, MPay.class));
	}

	@Override
	public void onBackPressed() {
		// do something on back.

		Intent i = new Intent().setClass(MWallet.this, WalletMain.class);
		i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(i);
		finish();
		return;

	}

	private void paymentDialog() {
		final CharSequence[] items = { "SAFE Message", "SAFE Data", "SAFE Image" };

		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("Payment Methods");
		builder.setItems(items, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int item) {
				if (item == 0) {
					// safe message
				}
				if (item == 1) {
					startActivity(new Intent(MWallet.this, BTPayWallet.class));
				}
				if (item == 2) {
					startActivity(new Intent(MWallet.this, QRPayWallet.class));
				}
				else
					return;
			}
		});
		AlertDialog alert = builder.create();
		alert.show();
	}

}
