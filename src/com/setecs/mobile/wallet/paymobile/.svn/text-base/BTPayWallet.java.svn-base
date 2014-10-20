package com.setecs.mobile.wallet.paymobile;

import static com.setecs.mobile.safe.apps.shared.Constants.SAFEIPNO;
import static com.setecs.mobile.wallet.safe.wallet.Constants.ACCOUNTLIST;
import static com.setecs.mobile.wallet.safe.wallet.Constants.ACCOUNTLISTFILENAME;
import static com.setecs.mobile.wallet.safe.wallet.Constants.CONFIGFILENAME;
import static com.setecs.mobile.wallet.safe.wallet.Constants.MOBILENO;

import java.util.ArrayList;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.setecs.mobile.safe.apps.shared.CustomExceptionHandler;
import com.setecs.mobile.safe.apps.shared.DisplaySAFEResponse;
import com.setecs.mobile.safe.apps.shared.SharedMethods;
import com.setecs.mobile.safe.apps.util.security.UUIDGenerator;
import com.setecs.mobile.walletDev.R;
import com.setecs.mobile.wallet.market.database.Constants;
import com.setecs.mobile.wallet.market.database.MyDB;


/**
 * This is the main Activity that displays the current chat session.
 */
public class BTPayWallet extends Activity implements Runnable {
	// Debugging
	private static final String TAG = "BTPayWallet";
	private static final boolean D = true;
	// Message types sent from the BluetoothChatService Handler
	public static final int MESSAGE_STATE_CHANGE = 1;
	public static final int MESSAGE_READ = 2;
	public static final int MESSAGE_WRITE = 3;
	public static final int MESSAGE_DEVICE_NAME = 4;
	public static final int MESSAGE_TOAST = 5;

	// Key names received from the BluetoothChatService Handler
	public static final String DEVICE_NAME = "device_name";
	public static final String TOAST = "toast";

	// Intent request codes
	private static final int REQUEST_CONNECT_DEVICE = 1;
	private static final int REQUEST_ENABLE_BT = 2;
	private static final int SAFEMSG = 0;
	protected static final int PAYMENT_RESPONSE = 1;
	protected static final int WALLET_MSG = 1;
	private static final int MEMSG = 2;
	private static final int RCTREQ_RESPONSE = 3;
	private static final int SERVERERROR = 4;

	// Layout Views
	private TextView mTitle;
	// String buffer for outgoing messages
	private StringBuffer mOutStringBuffer;
	// Local Bluetooth adapter
	private BluetoothAdapter mBluetoothAdapter = null;
	// Member object for the chat services
	private BTPayService mChatService = null;

	private String tipmsg;
	private String[] PoSmsgs;
	private String rcpntAccno;
	private String[] separated;
	private String paymentamntmsg = "";
	private Button paybtn;
	private TextView totalamnt;
	private String totalamntmsg;
	private String accountno;
	private int header;
	private String[] SAFEres;
	private String srcAccno;
	protected String message;
	private String destAccno;
	private String userMobNo;
	private static boolean connected = false;
	ArrayAdapter<String> spinnerAdapter;
	ArrayList<String> ids, amounts, list, list_original, accounts, discounts;
	private Spinner gift;
	private MyDB dba;
	private Cursor cursor;
	private int list_size;
	private int SpinnerInitializedCount = 0;
	private final int SpinnerCount = 1;
	private TextView paymentamntview;
	private EditText paymentamnttxt;
	private EditText tiptxt;
	private EditText rcpntaccounttxt;
	private TextView rcpntaccountview;
	public int position;
	public String choice = "";
	private String giftcard_amt_to_pay;
	private float giftcard_result;
	public boolean BTconnected = false;
	protected boolean saferesponse = false;
	private UUID mUUid;
	private String uuid;
	// Return Intent extra
	public static String EXTRA_DEVICE_ADDRESS = "device_address";
	private String[] MEres;
	private Thread thread;
	private Long id;
	private String alert_msg;
	protected boolean walletconnected = false;
	private String MeResponse = "";
	private String btEnabled = "";
	private boolean notFound = false;
	private String result;
	private Message handlermsg;
	private String resultmessage;
	private TextView msg_title;
	private TextView alert_message;
	private RelativeLayout Rlayout;
	private Button pos_button;
	private final SharedMethods cv = new SharedMethods();
	protected boolean connect = false;
	protected boolean unableConnect = false;
	private Context mContext;
	private final DisplaySAFEResponse ds = new DisplaySAFEResponse();
	private ArrayAdapter<CharSequence> adapter = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (D)
			Log.e(TAG, "+++ ON CREATE +++");
		Thread.setDefaultUncaughtExceptionHandler(new CustomExceptionHandler(BTPayWallet.this));

		this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		mContext = this.getApplicationContext();

		// Set up the window layout
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.otc_payment);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.custom_title);

		// Set up the custom title
		mTitle = (TextView) findViewById(R.id.title_left_text);
		mTitle.setText("m-Pay");
		mTitle = (TextView) findViewById(R.id.title_right_text);

		// Get local Bluetooth adapter
		mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

		// Set up the custom layout
		setUpLayout();
	}

	public void setUpLayout() {
		paybtn = (Button) findViewById(R.id.btn_pay);
		paybtn.setEnabled(false);
		paybtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				totalamntmsg = totalamnt.getText().toString();
				//				rcpntaccountview.setText(rcpntAccno);
				if (rcpntaccountview.getVisibility() == View.VISIBLE)
					rcpntAccno = rcpntaccountview.getText().toString();
				else
					rcpntAccno = rcpntaccounttxt.getText().toString();

				if (totalamntmsg.length() > 0 && srcAccno.length() > 0 && rcpntAccno.length() > 0) {
					if (choice.equals("coupon") || choice.equals("giftcard")) {
						promotionspPayment(v);
					}

					else {
						saferesponse = true;
						waitDialog();
					}
				}
				else
					ds.blankFieldsWarning(BTPayWallet.this);

			}
		});
		paybtn.requestFocus();
		totalamnt = (TextView) findViewById(R.id.totalamntmsg);
		paymentamntview = (TextView) findViewById(R.id.paymntamntmsg);
		paymentamnttxt = (EditText) findViewById(R.id.amounttxt);

		paymentamntmsg = paymentamntview.getText().toString();

		tiptxt = (EditText) findViewById(R.id.tiptxt);
		tipmsg = tiptxt.getText().toString();
		rcpntaccounttxt = (EditText) findViewById(R.id.rcpntaccounttxt);
		rcpntaccountview = (TextView) findViewById(R.id.rcpntaccnt);
		paymentamntview.addTextChangedListener(new TextWatcher() {

			private float y = 0;

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {

			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {

			}

			@Override
			public void afterTextChanged(Editable arg0) {

				if (tiptxt.getText().toString().length() == 0 && paymentamntview.getText().toString().length() == 0) {
					y = 0;
				}
				else if (paymentamntview.getText().toString().length() == 0) {
					tipmsg = tiptxt.getText().toString();
					y = Float.valueOf(tipmsg);

				}
				else if (tiptxt.getText().toString().length() == 0) {
					paymentamntmsg = paymentamntview.getText().toString();
					y = Float.valueOf(paymentamntmsg);

				}

				else {
					paymentamntmsg = paymentamntview.getText().toString();
					tipmsg = tiptxt.getText().toString();
					y = Float.valueOf(paymentamntmsg) + Float.valueOf(tipmsg);
				}
				totalamnt.setText(String.valueOf(y));

			}
		});

		paymentamnttxt.addTextChangedListener(new TextWatcher() {

			private float y = 0;

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
					y = Float.valueOf(tipmsg);
				}
				else if (tiptxt.getText().toString().length() == 0) {
					paymentamntmsg = paymentamnttxt.getText().toString();
					y = Float.valueOf(paymentamntmsg);
				}

				else {
					paymentamntmsg = paymentamnttxt.getText().toString();
					tipmsg = tiptxt.getText().toString();
					y = Float.valueOf(paymentamntmsg) + Float.valueOf(tipmsg);
				}

				totalamnt.setText(String.valueOf(y));
				paybtn.setEnabled(true);

			}
		});

		tiptxt.addTextChangedListener(new TextWatcher() {

			private float x = 0;

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {

			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {

			}

			@Override
			public void afterTextChanged(Editable arg0) {

				if (paymentamntview.getVisibility() == View.VISIBLE) {
					if (tiptxt.getText().toString().length() == 0 && paymentamntview.getText().toString().length() == 0) {
						x = 0;
					}
					else if (tiptxt.getText().toString().length() == 0)
						x = Float.valueOf(paymentamntview.getText().toString());
					else if (paymentamntview.getText().toString().length() == 0)
						x = Float.valueOf(tiptxt.getText().toString());
					else
						x = Float.valueOf(paymentamntview.getText().toString()) + Float.valueOf(tiptxt.getText()
								.toString());

					totalamnt.setText(String.valueOf(x));

				}
				else {
					if (tiptxt.getText().toString().length() == 0 && paymentamnttxt.getText().toString().length() == 0) {
						x = 0;
					}
					else if (tiptxt.getText().toString().length() == 0)
						x = Float.valueOf(paymentamnttxt.getText().toString());
					else if (paymentamnttxt.getText().toString().length() == 0)
						x = Float.valueOf(tiptxt.getText().toString());
					else
						x = Float.valueOf(paymentamnttxt.getText().toString()) + Float.valueOf(tiptxt.getText()
								.toString());

					totalamnt.setText(String.valueOf(x));
					paybtn.setEnabled(true);
				}
			}
		});

		spinnerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, fillData());
		spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		gift = (Spinner) findViewById(R.id.pay_with);
		gift.setAdapter(spinnerAdapter);
		gift.setOnItemSelectedListener(new MyOnItemSelectedListener());

		final Spinner spinner = (Spinner) findViewById(R.id.spinner);
		accountno = cv.fetchAccountList(BTPayWallet.this, ACCOUNTLIST, ACCOUNTLISTFILENAME);

		adapter = new ArrayAdapter<CharSequence>(this, android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		adapter.add(accountno);

		spinner.setAdapter(adapter);

		spinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				TextView temp = (TextView) arg1;
				srcAccno = temp.getText().toString();

			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {

			}
		});
	}

	protected void hideKeyboard() {

		InputMethodManager inputManager = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
		inputManager.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(),
				InputMethodManager.HIDE_NOT_ALWAYS);
	}

	private ArrayList<String> fillData() {
		dba = new MyDB(this);
		dba.open();
		list = new ArrayList<String>();
		list.add("SAFE Account");
		list.add("Credit Card");
		list.add("Bank Account");

		ids = new ArrayList<String>();
		amounts = new ArrayList<String>();
		accounts = new ArrayList<String>();
		discounts = new ArrayList<String>();

		// Get Coupons
		cursor = dba.getCoupons();
		startManagingCursor(cursor);

		cursor.moveToFirst();
		while (cursor.isAfterLast() == false) {
			if (cursor.getString(cursor.getColumnIndex(Constants.AMOUNT)).equals("null")) {
				Integer discount = (int) (Float.valueOf(cursor.getString(cursor.getColumnIndex(Constants.DISCOUNT))) * 100);
				list.add("m-Coupon: " + cursor.getString(cursor.getColumnIndex(Constants.DESCRIPTION))
						+ " - Discount: "
						+ String.valueOf(discount)
						+ "%");
			}
			else {
				list.add("m-Coupon: " + cursor.getString(cursor.getColumnIndex(Constants.DESCRIPTION))
						+ " - Amount: "
						+ cursor.getString(cursor.getColumnIndex(Constants.AMOUNT)));
			}
			ids.add(Long.toString(cursor.getLong(cursor.getColumnIndex(Constants.KEY_ID))));
			amounts.add(cursor.getString(cursor.getColumnIndex(Constants.AMOUNT)));
			discounts.add(cursor.getString(cursor.getColumnIndex(Constants.DISCOUNT)));
			accounts.add(cursor.getString(cursor.getColumnIndex(Constants.MERCHANT_ACCOUNT)));
			cursor.moveToNext();
		}
		cursor.close();

		// Get Giftcards
		cursor = dba.getGiftcards();
		startManagingCursor(cursor);

		cursor.moveToFirst();
		while (cursor.isAfterLast() == false) {
			list.add("m-Gift Card: " + cursor.getString(cursor.getColumnIndex(Constants.DESCRIPTION))
					+ " - Amount: "
					+ cursor.getString(cursor.getColumnIndex(Constants.AMOUNT)));
			ids.add(Long.toString(cursor.getLong(cursor.getColumnIndex(Constants.KEY_ID))));
			amounts.add(cursor.getString(cursor.getColumnIndex(Constants.AMOUNT)));
			discounts.add(cursor.getString(cursor.getColumnIndex(Constants.DISCOUNT)));
			accounts.add(cursor.getString(cursor.getColumnIndex(Constants.MERCHANT_ACCOUNT)));
			cursor.moveToNext();
		}
		cursor.close();

		// Get BankCards
		cursor = dba.getBankCards();
		startManagingCursor(cursor);

		cursor.moveToFirst();
		while (cursor.isAfterLast() == false) {
			list.add("Card: " + cursor.getString(cursor.getColumnIndex(Constants.CARD_BRAND))
					+ " - Number: "
					+ cursor.getString(cursor.getColumnIndex(Constants.CARD_NUMBER)));
			ids.add(Long.toString(cursor.getLong(cursor.getColumnIndex(Constants.KEY_ID))));
			cursor.moveToNext();
		}
		cursor.close();

		// Get BankAccounts
		cursor = dba.getBankAccounts();
		startManagingCursor(cursor);

		cursor.moveToFirst();
		while (cursor.isAfterLast() == false) {
			list.add("Account: " + cursor.getString(cursor.getColumnIndex(Constants.BANK_ACCOUNT_NUMBER))
					+ " - Balance: "
					+ cursor.getString(cursor.getColumnIndex(Constants.BANK_ACCOUNT_BALANCE)));
			ids.add(Long.toString(cursor.getLong(cursor.getColumnIndex(Constants.KEY_ID))));
			cursor.moveToNext();

		}
		cursor.close();

		list_size = list.size();
		return list;
	}

	public class MyOnItemSelectedListener implements OnItemSelectedListener {

		@Override
		public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
			if (SpinnerInitializedCount < SpinnerCount) { // prevent action when rendering
				SpinnerInitializedCount++;
				position = -1;
			}
			else if (list_size != 3) { // Dropdown contains giftcards and coupons
				if (pos > 2) {
					if (parent.getItemAtPosition(pos).toString().contains("m-Coupon")) { // This is a coupon
						choice = "coupon";
						if (amounts.get(pos - 3).equals("null")) {
							if (!paymentamntview.getText().toString().equals("")) {
								String amt = paymentamntview.getText().toString();
								Integer amtInt = Integer.parseInt(amt);
								//Integer discount= Integer.parseInt(discounts.get(pos-3));
								float discount = Float.valueOf(discounts.get(pos - 3).trim()).floatValue();
								float amt_to_pay = amtInt - (amtInt * discount);
								//Integer amt_to_pay = (int) (amtInt - (amtInt*discount));
								paymentamntview.setText(String.valueOf(amt_to_pay));
							}
							rcpntaccountview.setText(accounts.get(pos - 3));
						}
						else {
							paymentamntview.setText(amounts.get(pos - 3));
							rcpntaccountview.setText(accounts.get(pos - 3));
						}
					}
					else if (parent.getItemAtPosition(pos).toString().contains("Account")) { // This is an account
						accountno = parent
								.getItemAtPosition(pos)
								.toString()
								.substring(parent.getItemAtPosition(pos).toString().indexOf("t:") + 3,
										parent.getItemAtPosition(pos).toString().indexOf("-"));
						adapter.clear();
						adapter.add(accountno);

					}
					else if (parent.getItemAtPosition(pos).toString().contains("Card")) { // This is an account
						accountno = parent
								.getItemAtPosition(pos)
								.toString()
								.substring(parent.getItemAtPosition(pos).toString().indexOf("r:") + 3,
										parent.getItemAtPosition(pos).toString().length());
						adapter.clear();
						adapter.add(accountno);

					}
					else if ((parent.getItemAtPosition(pos).toString().contains("m-Gift Card"))) { // This is a giftcard
						choice = "giftcard";
						giftCalc(pos);
						rcpntaccountview.setText(accounts.get(pos - 3));
					}
					position = pos - 3;
					paybtn.setEnabled(true);
				}
				else { // This is not a token
					paymentamntview.setText("");
					position = -1;
					if (pos == 0) {
						accountno = cv.fetchAccountList(BTPayWallet.this, ACCOUNTLIST, ACCOUNTLISTFILENAME);

						adapter.clear();
						adapter.add(accountno);
						checkMode();
					}
					//		    }
				}
			}
			else {
				choice = "none";
				position = -1;
			}
			if (pos == 0)
				checkMode();
		}

		@Override
		public void onNothingSelected(AdapterView<?> parent) {
			// Do nothing.
		}
	}

	public void giftCalc(int pos) {

		if (!paymentamntview.getText().toString().equals("")) {
			giftcard_amt_to_pay = paymentamntview.getText().toString();
			float amount_to_pay = Float.valueOf(giftcard_amt_to_pay);
			float gift_amount = Float.valueOf(amounts.get(pos - 3).trim()).floatValue();
			giftcard_result = amount_to_pay - gift_amount;
			if (giftcard_result > 0) {
				paymentamntview.setText(String.valueOf(giftcard_result));
				//delete giftcard
			}
			else if (giftcard_result < 0) {
				paymentamntview.setText("0");
				//subtract the giftcard
			}
			else {
				paymentamntview.setText("0");
				// delete the giftcard
			}
		}
	}

	private class CheckConnectivity extends AsyncTask<Integer, Integer, Integer> {
		//This class definition states that DownloadImageTask will take String parameters, publish Integer progress updates, and return a Bitmap
		private final ProgressDialog cdialog = new ProgressDialog(BTPayWallet.this);;
		private int connectionResult;

		@Override
		protected void onPreExecute() {
			this.cdialog.setMessage("Checking connectivity...");
			this.cdialog.show();
		}

		@Override
		protected Integer doInBackground(Integer... params) {

			try {
				hideKeyboard();
				publishProgress(0);
				Thread.sleep(500);
				publishProgress(1);
				Thread.sleep(500);
				publishProgress(2);
				new Thread(new Runnable() {
					@Override
					public void run() {

						if (mBluetoothAdapter == null) {
							connectionResult = 0;
							publishProgress(3);
							return;
						}
						else {
							connectionResult = 1;
							publishProgress(4);
							checkBTEnabled();
							while (btEnabled.equals("")) {
							}
							if (btEnabled.equals("1")) {
								publishProgress(7);
								Intent serverIntent = new Intent(BTPayWallet.this, DeviceListActivityOriginal.class);
								startActivityForResult(serverIntent, REQUEST_CONNECT_DEVICE);
								while (true) {
									if (connect) {
										publishProgress(5);
										return;
									}
									if (unableConnect) {
										publishProgress(6);
										return;
									}
									if (notFound) {
										publishProgress(8);
										return;
									}

								}
							}
							else {
								publishProgress(6);

							}
						}
						return;

					}
				}).start();
			}
			catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				Log.e(TAG, "CheckConnectivity", e1);

			}

			return connectionResult;
		}

		@Override
		protected void onProgressUpdate(Integer... progress) {
			if (progress[0].equals(0))
				this.cdialog.setMessage("Checking NFC connectivity...");
			if (progress[0].equals(1))
				this.cdialog.setMessage("NFC is not available");
			if (progress[0].equals(2))
				this.cdialog.setMessage("Checking Bluetooth connectivity...");
			if (progress[0].equals(3)) {
				this.cdialog.setMessage("Bluetooth not available");
				cdialog.dismiss();
				OTAmode();

			}
			if (progress[0].equals(4))
				this.cdialog.setMessage("Bluetooth connectivity found");
			if (progress[0].equals(6)) {
				this.cdialog.setMessage("Unable to connect");
				cdialog.dismiss();
			}
			if (progress[0].equals(7)) {
				this.cdialog.setMessage("Discovering...");
			}
			if (progress[0].equals(8)) {
				this.cdialog.setMessage("No SAFE device was found...");
				cdialog.dismiss();

			}
			if (progress[0].equals(5)) {
				this.cdialog.setMessage("connected to BT device");
				BTconnected = true;
				if (cdialog.isShowing()) {
					if (BTconnected)
						cdialog.dismiss();
				}
			}

		}

		@Override
		protected void onPostExecute(final Integer success) {

		}
	}

	protected boolean checkBTEnabled() {

		// If BT is not on, request that it be enabled.
		// setupChat() will then be called during onActivityResult
		if (!mBluetoothAdapter.isEnabled()) {
			Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
			startActivityForResult(enableIntent, REQUEST_ENABLE_BT);
			// Otherwise, setup the chat session
		}
		else {
			btEnabled = "1";
			if (mChatService == null)
				setupChat();
			else if (mChatService.getState() == BTPayService.STATE_NONE) {
				// Start the Bluetooth chat services
				mChatService.start();
			}
		}
		return BTconnected;
	}

	public void checkMode() {

		if (!unableConnect)
			new CheckConnectivity().execute();
	}

	private void setupChat() {
		Log.d(TAG, "setupChat()");

		mUUid = generateUUID();
		// Initialize the BluetoothChatService to perform bluetooth connections
		mChatService = new BTPayService(this, mHandler, mUUid);

		// Initialize the buffer for outgoing messages
		mOutStringBuffer = new StringBuffer("");
	}

	private UUID generateUUID() {

		try {
			PackageInfo pkgInfo = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_SIGNATURES);
			android.content.pm.Signature[] sigs = pkgInfo.signatures;
			Log.d("APP_SIG", sigs.length + " signatures found");
			for (Signature sig : sigs) {
				Log.d("MyApp", "Signature hashcode : " + sig.hashCode());
				uuid = UUIDGenerator.getUUID(sig.hashCode());

			}
		}
		catch (PackageManager.NameNotFoundException e) {
			Log.d("APP_SIG", "Package " + getPackageName() + " not found");
		}
		return UUID.fromString(uuid);
	}

	@Override
	public synchronized void onPause() {
		super.onPause();
		if (D)
			Log.i(TAG, "- ON PAUSE -");
	}

	@Override
	public void onStop() {
		super.onStop();
		connect = false;

		if (D)
			Log.i(TAG, "-- ON STOP --");
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		// Stop the Bluetooth services
		if (mChatService != null)
			mChatService.stop();
		if (D)
			Log.e(TAG, "--- ON DESTROY ---");
	}

	@Override
	public void onBackPressed() {
		// do something on back.
		//onDestroy();
		finish();
		return;
	}

	private void ensureDiscoverable() {
		if (D)
			Log.d(TAG, "ensure discoverable");
		if (mBluetoothAdapter.getScanMode() != BluetoothAdapter.SCAN_MODE_CONNECTABLE_DISCOVERABLE) {
			Intent discoverableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
			discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
			startActivity(discoverableIntent);
		}
	}

	private void sendMessage(String message) {
		// Check that we're actually connected before trying anything
		if (mChatService.getState() != BTPayService.STATE_CONNECTED) {
			Toast.makeText(this, R.string.not_connected, Toast.LENGTH_SHORT).show();
			return;
		}

		// Check that there's actually something to send
		if (message.length() > 0) {
			// Get the message bytes and tell the BluetoothChatService to write
			byte[] send = message.getBytes();
			mChatService.write(send);

			// Reset out string buffer to zero and clear the edit text field
			mOutStringBuffer.setLength(0);
		}
	}

	// The Handler that gets information back from the BluetoothChatService
	private final Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case MESSAGE_STATE_CHANGE:
				if (D)
					Log.i(TAG, "MESSAGE_STATE_CHANGE: " + msg.arg1);
				switch (msg.arg1) {
				case BTPayService.STATE_CONNECTED:
					mTitle.setText(R.string.title_connected_to);
					//					mTitle.append(mConnectedDeviceName);
					mTitle.append("Merchant");
					connect = true;
					OTCmode();
					break;
				case BTPayService.STATE_CONNECTING:
					mTitle.setText(R.string.title_connecting);
					break;
				case BTPayService.STATE_LISTEN:
				case BTPayService.STATE_NONE:
					mTitle.setText(R.string.title_not_connected);
					if (connect)
						onDestroy();
					break;
				}
				break;
			case MESSAGE_WRITE:
				break;
			case MESSAGE_READ:
				byte[] readBuf = (byte[]) msg.obj;
				// construct a string from the valid bytes in the buffer
				String readMessage = new String(readBuf, 0, msg.arg1);
				if (readMessage.length() > 0)
					receiveMEResponse(readMessage);
				break;
			case MESSAGE_DEVICE_NAME:
				msg.getData().getString(DEVICE_NAME);
				Toast.makeText(getApplicationContext(), "Connected to " + "Merchant", Toast.LENGTH_SHORT).show();
				walletconnected = true;

				break;
			case MESSAGE_TOAST:
				Toast.makeText(getApplicationContext(), msg.getData().getString(TOAST), Toast.LENGTH_SHORT).show();
				if (msg.getData().getString(TOAST).contains("Unable")) {
					unableConnect = true;
					OTAmode();
				}

				break;
			}
		}
	};
	private TextView rcpntaccount;
	private TextView paymentamntlbl;

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (D)
			Log.d(TAG, "onActivityResult " + resultCode);
		switch (requestCode) {
		case REQUEST_CONNECT_DEVICE:
			// When DeviceListActivity returns with a device to connect
			if (resultCode == Activity.RESULT_OK) {
				// Get the device MAC address
				String address = data.getExtras().getString(DeviceListActivityOriginal.EXTRA_DEVICE_ADDRESS);
				// Get the BLuetoothDevice object
				BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(address);
				// Attempt to connect to the device
				mChatService.connect(device);
			}
			else {
				notFound = true;
				//				Toast.makeText(this, "No SAFE device was found", Toast.LENGTH_SHORT).show();
				OTAmode();
			}
			break;
		case REQUEST_ENABLE_BT:
			// When the request to enable Bluetooth returns
			if (resultCode == Activity.RESULT_OK) {
				// Bluetooth is now enabled, so set up a chat session
				btEnabled = "1";
				setupChat();
			}
			else {
				// User did not enable Bluetooth or an error occured
				btEnabled = "0";
				Log.d(TAG, "BT not enabled");
				Toast.makeText(this, "Bluetooth not enabled", Toast.LENGTH_SHORT).show();
				unableConnect = true;
				OTAmode();
				//                finish();
			}
		}
	}

	protected void OTCmode() {

		paymentamnttxt.setVisibility(View.GONE);
		paymentamntview.setVisibility(View.VISIBLE);
		rcpntaccounttxt.setVisibility(View.GONE);
		rcpntaccountview.setVisibility(View.VISIBLE);
	}

	protected void OTAmode() {

		rcpntaccount = (TextView) findViewById(R.id.rcpntaccntview);
		paymentamntlbl = (TextView) findViewById(R.id.paymntamntlbl);

		paymentamnttxt.setVisibility(View.VISIBLE);
		paymentamnttxt.requestFocus();
		paymentamntview.setVisibility(View.GONE);
		rcpntaccounttxt.setVisibility(View.VISIBLE);
		rcpntaccountview.setVisibility(View.GONE);
		rcpntaccount.setVisibility(View.GONE);
		paymentamntlbl.setVisibility(View.GONE);
		paybtn.setEnabled(true);

	}

	protected void receiveMEResponse(final String readMessage) {

		SAFEres = readMessage.split(":");
		header = Integer.parseInt(SAFEres[0]);
		switch (header) {
		case 0:
			rcvPaymentinfo(readMessage);
			break;
		default:
			MeResponse = readMessage;
			handlermsg = new Message();
			handlermsg.what = MEMSG;
			handlermsg.arg1 = RCTREQ_RESPONSE;
			handler.sendMessage(handlermsg);
			break;
		}

	}

	private void displaySafeResponse(final String readMessage) {

		setContentView(R.layout.system_msg);
		msg_title = (TextView) findViewById(R.id.title);

		MEres = readMessage.split(":");
		header = Integer.parseInt(MEres[0]);

		switch (header) {
		case 1:
			msg_title.setText("SAFE Payment System");

			resultmessage = readMessage.substring(3);

			if (readMessage.contains("Money transferred:") && readMessage.contains("Receiver")
					&& readMessage.contains("Balance:")) {
				resultmessage = "Transaction successful!\n\n" + readMessage.substring(readMessage.indexOf("Money"),
						readMessage.indexOf("Receiver") - 1)
						+ "\n"
						+ readMessage.substring(readMessage.indexOf("Receiver"), readMessage.indexOf("Balance") - 1)
						+ "\n\n"
						+ "Balance: "
						+ readMessage.substring(readMessage.indexOf("Balance") + 11);
			}
			alert_message = (TextView) findViewById(R.id.message);
			alert_message.setText(resultmessage);
			alert_message.setVisibility(View.VISIBLE);
			Rlayout = (RelativeLayout) findViewById(R.id.SingleLayout);
			Rlayout.setVisibility(View.VISIBLE);
			pos_button = (Button) findViewById(R.id.btnSingle);
			pos_button.setText("	Ok	");
			pos_button.setVisibility(View.VISIBLE);
			pos_button.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					if (choice.equals("coupon") || choice.equals("giftcard")) {
						if (!result.contains("ERROR") && (position != -1)) {
							if (choice.equals("coupon")) { // Coupon was used
								id = Long.valueOf(ids.get(position));
								dba.deleteCoupon(id);
								dba.close();
							}
							else if (choice.equals("giftcard")) { // Giftcard was used
								if (giftcard_result > 0) {
									//delete giftcard
									id = Long.valueOf(ids.get(position));
									dba.deleteGiftcard(id);
									dba.close();
								}
								else if (giftcard_result < 0) {
									//subtract the giftcard
								}
								else {
									// delete the giftcard
								}
							}
						}

						finish();
					}

					else {
						if (readMessage.contains("Invalid account"))
							if (walletconnected)
								sendMessage("7: ");
							else {
								finish();
							}
						else if (walletconnected) {
							saferesponse = false;
							waitDialog();
							sendMessage("1: ");
						}
						else {
							finish();
						}

					}
				}
			});
			break;
		//transaction success
		case 3:

			//			msg_title.setText("SAFE Payment System");
			//			alert_message = (TextView) findViewById(R.id.message);
			//			alert_message.setText("Do you want the receipt?");
			//			alert_message.setVisibility(View.VISIBLE);
			//			//			alert.setMessage("Do you want the receipt?");
			//			Llayout = (LinearLayout) findViewById(R.id.MltiLayout);
			//			Llayout.setVisibility(View.VISIBLE);
			//			pos_button = (Button) findViewById(R.id.pos_btn);
			//			pos_button.setText("	Yes	");
			//			pos_button.setVisibility(View.VISIBLE);
			//			pos_button.setOnClickListener(new OnClickListener() {
			//				@Override
			//				public void onClick(View v) {
			//					waitDialog();
			//					sendMessage("4: ");
			//				}
			//			});
			//			neg_button = (Button) findViewById(R.id.neg_btnn);
			//			neg_button.setText("	No	");
			//			neg_button.setVisibility(View.VISIBLE);
			//			neg_button.setOnClickListener(new OnClickListener() {
			//				@Override
			//				public void onClick(View v) {
			//					sendMessage("5: ");
			//					onDestroy();
			//					return;
			//				}
			//			});

			break;
		//transaction failure
		case 6:
			setContentView(R.layout.system_msg);
			msg_title = (TextView) findViewById(R.id.title);
			msg_title.setText("Transaction Receipt");
			//			alert.setTitle("Transaction Receipt");
			alert_message = (TextView) findViewById(R.id.message);
			alert_message.setText("Receipt recieved");
			alert_message.setVisibility(View.VISIBLE);
			//			alert.setMessage("Your Receipt");
			Rlayout = (RelativeLayout) findViewById(R.id.SingleLayout);
			Rlayout.setVisibility(View.VISIBLE);
			pos_button = (Button) findViewById(R.id.btnSingle);
			pos_button.setText("	Ok	");
			pos_button.setVisibility(View.VISIBLE);
			pos_button.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					sendMessage("6: ");
					//onDestroy();
					finish();
					return;
				}
			});

			break;
		case 7:

			msg_title = (TextView) findViewById(R.id.title);
			msg_title.setText("Transaction Result");
			alert_message = (TextView) findViewById(R.id.message);
			alert_message.setText("Server not responding");
			alert_message.setVisibility(View.VISIBLE);
			Rlayout = (RelativeLayout) findViewById(R.id.SingleLayout);
			Rlayout.setVisibility(View.VISIBLE);
			pos_button = (Button) findViewById(R.id.btnSingle);
			pos_button.setText("	Ok	");
			pos_button.setVisibility(View.VISIBLE);
			pos_button.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					finish();
					return;
				}
			});

			break;
		case 8:
			msg_title = (TextView) findViewById(R.id.title);
			msg_title.setText("Transaction Result");
			//			alert.setTitle("Transaction Result");

			alert_message = (TextView) findViewById(R.id.message);
			alert_message.setText("Server not responding");
			alert_message.setVisibility(View.VISIBLE);
			//			alert.setMessage("Server not responding");
			Rlayout = (RelativeLayout) findViewById(R.id.SingleLayout);
			Rlayout.setVisibility(View.VISIBLE);
			pos_button = (Button) findViewById(R.id.btnSingle);
			pos_button.setText("	Ok	");
			pos_button.setVisibility(View.VISIBLE);
			pos_button.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					finish();
					return;
				}
			});

			break;

		}

	}

	private void rcvPaymentinfo(final String readMessage) {

		Toast.makeText(getApplicationContext(), "Payment information is recieved", Toast.LENGTH_SHORT).show();
		PoSmsgs = readMessage.split(":");
		paymentamntmsg = PoSmsgs[1];
		rcpntAccno = PoSmsgs[2];
		paymentamntview.setText(paymentamntmsg);
		rcpntaccountview.setText(rcpntAccno);
		totalamntmsg = totalamnt.getText().toString();
		paybtn.setEnabled(true);

	}

	protected void promotionspPayment(View v) {

		Integer buttonId = ((Button) v).getId();
		rcpntaccountview.getText().toString();
		if (buttonId == R.id.btn_pay) {

			if (totalamnt.getText().equals("0.0") && choice.equals("giftcard")) { // Check if amount is zero
				if (giftcard_result < 0) {
					//subtract the giftcard
					id = Long.valueOf(ids.get(position));
					dba.editGiftcard(id, String.valueOf(Math.abs(giftcard_result)));
					alert_msg = "m-Gift Card has been used\nRemaining balance: " + String.valueOf(Math
							.abs(giftcard_result)) + " SEK";
					dba.close();
				}
				else {
					// delete the giftcard
					id = Long.valueOf(ids.get(position));
					dba.deleteGiftcard(id);
					alert_msg = "m-Gift Card has been used and deleted from wallet";
					dba.close();
				}
				AlertDialog.Builder alert = new AlertDialog.Builder(BTPayWallet.this);
				alert.setTitle("Response");
				alert.setMessage(alert_msg);
				alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int whichButton) {
						finish();
					}
				});
				alert.show();
			}
			else {
				waitDialog();
			}

		}
		else
			ds.blankFieldsWarning(BTPayWallet.this);
	}

	protected void waitDialog() {

		cv.progressStart(BTPayWallet.this, "Please wait...");
		if (saferesponse) {
			thread = new Thread(this);
			thread.start();
		}
	}

	@Override
	public void run() {
		message = totalamntmsg + ":" + srcAccno + ":" + rcpntAccno;
		sendToSafe(message);
	}

	private final Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case SAFEMSG:
				switch (msg.arg1) {
				case PAYMENT_RESPONSE:
					cv.progressStop();
					if (result.contains("Unknown"))
						displaySafeResponse("8:" + result);
					else if (result.contains("responding"))
						displaySafeResponse("7:" + result);
					else
						displaySafeResponse("1:" + result);
					break;
				}
				break;
			case MEMSG:
				switch (msg.arg1) {
				case RCTREQ_RESPONSE:
					cv.progressStop();
					displaySafeResponse(MeResponse);
					break;
				}
				break;
			case SERVERERROR:
				Toast.makeText(BTPayWallet.this, "Server not responding", Toast.LENGTH_LONG).show();
				break;

			}

		}
	};

	protected void sendToSafe(String readMessage) {

		separated = readMessage.split(":");
		srcAccno = separated[1];
		paymentamntmsg = separated[0];
		destAccno = separated[2];
		userMobNo = cv.readConfigurationFile(BTPayWallet.this, MOBILENO, CONFIGFILENAME);
		if (userMobNo.length() > 0) {
			SharedMethods.serverIpAddress = cv.readConfigurationFile(BTPayWallet.this, SAFEIPNO, CONFIGFILENAME);
			if (!SharedMethods.serverIpAddress.equals("")) {

				FutureTask<String> task = new FutureTask<String>(new SendToSAFEThread(connected, paymentamntmsg,
						srcAccno, destAccno, userMobNo, SharedMethods.serverIpAddress));
				ExecutorService es = Executors.newSingleThreadExecutor();
				es.submit(task);
				try {
					result = task.get();
					if (result != null && result.length() > 0) {
						handlermsg = new Message();
						handlermsg.what = SAFEMSG;
						handlermsg.arg1 = PAYMENT_RESPONSE;
						handler.sendMessage(handlermsg);
					}

				}
				catch (Exception e) {
					Log.e(TAG, "sendToSafe", e);
				}
				es.shutdown();

			}

		}
	}

	protected void alert() {

		new AlertDialog.Builder(this).setTitle("Alert").setMessage("Wrong PIN!").setIcon(R.drawable.warningicon32x32)
				.setNeutralButton("Close", null).show();

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.option_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.scan:
			// Launch the DeviceListActivity to see devices and do scan
			Intent serverIntent = new Intent(this, DeviceListActivityOriginal.class);
			startActivityForResult(serverIntent, REQUEST_CONNECT_DEVICE);
			return true;
		case R.id.discoverable:
			// Ensure this device is discoverable by others
			ensureDiscoverable();
			return true;
		}
		return false;
	}

	/*@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if (mBluetoothAdapter != null)
				if (mBluetoothAdapter.isEnabled())
					mBluetoothAdapter.disable();

			finish();

		}

		return true;
	}*/

}