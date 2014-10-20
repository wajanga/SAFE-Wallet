package com.setecs.mobile.wallet.market.tickets;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;

import android.app.ListActivity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.setecs.mobile.safe.apps.shared.CustomExceptionHandler;
import com.setecs.mobile.walletDev.R;
import com.setecs.mobile.wallet.market.database.Constants;
import com.setecs.mobile.wallet.market.main.HomeActivity;
import com.setecs.mobile.wallet.market.provider.TicketProvider;
import com.setecs.mobile.wallet.market.utility.SafeReplyHandler;
import com.setecs.mobile.wallet.net.HttpAsyncTask;


public class MarketTickets extends ListActivity implements SafeReplyHandler {

	private TicketRowAdapter adapter;
	private ArrayList<Ticket> ticketList;
	private TextView empty;
	private final String resourcePath = "/location2/get_ticket_list_wallet.php";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Thread.setDefaultUncaughtExceptionHandler(new CustomExceptionHandler(MarketTickets.this));

		constructGUI();

		getTicketList();
	}

	public void constructGUI() {
		setContentView(R.layout.market_item_list);

		empty = (TextView) getListView().getEmptyView();
		empty.setText(R.string.no_tickets);
	}

	private void getTicketList() {
		TicketParser ticketParser = new TicketParser();
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		new HttpAsyncTask(this, resourcePath, nameValuePairs, ticketParser, this).execute();
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		Ticket ticket = ticketList.get(position);

		ContentValues newValues = new ContentValues();
		newValues.put(Constants.TICKET_ID, ticket.getTicketId());
		newValues.put(Constants.MERCHANT_ACCOUNT, ticket.getMerchantAccount());
		newValues.put(Constants.MERCHANT_NAME, ticket.getMerchantName());
		newValues.put(Constants.DESCRIPTION, ticket.getDescription());
		newValues.put(Constants.DATE, ticket.getDate());
		newValues.put(Constants.TIME, ticket.getTime());
		newValues.put(Constants.AMOUNT, ticket.getAmount());
		newValues.put(Constants.IMAGE_NAME, ticket.getImageString());

		// Get the Content Resolver
		ContentResolver cr = getContentResolver();

		if (isTicketInDB(ticket.getTicketId())) {
			Toast.makeText(this, "m-Ticket has already been downloaded", Toast.LENGTH_SHORT).show();
		}
		else {
			// Insert the row into your table
			Uri myRowUri = cr.insert(TicketProvider.CONTENT_URI, newValues);
			if (!myRowUri.equals(null)) {
				Toast.makeText(this, "m-Ticket downloaded successfully", Toast.LENGTH_SHORT).show();
				Intent i = new Intent(MarketTickets.this, TicketsHome.class);
				i.putExtra("pinflag", false);
				startActivity(i);
				finish();
			}
		}
	}

	public void in_wallet(View v) {
		Intent i = new Intent(this, SavedTickets.class);
		startActivity(i);
	}

	@Override
	public void onDestroy() {
		setListAdapter(null);
		super.onDestroy();
	}

	private boolean isTicketInDB(String id) {
		ContentResolver cr = getContentResolver();

		String where = Constants.TICKET_ID + "=" + id;

		String[] result_columns = null;
		String whereArgs[] = null;
		String order = null;

		Cursor resultCursor = cr.query(TicketProvider.CONTENT_URI, result_columns, where, whereArgs, order);

		return resultCursor.moveToFirst();
	}

	@SuppressWarnings("unchecked")
	@Override
	public void displayList(ArrayList<?> object) {
		ticketList = (ArrayList<Ticket>) object;
		adapter = new TicketRowAdapter(MarketTickets.this, ticketList);
		setListAdapter(adapter);
	}

	@Override
	public void displayMessage(String message) {
		empty.setText(message);
	}

	@Override
	public void onBackPressed() {
		Intent i = new Intent(this, HomeActivity.class);
		startActivity(i);
		finish();
		return;
	}

}
