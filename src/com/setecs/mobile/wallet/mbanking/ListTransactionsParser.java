package com.setecs.mobile.wallet.mbanking;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.setecs.mobile.wallet.market.utility.SafeReplyHandler;
import com.setecs.mobile.wallet.market.utility.SafeReplyParser;


public class ListTransactionsParser implements SafeReplyParser {

	private final ArrayList<BankTransaction> bankTransList = new ArrayList<BankTransaction>();
	private String errorMessage = "";

	@Override
	public void parse(String response) {
		try {
			JSONObject entry = new JSONObject(response);
			JSONArray transactions = entry.getJSONArray("transactions");
			for (int i = 0; i < transactions.length(); i++) {
				JSONObject object = transactions.getJSONObject(i);
				BankTransaction transaction = new BankTransaction();
				transaction.setId(entry.getString("bankTransactionId"));

				String date = object.getString("date");
				transaction.setDate(date);
				String[] dateTime = date.split(" ");
				transaction.setDateTime("DATE: " + dateTime[0] + "  TIME: " + dateTime[1]);

				transaction.setAmount(object.getString("amount"));
				transaction.setDescription(object.getString("description"));
				transaction.setTax(object.getString("taxAmount"));
				transaction.setCurrencySymbol(object.getString("currencySymbol"));
				bankTransList.add(transaction);
			}
		}
		catch (JSONException e) {
			errorMessage = response;
		}
	}

	@Override
	public void output(SafeReplyHandler replyHandler, String message) {
		if (message.equals("")) {
			if (errorMessage.equals(""))
				replyHandler.displayList(bankTransList);
			else
				replyHandler.displayMessage(errorMessage);
		}
		else {
			replyHandler.displayMessage(message);
		}
	}

}
