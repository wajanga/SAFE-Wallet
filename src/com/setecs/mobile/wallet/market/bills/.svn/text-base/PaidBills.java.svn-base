package com.setecs.mobile.wallet.market.bills;

import static com.setecs.mobile.wallet.safe.wallet.Constants.ACCOUNTLIST;
import static com.setecs.mobile.wallet.safe.wallet.Constants.ACCOUNTLISTFILENAME;
import static com.setecs.mobile.wallet.safe.wallet.Constants.CONFIGFILENAME;
import static com.setecs.mobile.wallet.safe.wallet.Constants.MOBILENO;

import java.util.ArrayList;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.setecs.mobile.safe.apps.shared.CustomExceptionHandler;
import com.setecs.mobile.safe.apps.shared.SharedMethods;
import com.setecs.mobile.walletDev.R;
import com.setecs.mobile.wallet.market.main.HomeActivity;
import com.setecs.mobile.wallet.market.utility.SafeReplyHandler;
import com.setecs.mobile.wallet.net.SocketAsyncTask3;


public class PaidBills extends ListActivity implements SafeReplyHandler {

	private TextView empty;
	private ArrayList<Bill> billList;
	private BillRowAdapter adapter;
	private String userMobNo;
	private final SharedMethods cv = new SharedMethods();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Thread.setDefaultUncaughtExceptionHandler(new CustomExceptionHandler(PaidBills.this));

		constructGUI();

		getBillList();

		/*billList = createBillList();
		adapter = new BillRowAdapter(PaidBills.this, billList);
		setListAdapter(adapter);*/
	}

	public void constructGUI() {
		setContentView(R.layout.list2);

		empty = (TextView) getListView().getEmptyView();
		empty.setText(R.string.no_paid_bills);
	}

	private void getBillList() {
		userMobNo = cv.readConfigurationFile(this, MOBILENO, CONFIGFILENAME);

		if (userMobNo.equals("")) {
			Toast.makeText(this, "Could not read the user's mobile number", Toast.LENGTH_SHORT).show();
			return;
		}

		BillParserPaid billParser = new BillParserPaid();
		String billMsg = "(" + userMobNo + ";" + "bl " + getSafeAccount() + ")";
		new SocketAsyncTask3(this, billParser, this).execute(billMsg);
	}

	private ArrayList<Bill> createBillList() {
		ArrayList<Bill> billList = new ArrayList<Bill>();
		Bill bill = new Bill("1", "Internet Bill (Jan)", "70", "PAID");
		billList.add(bill);
		bill = new Bill("2", "Power Bill (Jan)", "170", "PAID");
		billList.add(bill);
		bill = new Bill("3", "Water Bill (Jan)", "200", "PAID");
		billList.add(bill);
		bill = new Bill("4", "Dry Cleaning Bill", "150", "PAID");
		billList.add(bill);
		return billList;
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		Bill bill = billList.get(position);
		Intent i = new Intent(PaidBills.this, BillActivity.class);
		i.putExtra("id", bill.getId());
		i.putExtra("bill_status", bill.getStatus());
		i.putExtra("bill_no", bill.getNumber());
		i.putExtra("bill_desc", bill.getDescription());
		i.putExtra("amount", bill.getAmount());
		startActivity(i);
		finish();
	}

	private String getSafeAccount() {
		String[] acc = cv.fetchAccounts(this, ACCOUNTLIST, ACCOUNTLISTFILENAME);
		return acc[0];
	}

	@Override
	public void onDestroy() {
		setListAdapter(null);
		super.onDestroy();
	}

	public void in_wallet(View v) {
		// Do nothing
	}

	public void in_market(View v) {
		Intent i = new Intent(this, UnPaidBills.class);
		startActivity(i);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void displayList(ArrayList<?> object) {
		billList = (ArrayList<Bill>) object;
		adapter = new BillRowAdapter(PaidBills.this, billList);
		setListAdapter(adapter);
	}

	@Override
	public void displayMessage(String message) {
		empty.setText(message);
	}

	@Override
	public void onBackPressed() {
		Intent i = new Intent(this, HomeActivity.class);
		startActivity(i);
		finish();
		return;
	}

}
