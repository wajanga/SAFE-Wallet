package com.setecs.mobile.wallet.market.promotions;

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
import com.setecs.mobile.wallet.market.provider.PromProvider;


public class DownloadedProm extends ListActivity {

	private Cursor resultCursor;
	private ArrayList<Prom> promList;
	private PromRowAdapter adapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Thread.setDefaultUncaughtExceptionHandler(new CustomExceptionHandler(DownloadedProm.this));

		setContentView(R.layout.market_item_list);

		TextView empty = (TextView) getListView().getEmptyView();
		empty.setText(R.string.no_promotions);

		getPromList();
	}

	private void getPromList() {
		ContentResolver cr = getContentResolver();

		String[] result_columns = null;
		String where = null;
		String whereArgs[] = null;
		String order = null;

		resultCursor = cr.query(PromProvider.CONTENT_URI, result_columns, where, whereArgs, order);

		promList = populateProms(resultCursor);
		adapter = new PromRowAdapter(this, promList);
		setListAdapter(adapter);
		resultCursor.close();
	}

	private ArrayList<Prom> populateProms(Cursor resultCursor) {
		ArrayList<Prom> resultPromList = new ArrayList<Prom>();
		while (resultCursor.moveToNext()) {
			Prom prom = new Prom();
			prom.setId(resultCursor.getLong(resultCursor.getColumnIndex(Constants.KEY_ID)));
			prom.setMerchantAccount(resultCursor.getString(resultCursor.getColumnIndex(Constants.MERCHANT_ACCOUNT)));
			prom.setMerchantName(resultCursor.getString(resultCursor.getColumnIndex(Constants.MERCHANT_NAME)));
			prom.setDescription(resultCursor.getString(resultCursor.getColumnIndex(Constants.DESCRIPTION)));
			prom.setStartDate(resultCursor.getString(resultCursor.getColumnIndex(Constants.START_DATE)));
			prom.setEndDate(resultCursor.getString(resultCursor.getColumnIndex(Constants.END_DATE)));
			prom.setImageString(resultCursor.getString(resultCursor.getColumnIndex(Constants.IMAGE_NAME)));
			resultPromList.add(prom);
		}
		return resultPromList;
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		Prom prom = promList.get(position);
		Intent i = new Intent(DownloadedProm.this, PromActivity.class);
		i.putExtra("id", prom.getId());
		i.putExtra("prom_id", prom.getPromId());
		i.putExtra("merchant_account", prom.getMerchantAccount());
		i.putExtra("merchant_name", prom.getMerchantName());
		i.putExtra("description", prom.getDescription());
		i.putExtra("start_date", prom.getStartDate());
		i.putExtra("end_date", prom.getEndDate());
		i.putExtra("image_name", prom.getImageString());
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
