package com.setecs.mobile.wallet.market.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;

import com.setecs.mobile.wallet.market.database.Constants;
import com.setecs.mobile.wallet.market.database.MyDBHelper;


public class AdProvider extends ContentProvider {

	private MyDBHelper dbHelper;

	private static final int ALL_ADS = 1;
	private static final int SINGLE_AD = 2;

	public static final String AUTHORITY = "com.setecs.mobile.wallet.AdProvider";
	public static final String AD = "ad";
	public static final String ALL_ADS_TYPE = "vnd.android.cursor.dir/vnd.setecs.ad";
	public static final String SINGLE_AD_TYPE = "vnd.android.cursor.item/vnd.setecs.ad";
	public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + AD);

	private static final UriMatcher uriMatcher;

	static {
		uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
		uriMatcher.addURI(AUTHORITY, AD, ALL_ADS);
		uriMatcher.addURI(AUTHORITY, AD + "/#", SINGLE_AD);
	}

	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		SQLiteDatabase db = dbHelper.getWritableDatabase();

		// If this is a row URI, limit the deletion to the specified row.
		switch (uriMatcher.match(uri)) {
		case SINGLE_AD:
			String rowID = uri.getPathSegments().get(1);
			selection = Constants.KEY_ID + "="
					+ rowID
					+ (!TextUtils.isEmpty(selection) ? " AND (" + selection + ')' : "");
		default:
			break;
		}

		if (selection == null)
			selection = "1";

		// Perform the deletion.
		int deleteCount = db.delete(Constants.TABLE_NAME_AD, selection, selectionArgs);

		// Notify any observers of the change in the data set.
		getContext().getContentResolver().notifyChange(uri, null);

		// Return the number of deleted items.
		return deleteCount;
	}

	@Override
	public String getType(Uri uri) {
		// Return a string that identifies the MIME type for a Content Provider URI
		switch (uriMatcher.match(uri)) {
		case ALL_ADS:
			return ALL_ADS_TYPE;
		case SINGLE_AD:
			return SINGLE_AD_TYPE;
		default:
			throw new IllegalArgumentException("Unsupported URI: " + uri);
		}
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		SQLiteDatabase db = dbHelper.getWritableDatabase();

		String nullColumnHack = null;

		// Insert the values into the table
		long id = db.insert(Constants.TABLE_NAME_AD, nullColumnHack, values);

		// Construct and return the URI of the newly inserted row.
		if (id > -1) {
			// Construct and return the URI of the newly inserted row.
			Uri insertedId = ContentUris.withAppendedId(CONTENT_URI, id);

			// Notify any observers of the change in the data set.
			getContext().getContentResolver().notifyChange(insertedId, null);

			return insertedId;
		}
		else
			return null;
	}

	@Override
	public boolean onCreate() {
		dbHelper = new MyDBHelper(getContext(), Constants.DATABASE_NAME, null, Constants.DATABASE_VERSION);
		return true;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
		SQLiteDatabase db;
		try {
			db = dbHelper.getWritableDatabase();
		}
		catch (SQLiteException ex) {
			db = dbHelper.getReadableDatabase();
		}

		String groupBy = null;
		String having = null;

		SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();

		// If this is a row query, limit the result set to the passed in row.
		switch (uriMatcher.match(uri)) {
		case SINGLE_AD:
			String rowID = uri.getPathSegments().get(1);
			queryBuilder.appendWhere(Constants.KEY_ID + "=" + rowID);
		default:
			break;
		}

		queryBuilder.setTables(Constants.TABLE_NAME_AD);

		Cursor cursor = queryBuilder.query(db, projection, selection, selectionArgs, groupBy, having, sortOrder);

		return cursor;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
		SQLiteDatabase db = dbHelper.getWritableDatabase();

		// If this is a row URI, limit the deletion to the specified row.
		switch (uriMatcher.match(uri)) {
		case SINGLE_AD:
			String rowID = uri.getPathSegments().get(1);
			selection = Constants.KEY_ID + "="
					+ rowID
					+ (!TextUtils.isEmpty(selection) ? " AND (" + selection + ')' : "");
		default:
			break;
		}

		int updateCount = db.update(Constants.TABLE_NAME_AD, values, selection, selectionArgs);

		// Notify any observers of the change in the data set.
		getContext().getContentResolver().notifyChange(uri, null);

		return updateCount;
	}

}
