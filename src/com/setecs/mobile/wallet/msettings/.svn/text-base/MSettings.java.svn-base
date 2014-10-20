package com.setecs.mobile.wallet.msettings;

import static com.setecs.mobile.wallet.safe.wallet.Constants.CONFIGFILENAME;
import static com.setecs.mobile.wallet.safe.wallet.Constants.SAFEIPNO;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Iterator;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.setecs.mobile.safe.apps.shared.DisplaySAFEResponse;
import com.setecs.mobile.safe.apps.shared.SecuritySettings;
import com.setecs.mobile.safe.apps.shared.SharedMethods;
import com.setecs.mobile.safe.apps.shared.ShowSafeSystemList;
import com.setecs.mobile.walletDev.R;
import com.setecs.mobile.wallet.safe.wallet.WalletMain;


public class MSettings extends Activity {

	private static final String TAG = null;
	private final DisplaySAFEResponse ds = new DisplaySAFEResponse();
	private String safeIpAddress = "", safeMobileNumber = "";
	private static final int SAFE_SYSTEM_SELECTION = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

		setContentView(R.layout.m_settings);
		((Button) findViewById(R.id.application_conf)).setOnClickListener(mSecurityButtonListener);
		((Button) findViewById(R.id.user_reg)).setOnClickListener(mSecurityButtonListener);
		((Button) findViewById(R.id.wallet_applications)).setOnClickListener(mSecurityButtonListener);
		((Button) findViewById(R.id.security_settings)).setOnClickListener(mSecurityButtonListener);
		((Button) findViewById(R.id.system_info)).setOnClickListener(mSecurityButtonListener);
	}

	protected OnClickListener mSecurityButtonListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			Integer buttonId = ((Button) v).getId();

			if (buttonId == R.id.application_conf) {
				application_conf();
			}
			if (buttonId == R.id.user_reg) {
				user_reg();
			}
			if (buttonId == R.id.wallet_applications) {
				wallet_applications();
			}
			if (buttonId == R.id.security_settings) {
				security_settings();
			}
			if (buttonId == R.id.system_info) {
				system_info();
			}

		}
	};

	protected void security_settings() {
		Intent i = new Intent().setClass(getApplicationContext(), SecuritySettings.class);
		Bundle bundle = new Bundle(); // bundle is like the letter
		bundle.putString("app_name", "wallet");
		i.putExtras(bundle);// actually it's bundle who carries the content u
		// wanna pass
		i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(i);
	}

	protected void system_info() {
		startActivity(new Intent(getApplicationContext(), SystemInfoActivity.class));
	}

	protected void wallet_applications() {
		startActivity(new Intent(getApplicationContext(), WalletApplications.class));
	}

	protected void user_reg() {
		startActivity(new Intent(getApplicationContext(), UserRegistration.class));
	}

	protected void application_conf() {
		// show safe selection screen
		Intent i = new Intent(this, ShowSafeSystemList.class);
		startActivityForResult(i, SAFE_SYSTEM_SELECTION);
	}

	private void saveSafeConfiguration(String ipNumber, String safeMobile) {
		HashMap<String, String> hmap = new HashMap<String, String>();
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
			hmap.put("SAFE_mob_no", safeMobile);

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

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == Activity.RESULT_OK) {
			String result = data.getExtras().getString(ShowSafeSystemList.SAFE_CHOICE);
			String[] safeConfig = null;
			if (!result.equals("")) {
				// demo is chosen
				if (result.equals(getString(R.string.demo))) {
					// fetch corresponding safe configuration from xml
					safeConfig = getResources().getStringArray(R.array.demo);
				}
				// usa is chosen
				else if (result.equals(getString(R.string.usa))) {
					safeConfig = getResources().getStringArray(R.array.demo);
				}
				// sweden is chosen
				else if (result.equals(getString(R.string.sweden))) {
					safeConfig = getResources().getStringArray(R.array.demo);
				}
				// peru is chosen
				else if (result.equals(getString(R.string.peru))) {
					safeConfig = getResources().getStringArray(R.array.demo);
				}
				// mozambique is chosen
				else if (result.equals(getString(R.string.mozambique))) {
					safeConfig = getResources().getStringArray(R.array.demo);
				}
				// jordan is chosen
				else if (result.equals(getString(R.string.jordan))) {
					safeConfig = getResources().getStringArray(R.array.demo);
				}
				safeMobileNumber = safeConfig[1];
				safeIpAddress = safeConfig[2];
				saveSafeConfiguration(safeMobileNumber, safeIpAddress);
			}
		}
	}

	@Override
	public void onBackPressed() {
		// do something on back.
		SharedMethods.login = false;

		Intent i = new Intent().setClass(MSettings.this, WalletMain.class);
		i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(i);
		finish();
		return;

	}
} // end class

