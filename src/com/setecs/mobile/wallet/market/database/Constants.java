package com.setecs.mobile.wallet.market.database;

public class Constants {

	public static final String DATABASE_NAME = "datastorage";
	public static final int DATABASE_VERSION = 28;

	public static final String TABLE_NAME_PROM = "promotion";
	public static final String TABLE_NAME_AD = "ad";
	public static final String TABLE_NAME_COUPON = "coupon";
	public static final String TABLE_NAME_GIFTCARD = "giftcard";
	//public static final String TABLE_NAME_TOKEN = "token";
	public static final String TABLE_NAME_TICKET = "ticket";
	public static final String TABLE_NAME_BANK_CARD = "bank_card";
	public static final String TABLE_NAME_BANK_ACCOUNT = "bank_account";

	public static final String KEY_ID = "_id";
	public static final String PROM_ID = "promid";
	public static final String COUPON_ID = "couponid";
	public static final String GIFTCARD_ID = "giftcardid";
	public static final String TICKET_ID = "ticketid";
	//public static final String TOKEN_ID = "tokenid";
	//public static final String TOKEN_CAT = "tokencat";
	public static final String MERCHANT = "merchant";
	public static final String MERCHANT_NAME = "merchant_name";
	public static final String DESCRIPTION = "description";
	public static final String DATE = "date";
	public static final String START_DATE = "start_date";
	public static final String STARTDATE = "startdate";
	public static final String END_DATE = "end_date";
	public static final String AMOUNT = "amount";
	public static final String VALUE = "value";
	public static final String QR_NAME = "qr_name";
	public static final String DISCOUNT = "discount";
	public static final String MERCHANT_ACCOUNT = "merchant_account";
	public static final String TIME = "time";
	public static final String IMAGE_NAME = "image_name";
	public static final String IMG_NAME = "img_name";

	//for bank_card table
	public static final String CARD_ID = "card_id";
	public static final String CARD_BRAND = "card_brand";
	public static final String CARD_TYPE = "card_type";
	public static final String ISSUING_BANK = "issuing_bank";
	public static final String CARD_NUMBER = "card_number";
	public static final String AUTH_CODE = "auth_code";
	public static final String EXP_MONTH = "exp_month";
	public static final String EXP_YEAR = "exp_year";

	//for bank_account table
	public static final String BANK_ACCOUNT_ID = "bank_account_id";
	public static final String BANK_IBAN = "bank_iban";
	public static final String BANK_ACCOUNT_NUMBER = "bank_account_number";
	public static final String BANK_NAME = "bank_name";
	public static final String BANK_ACCOUNT_BALANCE = "balance";

	//for ad table
	public static final String AD_ID = "ad_id";
}
