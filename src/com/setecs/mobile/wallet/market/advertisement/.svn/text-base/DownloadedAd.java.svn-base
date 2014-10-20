package com.setecs.mobile.wallet.market.advertisement;

import java.util.ArrayList;
import java.util.HashMap;

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
import com.setecs.mobile.wallet.market.provider.AdProvider;


public class DownloadedAd extends ListActivity {

	public Cursor cursor;
	public ArrayList<HashMap<String, String>> ad_list;
	private AdRowAdapter adapter;
	private ArrayList<Ad> adList;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Thread.setDefaultUncaughtExceptionHandler(new CustomExceptionHandler(DownloadedAd.this));

		//setContentView(R.layout.token_view);
		setContentView(R.layout.market_item_list);

		TextView empty = (TextView) getListView().getEmptyView();
		empty.setText(R.string.no_ads);

		/*dba = new MyDB(this);
		dba.open();
		fillData();*/
		getAdList();
	}

	private void getAdList() {
		ContentResolver cr = getContentResolver();

		String[] result_columns = null;
		String where = null;
		String whereArgs[] = null;
		String order = null;

		Cursor resultCursor = cr.query(AdProvider.CONTENT_URI, result_columns, where, whereArgs, order);

		adList = populateAds(resultCursor);
		adapter = new AdRowAdapter(this, adList);
		setListAdapter(adapter);
		resultCursor.close();
	}

	private ArrayList<Ad> populateAds(Cursor resultCursor) {
		ArrayList<Ad> resultAdList = new ArrayList<Ad>();
		while (resultCursor.moveToNext()) {
			Ad ad = new Ad();
			ad.setId(resultCursor.getLong(resultCursor.getColumnIndex(Constants.KEY_ID)));
			ad.setMerchantAccount(resultCursor.getString(resultCursor.getColumnIndex(Constants.MERCHANT_ACCOUNT)));
			ad.setMerchantName(resultCursor.getString(resultCursor.getColumnIndex(Constants.MERCHANT_NAME)));
			ad.setDescription(resultCursor.getString(resultCursor.getColumnIndex(Constants.DESCRIPTION)));
			ad.setStartDate(resultCursor.getString(resultCursor.getColumnIndex(Constants.START_DATE)));
			ad.setEndDate(resultCursor.getString(resultCursor.getColumnIndex(Constants.END_DATE)));
			ad.setImageString(resultCursor.getString(resultCursor.getColumnIndex(Constants.IMAGE_NAME)));
			resultAdList.add(ad);
		}
		return resultAdList;
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		Ad ad = adList.get(position);
		Intent i = new Intent(DownloadedAd.this, AdActivity.class);
		i.putExtra("id", ad.getId());
		i.putExtra("ad_id", ad.getAdId());
		i.putExtra("merchant_account", ad.getMerchantAccount());
		i.putExtra("merchant_name", ad.getMerchantName());
		i.putExtra("description", ad.getDescription());
		i.putExtra("start_date", ad.getStartDate());
		i.putExtra("end_date", ad.getEndDate());
		i.putExtra("image_name", ad.getImageString());
		startActivity(i);
		finish();
	}

	@Override
	public void onBackPressed() {
		Intent i = new Intent(this, HomeActivity.class);
		startActivity(i);
		finish();
		return;
	}

}
