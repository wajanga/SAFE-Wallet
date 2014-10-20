package com.setecs.mobile.wallet.mbanking;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.setecs.mobile.walletDev.R;
import com.setecs.mobile.wallet.market.database.Constants;
import com.setecs.mobile.wallet.market.provider.BankAccountProvider;


public class BankAccountActivity extends Activity {

	private Intent i;
	private long id;
	private TextView t1, t2, t3;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.bank_account);

		TextView t = (TextView) findViewById(R.id.widget236);
		t.setText("Bank Account");

		t1 = (TextView) findViewById(R.id.bank_name);
		t2 = (TextView) findViewById(R.id.number);
		t3 = (TextView) findViewById(R.id.balance);

		i = getIntent();
		id = i.getLongExtra("id", 0);
		t1.setText("BANK NAME: " + i.getStringExtra("bank_name"));
		t2.setText("IBAN: " + i.getStringExtra("iban"));
		t3.setText("ACC #: " + i.getStringExtra("account_number"));
	}

	public void delete(View v) {
		confirmDelete();
	}

	public void ok(View v) {
		Intent i = new Intent(this, ListBankAccountsActivity.class);
		startActivity(i);
		finish();
	}

	@Override
	public void onBackPressed() {
		// do something on back.
		Intent i = new Intent(this, ListBankAccountsActivity.class);
		startActivity(i);
		return;
	}

	public void deleteAccount() {
		String where = Constants.KEY_ID + "=" + id;
		String whereArgs[] = null;

		ContentResolver cr = getContentResolver();

		int deletedRowCount = cr.delete(BankAccountProvider.CONTENT_URI, where, whereArgs);
		if (deletedRowCount > 0)
			Toast.makeText(this, "Bank Account deleted successfully", Toast.LENGTH_SHORT).show();
	}

	private void confirmDelete() {

		final AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage("Are you sure you want to delete?").setCancelable(false)
				.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(final DialogInterface dialog, final int id) {
						deleteAccount();
						Intent i = new Intent().setClass(BankAccountActivity.this, ListBankAccountsActivity.class);
						startActivity(i);
						finish();
					}
				}).setNegativeButton("No", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(final DialogInterface dialog, final int id) {
						dialog.cancel();
					}
				});
		final AlertDialog alert = builder.create();
		alert.show();
	}

}
