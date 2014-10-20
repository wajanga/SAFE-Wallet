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


public class DepositCash extends Activity {

	private static final int FETCHLIST = 0;
	private static final int SAFEMSG = 1;
	protected static final int EMPTYFIELDS = 2;
	protected static final int NOMOBILENO = 3;
	private static final String TAG = "DepositCash";
	private static final int NOFETCHLIST = 4;
	private static final int SERVERERROR = 5;
	private String accountno = "";
	private Message handlermsg;
	private ArrayAdapter<CharSequence> adapter;
	private Spinner spinner;
	protected SharedMethods cv = new SharedMethods();
	private String userMobNo = "";
	private String result = "";
	private final DisplaySAFEResponse ds = new DisplaySAFEResponse();
	protected boolean sndDepCash = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		Thread.setDefaultUncaughtExceptionHandler(new CustomExceptionHandler(DepositCash.this));

		setContentView(R.layout.deposit_cash);
		((Button) findViewById(R.id.btn_deposit)).setOnClickListener(dpCashButtonListener);

		spinner = (Spinner) findViewById(R.id.depspinner);
		adapter = new ArrayAdapter<CharSequence>(this, android.R.layout.simple_spinner_item);
		fetchAccountList();
	}

	private void waitDialog() {
		// TODO Auto-generated method stub
		cv.progressStart(DepositCash.this, "Please wait...");
		//		if (SharedMethods.socket == null || !SharedMethods.socket.isConnected()) {
		//			if (!cv.createSession()) {
		//				cv.progressStop();
		//				handlermsg = new Message();
		//				handlermsg.what = SERVERERROR;
		//				handler.sendMessage(handlermsg);
		//			}
		//			else {
		//				depositCashThread();
		//			}
		//		}
		//		else {
		//			depositCashThread();
		//		}
		depositCashThread();

	}

	private void depositCashThread() {
		// TODO Auto-generated method stub
		if (sndDepCash) {
			Thread t = new Thread(new Runnable() {
				@Override
				public void run() {
					// TODO Auto-generated method stub
					sendDepositCash();
				}
			});
			t.start(); // Start it running
		}

	}

	private void fetchAccountList() {
		// TODO Auto-generated method stub

		accountno = cv.fetchAccountList(DepositCash.this, ACCOUNTLIST, ACCOUNTLISTFILENAME);
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

			case NOFETCHLIST:
				//cv.progressStop();
				displaySafeRespons("");
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
				Toast.makeText(DepositCash.this, "Server not responding", Toast.LENGTH_LONG).show();
				break;
			}

		}
	};

	protected OnClickListener dpCashButtonListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			Integer buttonId = ((Button) v).getId();

			if (buttonId == R.id.btn_deposit) {
				sndDepCash = true;
				waitDialog();
			}

		}
	};

	protected void sendDepositCash() {
		//		// TODO Auto-generated method stub
		//		if (SharedMethods.connected) {
		EditText dpamountxt = (EditText) findViewById(R.id.dpAmount);
		EditText agMobNotxt = (EditText) findViewById(R.id.agMobNo);
		String dpamountmsg = dpamountxt.getText().toString();
		String agMobNomsg = agMobNotxt.getText().toString();

		if (dpamountmsg.length() > 0 && agMobNomsg.length() > 0) {
			userMobNo = cv.readConfigurationFile(DepositCash.this, MOBILENO, CONFIGFILENAME);
			if (userMobNo.length() > 0) {
				SharedMethods.serverIpAddress = cv.readConfigurationFile(DepositCash.this, SAFEIPNO, CONFIGFILENAME);
				if (!SharedMethods.serverIpAddress.equals("")) {
					FutureTask<String> task = new FutureTask<String>(new SendDepositCashThread(SharedMethods.connected,
							dpamountmsg, agMobNomsg, SharedMethods.serverIpAddress, userMobNo));
					ExecutorService es = Executors.newSingleThreadExecutor();
					es.submit(task);
					try {
						result = task.get();
						handlermsg = new Message();
						handlermsg.what = SAFEMSG;
						handler.sendMessage(handlermsg);
					}
					catch (Exception e) {
						Log.e(TAG, "sendDepositCash", e);
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

	protected void noMobileNoWarning() {
		ds.noMobileNoWarning(DepositCash.this);

	}

	protected void blankFieldsWarning() {
		ds.blankFieldsWarning(DepositCash.this);

	}

	private void warnmsg(int error) {
		// TODO Auto-generated method stub
		handlermsg = new Message();
		handlermsg.what = error;
		handler.sendMessage(handlermsg);

	}

	private void displaySafeRespons(String result) {
		// TODO Auto-generated method stub
		ds.displaySAFErespons(DepositCash.this, result, "Deposit Cash");

	}

} // end class

