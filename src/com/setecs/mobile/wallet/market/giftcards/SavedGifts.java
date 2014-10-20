package com.setecs.mobile.wallet.market.giftcards;

import java.util.ArrayList;

import android.app.ListActivity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.setecs.mobile.safe.apps.shared.CustomExceptionHandler;
import com.setecs.mobile.walletDev.R;
import com.setecs.mobile.wallet.market.database.Constants;
import com.setecs.mobile.wallet.market.main.HomeActivity;
import com.setecs.mobile.wallet.market.provider.GiftcardProvider;


public class SavedGifts extends ListActivity {

	private Cursor resultCursor;
	private ArrayList<Giftcard> giftcardList;
	private GiftcardRowAdapter adapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Thread.setDefaultUncaughtExceptionHandler(new CustomExceptionHandler(SavedGifts.this));

		setContentView(R.layout.list2);

		TextView empty = (TextView) getListView().getEmptyView();
		empty.setText(R.string.no_giftcards);

		getGiftcardList();
	}

	private void getGiftcardList() {
		ContentResolver cr = getContentResolver();

		String[] result_columns = null;
		String where = null;
		String whereArgs[] = null;
		String order = null;

		resultCursor = cr.query(GiftcardProvider.CONTENT_URI, result_columns, where, whereArgs, order);

		giftcardList = populateGiftcards(resultCursor);
		adapter = new GiftcardRowAdapter(this, giftcardList);
		setListAdapter(adapter);
		resultCursor.close();
	}

	private ArrayList<Giftcard> populateGiftcards(Cursor resultCursor) {
		ArrayList<Giftcard> resultGiftcardList = new ArrayList<Giftcard>();
		while (resultCursor.moveToNext()) {
			Giftcard giftcard = new Giftcard();
			giftcard.setId(resultCursor.getLong(resultCursor.getColumnIndex(Constants.KEY_ID)));
			giftcard.setMerchantAccount(resultCursor.getString(resultCursor.getColumnIndex(Constants.MERCHANT_ACCOUNT)));
			giftcard.setMerchantName(resultCursor.getString(resultCursor.getColumnIndex(Constants.MERCHANT_NAME)));
			giftcard.setDescription(resultCursor.getString(resultCursor.getColumnIndex(Constants.DESCRIPTION)));
			giftcard.setAmount(resultCursor.getString(resultCursor.getColumnIndex(Constants.AMOUNT)));
			giftcard.setImageString(resultCursor.getString(resultCursor.getColumnIndex(Constants.IMAGE_NAME)));
			resultGiftcardList.add(giftcard);
		}
		return resultGiftcardList;
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		Giftcard giftcard = giftcardList.get(position);
		Intent i = new Intent(SavedGifts.this, GiftcardActivity.class);
		i.putExtra("id", giftcard.getId());
		i.putExtra("merchant_account", giftcard.getMerchantAccount());
		i.putExtra("merchant_name", giftcard.getMerchantName());
		i.putExtra("description", giftcard.getDescription());
		i.putExtra("amount", giftcard.getAmount());
		i.putExtra("image_name", giftcard.getImageString());
		startActivity(i);
		finish();
	}

	public void in_wallet(View v) {
		// Do nothing
	}

	public void in_market(View v) {
		Intent i = new Intent(this, MarketGifts.class);
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
