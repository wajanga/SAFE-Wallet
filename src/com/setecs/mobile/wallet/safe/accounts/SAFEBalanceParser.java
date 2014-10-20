package com.setecs.mobile.wallet.safe.accounts;

import org.json.JSONException;
import org.json.JSONObject;

import com.setecs.mobile.wallet.market.utility.SafeReplyHandler;
import com.setecs.mobile.wallet.market.utility.SafeReplyParser;


public class SAFEBalanceParser implements SafeReplyParser {

	private String output;

	@Override
	public void parse(String response) {
		//output = "Transfer could not be completed";
		output = response;

		try {
			JSONObject entry = new JSONObject(response);
			output = "";
			String bankTransId = "Account Type: " + entry.getJSONObject("accountId").getString("type") + "\n";
			String account = "Account No: " + entry.getJSONObject("accountId").getString("accountNo") + "\n";
			String balance = "Balance Available: " + entry.getString("balanceAvailable") + "\n";
			String currency = "Currency: " + entry.getString("currency") + "\n";

			output = output + bankTransId + account + balance + currency;
		}
		catch (JSONException e) {
			// ignore
		}
	}

	@Override
	public void output(SafeReplyHandler replyHandler, String message) {
		if (message.equals("")) {
			replyHandler.displayMessage(output);
		}
		else {
			replyHandler.displayMessage(message);
		}
	}

}
