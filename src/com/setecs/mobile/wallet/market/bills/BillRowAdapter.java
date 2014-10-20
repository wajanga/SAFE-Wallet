package com.setecs.mobile.wallet.market.bills;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.setecs.mobile.walletDev.R;


public class BillRowAdapter extends BaseAdapter {

	private static LayoutInflater mInflater = null;
	private final ArrayList<Bill> serviceList;
	private Bill bill;
	private final Activity activity;

	public BillRowAdapter(Activity a, ArrayList<Bill> serviceList) {
		activity = a;
		this.serviceList = serviceList;
		mInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	private static class ViewHolder {
		public TextView billNo, amount, status;
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
			convertView = mInflater.inflate(R.layout.bill_row, null);
			holder = new ViewHolder();
			holder.billNo = (TextView) convertView.findViewById(R.id.billNo);
			holder.amount = (TextView) convertView.findViewById(R.id.amount);
			holder.status = (TextView) convertView.findViewById(R.id.billStatus);
			convertView.setTag(holder);
		}
		bill = (Bill) serviceList.toArray()[position];
		holder = (ViewHolder) convertView.getTag();
		holder.billNo.setText("Bill #: " + bill.getNumber());
		holder.amount.setText("$" + bill.getAmount());
		holder.status.setText(bill.getStatus());

		return convertView;
	}

}
