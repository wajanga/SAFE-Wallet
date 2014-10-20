package com.setecs.mobile.wallet.market.tickets;

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


public class TicketRowAdapter extends BaseAdapter {

	private static LayoutInflater mInflater = null;
	private final ArrayList<Ticket> ticketList;
	private Ticket ticket;
	public ImageLoader imageLoader;
	private final Activity activity;

	public TicketRowAdapter(Activity a, ArrayList<Ticket> ticketList) {
		activity = a;
		this.ticketList = ticketList;
		mInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		imageLoader = new ImageLoader(activity.getApplicationContext());
	}

	private static class ViewHolder {
		public TextView description, amount, date, time;
		public ImageView img;
	}

	@Override
	public int getCount() {
		return ticketList.size();
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
			convertView = mInflater.inflate(R.layout.ticket_row, null);
			holder = new ViewHolder();
			holder.description = (TextView) convertView.findViewById(R.id.description);
			holder.amount = (TextView) convertView.findViewById(R.id.amount);
			holder.date = (TextView) convertView.findViewById(R.id.date);
			holder.time = (TextView) convertView.findViewById(R.id.time);
			holder.img = (ImageView) convertView.findViewById(R.id.icon);
			convertView.setTag(holder);
		}
		ticket = (Ticket) ticketList.toArray()[position];
		holder = (ViewHolder) convertView.getTag();
		holder.description.setText(ticket.getDescription());
		holder.amount.setText("$" + ticket.getAmount());
		holder.date.setText("DATE: " + ticket.getDate());
		holder.time.setText("TIME: " + ticket.getTime());

		if (!ticket.getImageString().equals("none.jpg"))
			imageLoader.DisplayImage(MarketConstant.IMAGE_ADDRESS + ticket.getImageString(), activity, holder.img);

		return convertView;
	}

}
