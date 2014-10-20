package com.setecs.mobile.wallet.safe.accounts;

import static com.setecs.mobile.wallet.safe.wallet.Constants.ACCOUNTLIST;
import static com.setecs.mobile.wallet.safe.wallet.Constants.ACCOUNTLISTFILENAME;

import java.util.HashMap;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import com.setecs.mobile.safe.apps.shared.CustomExceptionHandler;
import com.setecs.mobile.safe.apps.shared.DisplaySAFEResponse;
import com.setecs.mobile.safe.apps.shared.SharedMethods;


public class ListSafeAccounts extends Activity {

	private static final int FETCHLIST = 0;
	private static final int NOFETCHLIST = 1;
	protected static final int SERVERERROR = 2;
	private String accountlist = "";
	HashMap<String, String> hm = new HashMap<String, String>();
	private Message handlermsg;
	protected SharedMethods cv = new SharedMethods();
	protected DisplaySAFEResponse ds = new DisplaySAFEResponse();

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		Thread.setDefaultUncaughtExceptionHandler(new CustomExceptionHandler(ListSafeAccounts.this));

		waitDialog();

	}

	private void waitDialog() {
		
		cv.progressStart(ListSafeAccounts.this, "Please wait...");
		Thread t = new Thread(new Runnable() {
			@Override
			public void run() {
				//				
				//				if (SharedMethods.socket == null || !SharedMethods.socket.isConnected()) {
				//					if (!cv.createSession()) {
				//						cv.progressStop();
				//						handlermsg = new Message();
				//						handlermsg.what = SERVERERROR;
				//						handler.sendMessage(handlermsg);
				//
				//					}
				//					else {
				//						fetchAccountList();
				//
				//					}
				//				}
				//				else {
				//					fetchAccountList();
				//
				//				}
				fetchAccountList();

			}
		});
		t.start(); // Start it running
	}

	private void fetchAccountList() {
		
		accountlist = cv.fetchAccountList(ListSafeAccounts.this, ACCOUNTLIST, ACCOUNTLISTFILENAME);
		if (accountlist.length() > 0) {
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
				cv.progressStop();
				ds.displaySAFErespons(ListSafeAccounts.this, "SAFE Accounts:\n\n" + accountlist, "List SAFE Accounts");

				break;
			case SERVERERROR:
				Toast.makeText(ListSafeAccounts.this, "Server not responding", Toast.LENGTH_LONG).show();
				break;

			}

		}
	};

}
