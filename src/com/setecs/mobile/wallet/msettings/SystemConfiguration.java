package com.setecs.mobile.wallet.msettings;

import static com.setecs.mobile.wallet.safe.wallet.Constants.CONFIGFILENAME;
import static com.setecs.mobile.wallet.safe.wallet.Constants.SAFEIPNO;

import java.io.File;
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
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.setecs.mobile.safe.apps.shared.CustomExceptionHandler;
import com.setecs.mobile.safe.apps.shared.DisplaySAFEResponse;
import com.setecs.mobile.safe.apps.shared.SharedMethods;
import com.setecs.mobile.walletDev.R;
import com.setecs.mobile.wallet.safe.wallet.Login;


public class SystemConfiguration extends Activity {

	private static final String TAG = "ApplicationConfiguration";
	protected static final int SERVERERROR = 0;
	private final DisplaySAFEResponse ds = new DisplaySAFEResponse();
	private String msg1 = "";
	private String msg2 = "";
	HashMap<String, String> hmap = new HashMap<String, String>();
	protected SharedMethods sm = new SharedMethods();
	protected boolean connected = false;
	protected Message handlermsg;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		Thread.setDefaultUncaughtExceptionHandler(new CustomExceptionHandler(SystemConfiguration.this));

		if (this.getIntent().getExtras() != null) {
			Bundle bundle = this.getIntent().getExtras();//get the intent & bundle passed by X
			bundle.getBoolean("regstatus");
		}
		setContentView(R.layout.system_conf);
		loadFields();

		((Button) findViewById(R.id.confsave_btn)).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				saveWalletConfiguration();
			}
		});
	}

	private void loadFields() {
		File file = getBaseContext().getFileStreamPath(CONFIGFILENAME);
		EditText etext1 = (EditText) findViewById(R.id.SAFE_ip_no);
		EditText etext2 = (EditText) findViewById(R.id.SAFE_mob_no);

		if (file.exists()) {

			try {

				FileInputStream f = openFileInput(CONFIGFILENAME);
				ObjectInputStream s = new ObjectInputStream(f);
				@SuppressWarnings("unchecked")
				HashMap<String, String> readObject = (HashMap<String, String>) s.readObject();
				Iterator<String> myVeryOwnIterator = readObject.keySet().iterator();
				while (myVeryOwnIterator.hasNext()) {
					String key = myVeryOwnIterator.next();
					if (key.equals("SAFE_mob_no"))
						etext2.setText(readObject.get(key));
					if (key.equals(SAFEIPNO))
						etext1.setText(readObject.get(key));
				}
			}
			catch (Exception e) {
				Log.e(TAG, "loadFields", e);

			}

		}
	}

	private void saveWalletConfiguration() {

		String value = "";
		File file = getBaseContext().getFileStreamPath(CONFIGFILENAME);
		EditText etext1 = (EditText) findViewById(R.id.SAFE_ip_no);
		EditText etext2 = (EditText) findViewById(R.id.SAFE_mob_no);
		msg1 = etext1.getText().toString();
		msg2 = etext2.getText().toString();

		if (file.exists()) {

			if (msg1.length() > 0 && msg2.length() > 0) {

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
					hmap.put(SAFEIPNO, msg1);
					hmap.put("SAFE_mob_no", msg2);

					FileOutputStream fStream = openFileOutput(CONFIGFILENAME, Context.MODE_PRIVATE);
					ObjectOutputStream oStream = new ObjectOutputStream(fStream);
					oStream.writeObject(hmap);

					oStream.flush();
					oStream.close();
					fStream.close();
					etext1.setText("");
					etext2.setText("");
					if (SharedMethods.initFlag) {
						SharedMethods.serverIpAddress = msg1;

						goToRegistration();

						//						connectSystem();
					}
					else {
						displaySaveConfirmation("Configuration saved successfully");
					}
				}
				catch (Exception e) {
					Log.e(TAG, "saveWalletConfiguration", e);

				}
			}
			else {
				ds.blankFieldsWarning(SystemConfiguration.this);
			}

		}
		else {

			if (msg1.length() > 0 && msg2.length() > 0) {
				hmap.put(SAFEIPNO, msg1);
				hmap.put("SAFE_mob_no", msg2);
				try {
					FileOutputStream fStream = openFileOutput(CONFIGFILENAME, Context.MODE_PRIVATE);
					ObjectOutputStream oStream = new ObjectOutputStream(fStream);
					oStream.writeObject(hmap);

					oStream.flush();
					oStream.close();
					fStream.close();

				}
				catch (Exception e) {
					Log.e(TAG, "saveWalletConfiguration", e);

				}
				if (SharedMethods.initFlag) {
					SharedMethods.serverIpAddress = msg1;

					goToRegistration();

					//					connectSystem();
				}
				else {
					displaySaveConfirmation("Configuration saved successfully");
				}

			}
			else {
				ds.blankFieldsWarning(SystemConfiguration.this);
			}
		}

	}

	public void goToRegistration() {
		startActivity(new Intent(getApplicationContext(), UserRegistration.class));
		finish();

	}

	public void displaySaveConfirmation(String result) {

		ds.displaySAFErespons(SystemConfiguration.this, result, "Wallet Configuration");

	}

	@Override
	public void onBackPressed() {
		if (SharedMethods.initFlag) {
			SharedMethods.showFirstRun = true;
			startActivity(new Intent(getApplicationContext(), Login.class));
			finish();
		}
		else
			finish();
	}

} // end class