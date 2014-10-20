package com.setecs.mobile.wallet.mmoney;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.setecs.mobile.walletDev.R;


public class mMoney extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

		setContentView(R.layout.m_money);
		((Button) findViewById(R.id.Load_Cash)).setOnClickListener(mMoneyButtonListener);
		((Button) findViewById(R.id.Unload_Cash)).setOnClickListener(mMoneyButtonListener);
		((Button) findViewById(R.id.View_Stored_Money)).setOnClickListener(mMoneyButtonListener);
	}

	protected OnClickListener mMoneyButtonListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			Integer buttonId = ((Button) v).getId();

			if (buttonId == R.id.Load_Cash) {
				Load_Cash();
			}
			if (buttonId == R.id.Unload_Cash) {
				Unload_Cash();
			}
			if (buttonId == R.id.View_Stored_Money) {
				View_Stored_Money();
			}

		}

	};

	protected void Load_Cash() {
		startActivity(new Intent(getApplicationContext(), LoadCash.class));
	}

	protected void View_Stored_Money() {
		startActivity(new Intent(getApplicationContext(), ViewStoredMoney.class));
	}

	protected void Unload_Cash() {
		startActivity(new Intent(getApplicationContext(), UnloadCash.class));
	}

}
