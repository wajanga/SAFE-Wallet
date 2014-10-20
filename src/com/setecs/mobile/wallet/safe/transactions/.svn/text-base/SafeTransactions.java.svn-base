package com.setecs.mobile.wallet.safe.transactions;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.setecs.mobile.safe.apps.shared.AccountBalance;
import com.setecs.mobile.walletDev.R;


public class SafeTransactions extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		//		walletmain.setPinFlag(false);

		super.onCreate(savedInstanceState);

		setContentView(R.layout.safe_transactions);
		((Button) findViewById(R.id.deposit_cash)).setOnClickListener(safeTransactionsButtonListener);
		((Button) findViewById(R.id.withdraw_cash)).setOnClickListener(safeTransactionsButtonListener);
		((Button) findViewById(R.id.transfer_money)).setOnClickListener(safeTransactionsButtonListener);
		((Button) findViewById(R.id.safe_balance)).setOnClickListener(safeTransactionsButtonListener);
		((Button) findViewById(R.id.list_transactions)).setOnClickListener(safeTransactionsButtonListener);

	}

	//
	// buttons from SAFE Transactions
	//
	protected OnClickListener safeTransactionsButtonListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			Integer buttonId = ((Button) v).getId();

			if (buttonId == R.id.deposit_cash) {
				depositCash();
			}
			if (buttonId == R.id.withdraw_cash) {
				withdrawCash();
			}
			if (buttonId == R.id.transfer_money) {
				transferMoney();
			}
			if (buttonId == R.id.safe_balance) {
				accountBalance();
			}

			if (buttonId == R.id.list_transactions) {
				listTransactions();
			}

		}

	};

	public void depositCash() {
		startActivity(new Intent(getApplicationContext(), DepositCash.class));

	}

	protected void listTransactions() {
		startActivity(new Intent(getApplicationContext(), ListTransactions.class));
	}

	public void withdrawCash() {
		startActivity(new Intent(getApplicationContext(), WithdrawCash.class));

	}

	public void transferMoney() {
		//startActivity(new Intent(getApplicationContext(), TransferMoney.class));
		startActivity(new Intent(getApplicationContext(), TransferMoneyActivity.class));
	}

	private void accountBalance() {
		startActivity(new Intent(getApplicationContext(), AccountBalance.class));
	}

} // end class

