package com.setecs.mobile.wallet.market.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class MyDBHelper extends SQLiteOpenHelper {

	private static final String CREATE_TABLE_PROM = "create table " + Constants.TABLE_NAME_PROM
			+ " ("
			+ Constants.KEY_ID
			+ " integer primary key autoincrement, "
			+ Constants.PROM_ID
			+ " text not null, "
			+ Constants.MERCHANT_ACCOUNT
			+ " text not null, "
			+ Constants.MERCHANT_NAME
			+ " text not null, "
			+ Constants.DESCRIPTION
			+ " text not null, "
			+ Constants.START_DATE
			+ " text not null, "
			+ Constants.END_DATE
			+ " text not null, "
			+ Constants.IMAGE_NAME
			+ " text);";

	private static final String CREATE_TABLE_AD = "create table " + Constants.TABLE_NAME_AD
			+ " ("
			+ Constants.KEY_ID
			+ " integer primary key autoincrement, "
			+ Constants.AD_ID
			+ " text not null, "
			+ Constants.MERCHANT_ACCOUNT
			+ " text not null, "
			+ Constants.MERCHANT_NAME
			+ " text not null, "
			+ Constants.DESCRIPTION
			+ " text not null, "
			+ Constants.START_DATE
			+ " text not null, "
			+ Constants.END_DATE
			+ " text not null, "
			+ Constants.IMAGE_NAME
			+ " text);";

	private static final String CREATE_TABLE_COUPON = "create table " + Constants.TABLE_NAME_COUPON
			+ " ("
			+ Constants.KEY_ID
			+ " integer primary key autoincrement, "
			+ Constants.COUPON_ID
			+ " text not null, "
			+ Constants.MERCHANT_ACCOUNT
			+ " text not null, "
			+ Constants.MERCHANT_NAME
			+ " text not null, "
			+ Constants.DESCRIPTION
			+ " text not null, "
			+ Constants.START_DATE
			+ " text not null, "
			+ Constants.END_DATE
			+ " text not null, "
			+ Constants.AMOUNT
			+ " long, "
			+ Constants.DISCOUNT
			+ " long, "
			+ Constants.VALUE
			+ " text, "
			+ Constants.IMAGE_NAME
			+ " text, "
			+ Constants.QR_NAME
			+ " text); ";

	private static final String CREATE_TABLE_GIFTCARD = "create table " + Constants.TABLE_NAME_GIFTCARD
			+ " ("
			+ Constants.KEY_ID
			+ " integer primary key autoincrement, "
			+ Constants.GIFTCARD_ID
			+ " text not null, "
			+ Constants.MERCHANT_NAME
			+ " text not null, "
			+ Constants.DESCRIPTION
			+ " text not null, "
			+ Constants.AMOUNT
			+ " long, "
			+ Constants.MERCHANT_ACCOUNT
			+ " text not null, "
			+ Constants.IMAGE_NAME
			+ " text, "
			+ Constants.QR_NAME
			+ " text); ";

	private static final String CREATE_TABLE_TICKET = "create table " + Constants.TABLE_NAME_TICKET
			+ " ("
			+ Constants.KEY_ID
			+ " integer primary key autoincrement, "
			+ Constants.TICKET_ID
			+ " text not null, "
			+ Constants.MERCHANT_NAME
			+ " text not null, "
			+ Constants.DESCRIPTION
			+ " text not null, "
			+ Constants.DATE
			+ " text not null, "
			+ Constants.TIME
			+ " text not null, "
			+ Constants.AMOUNT
			+ " long, "
			+ Constants.MERCHANT_ACCOUNT
			+ " text not null, "
			+ Constants.IMAGE_NAME
			+ " text, "
			+ Constants.QR_NAME
			+ " text); ";

	private static final String CREATE_TABLE_BANK_CARD = "create table " + Constants.TABLE_NAME_BANK_CARD
			+ " ("
			+ Constants.KEY_ID
			+ " integer primary key autoincrement, "
			+ Constants.CARD_ID
			+ " text not null, "
			+ Constants.CARD_BRAND
			+ " text not null, "
			+ Constants.CARD_TYPE
			+ " text not null, "
			+ Constants.ISSUING_BANK
			+ " text not null, "
			+ Constants.CARD_NUMBER
			+ " text not null, "
			+ Constants.AUTH_CODE
			+ " text not null, "
			+ Constants.EXP_MONTH
			+ " text not null, "
			+ Constants.EXP_YEAR
			+ " text not null); ";

	private static final String CREATE_TABLE_BANK_ACCOUNT = "create table " + Constants.TABLE_NAME_BANK_ACCOUNT
			+ " ("
			+ Constants.KEY_ID
			+ " integer primary key autoincrement, "
			+ Constants.BANK_ACCOUNT_ID
			+ " text, "
			+ Constants.BANK_IBAN
			+ " text not null, "
			+ Constants.BANK_ACCOUNT_NUMBER
			+ " text not null, "
			+ Constants.BANK_ACCOUNT_BALANCE
			+ " text, "
			+ Constants.BANK_NAME
			+ " text not null); ";

	public MyDBHelper(Context context, String name, CursorFactory factory, int version) {
		super(context, name, factory, version);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		Log.v("MyDBhelper onCreate", "Creating all the tables");
		try {
			db.execSQL(CREATE_TABLE_AD);
			db.execSQL(CREATE_TABLE_PROM);
			db.execSQL(CREATE_TABLE_COUPON);
			db.execSQL(CREATE_TABLE_GIFTCARD);
			db.execSQL(CREATE_TABLE_TICKET);
			db.execSQL(CREATE_TABLE_BANK_CARD);
			db.execSQL(CREATE_TABLE_BANK_ACCOUNT);
		}
		catch (SQLiteException ex) {
			Log.v("Create table exception", ex.getMessage());
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.w("Wallet Database", "Upgrading from version " + oldVersion
				+ " to "
				+ newVersion
				+ ", which will destroy all old data");
		db.execSQL("drop table if exists " + Constants.TABLE_NAME_AD);
		db.execSQL("drop table if exists " + Constants.TABLE_NAME_PROM);
		db.execSQL("drop table if exists " + Constants.TABLE_NAME_COUPON);
		db.execSQL("drop table if exists " + Constants.TABLE_NAME_GIFTCARD);
		db.execSQL("drop table if exists " + Constants.TABLE_NAME_TICKET);
		db.execSQL("drop table if exists " + Constants.TABLE_NAME_BANK_CARD);
		db.execSQL("drop table if exists " + Constants.TABLE_NAME_BANK_ACCOUNT);
		onCreate(db);
	}

}
