package com.setecs.mobile.wallet.market.advertisement;

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


public class AdRowAdapter extends BaseAdapter {

	private static LayoutInflater mInflater = null;
	private final ArrayList<Ad> adList;
	private Ad ad;
	public ImageLoader imageLoader;
	private final Activity activity;

	public AdRowAdapter(Activity a, ArrayList<Ad> adList) {
		activity = a;
		this.adList = adList;
		mInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		imageLoader = new ImageLoader(activity.getApplicationContext());
	}

	private static class ViewHolder {
		public TextView tv1, tv2, tv3;
		public ImageView img;
	}

	@Override
	public int getCount() {
		return adList.size();
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
			convertView = mInflater.inflate(R.layout.ad_row, null);
			holder = new ViewHolder();
			holder.tv1 = (TextView) convertView.findViewById(R.id.description);
			holder.tv2 = (TextView) convertView.findViewById(R.id.startDate);
			holder.tv3 = (TextView) convertView.findViewById(R.id.endDate);
			holder.img = (ImageView) convertView.findViewById(R.id.icon);
			convertView.setTag(holder);
		}
		ad = (Ad) adList.toArray()[position];
		holder = (ViewHolder) convertView.getTag();
		holder.tv1.setText(ad.getDescription());
		holder.tv2.setText("START DATE: " + ad.getStartDate());
		holder.tv3.setText("END DATE: " + ad.getEndDate());

		if (!ad.getImageString().equals("none.jpg"))
			imageLoader.DisplayImage(MarketConstant.IMAGE_ADDRESS + ad.getImageString(), activity, holder.img);

		return convertView;
	}

}
