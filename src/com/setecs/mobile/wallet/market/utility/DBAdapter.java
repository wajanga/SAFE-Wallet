package com.setecs.mobile.wallet.market.utility;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;


public class DBAdapter {

	// Database fields
	public static final String KEY_ROWID = "_id";
	public static final String KEY_DESCRIPTION = "description";
	public static final String KEY_START = "startdate";
	public static final String KEY_END = "enddate";
	private static final String DATABASE_TABLE = "prom";
	private final Context context;
	private SQLiteDatabase database;
	private DBHelper dbHelper;

	public DBAdapter(Context context) {
		this.context = context;
	}

	public DBAdapter open() throws SQLException {
		dbHelper = new DBHelper(context);
		database = dbHelper.getWritableDatabase();
		return this;
	}

	public void close() {
		dbHelper.close();
	}

	/**
	 * Create a new todo If the todo is successfully created return the new
	 * rowId for that note, otherwise return a -1 to indicate failure.
	 */
	public long createTodo(String description, String startdate, String enddate) {
		ContentValues initialValues = createContentValues(description, startdate, enddate);

		return database.insert(DATABASE_TABLE, null, initialValues);
	}

	/**
	 * Update the todo
	 */
	public boolean updateTodo(long rowId, String description, String startdate, String enddate) {
		ContentValues updateValues = createContentValues(description, startdate, enddate);

		return database.update(DATABASE_TABLE, updateValues, KEY_ROWID + "=" + rowId, null) > 0;
	}

	/**
	 * Deletes todo
	 */
	public boolean deleteTodo(long rowId) {
		return database.delete(DATABASE_TABLE, KEY_ROWID + "=" + rowId, null) > 0;
	}

	public boolean deleteTodoDesc(String desc) {
		return database.delete(DATABASE_TABLE, KEY_DESCRIPTION + "='" + desc + "'", null) > 0;
	}

	/**
	 * Return a Cursor over the list of all todo in the database
	 * 
	 * @return Cursor over all notes
	 */
	public Cursor fetchAllTodos() {
		return database.query(DATABASE_TABLE, new String[] { KEY_ROWID, KEY_DESCRIPTION, KEY_START, KEY_END }, null,
				null, null, null, null);
	}

	/**
	 * Return a Cursor positioned at the defined todo
	 */
	public Cursor fetchTodo(long rowId) throws SQLException {
		Cursor mCursor = database.query(true, DATABASE_TABLE, new String[] { KEY_ROWID,
				KEY_DESCRIPTION,
				KEY_START,
				KEY_END }, KEY_ROWID + "=" + rowId, null, null, null, null, null);
		if (mCursor != null) {
			mCursor.moveToFirst();
		}
		return mCursor;
	}

	public Cursor fetchSpecificTodo(String description) throws SQLException {
		Cursor mCursor = database.query(true, DATABASE_TABLE, new String[] { KEY_ROWID,
				KEY_DESCRIPTION,
				KEY_START,
				KEY_END }, KEY_DESCRIPTION + "='" + description + "'", null, null, null, null, null);
		if (mCursor != null) {
			mCursor.moveToFirst();
		}
		return mCursor;
	}

	private ContentValues createContentValues(String description, String startdate, String enddate) {
		ContentValues values = new ContentValues();
		values.put(KEY_DESCRIPTION, description);
		values.put(KEY_START, startdate);
		values.put(KEY_END, enddate);
		return values;
	}

}
