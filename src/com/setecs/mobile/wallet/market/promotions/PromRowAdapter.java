package com.setecs.mobile.wallet.market.promotions;

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


public class PromRowAdapter extends BaseAdapter {

	private static LayoutInflater mInflater = null;
	private final ArrayList<Prom> promList;
	private Prom prom;
	public ImageLoader imageLoader;
	private final Activity activity;

	public PromRowAdapter(Activity a, ArrayList<Prom> object) {
		activity = a;
		this.promList = object;
		mInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		imageLoader = new ImageLoader(activity.getApplicationContext());
	}

	private static class ViewHolder {
		public TextView tv1, tv2, tv3;
		public ImageView img;
	}

	@Override
	public int getCount() {
		return promList.size();
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
			convertView = mInflater.inflate(R.layout.prom_row, null);
			holder = new ViewHolder();
			holder.tv1 = (TextView) convertView.findViewById(R.id.description);
			holder.tv2 = (TextView) convertView.findViewById(R.id.startDate);
			holder.tv3 = (TextView) convertView.findViewById(R.id.endDate);
			holder.img = (ImageView) convertView.findViewById(R.id.icon);
			convertView.setTag(holder);
		}
		prom = (Prom) promList.toArray()[position];
		holder = (ViewHolder) convertView.getTag();
		holder.tv1.setText(prom.getDescription());
		holder.tv2.setText("START DATE: " + prom.getStartDate());
		holder.tv3.setText("END DATE: " + prom.getEndDate());

		if (!prom.getImageString().equals("none.jpg"))
			imageLoader.DisplayImage(MarketConstant.IMAGE_ADDRESS + prom.getImageString(), activity, holder.img);

		return convertView;
	}

}
