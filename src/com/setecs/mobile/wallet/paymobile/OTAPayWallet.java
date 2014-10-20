package com.setecs.mobile.wallet.paymobile;

import static com.setecs.mobile.safe.apps.shared.Constants.SAFEIPNO;
import static com.setecs.mobile.wallet.safe.wallet.Constants.ACCOUNTLIST;
import static com.setecs.mobile.wallet.safe.wallet.Constants.ACCOUNTLISTFILENAME;
import static com.setecs.mobile.wallet.safe.wallet.Constants.CONFIGFILENAME;
import static com.setecs.mobile.wallet.safe.wallet.Constants.MOBILENO;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.setecs.mobile.safe.apps.shared.DisplaySAFEResponse;
import com.setecs.mobile.safe.apps.shared.SharedMethods;
import com.setecs.mobile.walletDev.R;


public class OTAPayWallet extends Activity {

	private EditText amountET, tipET, rcptAccET;
	private TextView totalTV, sourceAccTV;
	private ProgressDialog progressDialog;

	private String sourceAcc, rcptAcc, total, results;
	private final String TAG = "OTAPayWallet";

	private final SharedMethods cv = new SharedMethods();
	private final DisplaySAFEResponse ds = new DisplaySAFEResponse();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ota_pay_wallet);

		initGUI();
	}

	private void initGUI() {
		sourceAccTV = (TextView) findViewById(R.id.sourceAcc);
		sourceAcc = cv.fetchAccountList(OTAPayWallet.this, ACCOUNTLIST, ACCOUNTLISTFILENAME);
		sourceAccTV.setText("SAFE Account: " + sourceAcc);

		totalTV = (TextView) findViewById(R.id.total);

		amountET = (EditText) findViewById(R.id.amount);
		amountET.addTextChangedListener(new TotalTextWatcher());

		tipET = (EditText) findViewById(R.id.tip);
		tipET.addTextChangedListener(new TotalTextWatcher());

		rcptAccET = (EditText) findViewById(R.id.rcptAcc);
	}

	public void pay(View v) {
		rcptAcc = rcptAccET.getText().toString();
		if (amountET.getText().length() > 0 && sourceAcc.length() > 0 && rcptAcc.length() > 0) {
			sendPayment();
		}
		else {
			Toast.makeText(this, "Please enter all fields", Toast.LENGTH_SHORT).show();
		}
	}

	private class TotalTextWatcher implements TextWatcher {
		private String amount, tip;
		private int y = 0;

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

		@Override
		public void onTextChanged(CharSequence s, int start, int before, int count) {}

		@Override
		public void afterTextChanged(Editable s) {
			if (tipET.getText().toString().length() == 0 && amountET.getText().toString().length() == 0) {
				y = 0;
			}
			else if (amountET.getText().toString().length() == 0) {
				tip = tipET.getText().toString();
				y = Integer.parseInt(tip);
			}
			else if (tipET.getText().toString().length() == 0) {
				amount = amountET.getText().toString();
				y = Integer.parseInt(amount);
			}
			else {
				amount = amountET.getText().toString();
				tip = tipET.getText().toString();
				y = Integer.parseInt(amount) + Integer.parseInt(tip);
			}

			total = Integer.toString(y);
			totalTV.setText("Total: " + total);
		}
	}

	private void sendPayment() {
		progressDialog = ProgressDialog.show(this, "", "Please wait...");

		Thread thread = new Thread() {
			@Override
			public void run() {

				String userMobNo = cv.readConfigurationFile(OTAPayWallet.this, MOBILENO, CONFIGFILENAME);
				SharedMethods.serverIpAddress = cv.readConfigurationFile(OTAPayWallet.this, SAFEIPNO, CONFIGFILENAME);

				if (!SharedMethods.serverIpAddress.equals("")) {
					FutureTask<String> task = new FutureTask<String>(new SendToSAFEThread(SharedMethods.connected,
							total, sourceAcc, rcptAcc, userMobNo, SharedMethods.serverIpAddress));
					ExecutorService es = Executors.newSingleThreadExecutor();
					es.submit(task);
					try {
						results = task.get();
					}
					catch (Exception e) {
						Log.e(TAG, "sndPayment", e);
					}
					es.shutdown();

				}
				handler.sendEmptyMessage(0);
			}
		};
		thread.start();
	}

	private void displaySAFErespons(String result) {
		ds.displaySAFErespons(OTAPayWallet.this, result, "SAFE Response");
	}

	private final Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			progressDialog.dismiss();
			displaySAFErespons(results);
		}
	};

}