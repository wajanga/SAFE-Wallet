package com.setecs.mobile.wallet.market.promotions;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.setecs.mobile.wallet.market.utility.SafeReplyHandler;
import com.setecs.mobile.wallet.market.utility.SafeReplyParser;


public class PromParser implements SafeReplyParser {

	private ArrayList<Prom> promList;
	private String response = "";

	private ArrayList<Prom> parseJSON(String reply) throws JSONException {
		ArrayList<Prom> promList = new ArrayList<Prom>();
		JSONArray entries = new JSONArray(reply);
		for (int i = 0; i < entries.length(); i++) {
			JSONObject object = entries.getJSONObject(i);
			Prom prom = new Prom();
			prom.setPromId(object.getString("id"));
			prom.setMerchantAccount(object.getString("merchant_account"));
			prom.setMerchantName(object.getString("merchant_name"));
			prom.setDescription(object.getString("description"));
			prom.setStartDate(object.getString("start_date"));
			prom.setEndDate(object.getString("end_date"));
			prom.setImageString(object.getString("image_name"));
			promList.add(prom);
		}
		return promList;
	}

	@Override
	public void parse(String response) {
		try {
			promList = parseJSON(response);
		}
		catch (JSONException e) {
			this.response = response;
		}
	}

	@Override
	public void output(SafeReplyHandler replyHandler, String message) {
		if (message.equals("") && response.equals(""))
			replyHandler.displayList(promList);
		else if (!message.equals(""))
			replyHandler.displayMessage(message);
		else
			replyHandler.displayMessage(response);
	}

}
