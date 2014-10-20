package com.setecs.mobile.wallet.msettings;

import static com.setecs.mobile.safe.apps.shared.Constants.SAFEIPNO;
import static com.setecs.mobile.wallet.safe.wallet.Constants.ACCOUNTLIST;
import static com.setecs.mobile.wallet.safe.wallet.Constants.ACCOUNTLISTFILENAME;
import static com.setecs.mobile.wallet.safe.wallet.Constants.CONFIGFILENAME;
import static com.setecs.mobile.wallet.safe.wallet.Constants.MOBILENO;
import static com.setecs.mobile.wallet.safe.wallet.Constants.REGFILENAME;
import static com.setecs.mobile.wallet.safe.wallet.Constants.SAFEPINFILENAME;
import static com.setecs.mobile.wallet.safe.wallet.Constants.SAFEPINNAME;
import static com.setecs.mobile.wallet.safe.wallet.Constants.USERNAME;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.StringTokenizer;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.setecs.mobile.safe.apps.shared.CustomExceptionHandler;
import com.setecs.mobile.safe.apps.shared.DisplaySAFEResponse;
import com.setecs.mobile.safe.apps.shared.SharedMethods;
import com.setecs.mobile.safe.apps.shared.Util;
import com.setecs.mobile.wallet.net.SocketAsyncTask;
import com.setecs.mobile.wallet.net.SocketResponseHandler;
import com.setecs.mobile.wallet.safe.wallet.WalletMain;
import com.setecs.mobile.walletDev.R;


public class UserRegistration extends Activity implements SocketResponseHandler {

	private static final String TAG = "UserRegistration";
	private String userMobNo = "";
	private final HashMap<String, String> hm = new HashMap<String, String>();
	private final HashMap<String, String> hmh = new HashMap<String, String>();
	private final HashMap<String, String> hmap = new HashMap<String, String>();
	HashMap<String, String[]> hAccList = new HashMap<String, String[]>();
	EditText fnametxt;
	EditText lnametxt;
	private final SharedMethods cv = new SharedMethods();
	private final HashMap<String, byte[]> hmapp = new HashMap<String, byte[]>();
	String pinmmsg;
	private SocketAsyncTask task;
	String fnamemsg = "";
	String lnamemsg = "";
	String userName = "";
	private SharedPreferences mPrefs;
	private final WalletParser walletParser = new WalletParser(this);
	private final DisplaySAFEResponse ds = new DisplaySAFEResponse();
	private boolean saveSafeInfo = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		Thread.setDefaultUncaughtExceptionHandler(new CustomExceptionHandler(UserRegistration.this));
		mPrefs = getApplicationContext().getSharedPreferences("myAppPrefs", 0);

		this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

		SharedMethods.serverIpAddress = cv.readConfigurationFile(UserRegistration.this, SAFEIPNO, CONFIGFILENAME);

		setContentView(R.layout.user_reg_blank);

		//checkRegStatus();
		useLocally();
	}

	private void checkRegStatus() {
		userMobNo = cv.readConfigurationFile(UserRegistration.this, MOBILENO, CONFIGFILENAME);
		if (userMobNo != null) {
			String msg = "(" + userMobNo + ";srs)";
			task = new SocketAsyncTask(this, this);
			task.execute(msg);
		}
		else {
			Toast.makeText(this, "Cannot check registration. Unknown customer", Toast.LENGTH_SHORT).show();
		}
	}

	void sendUserReg() {
		fnamemsg = fnametxt.getText().toString();
		lnamemsg = lnametxt.getText().toString();

		//remove spaces
		fnamemsg = Util.removeSpaces(fnamemsg);
		lnamemsg = Util.removeSpaces(lnamemsg);

		if (fnamemsg.length() > 0 && lnamemsg.length() > 0) {
			userMobNo = cv.readConfigurationFile(UserRegistration.this, MOBILENO, CONFIGFILENAME);
			if (userMobNo.length() > 0 || userMobNo != null) {
				if (fnamemsg.contains(" ")) {
					String cleanedString = "";
					for (int i = 0; i < fnamemsg.length(); i++) {
						if (fnamemsg.charAt(i) == ' ') {
							cleanedString += "_";
						}
						else {
							cleanedString += fnamemsg.charAt(i);
						}
					}
					fnamemsg = cleanedString;
				}
				if (lnamemsg.contains(" ")) {
					String cleanedString = "";
					for (int i = 0; i < lnamemsg.length(); i++) {
						if (lnamemsg.charAt(i) == ' ') {
							cleanedString += "_";
						}
						else {
							cleanedString += lnamemsg.charAt(i);
						}
					}
					lnamemsg = cleanedString;
				}
				String msg = "(" + userMobNo + ";sr " + fnamemsg + " " + lnamemsg + ")";
				task = new SocketAsyncTask(this, this);
				task.execute(msg);
			}
			else {
				ds.blankFieldsWarning(UserRegistration.this);
			}
		}
		else {
			ds.blankFieldsWarning(UserRegistration.this);
		}
	}

	void sendUserRegConf() {
		String msg = "(" + userMobNo + ";" + "sc " + pinmmsg + ")";
		task = new SocketAsyncTask(this, this);
		task.execute(msg);
	}

	void sendUserRegConfClosedSystem(String pin) {
		if (userMobNo.length() > 0 || userMobNo != null) {
			String msg = "(" + userMobNo + ";" + "sc " + pin + ")";
			task = new SocketAsyncTask(this, this);
			task.execute(msg);
		}
	}

	void sendGetUserAccount() {
		if (userMobNo.length() > 0 || userMobNo != null) {
			String msg = "(" + userMobNo + ";al)";
			task = new SocketAsyncTask(this, this);
			task.execute(msg);
		}
	}

	protected OnClickListener userRegButtonListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			Integer buttonId = ((Button) v).getId();

			if (buttonId == R.id.btn_usereg) {
				hideKeyboard(UserRegistration.this);
				sendUserReg();
			}
		}
	};

	protected void hideKeyboard(final Activity activity) {

		InputMethodManager inputManager = (InputMethodManager) activity.getApplicationContext().getSystemService(
				Context.INPUT_METHOD_SERVICE);
		inputManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(),
				InputMethodManager.HIDE_NOT_ALWAYS);
	}

	protected void saveSafePIN(String pinmmsg2, Activity activity) {

		byte[] pin = cv.encrypt(pinmmsg2);
		hmapp.put(SAFEPINNAME, pin);

		try {
			FileOutputStream fStream = activity.openFileOutput(SAFEPINFILENAME, Context.MODE_PRIVATE);
			ObjectOutputStream oStream = new ObjectOutputStream(fStream);
			oStream.writeObject(hmapp);

			oStream.flush();
			oStream.close();
			fStream.close();

		}
		catch (Exception e) {
			Log.e(TAG, "open file failed", e);

			return;
		}
	}

	protected void showMainScreen() {

		Intent i = new Intent().setClass(UserRegistration.this, WalletMain.class);
		Bundle bundle = new Bundle(); //bundle is like the letter
		bundle.putBoolean("pinflag", false);
		bundle.putBoolean("setRunned", false);
		i.putExtras(bundle);//actually it's bundle who carries the content u wanna pass
		i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(i);
		SharedMethods.initFlag = false;
		finish();

	}

	void saveRegistrationStatus(Activity activity) {
		hmh.put("RegistrationStatus", "1");

		try {
			FileOutputStream fStream = activity.openFileOutput(REGFILENAME, Context.MODE_PRIVATE);
			ObjectOutputStream oStream = new ObjectOutputStream(fStream);
			oStream.writeObject(hmh);

			oStream.flush();
			oStream.close();
			fStream.close();
		}
		catch (Exception e) {
			Log.e(TAG, "open file failed", e);

		}
	}

	void saveUserName(String fnamemsgg) {
		String value = "";
		File file = getBaseContext().getFileStreamPath(CONFIGFILENAME);
		if (file.exists()) {
			try {
				FileInputStream f = openFileInput(CONFIGFILENAME);
				ObjectInputStream s = new ObjectInputStream(f);
				@SuppressWarnings("unchecked")
				HashMap<String, String> readObject = (HashMap<String, String>) s.readObject();
				Iterator<String> myVeryOwnIterator = readObject.keySet().iterator();
				while (myVeryOwnIterator.hasNext()) {
					String key = myVeryOwnIterator.next();
					value = readObject.get(key);
					hmap.put(key, value);

				}
				hmap.put(USERNAME, fnamemsgg);
				FileOutputStream fStream = openFileOutput(CONFIGFILENAME, Context.MODE_PRIVATE);
				ObjectOutputStream oStream = new ObjectOutputStream(fStream);
				oStream.writeObject(hmap);

				oStream.flush();
				oStream.close();
				fStream.close();

			}
			catch (Exception e) {
				Log.e(TAG, "open file failed", e);

			}
		}
		else {
			try {
				hmap.put(USERNAME, fnamemsgg);
				FileOutputStream fStream = openFileOutput(CONFIGFILENAME, Context.MODE_PRIVATE);
				ObjectOutputStream oStream = new ObjectOutputStream(fStream);
				oStream.writeObject(hmap);

				oStream.flush();
				oStream.close();
				fStream.close();
			}
			catch (Exception e) {
				Log.e(TAG, "open file failed", e);

			}
		}

	}

	private void saveUserAccount(String response) {
		String nextToken;
		String[] accountlist = new String[10];
		int i = 0;
		try {
			if (response.contains("SETECS Account") || response.contains("Bank Account")) {
				StringTokenizer st = new StringTokenizer(response);
				while (st.hasMoreTokens()) {
					nextToken = (st.nextToken()).trim();
					if (Character.isDigit(nextToken.charAt(0))) {
						accountlist[i] = nextToken;
						i++;
					}

				}
				hAccList.put(ACCOUNTLIST, accountlist);

				FileOutputStream fStream = openFileOutput(ACCOUNTLISTFILENAME, Context.MODE_PRIVATE);
				ObjectOutputStream oStream = new ObjectOutputStream(fStream);
				oStream.writeObject(hAccList);

				oStream.flush();
				oStream.close();
				fStream.close();

			}
		}
		catch (Exception e) {
			Log.e(TAG, "Cannot save account list", e);
		}
	}

	void saveSafeInformation(Activity activity) {
		/*if (SharedMethods.initFlag) {
			saveRegistrationStatus(activity);
			cv.fetchSaveAccountList(UserRegistration.this, MOBILENO, CONFIGFILENAME, ACCOUNTLIST, ACCOUNTLISTFILENAME);
			saveSafePIN(pinmmsg, activity);
			showMainScreen();
			saveUserName(userName);
			SharedMethods.initFlag = false;
		}
		else {
			cv.fetchSaveAccountList(UserRegistration.this, MOBILENO, CONFIGFILENAME, ACCOUNTLIST, ACCOUNTLISTFILENAME);
			saveSafePIN(pinmmsg, activity);
			activity.finish();
		}*/
		saveSafeInfo = true;
		sendGetUserAccount();
	}

	void setRunned(boolean b) {
		SharedPreferences.Editor edit = mPrefs.edit();
		edit.putBoolean("WalletFirstRun", b);
		edit.commit();
		String FILENAME = "preferences_file";
		if (b)
			hm.put("WalletFirstRun", "1");
		else
			hm.put("WalletFirstRun", "0");

		try {
			FileOutputStream fStream = openFileOutput(FILENAME, Context.MODE_PRIVATE);
			ObjectOutputStream oStream = new ObjectOutputStream(fStream);
			oStream.writeObject(hm);

			oStream.flush();
			oStream.close();
			fStream.close();

		}
		catch (Exception e) {
			Log.e(TAG, "setRunned", e);

		}
	}

	@Override
	public void onBackPressed() {
		if (SharedMethods.initFlag) {
			startActivity(new Intent(getApplicationContext(), WalletConfiguration.class));
			finish();
		}
		else
			finish();
	}

	@Override
	public void handleResponse(String response) {
		if (saveSafeInfo) {
			if (SharedMethods.initFlag) {
				saveUserAccount(response);
				saveRegistrationStatus(this);
				saveSafePIN(pinmmsg, this);
				saveUserName(userName);
				SharedMethods.initFlag = false;
				showMainScreen();
			}
			else {
				saveSafePIN(pinmmsg, this);
				finish();
			}
		}
		else {
			walletParser.parseUserRegistration(response);
		}
	}

	@Override
	public void handleErrorResponse(String response) {
		walletParser.parseAndClose("User Configuration", response);
	}

	private void useLocally() {
		saveUserAccount("Bank Account: 123456");
		saveRegistrationStatus(this);
		saveSafePIN("1", this);
		saveUserName("Aron");
		SharedMethods.initFlag = false;
		showMainScreen();
	}

} // end class
