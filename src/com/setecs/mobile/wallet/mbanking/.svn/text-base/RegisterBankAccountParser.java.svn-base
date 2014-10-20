package com.setecs.mobile.wallet.mbanking;

import com.setecs.mobile.wallet.market.utility.SafeReplyHandler;
import com.setecs.mobile.wallet.market.utility.SafeReplyParser;


public class RegisterBankAccountParser implements SafeReplyParser {

	private String output;
	private String bankAccount = "";

	@Override
	public void parse(String response) {
		//output = "Transfer could not be completed";
		output = response;

		if (response.contains("Bank Account:"))
			bankAccount = output.substring(output.indexOf("Bank Account:") + 16,
					output.indexOf("\n", output.indexOf("Bank Account:") + 16));
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

	protected String getBankAccount() {
		return bankAccount;
	}

	protected boolean isError() {
		if (bankAccount.equals(""))
			return true;
		else
			return false;
	}

}
