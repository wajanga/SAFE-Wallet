package com.setecs.mobile.wallet.mbanking;

import static com.setecs.mobile.wallet.safe.wallet.Constants.ACCOUNTLIST;
import static com.setecs.mobile.wallet.safe.wallet.Constants.ACCOUNTLISTFILENAME;

import java.util.ArrayList;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.setecs.mobile.safe.apps.shared.SharedMethods;
import com.setecs.mobile.walletDev.R;
import com.setecs.mobile.wallet.market.utility.SpinnerWithHint;
import com.setecs.mobile.wallet.market.utility.WalletActivity;


public class PayBillActivity extends WalletActivity {

	private Spinner spinner;
	private final String TAG = "SpinnerHint";
	private SpinnerWithHint adapter;
	protected SharedMethods cv = new SharedMethods();
	private String bill_no, amount;
	private EditText billNoET, amountET;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		setContentView(R.layout.pay_invoic);

		Intent i = getIntent();
		bill_no = i.getStringExtra("bill_no");
		amount = i.getStringExtra("amount");

		billNoET = (EditText) findViewById(R.id.issuing_bank);
		amountET = (EditText) findViewById(R.id.cardnum);
		if (bill_no != null)
			billNoET.setText(bill_no);
		if (amount != null)
			amountET.setText(amount);

		spinner = (Spinner) findViewById(R.id.card_type);
		/*final Cursor categoryCursor = managedQuery(BankAccountProvider.CONTENT_URI, null, null, null, null);
		String[] columns = new String[] { Constants.BANK_NAME };
		int[] to = new int[] { android.R.id.text1 };
		SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, android.R.layout.simple_spinner_item,
				categoryCursor, columns, to);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);*/
		adapter = new SpinnerWithHint(this, getSafeAccounts(), "Bank Accounts");
		spinner.setAdapter(adapter);
		spinner.setOnItemSelectedListener(typeSelectedListener);
		spinner.setOnTouchListener(typeSpinnerTouchListener);
	}

	private final OnItemSelectedListener typeSelectedListener = new OnItemSelectedListener() {

		@Override
		public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
			Log.d(TAG, "user selected : " + spinner.getSelectedItem().toString());
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

	public void payBill(View v) {
		//Toast.makeText(this, "This function will be coming in future versions", Toast.LENGTH_SHORT).show();

		String message = "Bill # 1 has been paid successfully\n\nAmount: $170\n\nReceipt has been sent to you E-mail";
		displayResponseAndCloseActivity(this, "Pay Bill", message);
	}

	private String[] getSafeAccounts() {
		ArrayList<String> accounts = new ArrayList<String>();
		String[] acc = cv.fetchAccounts(PayBillActivity.this, ACCOUNTLIST, ACCOUNTLISTFILENAME);
		//accounts.add(acc[1]);
		accounts.add(acc[0]);
		return accounts.toArray(new String[accounts.size()]);
	}

}
