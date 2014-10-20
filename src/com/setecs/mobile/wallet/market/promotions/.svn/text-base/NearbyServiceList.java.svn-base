package com.setecs.mobile.wallet.market.promotions;

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
import com.setecs.mobile.wallet.market.provider.PromProvider;
import com.setecs.mobile.wallet.market.utility.SafeReplyHandler;
import com.setecs.mobile.wallet.net.HttpAsyncTask;


public class NearbyServiceList extends ListActivity implements SafeReplyHandler {

	private TextView empty;
	private ArrayList<Prom> promList;
	private PromRowAdapter adapter;
	private final String resourcePath = "/location2/get_prom_list_wallet.php";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Thread.setDefaultUncaughtExceptionHandler(new CustomExceptionHandler(NearbyServiceList.this));

		constructGUI();

		getPromList();
	}

	public void constructGUI() {
		setContentView(R.layout.market_item_list);

		empty = (TextView) getListView().getEmptyView();
		empty.setText(R.string.no_promotions);
	}

	private void getPromList() {
		PromParser promParser = new PromParser();
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		new HttpAsyncTask(this, resourcePath, nameValuePairs, promParser, this).execute();
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		Prom prom = promList.get(position);

		ContentValues newValues = new ContentValues();
		newValues.put(Constants.PROM_ID, prom.getPromId());
		newValues.put(Constants.MERCHANT_ACCOUNT, prom.getMerchantAccount());
		newValues.put(Constants.MERCHANT_NAME, prom.getMerchantName());
		newValues.put(Constants.DESCRIPTION, prom.getDescription());
		newValues.put(Constants.START_DATE, prom.getStartDate());
		newValues.put(Constants.END_DATE, prom.getEndDate());
		newValues.put(Constants.IMAGE_NAME, prom.getImageString());

		// Get the Content Resolver
		ContentResolver cr = getContentResolver();

		if (isPromInDB(prom.getPromId())) {
			Toast.makeText(this, "m-Promotion has already been downloaded", Toast.LENGTH_SHORT).show();
		}
		else {
			// Insert the row into your table
			Uri myRowUri = cr.insert(PromProvider.CONTENT_URI, newValues);
			if (!myRowUri.equals(null)) {
				Toast.makeText(this, "m-Promotion downloaded successfully", Toast.LENGTH_SHORT).show();
				Intent i = new Intent(NearbyServiceList.this, Home.class);
				i.putExtra("pinflag", false);
				startActivity(i);
				finish();
			}
		}
	}

	public void in_wallet(View v) {
		Intent i = new Intent(this, DownloadedProm.class);
		startActivity(i);
	}

	private boolean isPromInDB(String id) {
		ContentResolver cr = getContentResolver();

		String where = Constants.PROM_ID + "=" + id;

		String[] result_columns = null;
		String whereArgs[] = null;
		String order = null;

		Cursor resultCursor = cr.query(PromProvider.CONTENT_URI, result_columns, where, whereArgs, order);

		return resultCursor.moveToFirst();
	}

	public void ok(View v) {
		Intent i = new Intent(NearbyServiceList.this, Home.class);
		i.putExtra("pinflag", false);
		startActivity(i);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void displayList(ArrayList<?> object) {
		promList = (ArrayList<Prom>) object;
		adapter = new PromRowAdapter(NearbyServiceList.this, promList);
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
