package com.setecs.mobile.wallet.mbanking;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;

import com.setecs.mobile.walletDev.R;


public class ListTransactionsIntermediate extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		setContentView(R.layout.list_transactions_mbanking);
	}

	public void listTransaction(View v) {
		showErrorDialog("Alert", "This function will be coming in future versions");
	}

	private void showErrorDialog(String title, String message) {
		new AlertDialog.Builder(this).setTitle(title).setMessage(message)
				.setPositiveButton("OK", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int arg1) {
						dialog.cancel();
						return;
					}
				}).show();
	}

}
