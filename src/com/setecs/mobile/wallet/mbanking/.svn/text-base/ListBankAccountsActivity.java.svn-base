package com.setecs.mobile.wallet.mbanking;

import java.util.ArrayList;

import android.app.ListActivity;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.setecs.mobile.walletDev.R;
import com.setecs.mobile.wallet.market.database.Constants;
import com.setecs.mobile.wallet.market.provider.BankAccountProvider;


public class ListBankAccountsActivity extends ListActivity {

	private BankAccRowAdapter adapter;
	private ArrayList<BankAccount> accList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

		setContentView(R.layout.list_bank_accounts);

		TextView t = (TextView) findViewById(R.id.widget236);
		t.setText("Bank Accounts");

		listAccounts();
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		BankAccount acc = accList.get(position);
		Intent i = new Intent(this, BankAccountActivity.class);
		i.putExtra("id", acc.getId());
		i.putExtra("bank_name", acc.getBankName());
		i.putExtra("iban", acc.getIban());
		i.putExtra("account_number", acc.getAccountNumber());
		startActivity(i);
		finish();
	}

	public void listAccounts() {
		ContentResolver cr = getContentResolver();

		String[] result_columns = null;
		String where = null;
		String whereArgs[] = null;
		String order = null;

		Cursor resultCursor = cr.query(BankAccountProvider.CONTENT_URI, result_columns, where, whereArgs, order);

		accList = populateAccs(resultCursor);
		adapter = new BankAccRowAdapter(this, accList);
		setListAdapter(adapter);
		resultCursor.close();
	}

	private ArrayList<BankAccount> populateAccs(Cursor resultCursor) {
		ArrayList<BankAccount> resultBankAccList = new ArrayList<BankAccount>();
		while (resultCursor.moveToNext()) {
			BankAccount acc = new BankAccount();
			acc.setId(resultCursor.getLong(resultCursor.getColumnIndex(Constants.KEY_ID)));
			acc.setBankName(resultCursor.getString(resultCursor.getColumnIndex(Constants.BANK_NAME)));
			acc.setIban(resultCursor.getString(resultCursor.getColumnIndex(Constants.BANK_IBAN)));
			acc.setAccountNumber(resultCursor.getString(resultCursor.getColumnIndex(Constants.BANK_ACCOUNT_NUMBER)));
			resultBankAccList.add(acc);
		}
		return resultBankAccList;
	}

	@Override
	public void onBackPressed() {
		// do something on back.
		Intent i = new Intent(this, MBanking.class);
		startActivity(i);
		return;
	}

}
