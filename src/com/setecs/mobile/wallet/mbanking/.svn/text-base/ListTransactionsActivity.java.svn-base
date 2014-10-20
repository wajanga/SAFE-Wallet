package com.setecs.mobile.wallet.mbanking;

import static com.setecs.mobile.wallet.safe.wallet.Constants.ACCOUNTLIST;
import static com.setecs.mobile.wallet.safe.wallet.Constants.ACCOUNTLISTFILENAME;
import static com.setecs.mobile.wallet.safe.wallet.Constants.CONFIGFILENAME;
import static com.setecs.mobile.wallet.safe.wallet.Constants.MOBILENO;

import java.util.ArrayList;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.setecs.mobile.safe.apps.shared.SharedMethods;
import com.setecs.mobile.walletDev.R;
import com.setecs.mobile.wallet.market.utility.SafeReplyHandler;
import com.setecs.mobile.wallet.market.utility.SpinnerWithHint;
import com.setecs.mobile.wallet.market.utility.WalletActivity;
import com.setecs.mobile.wallet.net.SocketAsyncTask3;


public class ListTransactionsActivity extends WalletActivity implements SafeReplyHandler {

	private String userMobNo, account, hint;
	private final SharedMethods cv = new SharedMethods();
	private ArrayList<BankTransaction> transList;
	private ListTransactionsRowAdapter mAdapter;
	private SpinnerWithHint adapter;
	private Spinner spinner;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.list_transactions_mbanking);

		populateSpinner();
	}

	public void listTrans(View v) {
		userMobNo = cv.readConfigurationFile(ListTransactionsActivity.this, MOBILENO, CONFIGFILENAME);
		if (userMobNo.equals("")) {
			Toast.makeText(this, "Could not read the user's mobile number", Toast.LENGTH_SHORT).show();
			finish();
		}
		if (account.equals("") || account.equals("None")) {
			Toast.makeText(this, "Could not read the user's account", Toast.LENGTH_SHORT).show();
			return;
		}
		String msg = "(" + userMobNo + ";" + "atl " + account + ")";
		ListTransactionsParser listParser = new ListTransactionsParser();
		new SocketAsyncTask3(this, listParser, this).execute(msg);
	}

	private void populateSpinner() {
		spinner = (Spinner) findViewById(R.id.source_bank);
		String[] bankAccList = getSafeAccounts();
		adapter = new SpinnerWithHint(this, bankAccList, hint);
		spinner.setAdapter(adapter);
		spinner.setOnItemSelectedListener(typeSelectedListener);
		spinner.setOnTouchListener(typeSpinnerTouchListener);
	}

	private final OnItemSelectedListener typeSelectedListener = new OnItemSelectedListener() {

		@Override
		public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
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

	private void displayListView() {
		this.setContentView(R.layout.list3);

		TextView header = (TextView) this.findViewById(R.id.list_header);
		header.setText("Bank Transactions");

		ListView list = (ListView) this.findViewById(android.R.id.list);
		mAdapter = new ListTransactionsRowAdapter(ListTransactionsActivity.this, transList);
		list.setAdapter(mAdapter);

		TextView empty = (TextView) findViewById(android.R.id.empty);
		list.setEmptyView(empty);

	}

	@SuppressWarnings("unchecked")
	@Override
	public void displayList(ArrayList<?> object) {
		transList = (ArrayList<BankTransaction>) object;
		mAdapter = new ListTransactionsRowAdapter(ListTransactionsActivity.this, transList);
		displayListView();
	}

	@Override
	public void displayMessage(String message) {
		displayResponseAndCloseActivity(this, "Bank Transactions", message);
	}

}
