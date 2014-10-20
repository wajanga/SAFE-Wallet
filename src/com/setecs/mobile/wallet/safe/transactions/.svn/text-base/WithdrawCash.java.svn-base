package com.setecs.mobile.wallet.safe.transactions;

import static com.setecs.mobile.safe.apps.shared.Constants.SAFEIPNO;
import static com.setecs.mobile.wallet.safe.wallet.Constants.ACCOUNTLIST;
import static com.setecs.mobile.wallet.safe.wallet.Constants.ACCOUNTLISTFILENAME;
import static com.setecs.mobile.wallet.safe.wallet.Constants.CONFIGFILENAME;
import static com.setecs.mobile.wallet.safe.wallet.Constants.MOBILENO;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.setecs.mobile.safe.apps.shared.CustomExceptionHandler;
import com.setecs.mobile.safe.apps.shared.DisplaySAFEResponse;
import com.setecs.mobile.safe.apps.shared.SharedMethods;
import com.setecs.mobile.walletDev.R;


public class WithdrawCash extends Activity {

	protected static final int FETCHLIST = 0;
	protected static final int SAFEMSG = 1;
	private static final String TAG = "WithdrawCash";
	private static final int NOFETCHLIST = 3;
	protected static final int EMPTYFIELDS = 4;
	protected static final int NOMOBILENO = 5;
	private static final int SERVERERROR = 2;
	private String accountno;
	protected boolean sndWthCash = false;
	private ArrayAdapter<CharSequence> adapter;
	private Spinner spinner;
	protected String result;
	private Message handlermsg;
	private String userMobNo;
	protected SharedMethods cv = new SharedMethods();
	private final DisplaySAFEResponse ds = new DisplaySAFEResponse();

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		setContentView(R.layout.withdraw_cash);
		Thread.setDefaultUncaughtExceptionHandler(new CustomExceptionHandler(WithdrawCash.this));

		((Button) findViewById(R.id.btn_withdraw)).setOnClickListener(wtCashButtonListener);
		spinner = (Spinner) findViewById(R.id.withspinner);
		adapter = new ArrayAdapter<CharSequence>(this, android.R.layout.simple_spinner_item);
		fetchAccountList();

	}

	private void waitDialog() {
		cv.progressStart(WithdrawCash.this, "Please wait...");

		//		if (SharedMethods.socket == null || !SharedMethods.socket.isConnected()) {
		//			if (!cv.createSession()) {
		//				cv.progressStop();
		//				handlermsg = new Message();
		//				handlermsg.what = SERVERERROR;
		//				handler.sendMessage(handlermsg);
		//			}
		//			else {
		//				withdrawCashThread();
		//			}
		//		}
		//		else {
		//			withdrawCashThread();
		//		}
		withdrawCashThread();

	}

	private void withdrawCashThread() {
		// TODO Auto-generated method stub
		if (sndWthCash) {
			Thread t = new Thread(new Runnable() {
				@Override
				public void run() {
					// TODO Auto-generated method stub

					sendWithdrawCash();
				}

			});
			t.start(); // Start it running
		}

	}

	private void fetchAccountList() {
		// TODO Auto-generated method stub

		accountno = cv.fetchAccountList(WithdrawCash.this, ACCOUNTLIST, ACCOUNTLISTFILENAME);
		if (accountno.length() > 0) {
			handlermsg = new Message();
			handlermsg.what = FETCHLIST;
			handler.sendMessage(handlermsg);
		}
		else {
			handlermsg = new Message();
			handlermsg.what = NOFETCHLIST;
			handler.sendMessage(handlermsg);
		}
	}

	private final Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {

			switch (msg.what) {
			case FETCHLIST:
				//cv.progressStop();
				adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				adapter.add(accountno);

				spinner.setAdapter(adapter);

				spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
					@Override
					public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

					}

					@Override
					public void onNothingSelected(AdapterView<?> arg0) {
						// TODO Auto-generated method stub

					}

				});
				break;
			case SAFEMSG:
				cv.progressStop();
				displaySafeRespons(result);

				break;

			case EMPTYFIELDS:
				cv.progressStop();
				blankFieldsWarning();
				break;

			case NOMOBILENO:
				cv.progressStop();
				noMobileNoWarning();
				break;
			case SERVERERROR:
				Toast.makeText(WithdrawCash.this, "Server not responding", Toast.LENGTH_LONG).show();
				break;
			}

		}
	};

	protected void noMobileNoWarning() {
		ds.noMobileNoWarning(WithdrawCash.this);

	}

	protected void blankFieldsWarning() {
		ds.blankFieldsWarning(WithdrawCash.this);

	}

	protected OnClickListener wtCashButtonListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			Integer buttonId = ((Button) v).getId();

			if (buttonId == R.id.btn_withdraw) {
				sndWthCash = true;
				waitDialog();
			}

		}

	};

	protected void sendWithdrawCash() {
		// TODO Auto-generated method stub
		//		if (SharedMethods.connected) {
		EditText wdpamountxt = (EditText) findViewById(R.id.wdpAmount);
		EditText wagMobNotxt = (EditText) findViewById(R.id.wagMobNo);
		String wdpamountmsg = wdpamountxt.getText().toString();
		String wagMobNomsg = wagMobNotxt.getText().toString();

		if (wdpamountmsg.length() > 0 && wagMobNomsg.length() > 0) {

			userMobNo = cv.readConfigurationFile(WithdrawCash.this, MOBILENO, CONFIGFILENAME);
			if (userMobNo.length() > 0) {
				SharedMethods.serverIpAddress = cv.readConfigurationFile(WithdrawCash.this, SAFEIPNO, CONFIGFILENAME);
				if (!SharedMethods.serverIpAddress.equals("")) {
					FutureTask<String> task = new FutureTask<String>(new SendWithdrawCashThread(
							SharedMethods.connected, wdpamountmsg, wagMobNomsg, SharedMethods.serverIpAddress,
							userMobNo));
					ExecutorService es = Executors.newSingleThreadExecutor();
					es.submit(task);
					try {
						result = task.get();
						handlermsg = new Message();
						handlermsg.what = SAFEMSG;
						handler.sendMessage(handlermsg);
					}
					catch (Exception e) {
						Log.e(TAG, "sendWithdrawCash", e);
					}
					es.shutdown();

				}

			}
			else {
				warnmsg(NOMOBILENO);
			}
		}
		else {
			warnmsg(EMPTYFIELDS);
		}

		//		}
	}

	private void warnmsg(int error) {
		// TODO Auto-generated method stub
		handlermsg = new Message();
		handlermsg.what = error;
		handler.sendMessage(handlermsg);
	}

	private void displaySafeRespons(String result) {
		// TODO Auto-generated method stub
		ds.displaySAFErespons(WithdrawCash.this, result, "Withdraw Cash");

	}

} // end class