package com.setecs.mobile.wallet.safe.accounts;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.setecs.mobile.walletDev.R;
import com.setecs.mobile.wallet.safe.transactions.DepositCash;
import com.setecs.mobile.wallet.safe.transactions.ListTransactions;
import com.setecs.mobile.wallet.safe.transactions.TransferMoneyActivity;
import com.setecs.mobile.wallet.safe.transactions.WithdrawCash;


public class SafeAccounts extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.safe_accounts);
		((Button) findViewById(R.id.list_SAFE_accounts)).setOnClickListener(safeaccountButtonListener);
		((Button) findViewById(R.id.safe_balance)).setOnClickListener(safeaccountButtonListener);
		((Button) findViewById(R.id.create_new_SAFE_account)).setOnClickListener(safeaccountButtonListener);
		((Button) findViewById(R.id.suspend_account)).setOnClickListener(safeaccountButtonListener);
		((Button) findViewById(R.id.activate_account)).setOnClickListener(safeaccountButtonListener);
		((Button) findViewById(R.id.terminate_account)).setOnClickListener(safeaccountButtonListener);

		((Button) findViewById(R.id.deposit_cash)).setOnClickListener(safeaccountButtonListener);
		((Button) findViewById(R.id.withdraw_cash)).setOnClickListener(safeaccountButtonListener);
		((Button) findViewById(R.id.transfer_money)).setOnClickListener(safeaccountButtonListener);
		((Button) findViewById(R.id.list_transactions)).setOnClickListener(safeaccountButtonListener);

	}

	protected OnClickListener safeaccountButtonListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			Integer buttonId = ((Button) v).getId();

			if (buttonId == R.id.list_SAFE_accounts) {
				list_SAFE_accounts();
			}
			if (buttonId == R.id.safe_balance) {
				accountBalance();
			}
			if (buttonId == R.id.create_new_SAFE_account) {
				create_new_SAFE_account();
			}
			if (buttonId == R.id.suspend_account) {
				suspend_account();
			}
			if (buttonId == R.id.activate_account) {
				activate_account();
			}
			if (buttonId == R.id.terminate_account) {
				terminate_account();
			}

			// new menu
			if (buttonId == R.id.deposit_cash) {
				depositCash();
			}
			if (buttonId == R.id.withdraw_cash) {
				withdrawCash();
			}
			if (buttonId == R.id.transfer_money) {
				transferMoney();
			}
			if (buttonId == R.id.list_transactions) {
				listTransactions();
			}
		}
	};

	private void accountBalance() {
		/*Intent i = new Intent().setClass(getApplicationContext(), AccountBalance.class);
		Bundle bundle = new Bundle(); // bundle is like the letter
		bundle.putString("app_name", "wallet");
		i.putExtras(bundle);// actually it's bundle who carries the content u
		// wanna pass
		i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(i);*/
		startActivity(new Intent(getApplicationContext(), SAFEBalanceActivity.class));
	}

	protected void list_SAFE_accounts() {
		startActivity(new Intent(getApplicationContext(), ListSafeAccounts.class));
	}

	protected void terminate_account() {
		startActivity(new Intent(getApplicationContext(), TerminateAccount.class));
	}

	protected void activate_account() {
		startActivity(new Intent(getApplicationContext(), ActivateAccount.class));
	}

	protected void suspend_account() {
		startActivity(new Intent(getApplicationContext(), SuspendAccounts.class));
	}

	protected void create_new_SAFE_account() {
		startActivity(new Intent(getApplicationContext(), CreateNewSafeAccounts.class));
	}

	// new menu
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

} // end class

