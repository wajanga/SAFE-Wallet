package com.setecs.mobile.wallet.mapplication;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.setecs.mobile.walletDev.R;


public class ServicesAdap extends BaseAdapter {

	private final LayoutInflater mInflater;
	private final ArrayList<HashMap<String, String>> serviceList;
	private HashMap<String, String> tempList;
	HashMap<String, String> data;

	public ServicesAdap(Context context, ArrayList<HashMap<String, String>> serviceList) {
		mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.serviceList = serviceList;
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
		
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.card_list_layout, null);
		}
		tempList = (HashMap<String, String>) serviceList.toArray()[position];
		TextView mer = (TextView) convertView.findViewById(R.id.testView3);
		mer.setText(tempList.get("merchant"));
		TextView desc = (TextView) convertView.findViewById(R.id.testView4);
		desc.setText(tempList.get("description"));
		TextView date = (TextView) convertView.findViewById(R.id.textView2);
		date.setText(tempList.get("enddate"));

		return convertView;
	}

}
