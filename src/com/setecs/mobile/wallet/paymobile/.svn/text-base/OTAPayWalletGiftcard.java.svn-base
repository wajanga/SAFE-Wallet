package com.setecs.mobile.wallet.paymobile;

import static com.setecs.mobile.safe.apps.shared.Constants.SAFEIPNO;
import static com.setecs.mobile.wallet.safe.wallet.Constants.ACCOUNTLIST;
import static com.setecs.mobile.wallet.safe.wallet.Constants.ACCOUNTLISTFILENAME;
import static com.setecs.mobile.wallet.safe.wallet.Constants.CONFIGFILENAME;
import static com.setecs.mobile.wallet.safe.wallet.Constants.MOBILENO;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.setecs.mobile.safe.apps.shared.DisplaySAFEResponse;
import com.setecs.mobile.safe.apps.shared.SharedMethods;
import com.setecs.mobile.walletDev.R;


public class OTAPayWalletGiftcard extends Activity implements Runnable {

	private String accountno;
	protected String srcAccount;
	private String tipmsg;
	private String userMobNo;
	private TextView totalamnt;
	private EditText rcpntaccounttxt;
	private EditText paymentamnttxt;
	private String paymentamntmsg;
	private EditText tiptxt;
	private AlertDialog warningDialog;
	private ProgressDialog progressDialog;
	private String rcpntAccountmsg;
	private String totalamntmsg;
	private Thread thread;
	private Intent i;
	private String amount;
	ArrayAdapter<String> spinnerAdapter;
	ArrayList<String> ids, amounts, list, list_original, accounts, discounts;
	private Spinner gift;
	private String account;
	private int position;
	private String results;
	private Message handlermsg;
	private static final int SAFEMSG = 0;
	protected static final int PAYMENT_RESPONSE = 1;
	private final SharedMethods cv = new SharedMethods();
	private final DisplaySAFEResponse ds = new DisplaySAFEResponse();

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

		setContentView(R.layout.ota_payment);

		gift = (Spinner) findViewById(R.id.pay_with);
		TextView t = (TextView) findViewById(R.id.pay_with_text);
		//
		((ViewManager) gift.getParent()).removeView(gift);
		((ViewManager) t.getParent()).removeView(t);

		i = getIntent();

		((Button) findViewById(R.id.btn_pay)).setOnClickListener(mpayButtonListener);
		totalamnt = (TextView) findViewById(R.id.totalamntmsg);
		paymentamnttxt = (EditText) findViewById(R.id.amounttxt);

		amount = i.getStringExtra("amount");

		account = i.getStringExtra("account");
		position = i.getIntExtra("position", 0);

		paymentamnttxt.setText(Integer.toString(Math.round(Float.valueOf(amount))));
		totalamnt.setText(Integer.toString(Math.round(Float.valueOf(amount))));

		paymentamntmsg = paymentamnttxt.getText().toString();
		tiptxt = (EditText) findViewById(R.id.tiptxt);
		tipmsg = tiptxt.getText().toString();
		rcpntaccounttxt = (EditText) findViewById(R.id.rcpntaccounttxt);
		rcpntaccounttxt.setText(account);

		paymentamnttxt.setEnabled(false);
		tiptxt.setEnabled(false);

		paymentamnttxt.addTextChangedListener(new TextWatcher() {

			private int y = 0;

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {

			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {

			}

			@Override
			public void afterTextChanged(Editable arg0) {

				if (tiptxt.getText().toString().length() == 0 && paymentamnttxt.getText().toString().length() == 0) {
					y = 0;
				}
				else if (paymentamnttxt.getText().toString().length() == 0) {
					tipmsg = tiptxt.getText().toString();
					y = Integer.parseInt(tipmsg);
				}
				else if (tiptxt.getText().toString().length() == 0) {
					paymentamntmsg = paymentamnttxt.getText().toString();
					y = Integer.parseInt(paymentamntmsg);
				}

				else
					y = Integer.parseInt(paymentamntmsg) + Integer.parseInt(tipmsg);

				totalamnt.setText(Integer.toString(y));
			}
		});

		tiptxt.addTextChangedListener(new TextWatcher() {

			private int x = 0;

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {

			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {

			}

			@Override
			public void afterTextChanged(Editable arg0) {

				if (tiptxt.getText().toString().length() == 0 && paymentamnttxt.getText().toString().length() == 0) {
					x = 0;
				}
				else if (tiptxt.getText().toString().length() == 0)
					x = Integer.parseInt(paymentamnttxt.getText().toString());
				else if (paymentamnttxt.getText().toString().length() == 0)
					x = Integer.parseInt(tiptxt.getText().toString());
				else
					x = Integer.parseInt(paymentamnttxt.getText().toString()) + Integer.parseInt(tiptxt.getText()
							.toString());

				totalamnt.setText(Integer.toString(x));
			}
		});

		final Spinner spinner = (Spinner) findViewById(R.id.spinner);
		accountno = cv.fetchAccountList(OTAPayWalletGiftcard.this, ACCOUNTLIST, ACCOUNTLISTFILENAME);

		ArrayAdapter<CharSequence> adapter = new ArrayAdapter<CharSequence>(this, android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		adapter.add(accountno);

		spinner.setAdapter(adapter);

		spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				TextView temp = (TextView) arg1;
				srcAccount = temp.getText().toString();
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {

			}
		});
	}

	protected OnClickListener mpayButtonListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			Integer buttonId = ((Button) v).getId();
			rcpntAccountmsg = rcpntaccounttxt.getText().toString();
			if (buttonId == R.id.btn_pay) {
				if (paymentamntmsg.length() > 0 && srcAccount.length() > 0 && rcpntAccountmsg.length() > 0) {
					waitDialog();
				}
				else
					warnmsg();
			}

		}
	};

	protected void sndPayment() {
		totalamnt = (TextView) findViewById(R.id.totalamntmsg);
		totalamntmsg = totalamnt.getText().toString();
		//rcpntAccountmsg = rcpntaccounttxt.getText().toString();

		userMobNo = cv.readConfigurationFile(OTAPayWalletGiftcard.this, MOBILENO, CONFIGFILENAME);

		if (userMobNo.length() > 0) {
			SharedMethods.serverIpAddress = cv.readConfigurationFile(OTAPayWalletGiftcard.this, SAFEIPNO,
					CONFIGFILENAME);
			if (!SharedMethods.serverIpAddress.equals("")) {
				FutureTask<String> task = new FutureTask<String>(new SendToSAFEThread(SharedMethods.connected,
						totalamntmsg, srcAccount, rcpntAccountmsg, userMobNo, SharedMethods.serverIpAddress));
				ExecutorService es = Executors.newSingleThreadExecutor();
				es.submit(task);
				try {
					results = task.get();
					if (results != null && results.length() > 0) {
						handlermsg = new Message();
						handlermsg.what = SAFEMSG;
						handlermsg.arg1 = PAYMENT_RESPONSE;
						handler.sendMessage(handlermsg);
					}
				}
				catch (Exception e) {
					System.err.println(e);
				}
				es.shutdown();
			}
		}
		else {
			ds.blankFieldsWarning(this);
		}
	}

	protected void warnmsg() {
		warningDialog = new AlertDialog.Builder(this).create();
		warningDialog.setTitle("Alert");
		warningDialog.setMessage("Please fill out empty fields!");
		warningDialog.setIcon(R.drawable.warningicon32x32);
		warningDialog.setButton("Try again", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {

			}
		});
		warningDialog.show();
	}

	protected void waitDialog() {
		progressDialog = ProgressDialog.show(this, "", "Please wait...");
		thread = new Thread(this);
		thread.start();

	}

	private void displaySAFErespons(final String result) {
		((Dialog) progressDialog).dismiss();
		AlertDialog.Builder alert = new AlertDialog.Builder(this);
		alert.setTitle("SAFE Response");
		alert.setMessage(result);
		alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int whichButton) {
				if (result.contains("ERROR") || result.equals(null)) {
					Intent i = new Intent();
					i.putExtra("position", position);
					setResult(0, i);
					finish();
				}
				else {
					Intent i = new Intent();
					i.putExtra("position", position);
					setResult(1, i);
					finish();
				}
			}
		});
		alert.show();
	}

	@Override
	public void run() {
		sndPayment();
	}

	private final Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			progressDialog.dismiss();
			displaySAFErespons(results);
			//displaySAFErespons("ERROR: Purchase could not be completed");
		}
	};

} // end class