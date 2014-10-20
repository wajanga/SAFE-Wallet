package com.setecs.mobile.wallet.mbanking;

import static com.setecs.mobile.wallet.safe.wallet.Constants.ACCOUNTLIST;
import static com.setecs.mobile.wallet.safe.wallet.Constants.ACCOUNTLISTFILENAME;
import static com.setecs.mobile.wallet.safe.wallet.Constants.CONFIGFILENAME;
import static com.setecs.mobile.wallet.safe.wallet.Constants.MOBILENO;

import java.util.ArrayList;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.widget.Toast;

import com.setecs.mobile.safe.apps.shared.SharedMethods;
import com.setecs.mobile.walletDev.R;
import com.setecs.mobile.wallet.market.utility.SafeReplyHandler;
import com.setecs.mobile.wallet.market.utility.WalletActivity;
import com.setecs.mobile.wallet.net.SocketAsyncTask3;


public class AccountStatusActivity extends WalletActivity implements SafeReplyHandler {

	private String userMobNo, account;
	private boolean result = true;
	private final SharedMethods cv = new SharedMethods();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		setContentView(R.layout.system_msg);

		checkStatus();
		if (!result)
			displayResponseAndCloseActivity(this, "Bank Account Status", "No bank account is available");
	}

	public void checkStatus() {
		userMobNo = cv.readConfigurationFile(this, MOBILENO, CONFIGFILENAME);
		account = cv.fetchAccounts(this, ACCOUNTLIST, ACCOUNTLISTFILENAME)[1];

		if (userMobNo.equals("")) {
			Toast.makeText(this, "Could not read the user's mobile number", Toast.LENGTH_SHORT).show();
			return;
		}

		if (account == null) {
			//Toast.makeText(this, "No bank account is available", Toast.LENGTH_SHORT).show();
			result = false;
			return;
		}

		String balMsg = "(" + userMobNo + ";" + "as " + account + ")";
		BankAccBalanceParser parser = new BankAccBalanceParser();
		new SocketAsyncTask3(this, parser, this).execute(balMsg);
	}

	@Override
	public void displayList(ArrayList<?> object) {
		// do nothing
	}

	@Override
	public void displayMessage(String message) {
		displayResponseAndCloseActivity(this, "Bank Account Status", message);
	}

}
