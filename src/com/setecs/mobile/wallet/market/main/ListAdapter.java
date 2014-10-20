package com.setecs.mobile.wallet.market.main;

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


public class ListAdapter extends BaseAdapter {

	private static LayoutInflater mInflater = null;
	private final ArrayList<HashMap<String, String>> serviceList;
	private HashMap<String, String> tempList;
	HashMap<String, String> data;
	public ImageLoader imageLoader;
	private final Activity activity;
	private final static String IMG_ADDRESS = "http://130.237.215.188/location2/images/";

	public ListAdapter(Activity a, ArrayList<HashMap<String, String>> serviceList) {
		activity = a;
		this.serviceList = serviceList;
		mInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		imageLoader = new ImageLoader(activity.getApplicationContext());
	}

	static class ViewHolder {
		public TextView tv1, tv2, tv3;
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
			convertView = mInflater.inflate(R.layout.voucher_list_row, null);
			holder = new ViewHolder();
			holder.tv1 = (TextView) convertView.findViewById(R.id.testView3);
			holder.tv2 = (TextView) convertView.findViewById(R.id.testView4);
			holder.tv3 = (TextView) convertView.findViewById(R.id.textView2);
			holder.img = (ImageView) convertView.findViewById(R.id.testView1);
			convertView.setTag(holder);
		}
		tempList = (HashMap<String, String>) serviceList.toArray()[position];
		holder = (ViewHolder) convertView.getTag();
		holder.tv1.setText(tempList.get("merchant_name"));
		holder.tv2.setText(tempList.get("description"));
		holder.tv3.setText(tempList.get("end_date"));

		imageLoader.DisplayImage(IMG_ADDRESS + tempList.get("image_name"), activity, holder.img);

		return convertView;
	}

}
