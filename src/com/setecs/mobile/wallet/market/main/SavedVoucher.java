package com.setecs.mobile.wallet.market.main;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.view.View;
import android.widget.ListView;

import com.setecs.mobile.wallet.market.advertisement.Ad;
import com.setecs.mobile.wallet.market.advertisement.MarketAd;
import com.setecs.mobile.wallet.market.coupons.Coupon;
import com.setecs.mobile.wallet.market.coupons.MarketCoupons;
import com.setecs.mobile.wallet.market.database.Constants;
import com.setecs.mobile.wallet.market.database.MyDB;
import com.setecs.mobile.wallet.market.giftcards.Giftcard;
import com.setecs.mobile.wallet.market.giftcards.MarketGifts;
import com.setecs.mobile.wallet.market.promotions.NearbyServiceList;
import com.setecs.mobile.wallet.market.promotions.Prom;
import com.setecs.mobile.wallet.market.tickets.MarketTickets;
import com.setecs.mobile.wallet.market.tickets.Ticket;


public class SavedVoucher extends ListActivity {

	public Cursor cursor;
	public MyDB dba;
	public Intent i;
	public long id;
	public String desc, start_date, end_date, date, merchant, merchant_name, amount, discount, time, img_name, qr_name;
	public final static int PROMOTION = 0, COUPON = 1, TICKET = 2, GIFTCARD = 3, AD = 4;
	public int choice;
	public ListAdapter adapter;
	public ArrayList<HashMap<String, String>> voucher_list;

	public void closeDatabase() {
		cursor.close();
		dba.close();
	}

	public void in_wallet(View v) {
		// Do nothing
	}

	public void setChoice(int choice) {
		this.choice = choice;
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		displayVoucher(choice, position);
	}

	public void displayVoucher(int choice, int position) {
		cursor.moveToPosition(position);

		id = cursor.getLong(cursor.getColumnIndex(Constants.KEY_ID));

		switch (choice) {
		case PROMOTION:
			merchant = cursor.getString(cursor.getColumnIndex(Constants.MERCHANT));
			merchant_name = cursor.getString(cursor.getColumnIndex(Constants.MERCHANT_NAME));
			desc = cursor.getString(cursor.getColumnIndex(Constants.DESCRIPTION));
			start_date = cursor.getString(cursor.getColumnIndex(Constants.START_DATE));
			end_date = cursor.getString(cursor.getColumnIndex(Constants.END_DATE));
			img_name = cursor.getString(cursor.getColumnIndex(Constants.IMAGE_NAME));
			closeDatabase();

			i = new Intent(this, Prom.class);
			i.putExtra("id", id);
			i.putExtra("desc", desc);
			i.putExtra("startdate", start_date);
			i.putExtra("enddate", end_date);
			i.putExtra("img_name", img_name);
			break;
		case AD:
			merchant = cursor.getString(cursor.getColumnIndex(Constants.MERCHANT));
			merchant_name = cursor.getString(cursor.getColumnIndex(Constants.MERCHANT_NAME));
			desc = cursor.getString(cursor.getColumnIndex(Constants.DESCRIPTION));
			start_date = cursor.getString(cursor.getColumnIndex(Constants.START_DATE));
			end_date = cursor.getString(cursor.getColumnIndex(Constants.END_DATE));
			img_name = cursor.getString(cursor.getColumnIndex(Constants.IMAGE_NAME));
			closeDatabase();

			i = new Intent(this, Ad.class);
			i.putExtra("id", id);
			i.putExtra("desc", desc);
			i.putExtra("startdate", start_date);
			i.putExtra("enddate", end_date);
			i.putExtra("img_name", img_name);
			break;
		case COUPON:
			merchant_name = cursor.getString(cursor.getColumnIndex(Constants.MERCHANT_NAME));
			desc = cursor.getString(cursor.getColumnIndex(Constants.DESCRIPTION));
			start_date = cursor.getString(cursor.getColumnIndex(Constants.START_DATE));
			end_date = cursor.getString(cursor.getColumnIndex(Constants.END_DATE));
			amount = cursor.getString(cursor.getColumnIndex(Constants.AMOUNT));
			discount = cursor.getString(cursor.getColumnIndex(Constants.DISCOUNT));
			img_name = cursor.getString(cursor.getColumnIndex(Constants.IMAGE_NAME));
			qr_name = cursor.getString(cursor.getColumnIndex(Constants.QR_NAME));
			closeDatabase();

			i = new Intent(this, Coupon.class);
			i.putExtra("id", id);
			i.putExtra("desc", desc);
			i.putExtra("startdate", start_date);
			i.putExtra("merchant_name", merchant_name);
			i.putExtra("enddate", end_date);
			if (amount.equals("null")) {
				Integer discountInt = (int) (Float.valueOf(discount) * 100);
				i.putExtra("amount", "Discount: " + String.valueOf(discountInt) + "%");
			}
			else {
				i.putExtra("amount", "Amount: " + amount + " EUROS");
			}
			i.putExtra("img_name", img_name);
			i.putExtra("qr_name", qr_name);
			break;
		case GIFTCARD:
			merchant_name = cursor.getString(cursor.getColumnIndex(Constants.MERCHANT_NAME));
			desc = cursor.getString(cursor.getColumnIndex(Constants.DESCRIPTION));
			date = cursor.getString(cursor.getColumnIndex(Constants.START_DATE));
			amount = cursor.getString(cursor.getColumnIndex(Constants.AMOUNT));
			img_name = cursor.getString(cursor.getColumnIndex(Constants.IMAGE_NAME));
			qr_name = cursor.getString(cursor.getColumnIndex(Constants.QR_NAME));
			closeDatabase();

			i = new Intent(this, Giftcard.class);
			i.putExtra("id", id);
			i.putExtra("merchant_name", merchant_name);
			i.putExtra("desc", desc);
			i.putExtra("date", date);
			i.putExtra("amount", amount);
			i.putExtra("img_name", img_name);
			i.putExtra("qr_name", qr_name);
			break;
		case TICKET:
			merchant_name = cursor.getString(cursor.getColumnIndex(Constants.MERCHANT_NAME));
			desc = cursor.getString(cursor.getColumnIndex(Constants.DESCRIPTION));
			amount = cursor.getString(cursor.getColumnIndex(Constants.AMOUNT));
			date = cursor.getString(cursor.getColumnIndex(Constants.START_DATE));
			time = cursor.getString(cursor.getColumnIndex(Constants.TIME));
			img_name = cursor.getString(cursor.getColumnIndex(Constants.IMAGE_NAME));
			qr_name = cursor.getString(cursor.getColumnIndex(Constants.QR_NAME));
			closeDatabase();

			i = new Intent(this, Ticket.class);
			i.putExtra("id", id);
			i.putExtra("merchant_name", merchant_name);
			i.putExtra("desc", desc);
			i.putExtra("amount", amount);
			i.putExtra("date", date);
			i.putExtra("time", time);
			i.putExtra("img_name", img_name);
			i.putExtra("qr_name", qr_name);
			break;
		}
		startActivity(i);
	}

	public void fillData(int choice) {
		//String[] from = null;
		switch (choice) {
		case PROMOTION:
			cursor = dba.getProms();
			//from = new String[] { Constants.MERCHANT_NAME, Constants.DESCRIPTION, Constants.END };
			break;
		case AD:
			cursor = dba.getAds();
			//from = new String[] { Constants.MERCHANT_NAME, Constants.DESCRIPTION, Constants.END };
			break;
		case COUPON:
			cursor = dba.getCoupons();
			//from = new String[] { Constants.MERCHANT_NAME, Constants.DESCRIPTION, Constants.END };
			break;
		case GIFTCARD:
			cursor = dba.getGiftcards();
			//from = new String[] { Constants.MERCHANT_NAME, Constants.DESCRIPTION, Constants.END };
			break;
		case TICKET:
			cursor = dba.getTickets();
			//from = new String[] { Constants.MERCHANT_NAME, Constants.DESCRIPTION, Constants.AMOUNT };
			break;
		}
		voucher_list = convertCursorToArrayList();
		adapter = new ListAdapter(this, voucher_list);
		setListAdapter(adapter);
	}

	public ArrayList<HashMap<String, String>> convertCursorToArrayList() {
		ArrayList<HashMap<String, String>> mArrayList = new ArrayList<HashMap<String, String>>();
		HashMap<String, String> map;
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			map = new HashMap<String, String>();
			map.put(Constants.MERCHANT_NAME, cursor.getString(cursor.getColumnIndex(Constants.MERCHANT_NAME)));
			map.put(Constants.DESCRIPTION, cursor.getString(cursor.getColumnIndex(Constants.DESCRIPTION)));
			map.put(Constants.START_DATE, cursor.getString(cursor.getColumnIndex(Constants.START_DATE)));
			map.put(Constants.IMAGE_NAME, cursor.getString(cursor.getColumnIndex(Constants.IMAGE_NAME)));
			mArrayList.add(map);
			cursor.moveToNext();
		}
		return mArrayList;
	}

	public void in_market(View v) {
		switch (choice) {
		case PROMOTION:
			i = new Intent(this, NearbyServiceList.class);
			break;
		case AD:
			i = new Intent(this, MarketAd.class);
			break;
		case COUPON:
			i = new Intent(this, MarketCoupons.class);
			break;
		case GIFTCARD:
			i = new Intent(this, MarketGifts.class);
			break;
		case TICKET:
			i = new Intent(this, MarketTickets.class);
			break;
		}
		startActivity(i);
	}

	@Override
	public void onBackPressed() {
		// do something on back.
		Intent i = new Intent(this, HomeActivity.class);
		startActivity(i);
		finish();
	}

}
