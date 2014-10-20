package com.setecs.mobile.wallet.safe.wallet;

import static com.setecs.mobile.wallet.safe.wallet.Constants.CONFIGFILENAME;
import static com.setecs.mobile.wallet.safe.wallet.Constants.MOBILENO;
import static com.setecs.mobile.wallet.safe.wallet.Constants.PINFILENAME;
import static com.setecs.mobile.wallet.safe.wallet.Constants.PINNAME;
import static com.setecs.mobile.wallet.safe.wallet.Constants.REGFILENAME;
import static com.setecs.mobile.wallet.safe.wallet.Constants.SAFEIPNO;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TextView;

import com.setecs.mobile.safe.apps.shared.SharedMethods;
import com.setecs.mobile.wallet.msettings.UserRegistration;
import com.setecs.mobile.walletDev.R;


public class Login extends Activity {
	private static final String TAG = "Login";
	HashMap<String, String> hm = new HashMap<String, String>();
	HashMap<String, String> hmh = new HashMap<String, String>();
	HashMap<String, String> hmm = new HashMap<String, String>();
	HashMap<String, byte[]> hmapp = new HashMap<String, byte[]>();
	private boolean showConf = false;
	private boolean checkReg = false;
	public static boolean connected = false;
	private EditText input;
	private TextView msg_title;
	private RelativeLayout Rlayout;
	private Button chng_button;
	private LinearLayout enterPinlayout;
	private AlertDialog warningDialog;
	private Context mContext;
	private final SharedMethods sm = new SharedMethods();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		setInitFlag(true);
		mContext = this.getApplicationContext();
		getApplicationName();

		mContext.getSharedPreferences("myAppPrefs", 0);
		if (getFirstRun()) {
			showWalletConfiguration();
		}
		else if (SharedMethods.showFirstRun) {
			showWalletConfiguration();
		}
		else if (checkSysConfiguration()) {
			beforeShowEnterPin();
			//showEnterPIN();
			showConf = true;
		}
		else if (checkRegistration()) {
			showConf = false;
			beforeShowEnterPin();
			//showEnterPIN();
			checkReg = true;
		}
		else {
			showConf = false;
			checkReg = false;
			beforeShowEnterPin();
			//showEnterPIN();
		}
	}

	/**
	 * 
	 */
	public void getApplicationName() {
		//		final PackageManager pm = mContext.getPackageManager();
		//		ApplicationInfo ai;
		//		try {
		//			ai = pm.getApplicationInfo(getPackageName(), 0);
		//		}
		//		catch (final NameNotFoundException e) {
		//			ai = null;
		//		}
		//		final String applicationName = (String) (ai != null ? pm.getApplicationLabel(ai) : "(unknown)");
	}

	public void showWalletConfiguration() {
		/*setInitFlag(true);
		Intent i = new Intent().setClass(getApplicationContext(), WalletConfiguration.class);
		Bundle bundle = new Bundle(); //bundle is like the letter
		bundle.putBoolean("regstatus", true);
		i.putExtras(bundle);//actually it's bundle who carries the content u wanna pass
		startActivity(i);
		finish();*/
		startActivity(new Intent(this, IntroScreen.class));
		finish();
	}

	public boolean getFirstRun() {
		boolean firstRun = true;
		if (readPreferences() != null && readPreferences().equals("1")) {
			firstRun = true;
		}
		else if (readPreferences() != null && readPreferences().equals("0")) {
			firstRun = false;
		}
		return firstRun;
	}

	private boolean checkRegistration() {

		String regstatus = null;

		File file = getBaseContext().getFileStreamPath(REGFILENAME);
		if (file.exists()) {
			try {
				FileInputStream f = openFileInput(REGFILENAME);
				ObjectInputStream s = new ObjectInputStream(f);
				@SuppressWarnings("unchecked")
				HashMap<String, String> readObject = (HashMap<String, String>) s.readObject();

				Iterator<String> myVeryOwnIterator = readObject.keySet().iterator();
				while (myVeryOwnIterator.hasNext()) {
					String key = myVeryOwnIterator.next();
					if (key.equals("RegistrationStatus"))
						regstatus = readObject.get(key);

				}
				s.close();
				f.close();
				if (regstatus.equals("1"))
					return false;
				else
					return true;

			}
			catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				Log.e(TAG, "checkRegistration", e);

				return true;
			}
			catch (IOException e) {
				// TODO Auto-generated catch block
				Log.e(TAG, "checkRegistration", e);

				return true;
			}
			catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				Log.e(TAG, "checkRegistration", e);

				return true;
			}

		}
		else
			return true;
	}

	private String readPreferences() {

		String readString = null;

		new StringBuilder();
		File file = getBaseContext().getFileStreamPath("preferences_file");
		if (file.exists()) {
			try {
				FileInputStream f = openFileInput("preferences_file");
				ObjectInputStream s = new ObjectInputStream(f);
				@SuppressWarnings("unchecked")
				HashMap<String, String> readObject = (HashMap<String, String>) s.readObject();

				Iterator<String> myVeryOwnIterator = readObject.keySet().iterator();
				while (myVeryOwnIterator.hasNext()) {
					String key = myVeryOwnIterator.next();
					if (key.equals("PoSFirstRun"))
						readString = readObject.get(key);
				}
				s.close();
				f.close();

			}
			catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				Log.e(TAG, "readPreferences", e);

				return readString;
			}
			catch (IOException e) {
				// TODO Auto-generated catch block
				Log.e(TAG, "readPreferences", e);

				return readString;
			}
			catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				Log.e(TAG, "readPreferences", e);

				return readString;
			}
		}
		return readString;
	}

	private boolean checkSysConfiguration() {
		String SAFE_ipno = null;

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
					if (key.equals(MOBILENO))
						readObject.get(key);
					if (key.equals(SAFEIPNO))
						SAFE_ipno = readObject.get(key);
				}
				s.close();
				f.close();

			}
			catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				Log.e(TAG, "checkConfiguration", e);

				return true;
			}
			catch (IOException e) {
				// TODO Auto-generated catch block
				Log.e(TAG, "checkConfiguration", e);

				return true;
			}
			catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				Log.e(TAG, "checkConfiguration", e);

				return true;
			}
			if (SAFE_ipno != null && !SAFE_ipno.equals(""))
				return false;
			else
				return true;
		}
		else
			return true;
	}

	private static final int LOGIN_INTRO_SCREEN = 0;

	public void beforeShowEnterPin() {
		Intent i = new Intent(this, IntroScreenLogin.class);
		startActivityForResult(i, LOGIN_INTRO_SCREEN);
	}

	public void showEnterPIN() {
		this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

		/*setContentView(R.layout.system_msg);
		msg_title = (TextView) findViewById(R.id.title);
		msg_title.setText("SETECS Wallet");
		enterPinlayout = (LinearLayout) findViewById(R.id.showEnterPin_lyout);
		enterPinlayout.setVisibility(View.VISIBLE);
		input = (EditText) findViewById(R.id.enterPin);
		Rlayout = (RelativeLayout) findViewById(R.id.SingleLayout);
		Rlayout.setVisibility(View.VISIBLE);
		chng_button = (Button) findViewById(R.id.btnSingle);
		chng_button.setText("Open Wallet");
		chng_button.setVisibility(View.VISIBLE);
		chng_button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				hideKeyboard();
				Editable value = input.getText();
				String message = value.toString();
				varify(message);
			}
		});
		input.setOnFocusChangeListener(new View.OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if (hasFocus) {
					Login.this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
				}
			}
		});
		input.requestFocus();*/
		setContentView(R.layout.system_msg);
		msg_title = (TextView) findViewById(R.id.title);
		msg_title.setText("SETECS Wallet");
		input = (EditText) findViewById(R.id.editText1);
		TableLayout Tlayout = (TableLayout) findViewById(R.id.login_layout);
		Tlayout.setVisibility(View.VISIBLE);
	}

	protected void hideKeyboard() {
		InputMethodManager inputManager = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
		inputManager.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(),
				InputMethodManager.HIDE_NOT_ALWAYS);
	}

	private void showMainScreen() {

		startActivity(new Intent(this, WalletMain.class));
		SharedMethods.initFlag = false;
		SharedMethods.login = false;
		finish();

	}

	protected void varify(String message) {

		byte[] inputPINBytes = message.getBytes();
		byte[] encryptedPIN = readPINFile(PINNAME);
		byte[] decryptedPIN = sm.decrypt(encryptedPIN, inputPINBytes);
		if (decryptedPIN.length > 0 && Arrays.equals(inputPINBytes, decryptedPIN)) {

			if (showConf) {
				setInitFlag(true);
				showWalletConfiguration();
			}
			if (checkReg) {
				setInitFlag(true);
				goToRegistration();
			}
			else if (!showConf && !checkReg)
				showMainScreen();
		}
		else {
			warningDialog = new AlertDialog.Builder(this).create();

			warningDialog.setTitle("Alert");

			warningDialog.setMessage("Wrong PIN!");

			warningDialog.setIcon(R.drawable.warningicon32x32);
			warningDialog.setButton("Try again", new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {

					showEnterPIN();

				}
			});
			warningDialog.show();
		}

	}

	private void goToRegistration() {

		setInitFlag(true);
		Intent i = new Intent().setClass(getApplicationContext(), UserRegistration.class);
		startActivity(i);
		finish();

	}

	private byte[] readPINFile(String string) {

		byte[] readByte = null;

		try {

			FileInputStream f = openFileInput(PINFILENAME);
			ObjectInputStream s = new ObjectInputStream(f);
			@SuppressWarnings("unchecked")
			HashMap<String, byte[]> readObj = (HashMap<String, byte[]>) s.readObject();

			Iterator<String> myVeryOwnIterator = readObj.keySet().iterator();
			while (myVeryOwnIterator.hasNext()) {
				String key = myVeryOwnIterator.next();
				if (key.equals(string))
					readByte = readObj.get(key);
			}
			s.close();
			f.close();

		}
		catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			Log.e(TAG, "readPINFile", e);

		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			Log.e(TAG, "readPINFile", e);

		}
		catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			Log.e(TAG, "readPINFile", e);

		}

		return readByte;
	}

	public static void setInitFlag(boolean initFlag) {
		SharedMethods.initFlag = initFlag;
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == Activity.RESULT_OK) {
			String result = data.getExtras().getString(IntroScreenLogin.WALLET_OPEN);
			if (result.equals("TRUE"))
				showEnterPIN();
			else
				finish();
		}
		else {
			finish();
		}
	}

	public void onButtonClick(View v) {
		switch (v.getId()) {
		case R.id.button1:
			input.append("1");
			break;
		case R.id.button2:
			input.append("2");
			break;
		case R.id.button3:
			input.append("3");
			break;
		case R.id.button4:
			input.append("4");
			break;
		case R.id.button5:
			input.append("5");
			break;
		case R.id.button6:
			input.append("6");
			break;
		case R.id.button7:
			input.append("7");
			break;
		case R.id.button8:
			input.append("8");
			break;
		case R.id.button9:
			input.append("9");
			break;
		case R.id.button10:
			if (input.getText().length() > 0) {
				input.getText().delete(input.getText().length() - 1, input.getText().length());
			}
			break;
		case R.id.button11:
			input.append("0");
			break;
		case R.id.button12:
			Editable value = input.getText();
			String message = value.toString();
			varify(message);
			break;
		}
	}

}
