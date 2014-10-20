package com.setecs.mobile.wallet.mmoney;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
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

import com.setecs.mobile.safe.apps.shared.DisplaySAFEResponse;
import com.setecs.mobile.walletDev.R;
import com.setecs.mobile.wallet.market.utility.SpinnerWithHint;


public class LoadCash extends Activity {

	public static final String CASH_FILE = "Cash File";

	private final String TAG = "SpinnerHint";
	private Spinner select_bank_acc;
	private final String[] data = { "Nordea", "SEB", "Barclays" };
	SpinnerWithHint spinner_adapter;
	private EditText amount;
	private String SpinnerText;

	private final DisplaySAFEResponse ds = new DisplaySAFEResponse();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

		setContentView(R.layout.load_cash);

		select_bank_acc = (Spinner) findViewById(R.id.bank_account);
		spinner_adapter = new SpinnerWithHint(this, data, "Select SAFE Account");
		select_bank_acc.setAdapter(spinner_adapter);
		select_bank_acc.setOnItemSelectedListener(typeSelectedListener);
		select_bank_acc.setOnTouchListener(typeSpinnerTouchListener);

		amount = (EditText) findViewById(R.id.amount_to_load);
	}

	private final OnItemSelectedListener typeSelectedListener = new OnItemSelectedListener() {

		@Override
		public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
			Log.d(TAG, "user selected : " + select_bank_acc.getSelectedItem().toString());

		}

		@Override
		public void onNothingSelected(AdapterView<?> parent) {

		}
	};

	private final OnTouchListener typeSpinnerTouchListener = new OnTouchListener() {

		@Override
		public boolean onTouch(View v, MotionEvent event) {
			spinner_adapter.selected = true;
			((BaseAdapter) spinner_adapter).notifyDataSetChanged();
			return false;
		}
	};

	public void loadCash(View v) {
		if (isFieldEmpty(amount)) {
			showDialog("Error", "You must enter the amount");
		}
		else {
			if (spinner_adapter.selected == false)
				SpinnerText = data[0];
			else
				SpinnerText = select_bank_acc.getSelectedItem().toString();
			SharedPreferences settings = getSharedPreferences(CASH_FILE, 0);
			SharedPreferences.Editor editor = settings.edit();
			editor.putString(SpinnerText, amount.getText().toString());
			editor.commit();
			ds.displaySAFErespons(this, "Cash loaded successfully", "Load Cash");
		}
	}

	private void showDialog(String title, String message) {
		new AlertDialog.Builder(this).setTitle(title).setMessage(message)
				.setPositiveButton("OK", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int arg1) {
						dialog.cancel();
						return;
					}
				}).show();
	}

	private boolean isFieldEmpty(EditText e) {
		boolean result = false;
		if (e.getText().toString().equals(""))
			result = true;
		return result;
	}

}
