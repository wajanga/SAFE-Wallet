package com.setecs.mobile.wallet.paymobile;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import com.setecs.mobile.walletDev.R;
import com.setecs.mobile.wallet.market.database.Constants;
import com.setecs.mobile.wallet.market.database.MyDB;


public class GiftcardPay extends ListActivity {

	private MyDB dba;
	private Cursor cursor;
	private String amount;
	long id;
	private ProgressDialog progress;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.generic_list);

		TextView empty = (TextView) getListView().getEmptyView();
		empty.setText(R.string.no_giftcards);

		dba = new MyDB(this);
		dba.open();
		fillData();
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		cursor = (Cursor) this.getListAdapter().getItem(position);
		givePrompt("Do you want to use this m-Gift Card for payment?");
	}

	private void fillData() {
		cursor = dba.getGiftcards();
		startManagingCursor(cursor);

		String[] from = new String[] { Constants.MERCHANT, Constants.DESCRIPTION, Constants.AMOUNT };
		int[] to = new int[] { R.id.merchant, R.id.description, R.id.amount };

		// Now create an array adapter and set it to display using our row
		SimpleCursorAdapter proms = new SimpleCursorAdapter(this, R.layout.giftcard_row, cursor, from, to);
		setListAdapter(proms);
	}

	@Override
	public void onBackPressed() {
		// do something on back.
		Intent i = new Intent(this, PayOptions.class);
		startActivity(i);
		return;
	}

	private void givePrompt(String text_to_display) {
		new AlertDialog.Builder(GiftcardPay.this).setTitle("Prompt").setMessage(text_to_display)
				.setPositiveButton("OK", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int arg1) {
						dialog.cancel();
						progress = ProgressDialog.show(GiftcardPay.this, "Processing...", "Please Wait", true, false);
						new processGiftTask().execute();
					}
				}).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int id) {
						dialog.cancel();
					}
				}).show();
	}

	private class processGiftTask extends AsyncTask<Void, Void, String> {

		@Override
		protected void onPreExecute() {

		}

		@Override
		protected void onPostExecute(String result) {
			cursor.close();
			dba.close();
			progress.cancel();
			Intent i = new Intent(GiftcardPay.this, OTAPayWallet.class);
			i.putExtra("amount", amount);
			startActivity(i);
		}

		@Override
		protected String doInBackground(Void... arg0) {
			if (cursor.moveToFirst()) {
				id = cursor.getLong(cursor.getColumnIndex(Constants.KEY_ID));
				amount = cursor.getString(cursor.getColumnIndex(Constants.AMOUNT));
			}
			String result = "Processing done";
			dba.deleteGiftcard(id);
			return result;
		}
	}

}
