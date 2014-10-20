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


public class TransferMoney extends Activity {

	private static final int FETCHLIST = 0;
	protected static final int SAFEMSG = 1;
	private static final String TAG = "TransferMoney";
	private static final int NOFETCHLIST = 4;
	protected static final int EMPTYFIELDS = 3;
	protected static final int NOMOBILENO = 5;
	private static final int SERVERERROR = 2;
	private String accountno = "";
	private Spinner spinner;
	private ArrayAdapter<CharSequence> adapter;
	private boolean trans_money = false;
	private Message handlermsg;
	protected String result = "";
	protected SharedMethods cv = new SharedMethods();
	private String userMobNo = "";
	private final DisplaySAFEResponse ds = new DisplaySAFEResponse();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		Thread.setDefaultUncaughtExceptionHandler(new CustomExceptionHandler(TransferMoney.this));

		setContentView(R.layout.transfer_money);
		((Button) findViewById(R.id.btn_transfer)).setOnClickListener(transferButtonListener);

		spinner = (Spinner) findViewById(R.id.trspinner);
		adapter = new ArrayAdapter<CharSequence>(this, android.R.layout.simple_spinner_item);
		fetchAccountList();

	}

	private void waitDialog() {
		// TODO Auto-generated method stub
		cv.progressStart(TransferMoney.this, "Please wait...");
		//		if (SharedMethods.socket == null || !SharedMethods.socket.isConnected()) {
		//			if (!cv.createSession()) {
		//				cv.progressStop();
		//				handlermsg = new Message();
		//				handlermsg.what = SERVERERROR;
		//				handler.sendMessage(handlermsg);
		//			}
		//			else {
		//				transMoneyThread();
		//			}
		//		}
		//		else {
		//			transMoneyThread();
		//		}
		transMoneyThread();

	}

	private void transMoneyThread() {
		// TODO Auto-generated method stub
		if (trans_money) {
			Thread t = new Thread(new Runnable() {
				@Override
				public void run() {
					// TODO Auto-generated method stub
					transferMoney();
				}
			});
			t.start(); // Start it running
		}

	}

	private void fetchAccountList() {
		// TODO Auto-generated method stub

		accountno = cv.fetchAccountList(TransferMoney.this, ACCOUNTLIST, ACCOUNTLISTFILENAME);
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
				Toast.makeText(TransferMoney.this, "Server not responding", Toast.LENGTH_LONG).show();
				break;
			}

		}
	};

	protected void noMobileNoWarning() {
		// TODO Auto-generated method stub
		ds.noMobileNoWarning(TransferMoney.this);

	}

	protected void blankFieldsWarning() {
		ds.blankFieldsWarning(TransferMoney.this);

	}

	protected OnClickListener transferButtonListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			Integer buttonId = ((Button) v).getId();

			if (buttonId == R.id.btn_transfer) {
				trans_money = true;
				waitDialog();
			}

		}
	};

	protected void transferMoney() {
		//		// TODO Auto-generated method stub
		//		if (SharedMethods.connected) {
		EditText transamountxt = (EditText) findViewById(R.id.transAmount);
		EditText rcvAccNo = (EditText) findViewById(R.id.rcvAccNo);
		String transamounmsg = transamountxt.getText().toString();
		String rcvAccNomsg = rcvAccNo.getText().toString();

		if (transamounmsg.length() > 0 && rcvAccNomsg.length() > 0) {

			userMobNo = cv.readConfigurationFile(TransferMoney.this, MOBILENO, CONFIGFILENAME);
			if (userMobNo.length() > 0) {
				SharedMethods.serverIpAddress = cv.readConfigurationFile(TransferMoney.this, SAFEIPNO, CONFIGFILENAME);

				if (!SharedMethods.serverIpAddress.equals("")) {

					FutureTask<String> task = new FutureTask<String>(new TransferMoneyThread(SharedMethods.connected,
							transamounmsg, rcvAccNomsg, SharedMethods.serverIpAddress, userMobNo));
					ExecutorService es = Executors.newSingleThreadExecutor();
					es.submit(task);
					try {
						result = task.get();
						handlermsg = new Message();
						handlermsg.what = SAFEMSG;
						handler.sendMessage(handlermsg);
					}
					catch (Exception e) {
						Log.e(TAG, "transferMoney", e);
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
		ds.displaySAFErespons(TransferMoney.this, result, "Transfer Money");

	}

} // end class