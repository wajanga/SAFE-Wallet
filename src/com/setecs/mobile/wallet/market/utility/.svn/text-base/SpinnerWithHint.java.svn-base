package com.setecs.mobile.wallet.market.utility;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.setecs.mobile.walletDev.R;


public class SpinnerWithHint extends BaseAdapter {

	private static LayoutInflater mInflater = null;
	private final Activity activity;
	private final String[] data;
	private TextView text;
	public boolean selected;
	private final String hint;

	public SpinnerWithHint(Activity a, String[] data, String hint) {
		activity = a;
		this.data = data;
		this.hint = hint;
		mInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		selected = false;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.row_spinner, null);
		}
		text = (TextView) convertView.findViewById(R.id.spinnerTarget);
		if (!selected) {
			text.setText(hint);
		}
		else {
			text.setText(data[position]);
		}
		return convertView;
	}

	@Override
	public int getCount() {
		return data.length;
	}

	@Override
	public Object getItem(int position) {
		return data[position];
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getDropDownView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = mInflater.inflate(android.R.layout.simple_spinner_dropdown_item, null);
		}
		text = (TextView) convertView.findViewById(android.R.id.text1);
		text.setText(data[position]);
		return convertView;
	};

}
