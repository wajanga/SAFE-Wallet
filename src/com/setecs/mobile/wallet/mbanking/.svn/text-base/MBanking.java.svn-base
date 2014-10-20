package com.setecs.mobile.wallet.mbanking;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.setecs.mobile.walletDev.R;
import com.setecs.mobile.wallet.market.utility.WalletActivity;
import com.setecs.mobile.wallet.mwallet.MWallet;


public class MBanking extends WalletActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

		setContentView(R.layout.m_banking);
		((Button) findViewById(R.id.Reg_Bank_Account)).setOnClickListener(mBankingButtonListener);
		((Button) findViewById(R.id.Pay_Bill)).setOnClickListener(mBankingButtonListener);
		((Button) findViewById(R.id.Transfer_Funds)).setOnClickListener(mBankingButtonListener);
		((Button) findViewById(R.id.List_Bank_Accounts)).setOnClickListener(mBankingButtonListener);
		((Button) findViewById(R.id.bank_acc_status)).setOnClickListener(mBankingButtonListener);
		((Button) findViewById(R.id.List_Transactions)).setOnClickListener(mBankingButtonListener);
	}

	protected OnClickListener mBankingButtonListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			Integer buttonId = ((Button) v).getId();

			if (buttonId == R.id.Reg_Bank_Account) {
				Reg_Bank_Account();
			}

			if (buttonId == R.id.Pay_Bill) {
				Pay_Bill();
			}
			if (buttonId == R.id.Transfer_Funds) {
				Transfer_Funds();
			}
			if (buttonId == R.id.List_Bank_Accounts) {
				List_Bank_Accounts();
			}
			if (buttonId == R.id.bank_acc_status) {
				Bank_Accounts_Status();
			}
			if (buttonId == R.id.List_Transactions) {
				List_Transactions();
			}

		}

	};

	protected void Pay_Bill() {
		startActivity(new Intent(getApplicationContext(), PayBillActivity.class));
	}

	protected void Reg_Card() {
		startActivity(new Intent(getApplicationContext(), RegisterCard.class));
	}

	protected void Reg_Bank_Account() {
		startActivity(new Intent(getApplicationContext(), RegisterBankAccountActivity.class));
	}

	protected void List_Transactions() {
		startActivity(new Intent(getApplicationContext(), ListTransactionsActivity.class));
		//startActivity(new Intent(getApplicationContext(), ListTransactionsIntermediate.class));
	}

	protected void List_Bank_Accounts() {
		startActivity(new Intent(getApplicationContext(), ListBankAccountsActivity.class));
	}

	protected void Bank_Accounts_Status() {
		startActivity(new Intent(getApplicationContext(), AccountStatusActivity.class));
	}

	protected void Transfer_Funds() {
		startActivity(new Intent(getApplicationContext(), TransferFundsActivity.class));
	}

	public void accountBalance(View v) {
		startActivity(new Intent(getApplicationContext(), BankAccBalanceActivity.class));
	}

	@Override
	public void onBackPressed() {
		// do something on back.
		Intent i = new Intent(this, MWallet.class);
		startActivity(i);
		return;
	}

} // end class

