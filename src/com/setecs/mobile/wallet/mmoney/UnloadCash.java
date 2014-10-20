package com.setecs.mobile.wallet.mmoney;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.BaseAdapter;
import android.widget.Spinner;

import com.setecs.mobile.walletDev.R;
import com.setecs.mobile.wallet.market.utility.SpinnerWithHint;
import com.setecs.mobile.wallet.market.utility.WalletActivity;


public class UnloadCash extends WalletActivity {

	private final String TAG = "SpinnerHint";
	private Spinner select_bank_acc;
	private final String[] data = { "Nordea", "SEB", "Barclays" };
	SpinnerWithHint spinner_adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

		setContentView(R.layout.unload_cash);

		select_bank_acc = (Spinner) findViewById(R.id.bank_account);
		spinner_adapter = new SpinnerWithHint(this, data, "Select SAFE Account");
		select_bank_acc.setAdapter(spinner_adapter);
		select_bank_acc.setOnItemSelectedListener(typeSelectedListener);
		select_bank_acc.setOnTouchListener(typeSpinnerTouchListener);

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

	public void unloadCash(View v) {
		showDialog("Error", "This feature will be coming in future versions");
	}

}
