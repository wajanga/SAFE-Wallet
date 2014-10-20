package com.setecs.mobile.wallet.market.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;


public class MyDB {

	private SQLiteDatabase db;
	private final Context context;
	private final MyDBHelper dbhelper;

	public MyDB(Context c) {
		context = c;
		dbhelper = new MyDBHelper(context, Constants.DATABASE_NAME, null, Constants.DATABASE_VERSION);
	}

	public void close() {
		db.close();
	}

	public void open() throws SQLiteException {
		try {
			db = dbhelper.getWritableDatabase();
		}
		catch (SQLiteException ex) {
			Log.v("Open database exception caught", ex.getMessage());
			db = dbhelper.getReadableDatabase();
		}
	}

	public long insertProm(String prom_id, String merchant, String merchant_name, String description, String startdate,
			String enddate, String img_name) {
		try {
			ContentValues newTaskValue = new ContentValues();
			newTaskValue.put(Constants.PROM_ID, prom_id);
			newTaskValue.put(Constants.MERCHANT, merchant);
			newTaskValue.put(Constants.MERCHANT_NAME, merchant_name);
			newTaskValue.put(Constants.DESCRIPTION, description);
			newTaskValue.put(Constants.START_DATE, startdate);
			newTaskValue.put(Constants.END_DATE, enddate);
			newTaskValue.put(Constants.IMAGE_NAME, img_name);
			return db.insert(Constants.TABLE_NAME_PROM, null, newTaskValue);
		}
		catch (SQLiteException ex) {
			Log.v("Insert into database exception caught", ex.getMessage());
			return -1;
		}
	}

	public long insertAd(String ad_id, String merchant, String merchant_name, String description, String startdate,
			String enddate, String img_name) {
		try {
			ContentValues newTaskValue = new ContentValues();
			newTaskValue.put(Constants.AD_ID, ad_id);
			newTaskValue.put(Constants.MERCHANT, merchant);
			newTaskValue.put(Constants.MERCHANT_NAME, merchant_name);
			newTaskValue.put(Constants.DESCRIPTION, description);
			newTaskValue.put(Constants.START_DATE, startdate);
			newTaskValue.put(Constants.END_DATE, enddate);
			newTaskValue.put(Constants.IMAGE_NAME, img_name);
			return db.insert(Constants.TABLE_NAME_AD, null, newTaskValue);
		}
		catch (SQLiteException ex) {
			Log.v("Insert into database exception caught", ex.getMessage());
			return -1;
		}
	}

	public long insertCoupon(String coupon_id, String merchant_name, String description, String startdate,
			String enddate, String amount, String merchant_account, String image_name, String qr_name) {
		try {
			ContentValues newTaskValue = new ContentValues();
			newTaskValue.put(Constants.COUPON_ID, coupon_id);
			newTaskValue.put(Constants.MERCHANT_NAME, merchant_name);
			newTaskValue.put(Constants.DESCRIPTION, description);
			newTaskValue.put(Constants.START_DATE, startdate);
			newTaskValue.put(Constants.END_DATE, enddate);
			newTaskValue.put(Constants.AMOUNT, amount);
			newTaskValue.put(Constants.MERCHANT_ACCOUNT, merchant_account);
			newTaskValue.put(Constants.IMAGE_NAME, image_name);
			newTaskValue.put(Constants.QR_NAME, qr_name);
			return db.insert(Constants.TABLE_NAME_COUPON, null, newTaskValue);
		}
		catch (SQLiteException ex) {
			Log.v("Insert into database exception caught", ex.getMessage());
			return -1;
		}
	}

	public long insertGiftcard(String giftcard_id, String merchant_name, String description, String amount,
			String merchant_account, String image_name, String qr_name) {
		try {
			ContentValues newTaskValue = new ContentValues();
			newTaskValue.put(Constants.GIFTCARD_ID, giftcard_id);
			newTaskValue.put(Constants.MERCHANT_NAME, merchant_name);
			newTaskValue.put(Constants.DESCRIPTION, description);
			newTaskValue.put(Constants.AMOUNT, amount);
			newTaskValue.put(Constants.MERCHANT_ACCOUNT, merchant_account);
			newTaskValue.put(Constants.IMAGE_NAME, image_name);
			newTaskValue.put(Constants.QR_NAME, qr_name);
			return db.insert(Constants.TABLE_NAME_GIFTCARD, null, newTaskValue);
		}
		catch (SQLiteException ex) {
			Log.v("Insert into database exception caught", ex.getMessage());
			return -1;
		}
	}

	public long insertTicket(String ticket_id, String merchant_name, String description, String startdate, String time,
			String amount, String merchant_account, String image_name, String qr_name) {
		try {
			ContentValues newTaskValue = new ContentValues();
			newTaskValue.put(Constants.TICKET_ID, ticket_id);
			newTaskValue.put(Constants.MERCHANT_NAME, merchant_name);
			newTaskValue.put(Constants.DESCRIPTION, description);
			newTaskValue.put(Constants.START_DATE, startdate);
			newTaskValue.put(Constants.TIME, time);
			newTaskValue.put(Constants.AMOUNT, amount);
			newTaskValue.put(Constants.MERCHANT_ACCOUNT, merchant_account);
			newTaskValue.put(Constants.IMAGE_NAME, image_name);
			newTaskValue.put(Constants.QR_NAME, qr_name);
			return db.insert(Constants.TABLE_NAME_TICKET, null, newTaskValue);
		}
		catch (SQLiteException ex) {
			Log.v("Insert into database exception caught", ex.getMessage());
			return -1;
		}
	}

	public long insertBankCard(String bank_card_id, String card_brand, String card_type, String issuing_bank,
			String card_number, String auth_code, String exp_month, String exp_year) {
		try {
			ContentValues newTaskValue = new ContentValues();
			newTaskValue.put(Constants.CARD_ID, bank_card_id);
			newTaskValue.put(Constants.CARD_BRAND, card_brand);
			newTaskValue.put(Constants.CARD_TYPE, card_type);
			newTaskValue.put(Constants.ISSUING_BANK, issuing_bank);
			newTaskValue.put(Constants.CARD_NUMBER, card_number);
			newTaskValue.put(Constants.AUTH_CODE, auth_code);
			newTaskValue.put(Constants.EXP_MONTH, exp_month);
			newTaskValue.put(Constants.EXP_YEAR, exp_year);
			return db.insert(Constants.TABLE_NAME_BANK_CARD, null, newTaskValue);
		}
		catch (SQLiteException ex) {
			Log.v("Insert into database exception caught", ex.getMessage());
			return -1;
		}
	}

	public long insertBankAccount(String bank_account_id, String bank_iban, String bank_account_number,
			String bank_name, String balance) {
		try {
			ContentValues newTaskValue = new ContentValues();
			newTaskValue.put(Constants.BANK_ACCOUNT_ID, bank_account_id);
			newTaskValue.put(Constants.BANK_IBAN, bank_iban);
			newTaskValue.put(Constants.BANK_ACCOUNT_NUMBER, bank_account_number);
			newTaskValue.put(Constants.BANK_NAME, bank_name);
			newTaskValue.put(Constants.BANK_ACCOUNT_BALANCE, balance);
			return db.insert(Constants.TABLE_NAME_BANK_ACCOUNT, null, newTaskValue);
		}
		catch (SQLiteException ex) {
			Log.v("Insert into database exception caught", ex.getMessage());
			return -1;
		}
	}

	public void deleteProm(long rowId) {
		db.delete(Constants.TABLE_NAME_PROM, Constants.KEY_ID + "=" + rowId, null);
	}

	public void deleteAd(long rowId) {
		db.delete(Constants.TABLE_NAME_AD, Constants.KEY_ID + "=" + rowId, null);
	}

	public void deleteCoupon(long rowId) {
		db.delete(Constants.TABLE_NAME_COUPON, Constants.KEY_ID + "=" + rowId, null);
	}

	public void deleteGiftcard(long rowId) {
		db.delete(Constants.TABLE_NAME_GIFTCARD, Constants.KEY_ID + "=" + rowId, null);
	}

	public void deleteTicket(long rowId) {
		db.delete(Constants.TABLE_NAME_TICKET, Constants.KEY_ID + "=" + rowId, null);
	}

	public void deleteBankCard(long rowId) {
		db.delete(Constants.TABLE_NAME_BANK_CARD, Constants.KEY_ID + "=" + rowId, null);
	}

	public void deleteBankAccount(long rowId) {
		db.delete(Constants.TABLE_NAME_BANK_ACCOUNT, Constants.KEY_ID + "=" + rowId, null);
	}

	public Cursor getProms() {
		Cursor c = db.query(Constants.TABLE_NAME_PROM, null, null, null, null, null, null);
		return c;
	}

	public Cursor getAds() {
		Cursor c = db.query(Constants.TABLE_NAME_AD, null, null, null, null, null, null);
		return c;
	}

	public Cursor getCoupons() {
		Cursor c = db.query(Constants.TABLE_NAME_COUPON, null, null, null, null, null, null);
		return c;
	}

	public Cursor getGiftcards() {
		Cursor c = db.query(Constants.TABLE_NAME_GIFTCARD, null, null, null, null, null, null);
		return c;
	}

	/*public Cursor getTokens(String token_cat) {
		Cursor c = db.query(Constants.TABLE_NAME_TOKEN, null, Constants.TOKEN_CAT + "=" + token_cat, null, null, null,
				null);
		return c;
	}*/

	public Cursor getTickets() {
		Cursor c = db.query(Constants.TABLE_NAME_TICKET, null, null, null, null, null, null);
		return c;
	}

	public Cursor getBankCards() {
		Cursor c = db.query(Constants.TABLE_NAME_BANK_CARD, null, null, null, null, null, null);
		return c;
	}

	public Cursor getBankAccounts() {
		Cursor c = db.query(Constants.TABLE_NAME_BANK_ACCOUNT, null, null, null, null, null, null);
		return c;
	}

	public boolean checkProm(String prom_id) {
		boolean result = false;
		Cursor c = db.query(Constants.TABLE_NAME_PROM, new String[] { Constants.KEY_ID }, Constants.PROM_ID + "="
				+ prom_id, null, null, null, null);
		if (c.getCount() > 0) {
			result = true;
			return result;
		}
		return result;
	}

	public boolean checkAd(String ad_id) {
		boolean result = false;
		Cursor c = db.query(Constants.TABLE_NAME_AD, new String[] { Constants.KEY_ID }, Constants.AD_ID + "=" + ad_id,
				null, null, null, null);
		if (c.getCount() > 0) {
			result = true;
			return result;
		}
		return result;
	}

	public boolean checkCoupon(String coupon_id) {
		boolean result = false;
		Cursor c = db.query(Constants.TABLE_NAME_COUPON, new String[] { Constants.KEY_ID }, Constants.COUPON_ID + "="
				+ coupon_id, null, null, null, null);
		if (c.getCount() > 0) {
			result = true;
			return result;
		}
		return result;
	}

	public boolean checkGiftcard(String token_id) {
		boolean result = false;
		Cursor c = db.query(Constants.TABLE_NAME_GIFTCARD, new String[] { Constants.KEY_ID },
				Constants.GIFTCARD_ID + "=" + token_id, null, null, null, null);
		if (c.getCount() > 0) {
			result = true;
			return result;
		}
		return result;
	}

	public boolean checkTicket(String token_id) {
		boolean result = false;
		Cursor c = db.query(Constants.TABLE_NAME_TICKET, new String[] { Constants.KEY_ID }, Constants.TICKET_ID + "="
				+ token_id, null, null, null, null);
		if (c.getCount() > 0) {
			result = true;
			return result;
		}
		return result;
	}

	public boolean checkBankCard(String bank_card_id) {
		boolean result = false;
		Cursor c = db.query(Constants.TABLE_NAME_BANK_CARD, new String[] { Constants.KEY_ID }, Constants.CARD_ID + "="
				+ bank_card_id, null, null, null, null);
		if (c.getCount() > 0) {
			result = true;
			return result;
		}
		return result;
	}

	public boolean checkBankAccount(String bank_account_id) {
		boolean result = false;
		Cursor c = db.query(Constants.TABLE_NAME_BANK_ACCOUNT, new String[] { Constants.KEY_ID },
				Constants.BANK_ACCOUNT_ID + "=" + bank_account_id, null, null, null, null);
		if (c.getCount() > 0) {
			result = true;
			return result;
		}
		return result;
	}

	public int editCoupon(long rowId, String amount) {
		try {
			ContentValues newTaskValue = new ContentValues();
			newTaskValue.put(Constants.AMOUNT, amount);
			return db.update(Constants.TABLE_NAME_COUPON, newTaskValue, Constants.KEY_ID + "=" + rowId, null);
		}
		catch (SQLiteException ex) {
			Log.v("Update into database exception caught", ex.getMessage());
			return 0;
		}
	}

	public int editGiftcard(long rowId, String amount) {
		try {
			ContentValues newTaskValue = new ContentValues();
			newTaskValue.put(Constants.AMOUNT, amount);
			return db.update(Constants.TABLE_NAME_GIFTCARD, newTaskValue, Constants.KEY_ID + "=" + rowId, null);
		}
		catch (SQLiteException ex) {
			Log.v("Update into database exception caught", ex.getMessage());
			return 0;
		}
	}

}
