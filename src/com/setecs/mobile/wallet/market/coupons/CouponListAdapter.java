package com.setecs.mobile.wallet.market.coupons;

import java.util.ArrayList;
import java.util.HashMap;

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
import com.setecs.mobile.wallet.market.main.WalletConstants;


public class CouponListAdapter extends BaseAdapter {

	private static LayoutInflater mInflater = null;
	private final ArrayList<HashMap<String, String>> serviceList;
	private HashMap<String, String> tempList;
	HashMap<String, String> data;
	public ImageLoader imageLoader;
	private final Activity activity;
	private final static String IMG_ADDRESS = "http://" + WalletConstants.SERVER_ADDRESS + "/location2/images/";

	public CouponListAdapter(Activity a, ArrayList<HashMap<String, String>> serviceList) {
		activity = a;
		this.serviceList = serviceList;
		mInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		imageLoader = new ImageLoader(activity.getApplicationContext());
	}

	static class ViewHolder {
		public TextView description, amount, date;
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

	@SuppressWarnings("unchecked")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.coupon_list_row, null);
			holder = new ViewHolder();
			holder.description = (TextView) convertView.findViewById(R.id.description);
			holder.amount = (TextView) convertView.findViewById(R.id.amount);
			holder.date = (TextView) convertView.findViewById(R.id.date);
			holder.img = (ImageView) convertView.findViewById(R.id.icon);
			convertView.setTag(holder);
		}
		tempList = (HashMap<String, String>) serviceList.toArray()[position];
		holder = (ViewHolder) convertView.getTag();
		holder.description.setText(tempList.get("description"));
		holder.amount.setText(tempList.get("amount"));
		holder.date.setText(tempList.get("end_date"));

		if (!tempList.get("image_name").equals("none.jpg"))
			imageLoader.DisplayImage(IMG_ADDRESS + tempList.get("image_name"), activity, holder.img);

		return convertView;
	}

}
