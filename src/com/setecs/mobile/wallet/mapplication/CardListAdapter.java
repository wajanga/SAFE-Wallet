package com.setecs.mobile.wallet.mapplication;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.setecs.mobile.walletDev.R;


public class CardListAdapter extends BaseAdapter {

	private static LayoutInflater mInflater = null;
	private final ArrayList<HashMap<String, String>> cardList;
	private HashMap<String, String> tempList;
	private final Activity activity;

	public CardListAdapter(Activity a, ArrayList<HashMap<String, String>> cardList) {
		activity = a;
		this.cardList = cardList;
		mInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	static class ViewHolder {
		public TextView card_brand, issuing_bank, card_number;
	}

	@Override
	public int getCount() {
		
		return cardList.size();
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
			convertView = mInflater.inflate(R.layout.card_list_row, null);
			holder = new ViewHolder();
			holder.card_brand = (TextView) convertView.findViewById(R.id.card_brand);
			holder.issuing_bank = (TextView) convertView.findViewById(R.id.issuing_bank);
			holder.card_number = (TextView) convertView.findViewById(R.id.card_number);
			convertView.setTag(holder);
		}
		tempList = (HashMap<String, String>) cardList.toArray()[position];
		holder = (ViewHolder) convertView.getTag();
		holder.card_brand.setText("Card Brand: " + tempList.get("card_brand"));
		holder.issuing_bank.setText("Issuing Bank: " + tempList.get("issuing_bank"));
		holder.card_number.setText("Card Number: " + tempList.get("card_number"));

		return convertView;
	}

}
