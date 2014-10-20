package com.setecs.mobile.wallet.mbanking;

import static com.setecs.mobile.wallet.safe.wallet.Constants.ACCOUNTLIST;
import static com.setecs.mobile.wallet.safe.wallet.Constants.ACCOUNTLISTFILENAME;
import static com.setecs.mobile.wallet.safe.wallet.Constants.CONFIGFILENAME;
import static com.setecs.mobile.wallet.safe.wallet.Constants.MOBILENO;

import java.util.ArrayList;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.BaseAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.setecs.mobile.safe.apps.shared.SharedMethods;
import com.setecs.mobile.walletDev.R;
import com.setecs.mobile.wallet.market.utility.SafeReplyHandler;
import com.setecs.mobile.wallet.market.utility.SpinnerWithHint;
import com.setecs.mobile.wallet.market.utility.WalletActivity;
import com.setecs.mobile.wallet.net.SocketAsyncTask3;


public class BankAccBalanceActivity extends WalletActivity implements SafeReplyHandler {

	private String userMobNo, account, hint;
	private final SharedMethods cv = new SharedMethods();
	private SpinnerWithHint adapter;
	private Spinner spinner;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		setContentView(R.layout.bank_account_balance);

		populateSpinner();
	}

	public void checkBalance(View v) {
		userMobNo = cv.readConfigurationFile(this, MOBILENO, CONFIGFILENAME);

		if (userMobNo.equals("")) {
			Toast.makeText(this, "Could not read the user's mobile number", Toast.LENGTH_SHORT).show();
			return;
		}

		if (account.equals("") || account.equals("None")) {
			Toast.makeText(this, "Please choose account", Toast.LENGTH_SHORT).show();
			return;
		}

		String balMsg = "(" + userMobNo + ";" + "ab " + account + ")";
		BankAccBalanceParser parser = new BankAccBalanceParser();
		new SocketAsyncTask3(this, parser, this).execute(balMsg);
	}

	private void populateSpinner() {
		spinner = (Spinner) findViewById(R.id.source_bank);
		/*final Cursor categoryCursor = managedQuery(BankAccountProvider.CONTENT_URI, null, null, null, null);
		String[] columns = new String[] { Constants.BANK_NAME };
		int[] to = new int[] { android.R.id.text1 };
		SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, android.R.layout.simple_spinner_item,
				categoryCursor, columns, to);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);*/
		String[] bankAccList = getSafeAccounts();
		adapter = new SpinnerWithHint(this, bankAccList, hint);
		spinner.setAdapter(adapter);
		spinner.setOnItemSelectedListener(typeSelectedListener);
		spinner.setOnTouchListener(typeSpinnerTouchListener);
	}

	private final OnItemSelectedListener typeSelectedListener = new OnItemSelectedListener() {

		@Override
		public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
			//Log.d(TAG, "user selected : " + spinner.getSelectedItem().toString());
			account = spinner.getSelectedItem().toString();
		}

		@Override
		public void onNothingSelected(AdapterView<?> parent) {

		}
	};

	private final OnTouchListener typeSpinnerTouchListener = new OnTouchListener() {

		@Override
		public boolean onTouch(View v, MotionEvent event) {
			adapter.selected = true;
			((BaseAdapter) adapter).notifyDataSetChanged();
			return false;
		}
	};

	private String[] getSafeAccounts() {
		ArrayList<String> accounts = new ArrayList<String>();
		String[] acc = cv.fetchAccounts(this, ACCOUNTLIST, ACCOUNTLISTFILENAME);
		if (acc[1] != null) {
			accounts.add(acc[1]);
			hint = "Bank Accounts";
		}
		else {
			accounts.add("None");
			hint = "No Bank Account is available";
		}
		return accounts.toArray(new String[accounts.size()]);
	}

	@Override
	public void displayList(ArrayList<?> object) {
		// do nothing
	}

	@Override
	public void displayMessage(String message) {
		displayResponseAndCloseActivity(this, "Bank Account Balance", message);
	}

}
