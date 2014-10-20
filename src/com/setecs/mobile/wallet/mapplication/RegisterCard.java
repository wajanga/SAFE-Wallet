package com.setecs.mobile.wallet.mapplication;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.EditText;
import android.widget.Spinner;

import com.setecs.mobile.walletDev.R;
import com.setecs.mobile.wallet.market.database.MyDB;
import com.setecs.mobile.wallet.market.utility.InputFilterMinMax;


public class RegisterCard extends Activity {

	private static final String TAG = "RegisterCard";
	private EditText card_number;
	private EditText auth_code;
	private EditText exp_month;
	private EditText exp_year;
	private String card_brand, card_type, issuing_bank;
	private MyDB dba;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

		setContentView(R.layout.register_card);
		openDatabase();

		final Spinner card_brand_spinner = (Spinner) findViewById(R.id.cardspinner);
		final Spinner card_type_spinner = (Spinner) findViewById(R.id.card_type);
		card_number = (EditText) findViewById(R.id.cardnum);
		auth_code = (EditText) findViewById(R.id.authorcode);
		exp_month = (EditText) findViewById(R.id.exdate1);
		exp_month.setFilters(new InputFilter[] { new InputFilterMinMax("1", "12") });

		exp_year = (EditText) findViewById(R.id.exdate2);
		final Spinner issuing_bank_spinner = (Spinner) findViewById(R.id.issuing_bank);

		issuing_bank_spinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
				issuing_bank = parent.getItemAtPosition(pos).toString();
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				issuing_bank = parent.getItemAtPosition(0).toString();
			}

		});

		card_brand_spinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
				card_brand = parent.getItemAtPosition(pos).toString();
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				card_brand = parent.getItemAtPosition(0).toString();
			}

		});

		card_type_spinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
				card_type = parent.getItemAtPosition(pos).toString();
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				card_type = parent.getItemAtPosition(0).toString();
			}

		});

	}

	public void registerCard(View v) {
		saveCard();
	}

	public void displaySaveConfirmation(String result) {

		AlertDialog.Builder alert = new AlertDialog.Builder(this);

		alert.setTitle("New Card Registration");
		alert.setMessage(result);

		alert.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int whichButton) {
				finish();
			}
		});

		alert.show();
	}

	private void showAlert(String result) {

		AlertDialog.Builder alert = new AlertDialog.Builder(this);

		alert.setTitle("Error");
		alert.setMessage(result);

		alert.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int whichButton) {}
		});

		alert.show();
	}

	private void saveCard() {
		String number = card_number.getText().toString();
		String code = auth_code.getText().toString();
		String month = exp_month.getText().toString();
		String year = exp_year.getText().toString();
		if (card_number.equals(null) || auth_code.equals(null) || exp_month.equals(null) || exp_year.equals(null)) {
			showAlert("You must enter all fields");
		}
		else {
			long i = dba.insertBankCard("card_id", card_brand, card_type, issuing_bank, number, code, month, year);
			dba.close();
			if (i != -1)
				displaySaveConfirmation("Card registered successfully");
		}
	}

	private void openDatabase() {
		dba = new MyDB(this);
		dba.open();
	}

}
