package com.setecs.mobile.wallet.market.coupons;

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
import com.setecs.mobile.wallet.market.provider.CouponProvider;


public class SavedCoupons extends ListActivity {

	private Cursor resultCursor;
	private ArrayList<Coupon> couponList;
	private CouponRowAdapter adapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Thread.setDefaultUncaughtExceptionHandler(new CustomExceptionHandler(SavedCoupons.this));

		setContentView(R.layout.list);

		TextView empty = (TextView) getListView().getEmptyView();
		empty.setText(R.string.no_coupons);

		getCouponList();
	}

	private void getCouponList() {
		ContentResolver cr = getContentResolver();

		String[] result_columns = null;
		String where = null;
		String whereArgs[] = null;
		String order = null;

		resultCursor = cr.query(CouponProvider.CONTENT_URI, result_columns, where, whereArgs, order);

		couponList = populateCoupons(resultCursor);
		adapter = new CouponRowAdapter(this, couponList);
		setListAdapter(adapter);
		resultCursor.close();
	}

	private ArrayList<Coupon> populateCoupons(Cursor resultCursor) {
		ArrayList<Coupon> resultCouponList = new ArrayList<Coupon>();
		while (resultCursor.moveToNext()) {
			Coupon coupon = new Coupon();
			coupon.setId(resultCursor.getLong(resultCursor.getColumnIndex(Constants.KEY_ID)));
			coupon.setMerchantAccount(resultCursor.getString(resultCursor.getColumnIndex(Constants.MERCHANT_ACCOUNT)));
			coupon.setMerchantName(resultCursor.getString(resultCursor.getColumnIndex(Constants.MERCHANT_NAME)));
			coupon.setDescription(resultCursor.getString(resultCursor.getColumnIndex(Constants.DESCRIPTION)));
			coupon.setStartDate(resultCursor.getString(resultCursor.getColumnIndex(Constants.START_DATE)));
			coupon.setEndDate(resultCursor.getString(resultCursor.getColumnIndex(Constants.END_DATE)));
			coupon.setAmount(resultCursor.getString(resultCursor.getColumnIndex(Constants.AMOUNT)));
			coupon.setDiscount(resultCursor.getString(resultCursor.getColumnIndex(Constants.DISCOUNT)));
			coupon.setValue(resultCursor.getString(resultCursor.getColumnIndex(Constants.VALUE)));
			coupon.setImageString(resultCursor.getString(resultCursor.getColumnIndex(Constants.IMAGE_NAME)));
			resultCouponList.add(coupon);
		}
		return resultCouponList;
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		Coupon coupon = couponList.get(position);
		Intent i = new Intent(SavedCoupons.this, CouponActivity.class);
		i.putExtra("id", coupon.getId());
		i.putExtra("merchant_account", coupon.getMerchantAccount());
		i.putExtra("merchant_name", coupon.getMerchantName());
		i.putExtra("description", coupon.getDescription());
		i.putExtra("start_date", coupon.getStartDate());
		i.putExtra("end_date", coupon.getEndDate());
		i.putExtra("value", coupon.getValue());
		i.putExtra("image_name", coupon.getImageString());
		startActivity(i);
		finish();
	}

	public void in_wallet(View v) {
		// Do nothing
	}

	public void in_market(View v) {
		Intent i = new Intent(this, MarketCoupons.class);
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
