package com.setecs.mobile.wallet.market.giftcards;

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


public class GiftcardRowAdapter extends BaseAdapter {

	private static LayoutInflater mInflater = null;
	private final ArrayList<Giftcard> serviceList;
	private Giftcard giftcard;
	private final ImageLoader imageLoader;
	private final Activity activity;

	public GiftcardRowAdapter(Activity a, ArrayList<Giftcard> serviceList) {
		activity = a;
		this.serviceList = serviceList;
		mInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		imageLoader = new ImageLoader(activity.getApplicationContext());
	}

	private static class ViewHolder {
		public TextView description, amount;
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
			convertView = mInflater.inflate(R.layout.giftcard_row, null);
			holder = new ViewHolder();
			holder.description = (TextView) convertView.findViewById(R.id.description);
			holder.amount = (TextView) convertView.findViewById(R.id.amount);
			holder.img = (ImageView) convertView.findViewById(R.id.icon);
			convertView.setTag(holder);
		}
		giftcard = (Giftcard) serviceList.toArray()[position];
		holder = (ViewHolder) convertView.getTag();
		holder.description.setText(giftcard.getDescription());
		holder.amount.setText("$" + giftcard.getAmount());

		if (!giftcard.getImageString().equals("none.jpg"))
			imageLoader.DisplayImage(MarketConstant.IMAGE_ADDRESS + giftcard.getImageString(), activity, holder.img);

		return convertView;
	}

}
