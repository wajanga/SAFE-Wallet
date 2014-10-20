package com.setecs.mobile.wallet.mbanking;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.setecs.mobile.walletDev.R;


public class BankAccRowAdapter extends BaseAdapter {

	private static LayoutInflater mInflater = null;
	private final ArrayList<BankAccount> accList;
	private BankAccount bankAcc;
	private final Activity activity;

	public BankAccRowAdapter(Activity a, ArrayList<BankAccount> accList) {
		activity = a;
		this.accList = accList;
		mInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	private static class ViewHolder {
		public TextView tv1, tv2, tv3;
	}

	@Override
	public int getCount() {
		return accList.size();
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
			convertView = mInflater.inflate(R.layout.bank_account_row, null);
			holder = new ViewHolder();
			holder.tv1 = (TextView) convertView.findViewById(R.id.bankName);
			holder.tv2 = (TextView) convertView.findViewById(R.id.iban);
			holder.tv3 = (TextView) convertView.findViewById(R.id.accNumber);
			convertView.setTag(holder);
		}
		bankAcc = (BankAccount) accList.toArray()[position];
		holder = (ViewHolder) convertView.getTag();
		holder.tv1.setText("BANK NAME: " + bankAcc.getBankName());
		holder.tv2.setText("IBAN: " + bankAcc.getIban());
		holder.tv3.setText("ACC #: " + bankAcc.getAccountNumber());

		return convertView;
	}

}
