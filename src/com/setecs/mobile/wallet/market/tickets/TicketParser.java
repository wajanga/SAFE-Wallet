package com.setecs.mobile.wallet.market.tickets;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.setecs.mobile.wallet.market.utility.SafeReplyHandler;
import com.setecs.mobile.wallet.market.utility.SafeReplyParser;


public class TicketParser implements SafeReplyParser {

	private ArrayList<Ticket> ticketList;
	private String response = "";

	private ArrayList<Ticket> parseJSON(String reply) throws JSONException {
		ArrayList<Ticket> ticketList = new ArrayList<Ticket>();
		JSONArray entries = new JSONArray(reply);
		for (int i = 0; i < entries.length(); i++) {
			JSONObject object = entries.getJSONObject(i);
			Ticket ticket = new Ticket();
			ticket.setTicketId(object.getString("id"));
			ticket.setMerchantAccount(object.getString("merchant_account"));
			ticket.setMerchantName(object.getString("merchant_name"));
			ticket.setDescription(object.getString("description"));
			ticket.setAmount(object.getString("amount"));
			ticket.setDate(object.getString("date"));
			ticket.setTime(object.getString("time"));
			ticket.setQrImageString(object.getString("qr_name"));
			ticket.setImageString(object.getString("image_name"));
			ticketList.add(ticket);
		}
		return ticketList;
	}

	@Override
	public void parse(String response) {
		try {
			ticketList = parseJSON(response);
		}
		catch (JSONException e) {
			this.response = response;
		}
	}

	@Override
	public void output(SafeReplyHandler replyHandler, String message) {
		if (message.equals("") && response.equals(""))
			replyHandler.displayList(ticketList);
		else if (!message.equals(""))
			replyHandler.displayMessage(message);
		else
			replyHandler.displayMessage(response);
	}

}
