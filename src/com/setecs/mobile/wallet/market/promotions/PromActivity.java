package com.setecs.mobile.wallet.market.promotions;

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
import com.setecs.mobile.wallet.market.provider.PromProvider;
import com.setecs.mobile.wallet.market.utility.QRCodeEncoder;
import com.setecs.mobile.wallet.market.utility.SafeReplyHandler;


public class PromActivity extends Activity implements SafeReplyHandler {

	private static final String TAG = "m-Merchant";
	private long id;
	private ImageLoader imageLoader;
	private QRCodeEncoder qrCodeEncoder;
	private String description = null, startDate = null, endDate = null;
	private String merchantAccount = null;
	private String merchantName = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.prom_test);
		//setContentView(R.layout.prom);
		Intent i = getIntent();

		id = i.getLongExtra("id", 0);
		merchantAccount = i.getStringExtra("merchant_account");
		merchantName = i.getStringExtra("merchant_name");
		description = i.getStringExtra("description");
		startDate = i.getStringExtra("start_date");
		endDate = i.getStringExtra("end_date");
		imageLoader = new ImageLoader(this);

		TextView prom_text = (TextView) findViewById(R.id.prom_text);
		TextView prom_start = (TextView) findViewById(R.id.prom_start);
		TextView prom_end = (TextView) findViewById(R.id.prom_end);
		ImageView icon = (ImageView) findViewById(R.id.testView1);

		prom_text.setText(description);
		prom_start.setText("START DATE: " + startDate);
		prom_end.setText("END DATE: " + endDate);
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

		String data = "PROM|" + merchantAccount
				+ "|"
				+ merchantName
				+ "|"
				+ description
				+ "|"
				+ startDate
				+ "|"
				+ endDate;

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

	public void prom_ok(View v) {
		Intent i = new Intent().setClass(PromActivity.this, Home.class);
		startActivity(i);
		finish();
	}

	public void prom_delete(View v) {
		confirmDelete();
	}

	public void deleteProm() {
		String where = Constants.KEY_ID + "=" + id;
		String whereArgs[] = null;

		ContentResolver cr = getContentResolver();

		int deletedRowCount = cr.delete(PromProvider.CONTENT_URI, where, whereArgs);
		if (deletedRowCount > 0)
			Toast.makeText(this, "m-Promotion deleted successfully", Toast.LENGTH_SHORT).show();
	}

	private void confirmDelete() {

		final AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage("Are you sure you want to delete?").setCancelable(false)
				.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(final DialogInterface dialog, final int id) {
						deleteProm();
						Intent i = new Intent().setClass(PromActivity.this, Home.class);
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
		showResponse(message, "m-Promotion");
	}

	private void showResponse(String result, String msgtitle) {
		DisplaySAFEResponse responseScreen = new DisplaySAFEResponse();
		responseScreen.displaySAFErespons(this, result, msgtitle);
	}

	@Override
	public void onBackPressed() {
		Intent i = new Intent().setClass(PromActivity.this, Home.class);
		startActivity(i);
		finish();
		return;
	}

}
