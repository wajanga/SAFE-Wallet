package com.setecs.mobile.wallet.market.advertisement;

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
import com.setecs.mobile.wallet.market.provider.AdProvider;
import com.setecs.mobile.wallet.market.utility.SafeReplyHandler;
import com.setecs.mobile.wallet.net.HttpAsyncTask;


public class MarketAd extends ListActivity implements SafeReplyHandler {

	private AdRowAdapter adapter;
	private ArrayList<Ad> adList;
	private TextView empty;
	private final String resourcePath = "/location2/get_ad_list_wallet.php";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Thread.setDefaultUncaughtExceptionHandler(new CustomExceptionHandler(MarketAd.this));

		constructGUI();

		getAdList();
	}

	public void constructGUI() {
		setContentView(R.layout.market_item_list);

		empty = (TextView) getListView().getEmptyView();
		empty.setText(R.string.no_advertisements);
	}

	private void getAdList() {
		AdParser adParser = new AdParser();
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		new HttpAsyncTask(this, resourcePath, nameValuePairs, adParser, this).execute();
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		Ad ad = adList.get(position);

		ContentValues newValues = new ContentValues();
		newValues.put(Constants.AD_ID, ad.getAdId());
		newValues.put(Constants.MERCHANT_ACCOUNT, ad.getMerchantAccount());
		newValues.put(Constants.MERCHANT_NAME, ad.getMerchantName());
		newValues.put(Constants.DESCRIPTION, ad.getDescription());
		newValues.put(Constants.START_DATE, ad.getStartDate());
		newValues.put(Constants.END_DATE, ad.getEndDate());
		newValues.put(Constants.IMAGE_NAME, ad.getImageString());

		// Get the Content Resolver
		ContentResolver cr = getContentResolver();

		if (isAdInDB(ad.getAdId())) {
			Toast.makeText(this, "m-Advertisement has already been downloaded", Toast.LENGTH_SHORT).show();
		}
		else {
			// Insert the row into your table
			Uri myRowUri = cr.insert(AdProvider.CONTENT_URI, newValues);
			if (!myRowUri.equals(null)) {
				Toast.makeText(this, "m-Advertisement downloaded successfully", Toast.LENGTH_SHORT).show();
				Intent i = new Intent(MarketAd.this, AdHome.class);
				i.putExtra("pinflag", false);
				startActivity(i);
				finish();
			}
		}
	}

	public void in_wallet(View v) {
		Intent i = new Intent(this, DownloadedAd.class);
		startActivity(i);
	}

	private boolean isAdInDB(String id) {
		ContentResolver cr = getContentResolver();

		String where = Constants.AD_ID + "=" + id;

		String[] result_columns = null;
		String whereArgs[] = null;
		String order = null;

		Cursor resultCursor = cr.query(AdProvider.CONTENT_URI, result_columns, where, whereArgs, order);

		return resultCursor.moveToFirst();
	}

	public void ok(View v) {
		Intent i = new Intent(MarketAd.this, AdHome.class);
		i.putExtra("pinflag", false);
		startActivity(i);
	}

	@Override
	public void displayMessage(String message) {
		empty.setText(message);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void displayList(ArrayList<?> object) {
		adList = (ArrayList<Ad>) object;
		adapter = new AdRowAdapter(MarketAd.this, adList);
		setListAdapter(adapter);
	}

	@Override
	public void onBackPressed() {
		Intent i = new Intent(this, HomeActivity.class);
		startActivity(i);
		finish();
		return;
	}

}
