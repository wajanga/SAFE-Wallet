package com.setecs.mobile.wallet.market.giftcards;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;

import android.app.ListActivity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.setecs.mobile.safe.apps.shared.CustomExceptionHandler;
import com.setecs.mobile.walletDev.R;
import com.setecs.mobile.wallet.market.database.Constants;
import com.setecs.mobile.wallet.market.main.HomeActivity;
import com.setecs.mobile.wallet.market.provider.GiftcardProvider;
import com.setecs.mobile.wallet.market.utility.SafeReplyHandler;
import com.setecs.mobile.wallet.net.HttpAsyncTask;


public class MarketGifts extends ListActivity implements SafeReplyHandler {

	private TextView empty;
	private ArrayList<Giftcard> giftcardList;
	private GiftcardRowAdapter adapter;
	private final String resourcePath = "/location2/get_giftcard_list_wallet.php";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Thread.setDefaultUncaughtExceptionHandler(new CustomExceptionHandler(MarketGifts.this));

		constructGUI();

		getGiftcardList();
	}

	public void constructGUI() {
		setContentView(R.layout.list2);

		empty = (TextView) getListView().getEmptyView();
		empty.setText(R.string.no_giftcards);
	}

	private void getGiftcardList() {
		GiftcardParser giftcardParser = new GiftcardParser();
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		new HttpAsyncTask(this, resourcePath, nameValuePairs, giftcardParser, this).execute();
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		Giftcard giftcard = giftcardList.get(position);

		ContentValues newValues = new ContentValues();
		newValues.put(Constants.GIFTCARD_ID, giftcard.getGiftcardId());
		newValues.put(Constants.MERCHANT_ACCOUNT, giftcard.getMerchantAccount());
		newValues.put(Constants.MERCHANT_NAME, giftcard.getMerchantName());
		newValues.put(Constants.DESCRIPTION, giftcard.getDescription());
		newValues.put(Constants.AMOUNT, giftcard.getAmount());
		newValues.put(Constants.IMAGE_NAME, giftcard.getImageString());

		// Get the Content Resolver
		ContentResolver cr = getContentResolver();

		if (isGiftcardInDB(giftcard.getGiftcardId())) {
			Toast.makeText(this, "m-Gift Card has already been downloaded", Toast.LENGTH_SHORT).show();
		}
		else {
			// Insert the row into your table
			Uri myRowUri = cr.insert(GiftcardProvider.CONTENT_URI, newValues);
			if (!myRowUri.equals(null)) {
				Toast.makeText(this, "m-Gift Card downloaded successfully", Toast.LENGTH_SHORT).show();
				Intent i = new Intent(MarketGifts.this, GiftHome.class);
				i.putExtra("pinflag", false);
				startActivity(i);
				finish();
			}
		}
	}

	@Override
	public void onDestroy() {
		setListAdapter(null);
		super.onDestroy();
	}

	public void in_wallet(View v) {
		Intent i = new Intent(this, SavedGifts.class);
		startActivity(i);
	}

	private boolean isGiftcardInDB(String id) {
		ContentResolver cr = getContentResolver();

		String where = Constants.GIFTCARD_ID + "=" + id;

		String[] result_columns = null;
		String whereArgs[] = null;
		String order = null;

		Cursor resultCursor = cr.query(GiftcardProvider.CONTENT_URI, result_columns, where, whereArgs, order);

		return resultCursor.moveToFirst();
	}

	@SuppressWarnings("unchecked")
	@Override
	public void displayList(ArrayList<?> object) {
		giftcardList = (ArrayList<Giftcard>) object;
		adapter = new GiftcardRowAdapter(MarketGifts.this, giftcardList);
		setListAdapter(adapter);
	}

	@Override
	public void displayMessage(String message) {
		empty.setText(message);
	}

	@Override
	public void onBackPressed() {
		Intent i = new Intent(this, HomeActivity.class);
		startActivity(i);
		finish();
		return;
	}

}
