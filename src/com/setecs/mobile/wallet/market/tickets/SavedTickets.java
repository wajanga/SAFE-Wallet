package com.setecs.mobile.wallet.market.tickets;

import java.util.ArrayList;

import android.app.ListActivity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.setecs.mobile.walletDev.R;
import com.setecs.mobile.wallet.market.database.Constants;
import com.setecs.mobile.wallet.market.main.HomeActivity;
import com.setecs.mobile.wallet.market.provider.TicketProvider;


public class SavedTickets extends ListActivity {

	private Cursor resultCursor;
	private ArrayList<Ticket> ticketList;
	private TicketRowAdapter adapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.market_item_list);

		TextView empty = (TextView) getListView().getEmptyView();
		empty.setText(R.string.no_tickets);

		getTicketList();
	}

	private void getTicketList() {
		ContentResolver cr = getContentResolver();

		String[] result_columns = null;
		String where = null;
		String whereArgs[] = null;
		String order = null;

		resultCursor = cr.query(TicketProvider.CONTENT_URI, result_columns, where, whereArgs, order);

		ticketList = populateTickets(resultCursor);
		adapter = new TicketRowAdapter(this, ticketList);
		setListAdapter(adapter);
		resultCursor.close();
	}

	private ArrayList<Ticket> populateTickets(Cursor resultCursor) {
		ArrayList<Ticket> resultTicketList = new ArrayList<Ticket>();
		while (resultCursor.moveToNext()) {
			Ticket ticket = new Ticket();
			ticket.setId(resultCursor.getLong(resultCursor.getColumnIndex(Constants.KEY_ID)));
			ticket.setMerchantAccount(resultCursor.getString(resultCursor.getColumnIndex(Constants.MERCHANT_ACCOUNT)));
			ticket.setMerchantName(resultCursor.getString(resultCursor.getColumnIndex(Constants.MERCHANT_NAME)));
			ticket.setDescription(resultCursor.getString(resultCursor.getColumnIndex(Constants.DESCRIPTION)));
			ticket.setDate(resultCursor.getString(resultCursor.getColumnIndex(Constants.DATE)));
			ticket.setTime(resultCursor.getString(resultCursor.getColumnIndex(Constants.TIME)));
			ticket.setAmount(resultCursor.getString(resultCursor.getColumnIndex(Constants.AMOUNT)));
			ticket.setImageString(resultCursor.getString(resultCursor.getColumnIndex(Constants.IMAGE_NAME)));
			resultTicketList.add(ticket);
		}
		return resultTicketList;
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		Ticket ticket = ticketList.get(position);
		Intent i = new Intent(SavedTickets.this, TicketActivity.class);
		i.putExtra("id", ticket.getId());
		i.putExtra("merchant_account", ticket.getMerchantAccount());
		i.putExtra("merchant_name", ticket.getMerchantName());
		i.putExtra("description", ticket.getDescription());
		i.putExtra("date", ticket.getDate());
		i.putExtra("time", ticket.getTime());
		i.putExtra("amount", ticket.getAmount());
		i.putExtra("image_name", ticket.getImageString());
		startActivity(i);
		finish();
	}

	public void in_wallet(View v) {
		// Do nothing
	}

	public void in_market(View v) {
		Intent i = new Intent(this, MarketTickets.class);
		startActivity(i);
	}

	@Override
	public void onBackPressed() {
		// do something on back.
		Intent i = new Intent(this, HomeActivity.class);
		startActivity(i);
		finish();
	}

}
