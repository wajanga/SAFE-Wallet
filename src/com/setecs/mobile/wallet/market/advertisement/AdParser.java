package com.setecs.mobile.wallet.market.advertisement;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.setecs.mobile.wallet.market.utility.SafeReplyHandler;
import com.setecs.mobile.wallet.market.utility.SafeReplyParser;


public class AdParser implements SafeReplyParser {

	private ArrayList<Ad> adList;
	private String response = "";

	private ArrayList<Ad> parseJSON(String reply) throws JSONException {
		ArrayList<Ad> adList = new ArrayList<Ad>();
		JSONArray entries = new JSONArray(reply);
		for (int i = 0; i < entries.length(); i++) {
			JSONObject object = entries.getJSONObject(i);
			Ad ad = new Ad();
			ad.setAdId(object.getString("id"));
			ad.setMerchantAccount(object.getString("merchant_account"));
			ad.setMerchantName(object.getString("merchant_name"));
			ad.setDescription(object.getString("description"));
			ad.setStartDate(object.getString("start_date"));
			ad.setEndDate(object.getString("end_date"));
			ad.setImageString(object.getString("image_name"));
			adList.add(ad);
		}
		return adList;
	}

	@Override
	public void parse(String response) {
		try {
			adList = parseJSON(response);
		}
		catch (JSONException e) {
			this.response = response;
		}
	}

	@Override
	public void output(SafeReplyHandler replyHandler, String message) {
		if (message.equals("") && response.equals(""))
			replyHandler.displayList(adList);
		else if (!message.equals(""))
			replyHandler.displayMessage(message);
		else
			replyHandler.displayMessage(response);
	}

}
