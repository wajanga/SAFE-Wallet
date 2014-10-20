package com.setecs.mobile.wallet.market.coupons;

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
import com.setecs.mobile.wallet.market.provider.CouponProvider;
import com.setecs.mobile.wallet.market.utility.SafeReplyHandler;
import com.setecs.mobile.wallet.net.HttpAsyncTask;


public class MarketCoupons extends ListActivity implements SafeReplyHandler {

	private CouponRowAdapter adapter;
	private TextView empty;
	private ArrayList<Coupon> couponList;
	private final String resourcePath = "/location2/get_coupon_list_wallet.php";

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		Coupon coupon = couponList.get(position);

		ContentValues newValues = new ContentValues();
		newValues.put(Constants.COUPON_ID, coupon.getCouponId());
		newValues.put(Constants.MERCHANT_ACCOUNT, coupon.getMerchantAccount());
		newValues.put(Constants.MERCHANT_NAME, coupon.getMerchantName());
		newValues.put(Constants.DESCRIPTION, coupon.getDescription());
		newValues.put(Constants.START_DATE, coupon.getStartDate());
		newValues.put(Constants.END_DATE, coupon.getEndDate());
		newValues.put(Constants.AMOUNT, coupon.getAmount());
		newValues.put(Constants.DISCOUNT, coupon.getDiscount());
		newValues.put(Constants.VALUE, coupon.getValue());
		newValues.put(Constants.IMAGE_NAME, coupon.getImageString());

		// Get the Content Resolver
		ContentResolver cr = getContentResolver();

		if (isCouponInDB(coupon.getCouponId())) {
			Toast.makeText(this, "m-Coupon has already been downloaded", Toast.LENGTH_SHORT).show();
		}
		else {
			// Insert the row into your table
			Uri myRowUri = cr.insert(CouponProvider.CONTENT_URI, newValues);
			if (!myRowUri.equals(null)) {
				Toast.makeText(this, "m-Coupon downloaded successfully", Toast.LENGTH_SHORT).show();
				Intent i = new Intent(MarketCoupons.this, CouponsHome.class);
				i.putExtra("pinflag", false);
				startActivity(i);
				finish();
			}
		}
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Thread.setDefaultUncaughtExceptionHandler(new CustomExceptionHandler(MarketCoupons.this));

		constructGUI();

		getCouponList();
	}

	public void constructGUI() {
		setContentView(R.layout.list);

		empty = (TextView) getListView().getEmptyView();
		empty.setText(R.string.no_advertisements);
	}

	private void getCouponList() {
		CouponParser adParser = new CouponParser();
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		new HttpAsyncTask(this, resourcePath, nameValuePairs, adParser, this).execute();
	}

	private boolean isCouponInDB(String id) {
		ContentResolver cr = getContentResolver();

		String where = Constants.COUPON_ID + "=" + id;

		String[] result_columns = null;
		String whereArgs[] = null;
		String order = null;

		Cursor resultCursor = cr.query(CouponProvider.CONTENT_URI, result_columns, where, whereArgs, order);

		return resultCursor.moveToFirst();
	}

	public void in_wallet(View v) {
		Intent i = new Intent(this, SavedCoupons.class);
		startActivity(i);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void displayList(ArrayList<?> object) {
		couponList = (ArrayList<Coupon>) object;
		adapter = new CouponRowAdapter(MarketCoupons.this, couponList);
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