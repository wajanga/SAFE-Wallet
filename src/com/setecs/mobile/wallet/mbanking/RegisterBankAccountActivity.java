package com.setecs.mobile.wallet.mbanking;

import static com.setecs.mobile.wallet.safe.wallet.Constants.CONFIGFILENAME;
import static com.setecs.mobile.wallet.safe.wallet.Constants.MOBILENO;

import java.util.ArrayList;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.setecs.mobile.safe.apps.shared.SharedMethods;
import com.setecs.mobile.walletDev.R;
import com.setecs.mobile.wallet.market.database.Constants;
import com.setecs.mobile.wallet.market.provider.BankAccountProvider;
import com.setecs.mobile.wallet.market.utility.SafeReplyHandler;
import com.setecs.mobile.wallet.market.utility.WalletActivity;
import com.setecs.mobile.wallet.net.SocketAsyncTask3;


public class RegisterBankAccountActivity extends WalletActivity implements SafeReplyHandler {

	private RegisterBankAccountParser parser;
	private String userMobNo;
	private final SharedMethods cv = new SharedMethods();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

		setContentView(R.layout.register_bank_account);

		fetchBankAccounts();
	}

	private void fetchBankAccounts() {
		userMobNo = cv.readConfigurationFile(this, MOBILENO, CONFIGFILENAME);

		if (userMobNo.equals("")) {
			Toast.makeText(this, "Could not read the user's mobile number", Toast.LENGTH_SHORT).show();
			return;
		}

		String transferMsg = "(" + userMobNo + ";al)";
		parser = new RegisterBankAccountParser();
		new SocketAsyncTask3(this, parser, this).execute(transferMsg);
	}

	/*public void registerAccount(View v) {
		saveBankAccount();
	}*/

	private void saveBankAccount(String AccNumb) {
		if (AccNumb.equals("")) {
			Toast.makeText(this, "Cannot register bank account", Toast.LENGTH_SHORT).show();
			return;
		}
		if (isAccInDB(AccNumb)) {
			Toast.makeText(this, "Bank account already registered", Toast.LENGTH_SHORT).show();
		}
		else {
			ContentValues newValues = new ContentValues();
			newValues.put(Constants.BANK_IBAN, "IBAN");
			newValues.put(Constants.BANK_ACCOUNT_NUMBER, AccNumb);
			newValues.put(Constants.BANK_NAME, "BANK NAME");
			ContentResolver cr = getContentResolver();
			// Insert the row into your table
			Uri myRowUri = cr.insert(BankAccountProvider.CONTENT_URI, newValues);
			if (!myRowUri.equals(null)) {
				Toast.makeText(this, "Bank account registered successfully", Toast.LENGTH_SHORT).show();
			}
		}
	}

	@Override
	public void displayList(ArrayList<?> object) {
		// do nothing
	}

	@Override
	public void displayMessage(String message) {
		if (parser.isError())
			displayResponseAndCloseActivity(this, "Bank Accounts", message);
		else
			displayResponse(this, "Bank Accounts", message);
	}

	private boolean isAccInDB(String accNumb) {
		ContentResolver cr = getContentResolver();

		String where = Constants.BANK_ACCOUNT_NUMBER + "=" + accNumb;

		String[] result_columns = null;
		String whereArgs[] = null;
		String order = null;

		Cursor resultCursor = cr.query(BankAccountProvider.CONTENT_URI, result_columns, where, whereArgs, order);

		return resultCursor.moveToFirst();
	}

	private void displayResponse(final Activity activity, String title, final String result) {
		activity.setContentView(R.layout.system_msg);

		TextView msg_title = (TextView) activity.findViewById(R.id.title);
		msg_title.setText(title);

		TextView alert_message = (TextView) activity.findViewById(R.id.message);
		alert_message.setText(result);
		alert_message.setVisibility(View.VISIBLE);

		RelativeLayout Rlayout = (RelativeLayout) activity.findViewById(R.id.SingleLayout);
		Rlayout.setVisibility(View.VISIBLE);

		Button pos_button = (Button) activity.findViewById(R.id.btnSingle);
		pos_button.setText("	Register	");
		pos_button.setVisibility(View.VISIBLE);
		pos_button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				saveBankAccount(parser.getBankAccount());
			}
		});
	}

}
