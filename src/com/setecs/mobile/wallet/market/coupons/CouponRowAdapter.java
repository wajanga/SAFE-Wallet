package com.setecs.mobile.wallet.market.coupons;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.setecs.mobile.walletDev.R;
import com.setecs.mobile.wallet.imagedownloader.ImageLoader;
import com.setecs.mobile.wallet.market.main.MarketConstant;


public class CouponRowAdapter extends BaseAdapter {

	private static LayoutInflater mInflater = null;
	private final ArrayList<Coupon> serviceList;
	private Coupon tempList;
	private final ImageLoader imageLoader;
	private final Activity activity;

	public CouponRowAdapter(Activity a, ArrayList<Coupon> serviceList) {
		this.activity = a;
		this.serviceList = serviceList;
		mInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		imageLoader = new ImageLoader(activity.getApplicationContext());
	}

	private static class ViewHolder {
		public TextView description, amount, startDate;
		public TextView endDate;
		public ImageView img;
	}

	@Override
	public int getCount() {
		return serviceList.size();
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.coupon_row, null);
			holder = new ViewHolder();
			holder.description = (TextView) convertView.findViewById(R.id.description);
			holder.amount = (TextView) convertView.findViewById(R.id.amount);
			holder.startDate = (TextView) convertView.findViewById(R.id.startDate);
			holder.endDate = (TextView) convertView.findViewById(R.id.endDate);
			holder.img = (ImageView) convertView.findViewById(R.id.icon);
			convertView.setTag(holder);
		}
		tempList = (Coupon) serviceList.toArray()[position];
		holder = (ViewHolder) convertView.getTag();
		holder.description.setText(tempList.getDescription());
		holder.amount.setText(tempList.getValue());
		holder.startDate.setText("START DATE: " + tempList.getStartDate());
		if (tempList.getValue().contains("%")) {
			holder.endDate.setVisibility(View.VISIBLE);
			holder.endDate.setText("END DATE: " + tempList.getEndDate());
		}

		if (!tempList.getImageString().equals("none.jpg"))
			imageLoader.DisplayImage(MarketConstant.IMAGE_ADDRESS + tempList.getImageString(), activity, holder.img);

		return convertView;
	}

}
