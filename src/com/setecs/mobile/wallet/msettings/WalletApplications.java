package com.setecs.mobile.wallet.msettings;

import static com.setecs.mobile.wallet.safe.wallet.Constants.APPLICATIONSFILENAME;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;
import java.util.HashMap;
import java.util.Iterator;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.setecs.mobile.safe.apps.shared.DisplaySAFEResponse;
import com.setecs.mobile.walletDev.R;


public class WalletApplications extends Activity {

	private static final String TAG = "WalletApplications";
	private CompoundButton mShopp;
	private CheckBox mClub;
	private CheckBox mHealth;
	private String mShopptxt = "";
	private String mClubtxt = "";
	private String mHealthtxt = "";
	HashMap<String, String> hmap = new HashMap<String, String>();
	private final DisplaySAFEResponse ds = new DisplaySAFEResponse();

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		setContentView(R.layout.wallet_applications);
		((Button) findViewById(R.id.btnSave)).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				saveWalletApplications();
			}
		});
		mShopp = (CheckBox) findViewById(R.id.mShopp);
		mClub = (CheckBox) findViewById(R.id.mClub);
		mHealth = (CheckBox) findViewById(R.id.mHealth);
		mShopp.setChecked(true);
		mClub.setChecked(true);
		mHealth.setChecked(true);
		loadOptions();

	}

	private void loadOptions() {
		
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
				mShopp.setChecked(true);
			else
				mShopp.setChecked(false);
			if (mClubtxt.equals("1"))
				mClub.setChecked(true);
			else
				mClub.setChecked(false);
			if (mHealthtxt.equals("1"))
				mHealth.setChecked(true);
			else
				mHealth.setChecked(false);

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

	protected void saveWalletApplications() {
		
		if (mShopp.isChecked())
			mShopptxt = "1";
		else
			mShopptxt = "0";
		if (mClub.isChecked())
			mClubtxt = "1";
		else
			mClubtxt = "0";
		if (mHealth.isChecked())
			mHealthtxt = "1";
		else
			mHealthtxt = "0";

		hmap.put("mshop", mShopptxt);
		hmap.put("mclub", mClubtxt);
		hmap.put("mhealth", mHealthtxt);

		try {
			FileOutputStream fStream = openFileOutput(APPLICATIONSFILENAME, Context.MODE_PRIVATE);
			ObjectOutputStream oStream = new ObjectOutputStream(fStream);
			oStream.writeObject(hmap);

			oStream.flush();
			oStream.close();
			fStream.close();

		}
		catch (Exception e) {
			Log.e(TAG, "saveWalletConfiguration", e);

		}
		ds.displaySAFErespons(WalletApplications.this, "Configuration saved successfully", "Wallet Applications");

	}
}
