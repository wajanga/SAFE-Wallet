package com.setecs.mobile.wallet.mbanking;

import org.json.JSONException;
import org.json.JSONObject;

import com.setecs.mobile.wallet.market.utility.SafeReplyHandler;
import com.setecs.mobile.wallet.market.utility.SafeReplyParser;


public class TransferFundsParser implements SafeReplyParser {

	private String output;

	@Override
	public void parse(String response) {
		//output = "Transfer could not be completed";
		output = response;

		try {
			JSONObject entry = new JSONObject(response);
			output = "";
			String transId = "Transaction ID: " + entry.getString("transactionId") + "\n";
			String bankTransId = "Bank Transaction ID: " + entry.getString("bankTransactionId") + "\n";
			String fromClient = "From: " + entry.getString("fromClientName") + "\n";
			String toClient = "To: " + entry.getString("toClientName") + "\n";
			String currency = "Currency: " + entry.getString("currency") + "\n";

			String amount = entry.getString("amount");
			String transAmount = "";
			if (!amount.equals(""))
				transAmount = "Amount: $" + entry.getString("amount");

			output = output + transId + bankTransId + fromClient + toClient + currency + transAmount;
			//}
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
