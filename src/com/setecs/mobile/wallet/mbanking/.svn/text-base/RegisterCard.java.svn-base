package com.setecs.mobile.wallet.mbanking;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.setecs.mobile.walletDev.R;


public class RegisterCard extends Activity {

	private EditText cardNumber;
	private EditText cardAuthCode;
	private EditText cardExpDateMonth;
	private EditText cardExpDateYear;
	private String cardNumber_msg;
	private String cardAuthCode_msg;
	private String cardExpDateMonth_msg;
	private String cardExpDateYear_msg;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

		setContentView(R.layout.register_card);
		final Spinner spinner = (Spinner) findViewById(R.id.cardspinner);

		/*ArrayAdapter<CharSequence> adapter = new ArrayAdapter<CharSequence>(this, android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		adapter.add("VISA");
		adapter.add("Master Card");
		adapter.add("American Express");
		adapter.add("Discover");

		spinner.setAdapter(adapter);*/

		spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				TextView temp = (TextView) arg1;

				temp.getText().toString();

			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			

			}

		});

		cardNumber = (EditText) findViewById(R.id.cardnum);
		cardAuthCode = (EditText) findViewById(R.id.authorcode);
		cardExpDateMonth = (EditText) findViewById(R.id.exdate1);
		cardExpDateYear = (EditText) findViewById(R.id.exdate2);

		cardNumber_msg = cardNumber.getText().toString();
		cardAuthCode_msg = cardAuthCode.getText().toString();
		cardExpDateMonth_msg = cardExpDateMonth.getText().toString();
		cardExpDateYear_msg = cardExpDateYear.getText().toString();

		((Button) findViewById(R.id.btn_cardreg)).setOnClickListener(regCardButtonListener);

	}

	protected OnClickListener regCardButtonListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			Integer buttonId = ((Button) v).getId();

			if (buttonId == R.id.btn_cardreg) {
				registerCard(cardNumber_msg, cardAuthCode_msg, cardExpDateMonth_msg, cardExpDateYear_msg);
			}

		}
	};

	protected void registerCard(String cardNumber_msg, String cardAuthCode_msg, String cardExpDateMonth_msg,
			String cardExpDateYear_msg) {

		AlertDialog.Builder alert = new AlertDialog.Builder(this);

		alert.setTitle("SAFE Message");
		alert.setMessage(R.string.message_unavailable);

		alert.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int whichButton) {
				finish();
			}
		});

		alert.show();

	}

}
