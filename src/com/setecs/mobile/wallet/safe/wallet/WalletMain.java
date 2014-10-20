package com.setecs.mobile.wallet.safe.wallet;

import static com.setecs.mobile.wallet.safe.wallet.Constants.APPLICATIONSFILENAME;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.StreamCorruptedException;
import java.util.HashMap;
import java.util.Iterator;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.setecs.mobile.safe.apps.shared.ChangeSafePin;
import com.setecs.mobile.safe.apps.shared.CustomExceptionHandler;
import com.setecs.mobile.safe.apps.shared.SharedMethods;
import com.setecs.mobile.walletDev.R;
import com.setecs.mobile.wallet.market.main.HomeActivity;
import com.setecs.mobile.wallet.msettings.MSettings;
import com.setecs.mobile.wallet.mwallet.MWallet;


public class WalletMain extends Activity {

	protected static final int SERVERERROR = 0;
	SharedMethods sm = new SharedMethods();

	/** Called when the activity is first created. */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		SharedMethods.login = false;
		Thread.setDefaultUncaughtExceptionHandler(new CustomExceptionHandler(WalletMain.this));
		showMainScreen();

	}

	private final OnClickListener mainButtonListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			Integer buttonId = ((Button) v).getId();

			if (buttonId == R.id.m_wallet) {
				mWallet();
			}
			if (buttonId == R.id.m_market) {
				mMarket();
			}
			if (buttonId == R.id.m_club) {
				mClub();
			}
			if (buttonId == R.id.m_health) {
				mHealth();
			}
			if (buttonId == R.id.m_security) {
				mSettings();
			}

		}
	};
	private String mShopptxt;
	private String mClubtxt;
	private String mHealthtxt;
	protected Message handlermsg;

	protected void mMarket() {
		startActivity(new Intent(this, HomeActivity.class));

	}

	protected void mHealth() {
		
		Intent i = new Intent().setClass(getApplicationContext(), ChangeSafePin.class);
		Bundle bundle = new Bundle(); // bundle is like the letter
		bundle.putString("app_name", "wallet");
		i.putExtras(bundle);// actually it's bundle who carries the content u
		// wanna pass
		i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(i);
		startActivity(new Intent(this, MHealth.class));

	}

	protected void mClub() {
		
		startActivity(new Intent(this, MClub.class));

	}

	protected void mWallet() {
		startActivity(new Intent(this, MWallet.class));

	}

	protected void mSettings() {
		startActivity(new Intent(this, MSettings.class));

	}

	public void showMainScreen() {

		setContentView(R.layout.walletmain);
		((Button) findViewById(R.id.m_wallet)).setOnClickListener(mainButtonListener);
		((Button) findViewById(R.id.m_market)).setOnClickListener(mainButtonListener);
		((Button) findViewById(R.id.m_club)).setOnClickListener(mainButtonListener);
		((Button) findViewById(R.id.m_health)).setOnClickListener(mainButtonListener);
		((Button) findViewById(R.id.m_security)).setOnClickListener(mainButtonListener);
		showScreen();

	}

	private void showScreen() {
		
		FileInputStream f;
		try {
			f = openFileInput(APPLICATIONSFILENAME);
			ObjectInputStream s = new ObjectInputStream(f);
			@SuppressWarnings("unchecked")
			HashMap<String, String> readObject = (HashMap<String, String>) s.readObject();
			Iterator<String> myVeryOwnIterator = readObject.keySet().iterator();
			while (myVeryOwnIterator.hasNext()) {
				String key = myVeryOwnIterator.next();
				if (key.equals("mshop"))
					mShopptxt = readObject.get(key);
				if (key.equals("mclub"))
					mClubtxt = readObject.get(key);
				if (key.equals("mhealth"))
					mHealthtxt = readObject.get(key);

			}
			if (mShopptxt.equals("1"))
				((Button) findViewById(R.id.m_market)).setVisibility(View.VISIBLE);
			else
				((Button) findViewById(R.id.m_market)).setVisibility(View.GONE);

			if (mClubtxt.equals("1"))
				((Button) findViewById(R.id.m_club)).setVisibility(View.VISIBLE);
			else
				((Button) findViewById(R.id.m_club)).setVisibility(View.GONE);

			if (mHealthtxt.equals("1"))
				((Button) findViewById(R.id.m_health)).setVisibility(View.VISIBLE);
			else
				((Button) findViewById(R.id.m_health)).setVisibility(View.GONE);

		}
		catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (StreamCorruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	//	private void connectSystem() {
	//		
	//		sm.progressStart(WalletMain.this, "Connecting to SAFE system");
	//		SharedMethods.serverIpAddress = sm.readConfigurationFile(WalletMain.this, SAFEIPNO, CONFIGFILENAME);
	//
	//		Thread t = new Thread(new Runnable() {
	//			@Override
	//			public void run() {
	//				if (sm.checkInternetConnection(WalletMain.this)) {
	//
	//					if (SharedMethods.socket == null || SharedMethods.socket.isClosed()) {
	//						if (!sm.createSession()) {
	//							sm.progressStop();
	//							handlermsg = new Message();
	//							handlermsg.what = SERVERERROR;
	//							handler.sendMessage(handlermsg);
	//						}
	//						else {
	//							sm.progressStop();
	//						}
	//					}
	//					sm.progressStop();
	//
	//				}
	//				else {
	//					sm.progressStop();
	//
	//					startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
	//
	//				}
	//			}
	//
	//		});
	//		t.start(); // Start it running
	//	}

	@Override
	public void onStart() {
		super.onStart();

		//		connectSystem();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();

	}

	@Override
	public void onResume() {
		super.onResume();

		if (SharedMethods.login) {
			Intent i = new Intent().setClass(getApplicationContext(), Login.class);
			startActivity(i);
			finish();
			SharedMethods.secretKey = null;

		}
		else
			SharedMethods.login = true;
	}
}