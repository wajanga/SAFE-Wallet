package com.setecs.mobile.wallet.msettings;

import static com.setecs.mobile.wallet.safe.wallet.Constants.CONFIGFILENAME;
import static com.setecs.mobile.wallet.safe.wallet.Constants.MOBILENO;
import static com.setecs.mobile.wallet.safe.wallet.Constants.PINFILENAME;
import static com.setecs.mobile.wallet.safe.wallet.Constants.PINNAME;
import static com.setecs.mobile.wallet.safe.wallet.Constants.SAFEIPNO;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Iterator;

import javax.crypto.spec.SecretKeySpec;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.setecs.mobile.safe.apps.shared.CustomExceptionHandler;
import com.setecs.mobile.safe.apps.shared.DisplaySAFEResponse;
import com.setecs.mobile.safe.apps.shared.SharedMethods;
import com.setecs.mobile.safe.apps.shared.ShowSafeSystemList;
import com.setecs.mobile.safe.apps.util.security.CryptoProviderClient;
import com.setecs.mobile.safe.apps.util.security.DigestProvider;
import com.setecs.mobile.wallet.safe.wallet.Constants;
import com.setecs.mobile.wallet.safe.wallet.WalletMain;
import com.setecs.mobile.walletDev.R;


public class WalletConfiguration extends Activity {

	private static final String TAG = null;
	private EditText firstPIN;
	private EditText confirmPIN;
	private EditText user_mob_no;
	private EditText serverIpAddressTV;
	private byte[] pinBytes;
	private DigestProvider digest;
	private byte[] keyBytes;
	private CryptoProviderClient crypto;
	private SharedPreferences mPrefs;
	private final HashMap<String, String> hm = new HashMap<String, String>();
	private final HashMap<String, byte[]> hmapp = new HashMap<String, byte[]>();
	private final HashMap<String, String> hmap = new HashMap<String, String>();

	private TextView safeText;
	protected boolean showCountries = true;

	//private String safeIpAddress = Constants.SAFE_IP_ADDRESS;
	private String safeIpAddress = "";
	private String safeMobileNumber = "17654321";

	private final DisplaySAFEResponse ds = new DisplaySAFEResponse();
	//protected static final int COUNTRY_CODE = 0;
	private String COUNTRY_CODE = "";
	private static final int SAFE_SYSTEM_SELECTION = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		setInitFlag(true);
		Thread.setDefaultUncaughtExceptionHandler(new CustomExceptionHandler(WalletConfiguration.this));

		Context mContext = this.getApplicationContext();

		mPrefs = mContext.getSharedPreferences("myAppPrefs", 0);
		if (this.getIntent().getExtras() != null) {
			Bundle bundle = this.getIntent().getExtras();//get the intent & bundle passed by X
			bundle.getBoolean("regstatus");
		}
		//setContentView(R.layout.wallet_configuration);
		setContentView(R.layout.wallet_configuration2);

		firstPIN = (EditText) findViewById(R.id.firstPin);
		confirmPIN = (EditText) findViewById(R.id.confirmPin);
		safeText = (TextView) findViewById(R.id.safe_system_tv);
		user_mob_no = (EditText) findViewById(R.id.user_mob_no);
		serverIpAddressTV = (EditText) findViewById(R.id.ipAddress);

		addCountryCode();

		// select SAFE system button
		((Button) findViewById(R.id.selectSAFE_btn)).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// show safe selection screen
				Intent i = new Intent(WalletConfiguration.this, ShowSafeSystemList.class);
				startActivityForResult(i, SAFE_SYSTEM_SELECTION);
			}
		});

		((Button) findViewById(R.id.confsave_btn)).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				saveWalletConfiguration();
				//showSystemConfiguration();
				setRunned(false);

			}
		});
		firstPIN.setOnFocusChangeListener(new View.OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if (hasFocus) {
					WalletConfiguration.this.getWindow().setSoftInputMode(
							WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
				}
			}
		});
		firstPIN.requestFocus();
	}

	/*private void showSystemConfiguration() {
		setInitFlag(true);
		Intent i = new Intent().setClass(getApplicationContext(), SystemConfiguration.class);
		Bundle bundle = new Bundle(); //bundle is like the letter
		bundle.putBoolean("regstatus", true);
		i.putExtras(bundle);//actually it's bundle who carries the content u wanna pass
		startActivity(i);
		finish();

	}*/

	public void goToRegistration() {
		startActivity(new Intent(getApplicationContext(), UserRegistration.class));
		finish();
		//showMainScreen();
	}

	protected void showMainScreen() {

		Intent i = new Intent().setClass(WalletConfiguration.this, WalletMain.class);
		Bundle bundle = new Bundle(); //bundle is like the letter
		bundle.putBoolean("pinflag", false);
		bundle.putBoolean("setRunned", false);
		i.putExtras(bundle);//actually it's bundle who carries the content u wanna pass
		i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(i);
		SharedMethods.initFlag = false;
		finish();

	}

	private void addCountryCode() {
		TextView tvCountryCode = (TextView) findViewById(R.id.tv_country_code);
		tvCountryCode.setVisibility(View.VISIBLE);

		COUNTRY_CODE = Constants.US_COUNTRY_CODE;
	}

	protected void saveWalletConfiguration() {
		Editable value1 = firstPIN.getText();
		Editable value2 = confirmPIN.getText();
		String newPINtxt = value1.toString();
		String newPINcnfrmtxt = value2.toString();
		safeIpAddress = serverIpAddressTV.getText().toString();
		if ((user_mob_no.getText().toString()).length() > 0 && newPINtxt.length() > 0
				&& newPINcnfrmtxt.length() > 0
				&& !safeIpAddress.equals("")
				&& !safeMobileNumber.equals("")) {

			//MobNo = MobNo + user_mob_no.getText().toString();

			if (newPINcnfrmtxt.length() > 0 && newPINtxt.length() > 0 && newPINcnfrmtxt.equals(newPINtxt)) {
				saveConfirmPin(newPINcnfrmtxt);
				saveMobNo(COUNTRY_CODE + user_mob_no.getText().toString());
				//saveMobNo(MobNo);
				saveSafeConfiguration(safeIpAddress, safeMobileNumber);
				//showSystemConfiguration();
				goToRegistration();
			}
			else
				enterPinWarning();
		}
		else {
			ds.blankFieldsWarning(WalletConfiguration.this);
		}
	}

	private void saveMobNo(String MobNo) {
		// TODO Auto-generated method stub
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
				hmap.put(MOBILENO, MobNo);
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
				hmap.put(MOBILENO, MobNo);
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

	protected void enterPinWarning() {
		// TODO Auto-generated method stub
		AlertDialog.Builder warnalert = new AlertDialog.Builder(this);

		warnalert.setTitle("Alert");
		warnalert.setMessage("PINs are not the same!");
		warnalert.setIcon(R.drawable.warningicon32x32);

		warnalert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int whichButton) {
				clearThePINs();

			}
		});

		warnalert.show();
	}

	protected void clearThePINs() {
		// TODO Auto-generated method stub
		firstPIN.setText("");
		confirmPIN.setText("");
	}

	protected void saveConfirmPin(String newPINcnfrmtxt) {
		// TODO Auto-generated method stub

		byte[] pin = encryptPin(newPINcnfrmtxt);
		hmapp.put(PINNAME, pin);

		try {
			FileOutputStream fStream = openFileOutput(PINFILENAME, Context.MODE_PRIVATE);
			ObjectOutputStream oStream = new ObjectOutputStream(fStream);
			oStream.writeObject(hmapp);

			oStream.flush();
			oStream.close();
			fStream.close();

		}
		catch (Exception e) {
			Log.e(TAG, "saveConfirmPin", e);

		}

	}

	protected byte[] encryptPin(String newPINcnfrmtxt) {
		// TODO Auto-generated method stub
		pinBytes = newPINcnfrmtxt.getBytes();
		digest = new DigestProvider();
		keyBytes = digest.pinDigest(pinBytes);
		SecretKeySpec theKey = new SecretKeySpec(keyBytes, "AES");
		SharedMethods.secretKey = theKey;
		crypto = new CryptoProviderClient();
		return crypto.AESEncrypt(pinBytes, theKey);
	}

	public void setRunned(boolean b) {
		SharedPreferences.Editor edit = mPrefs.edit();
		edit.putBoolean("PoSFirstRun", b);
		edit.commit();
		String FILENAME = "preferences_file";
		if (b)
			hm.put("PoSFirstRun", "1");
		else
			hm.put("PoSFirstRun", "0");

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
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == Activity.RESULT_OK) {
			String result = data.getExtras().getString(ShowSafeSystemList.SAFE_CHOICE);
			String[] safeConfig = null;
			if (!result.equals("")) {
				// test is chosen
				if (result.equals(getString(R.string.test))) {
					safeConfig = getResources().getStringArray(R.array.test);
				}
				// test 17 is chosen
				else if (result.equals(getString(R.string.test17))) {
					safeConfig = getResources().getStringArray(R.array.test17);
				}
				// demo is chosen
				else if (result.equals(getString(R.string.demo))) {
					safeConfig = getResources().getStringArray(R.array.demo);
				}
				// usa is chosen
				else if (result.equals(getString(R.string.usa))) {
					safeConfig = getResources().getStringArray(R.array.usa);
				}
				// peru is chosen
				else if (result.equals(getString(R.string.peru))) {
					safeConfig = getResources().getStringArray(R.array.demo);
				}
				// mozambique is chosen
				else if (result.equals(getString(R.string.mozambique))) {
					safeConfig = getResources().getStringArray(R.array.demo);
				}
				// uganda is chosen
				else if (result.equals(getString(R.string.uganda))) {
					safeConfig = getResources().getStringArray(R.array.uganda);
				}
				else if (result.equals(getString(R.string.setecs_demo_system))) {
					safeConfig = getResources().getStringArray(R.array.setecs_demo_system);
				}
				else if (result.equals(getString(R.string.setecs_usa_1))) {
					safeConfig = getResources().getStringArray(R.array.setecs_usa_1);
				}
				else if (result.equals(getString(R.string.setecs_usa_2))) {
					safeConfig = getResources().getStringArray(R.array.setecs_usa_2);
				}
				else if (result.equals(getString(R.string.setecs_canada))) {
					safeConfig = getResources().getStringArray(R.array.setecs_canada);
				}
				else if (result.equals(getString(R.string.setecs_italy))) {
					safeConfig = getResources().getStringArray(R.array.setecs_italy);
				}
				//MobNo = safeConfig[0];
				safeMobileNumber = safeConfig[1];
				safeIpAddress = safeConfig[2];
				// set safe system choice textview
				safeText.setText("SAFE System: " + result);

				hideKeyboard();
			}
		}
	}

	public static void setInitFlag(boolean initFlag) {
		SharedMethods.initFlag = initFlag;
	}

	private void saveSafeConfiguration(String ipNumber, String safeMobile) {
		String value = "";
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
			hmap.put(SAFEIPNO, ipNumber);
			hmap.put("safe_mob_no", safeMobile);

			FileOutputStream fStream = openFileOutput(CONFIGFILENAME, Context.MODE_PRIVATE);
			ObjectOutputStream oStream = new ObjectOutputStream(fStream);
			oStream.writeObject(hmap);

			oStream.flush();
			oStream.close();
			fStream.close();
			if (SharedMethods.initFlag) {
				SharedMethods.serverIpAddress = ipNumber;
			}
			else {
				displaySaveConfirmation("Configuration saved successfully");
			}
		}
		catch (Exception e) {
			Log.e(TAG, "saveWalletConfiguration", e);
		}
	}

	public void displaySaveConfirmation(String result) {
		ds.displaySAFErespons(this, result, "Wallet Configuration");
	}

	private void hideKeyboard() {
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
		InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(user_mob_no.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
	}
}
