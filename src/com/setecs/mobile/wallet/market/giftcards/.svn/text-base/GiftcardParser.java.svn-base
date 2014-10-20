package com.setecs.mobile.wallet.market.giftcards;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.setecs.mobile.wallet.market.utility.SafeReplyHandler;
import com.setecs.mobile.wallet.market.utility.SafeReplyParser;


public class GiftcardParser implements SafeReplyParser {

	private ArrayList<Giftcard> giftcardList;
	private String response = "";

	private ArrayList<Giftcard> parseJSON(String reply) throws JSONException {
		ArrayList<Giftcard> giftcardList = new ArrayList<Giftcard>();
		JSONArray entries = new JSONArray(reply);
		for (int i = 0; i < entries.length(); i++) {
			JSONObject object = entries.getJSONObject(i);
			Giftcard giftcard = new Giftcard();
			giftcard.setGiftcardId(object.getString("id"));
			giftcard.setMerchantAccount(object.getString("merchant_account"));
			giftcard.setMerchantName(object.getString("merchant_name"));
			giftcard.setDescription(object.getString("description"));
			giftcard.setAmount(object.getString("amount"));
			giftcard.setQrImageString(object.getString("qr_name"));
			giftcard.setImageString(object.getString("image_name"));
			giftcardList.add(giftcard);
		}
		return giftcardList;
	}

	@Override
	public void parse(String response) {
		try {
			giftcardList = parseJSON(response);
		}
		catch (JSONException e) {
			this.response = response;
		}
	}

	@Override
	public void output(SafeReplyHandler replyHandler, String message) {
		if (message.equals("") && response.equals(""))
			replyHandler.displayList(giftcardList);
		else if (!message.equals(""))
			replyHandler.displayMessage(message);
		else
			replyHandler.displayMessage(response);
	}

}
