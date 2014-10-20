package com.setecs.mobile.wallet.market.bills;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.setecs.mobile.wallet.market.utility.SafeReplyHandler;
import com.setecs.mobile.wallet.market.utility.SafeReplyParser;


public class BillParser implements SafeReplyParser {

	private ArrayList<Bill> billList;
	private String response = "";

	/*private ArrayList<Bill> parseJSON(String reply) throws JSONException {
		ArrayList<Bill> billList = new ArrayList<Bill>();
		JSONArray entries = new JSONArray(reply);
		for (int i = 0; i < entries.length(); i++) {
			JSONObject object = entries.getJSONObject(i);
			Bill bill = new Bill();
			bill.setMerchantAccount(object.getString("merchant_account"));
			bill.setMerchantName(object.getString("merchant_name"));
			bill.setDescription(object.getString("description"));
			bill.setAmount(object.getString("amount"));
			billList.add(bill);
		}
		return billList;
	}*/

	private ArrayList<Bill> parseJSON(String reply) throws JSONException {
		ArrayList<Bill> billList = new ArrayList<Bill>();
		JSONObject entry = new JSONObject(reply);
		JSONArray ads = entry.getJSONArray("bills");
		for (int i = 0; i < ads.length(); i++) {
			JSONObject object = ads.getJSONObject(i);
			if (object.getString("status").equals("UNPAID")) {
				Bill bill = new Bill();
				bill.setDescription(object.getString("description"));
				bill.setAmount(object.getString("amount"));
				bill.setNumber(object.getString("billNumber"));
				bill.setStatus(object.getString("status"));
				bill.setMerchantAccount(object.getString("merchantAccount"));
				billList.add(bill);
			}
		}
		return billList;
	}

	@Override
	public void parse(String response) {
		try {
			billList = parseJSON(response);
		}
		catch (JSONException e) {
			this.response = response;
		}
	}

	@Override
	public void output(SafeReplyHandler replyHandler, String message) {
		if (message.equals("") && response.equals(""))
			replyHandler.displayList(billList);
		else if (!message.equals(""))
			replyHandler.displayMessage(message);
		else
			replyHandler.displayMessage(response);
	}

}
