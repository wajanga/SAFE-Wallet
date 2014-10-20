package com.setecs.mobile.wallet.market.advertisement;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.setecs.mobile.walletDev.R;


public class MarketAdNew extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.search_merchant_category);

		Spinner spinner = (Spinner) findViewById(R.id.search_category);
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.merchant_category,
				android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(adapter);
	}

	private class searchTask extends AsyncTask<Void, Void, String> {

		@Override
		protected void onPreExecute() {

		}

		@Override
		protected void onPostExecute(String result) {

		}

		@Override
		protected String doInBackground(Void... arg0) {
			String test = "test";
			return test;
		}
	}

}
