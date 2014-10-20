package com.setecs.mobile.wallet.safe.accounts;

import static com.setecs.mobile.wallet.safe.wallet.Constants.CONFIGFILENAME;
import static com.setecs.mobile.wallet.safe.wallet.Constants.MOBILENO;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.setecs.mobile.safe.apps.shared.SharedMethods;
import com.setecs.mobile.walletDev.R;


public class CreateNewSafeAccounts extends Activity {

	private static final String TAG = "CreateNewSafeAccounts";
	private TextView create_account_result;
	private String result;
	private String userMobNo;
	SharedMethods sm = new SharedMethods();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

		setContentView(R.layout.create_account);
		create_account_result = (TextView) findViewById(R.id.textView2);
		create_account_result.setText(createAccount());

	}

	private String createAccount() {
		
		if (!SharedMethods.connected) {

			userMobNo = sm.readConfigurationFile(CreateNewSafeAccounts.this, MOBILENO, CONFIGFILENAME);

			if (!SharedMethods.serverIpAddress.equals("")) {
				FutureTask<String> task = new FutureTask<String>(new FetchAccountListThread(userMobNo,
						SharedMethods.serverIpAddress));
				ExecutorService es = Executors.newSingleThreadExecutor();
				es.submit(task);
				try {
					result = task.get();
				}
				catch (Exception e) {
					Log.e(TAG, "createAccount", e);
				}
				es.shutdown();

			}

		}
		return result;
	}

}
