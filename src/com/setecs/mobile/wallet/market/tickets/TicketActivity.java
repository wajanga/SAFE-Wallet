package com.setecs.mobile.wallet.market.tickets;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.android.Contents;
import com.google.zxing.client.android.Intents;
import com.setecs.mobile.safe.apps.shared.DisplaySAFEResponse;
import com.setecs.mobile.walletDev.R;
import com.setecs.mobile.wallet.imagedownloader.ImageLoader;
import com.setecs.mobile.wallet.market.database.Constants;
import com.setecs.mobile.wallet.market.main.MarketConstant;
import com.setecs.mobile.wallet.market.provider.TicketProvider;
import com.setecs.mobile.wallet.market.utility.QRCodeEncoder;
import com.setecs.mobile.wallet.market.utility.SafeReplyHandler;


public class TicketActivity extends Activity implements SafeReplyHandler {

	private static final String TAG = "m-Merchant";
	private ImageLoader imageLoader;
	private QRCodeEncoder qrCodeEncoder;
	private long id;
	private String description = null, date = null, time = null, amount = null;
	private String merchantAccount = null;
	private String merchantName = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		//setContentView(R.layout.mtokendetail);
		setContentView(R.layout.m_ticket);
		TextView header = (TextView) findViewById(R.id.widget236);
		header.setText("View / Delete Ticket");

		Intent i = getIntent();
		id = i.getLongExtra("id", 0);
		merchantAccount = i.getStringExtra("merchant_account");
		merchantName = i.getStringExtra("merchant_name");
		description = i.getStringExtra("description");
		date = i.getStringExtra("date");
		time = i.getStringExtra("time");
		amount = i.getStringExtra("amount");
		imageLoader = new ImageLoader(this);

		TextView descriptionTV = (TextView) findViewById(R.id.description);
		TextView dateTV = (TextView) findViewById(R.id.date);
		TextView timeTV = (TextView) findViewById(R.id.time);
		TextView amountTV = (TextView) findViewById(R.id.amount);
		ImageView icon = (ImageView) findViewById(R.id.testView1);

		descriptionTV.setText(description);
		dateTV.setText("DATE: " + date);
		timeTV.setText("TIME: " + time);
		amountTV.setText("$" + amount);

		if (!i.getStringExtra("image_name").equals("none.jpg"))
			imageLoader.DisplayImage(MarketConstant.IMAGE_ADDRESS + i.getStringExtra("image_name"), this, icon);
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

		String data = "TICKET|" + merchantAccount
				+ "|"
				+ merchantName
				+ "|"
				+ description
				+ "|"
				+ date
				+ "|"
				+ time
				+ "|"
				+ amount;

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
		Intent i = new Intent().setClass(TicketActivity.this, TicketsHome.class);
		startActivity(i);
		finish();
	}

	public void delete(View v) {
		confirmDelete();
	}

	public void deleteTicket() {
		String where = Constants.KEY_ID + "=" + id;
		String whereArgs[] = null;

		ContentResolver cr = getContentResolver();

		int deletedRowCount = cr.delete(TicketProvider.CONTENT_URI, where, whereArgs);
		if (deletedRowCount > 0)
			Toast.makeText(this, "m-Ticket deleted successfully", Toast.LENGTH_SHORT).show();
	}

	private void confirmDelete() {

		final AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage("Are you sure you want to delete?").setCancelable(false)
				.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(final DialogInterface dialog, final int id) {
						deleteTicket();
						Intent i = new Intent().setClass(TicketActivity.this, TicketsHome.class);
						startActivity(i);
						finish();
					}
				}).setNegativeButton("No", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(final DialogInterface dialog, final int id) {
						dialog.cancel();
					}
				});
		final AlertDialog alert = builder.create();
		alert.show();
	}

	@Override
	public void displayList(ArrayList<?> object) {
		// do nothing
	}

	@Override
	public void displayMessage(String message) {
		showResponse(message, "m-Ticket");
	}

	private void showResponse(String result, String msgtitle) {
		DisplaySAFEResponse responseScreen = new DisplaySAFEResponse();
		responseScreen.displaySAFErespons(this, result, msgtitle);
	}

	@Override
	public void onBackPressed() {
		Intent i = new Intent().setClass(TicketActivity.this, TicketsHome.class);
		startActivity(i);
		finish();
		return;
	}

}
