package com.setecs.mobile.wallet.mapplication;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.ListActivity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.setecs.mobile.walletDev.R;
import com.setecs.mobile.wallet.market.database.Constants;
import com.setecs.mobile.wallet.market.database.MyDB;


public class ListCards extends ListActivity {

	private static final String TAG = "ListCards";
	public ArrayList<HashMap<String, String>> card_list;
	CardListAdapter adapter;
	String FILENAME = "card_list_file";
	private Cursor cursor;
	private MyDB dba;
	private long id;
	private String card_brand, card_type, issuing_bank, card_number, auth_code, exp_month, exp_year;
	private Intent i;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

		setContentView(R.layout.list_cards);
		openDatabase();
		listCards();
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		/*Intent i = new Intent(this, Card.class);
		startActivity(i);*/
		displayVoucher(position);
	}

	private ArrayList<HashMap<String, String>> convertCursorToArrayList() {
		ArrayList<HashMap<String, String>> mArrayList = new ArrayList<HashMap<String, String>>();
		HashMap<String, String> map;
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			map = new HashMap<String, String>();
			map.put(Constants.CARD_BRAND, cursor.getString(cursor.getColumnIndex(Constants.CARD_BRAND)));
			map.put(Constants.CARD_TYPE, cursor.getString(cursor.getColumnIndex(Constants.CARD_TYPE)));
			map.put(Constants.ISSUING_BANK, cursor.getString(cursor.getColumnIndex(Constants.ISSUING_BANK)));
			map.put(Constants.CARD_NUMBER, cursor.getString(cursor.getColumnIndex(Constants.CARD_NUMBER)));
			map.put(Constants.AUTH_CODE, cursor.getString(cursor.getColumnIndex(Constants.AUTH_CODE)));
			map.put(Constants.EXP_MONTH, cursor.getString(cursor.getColumnIndex(Constants.EXP_MONTH)));
			map.put(Constants.EXP_YEAR, cursor.getString(cursor.getColumnIndex(Constants.EXP_YEAR)));
			mArrayList.add(map);
			cursor.moveToNext();
		}
		return mArrayList;
	}

	public void listCards() {
		cursor = dba.getBankCards();
		card_list = convertCursorToArrayList();
		adapter = new CardListAdapter(this, card_list);
		setListAdapter(adapter);
	}

	public void displayVoucher(int position) {
		cursor.moveToPosition(position);
		id = cursor.getLong(cursor.getColumnIndex(Constants.KEY_ID));
		card_brand = cursor.getString(cursor.getColumnIndex(Constants.CARD_BRAND));
		card_type = cursor.getString(cursor.getColumnIndex(Constants.CARD_TYPE));
		issuing_bank = cursor.getString(cursor.getColumnIndex(Constants.ISSUING_BANK));
		card_number = cursor.getString(cursor.getColumnIndex(Constants.CARD_NUMBER));
		auth_code = cursor.getString(cursor.getColumnIndex(Constants.AUTH_CODE));
		exp_month = cursor.getString(cursor.getColumnIndex(Constants.EXP_MONTH));
		exp_year = cursor.getString(cursor.getColumnIndex(Constants.EXP_YEAR));
		closeDatabase();

		i = new Intent(this, Card.class);
		i.putExtra("id", id);
		i.putExtra("card_brand", card_brand);
		i.putExtra("issuing_bank", issuing_bank);
		i.putExtra("card_number", card_number);

		startActivity(i);
	}

	private void openDatabase() {
		dba = new MyDB(this);
		dba.open();
	}

	private void closeDatabase() {
		cursor.close();
		dba.close();
	}

	@Override
	public void onBackPressed() {
		// do something on back.
		Intent i = new Intent(this, MApplication.class);
		startActivity(i);
		return;
	}

}
