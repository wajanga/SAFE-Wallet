package com.setecs.mobile.wallet.market.bills;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.android.Contents;
import com.google.zxing.client.android.Intents;
import com.setecs.mobile.walletDev.R;
import com.setecs.mobile.wallet.market.utility.QRCodeEncoder;
import com.setecs.mobile.wallet.mbanking.PayBillActivity;


public class BillActivity extends Activity {

	private static final String TAG = "m-Merchant";
	private long id;
	private QRCodeEncoder qrCodeEncoder;
	private String bill_desc = null, amount = null;
	private String bill_no = null;
	private String bill_status = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.bill_activity);

		Intent i = getIntent();
		id = i.getLongExtra("id", 0);
		bill_status = i.getStringExtra("bill_status");
		bill_no = i.getStringExtra("bill_no");
		bill_desc = i.getStringExtra("bill_desc");
		amount = i.getStringExtra("amount");

		TextView bill_noTV = (TextView) findViewById(R.id.bill_no);
		TextView bill_descTV = (TextView) findViewById(R.id.bill_desc);
		TextView bill_statusTV = (TextView) findViewById(R.id.bill_status);
		TextView amountTV = (TextView) findViewById(R.id.amount);

		Button okBtn = (Button) findViewById(R.id.ok);
		Button payBtn = (Button) findViewById(R.id.pay);

		if (bill_status.equals("PAID")) {
			okBtn.setVisibility(View.VISIBLE);
			payBtn.setVisibility(View.GONE);
		}
		else {
			okBtn.setVisibility(View.GONE);
			payBtn.setVisibility(View.VISIBLE);
		}

		bill_descTV.setText("Description: " + bill_desc);
		bill_noTV.setText("Bill #: " + bill_no);
		bill_statusTV.setText("Status: " + bill_status);
		amountTV.setText("$" + amount);
	}

	@Override
	protected void onResume() {
		super.onResume();
		// This assumes the view is full screen, which is a good assumption
		WindowManager manager = (WindowManager) getSystemService(WINDOW_SERVICE);
		Display display = manager.getDefaultDisplay();
		int width = display.getWidth();
		int height = display.getHeight();
		int smallerDimension = width < height ? width : height;
		smallerDimension = smallerDimension * 7 / 8;

		//Intent intent = getIntent();
		Intent intent = new Intent();
		intent.setAction(Intents.Encode.ACTION);
		intent.putExtra(Intents.Encode.FORMAT, BarcodeFormat.QR_CODE.toString());
		intent.putExtra(Intents.Encode.TYPE, Contents.Type.TEXT);

		String data = "BILL|" + bill_no + "|" + bill_status + "|" + bill_desc + "|" + amount;

		intent.putExtra(Intents.Encode.DATA, data);

		try {
			qrCodeEncoder = new QRCodeEncoder(this, intent, smallerDimension);
			setTitle(getString(R.string.app_name) + " - " + qrCodeEncoder.getTitle());
			Bitmap bitmap = qrCodeEncoder.encodeAsBitmap();
			ImageView view = (ImageView) findViewById(R.id.qr);
			view.setImageBitmap(bitmap);
		}
		catch (WriterException e) {
			Log.e(TAG, "Could not encode barcode", e);
			qrCodeEncoder = null;
		}
		catch (IllegalArgumentException e) {
			Log.e(TAG, "Could not encode barcode", e);
			qrCodeEncoder = null;
		}
	}

	public void ok(View v) {
		Intent i = new Intent().setClass(BillActivity.this, BillHome.class);
		startActivity(i);
		finish();
	}

	public void pay(View v) {
		Intent i = new Intent(BillActivity.this, PayBillActivity.class);
		i.putExtra("bill_no", bill_no);
		i.putExtra("amount", amount);
		startActivity(i);
		finish();
	}

	@Override
	public void onBackPressed() {
		Intent i = new Intent().setClass(BillActivity.this, BillHome.class);
		startActivity(i);
		finish();
		return;
	}

}
