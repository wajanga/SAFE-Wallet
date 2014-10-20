package com.setecs.mobile.wallet.mbanking;

import org.json.JSONException;
import org.json.JSONObject;

import com.setecs.mobile.wallet.market.utility.SafeReplyHandler;
import com.setecs.mobile.wallet.market.utility.SafeReplyParser;


public class BankAccBalanceParser implements SafeReplyParser {

	private String output;

	@Override
	public void parse(String response) {
		//output = "Transfer could not be completed";
		output = response;

		try {
			JSONObject entry = new JSONObject(response);
			output = "";
			String bankTransId = "Bank Transaction ID: " + entry.getString("bankTransactionId") + "\n";
			String account = "Account: " + entry.getJSONObject("accountId").getString("accountNo") + "\n";
			String iban = "IBAN: " + entry.getJSONObject("accountId").getString("iban") + "\n";
			String balance = "Balance: " + entry.getString("balanceAvailable") + "\n";
			String currency = "Currency: " + entry.getString("currency") + "\n";

			output = output + bankTransId + account + iban + balance + currency;
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
