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


public class ListTransactionsRowAdapter extends BaseAdapter {

	private static LayoutInflater mInflater = null;
	private final Activity activity;
	private final ArrayList<BankTransaction> transList;
	private BankTransaction transaction;
	private String amount;

	public ListTransactionsRowAdapter(Activity a, ArrayList<BankTransaction> cardTransList) {
		activity = a;
		this.transList = cardTransList;
		mInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	private static class ViewHolder {
		public TextView description, transId, date, amount, tax;
	}

	@Override
	public int getCount() {
		return transList.size();
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
			convertView = mInflater.inflate(R.layout.list_transactions_row, null);
			holder = new ViewHolder();
			holder.description = (TextView) convertView.findViewById(R.id.card_number);
			holder.transId = (TextView) convertView.findViewById(R.id.transId);
			holder.date = (TextView) convertView.findViewById(R.id.date);
			holder.tax = (TextView) convertView.findViewById(R.id.status);
			holder.amount = (TextView) convertView.findViewById(R.id.amount);
			convertView.setTag(holder);
		}
		transaction = (BankTransaction) transList.toArray()[position];
		holder = (ViewHolder) convertView.getTag();
		holder.description.setText(transaction.getDescription());
		holder.transId.setText("Trans ID: " + transaction.getId());
		holder.date.setText(transaction.getDateTime());
		holder.tax.setText("Tax ID: " + transaction.getTax());

		amount = transaction.getAmount();
		if (amount.equals(""))
			holder.amount.setText("-");
		else
			holder.amount.setText("$" + transaction.getAmount());

		return convertView;
	}

	/*private String formatDate(String date) {
		String newDate = date;
		SimpleDateFormat curFormater = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat postFormater = new SimpleDateFormat("MMMM dd, yyyy");
		Date dateObj;
		try {
			dateObj = curFormater.parse(date);
			newDate = postFormater.format(dateObj);
		}
		catch (ParseException e) {
			Log.d("ListTransactionRowAdapter", "Exception: " + e.getMessage());
		}
		return newDate;
	}*/

}
