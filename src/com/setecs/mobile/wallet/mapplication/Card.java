package com.setecs.mobile.wallet.mapplication;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.setecs.mobile.walletDev.R;
import com.setecs.mobile.wallet.market.database.MyDB;


public class Card extends Activity {

	private MyDB dba;
	private Intent i;
	private long id;
	private TextView t1, t2, t3;
	private ProgressDialog progress;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.card);

		TextView t = (TextView) findViewById(R.id.widget236);
		t.setText("View / Delete Card");

		dba = new MyDB(this);
		dba.open();

		t1 = (TextView) findViewById(R.id.card_brand);
		t2 = (TextView) findViewById(R.id.issuing_bank);
		t3 = (TextView) findViewById(R.id.number);

		i = getIntent();
		id = i.getLongExtra("id", 0);
		t1.setText("Card Brand: " + i.getStringExtra("card_brand"));
		t2.setText("Issuing Bank: " + i.getStringExtra("issuing_bank"));
		t3.setText(i.getStringExtra("card_number"));
	}

	public void delete(View v) {
		confirmDelete();
	}

	public void ok(View v) {
		Intent i = new Intent(this, ListCards.class);
		startActivity(i);
	}

	@Override
	public void onBackPressed() {
		// do something on back.
		Intent i = new Intent(this, ListCards.class);
		startActivity(i);
		return;
	}

	public void deleteCard() {
		progress = ProgressDialog.show(this, "Deleting", "Please Wait", true, false);
		new deleteTask().execute();
	}

	private void confirmDelete() {
		
		final AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage("Are you sure you want to delete?").setCancelable(false)
				.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(final DialogInterface dialog, final int id) {
						deleteCard();
					}
				}).setNegativeButton("No", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(final DialogInterface dialog, final int id) {
						dialog.cancel();
					}
				});
		final AlertDialog alert = builder.create();
		alert.show();
	}

	private void giveFeedback(String text_to_display) {
		progress.cancel();
		new AlertDialog.Builder(this).setTitle("Success").setMessage(text_to_display)
				.setPositiveButton("OK", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int arg1) {
						dialog.cancel();
						Intent i = new Intent(Card.this, ListCards.class);
						startActivity(i);
					}
				}).show();
	}

	private class deleteTask extends AsyncTask<Void, Void, String> {

		@Override
		protected void onPreExecute() {

		}

		@Override
		protected void onPostExecute(String result) {
			giveFeedback(result);
		}

		@Override
		protected String doInBackground(Void... arg0) {
			String result = "Bank Card deleted successfully";
			dba.deleteBankCard(id);
			dba.close();
			return result;
		}
	}

}
