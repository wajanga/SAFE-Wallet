package com.setecs.mobile.wallet.market.advertisement;

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


public class AdAdapter extends BaseAdapter {
	private static LayoutInflater mInflater = null;
	private final ArrayList<HashMap<String, String>> serviceList;
	private HashMap<String, String> tempList;
	HashMap<String, String> data;
	public ImageLoader imageLoader;
	private final Activity activity;
	private final static String IMG_ADDRESS = "http://130.237.215.16/location2/images/";

	public AdAdapter(Activity a, ArrayList<HashMap<String, String>> serviceList) {
		activity = a;
		this.serviceList = serviceList;
		mInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		imageLoader = new ImageLoader(activity.getApplicationContext());
	}

	static class ViewHolder {
		public TextView tv1, tv2;
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
			convertView = mInflater.inflate(R.layout.ad_row, null);
			holder = new ViewHolder();
			holder.tv1 = (TextView) convertView.findViewById(R.id.testView3);
			holder.tv2 = (TextView) convertView.findViewById(R.id.testView4);
			holder.img = (ImageView) convertView.findViewById(R.id.testView1);
			convertView.setTag(holder);
		}
		tempList = (HashMap<String, String>) serviceList.toArray()[position];
		holder = (ViewHolder) convertView.getTag();
		holder.tv1.setText(tempList.get("description"));
		holder.tv2.setText(tempList.get("startdate"));

		if (!tempList.get("img_name").equals("none.jpg"))
			imageLoader.DisplayImage(IMG_ADDRESS + tempList.get("img_name"), activity, holder.img);

		return convertView;
	}
}
