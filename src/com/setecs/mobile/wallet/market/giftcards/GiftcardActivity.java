package com.setecs.mobile.wallet.market.giftcards;

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
import com.setecs.mobile.wallet.market.provider.GiftcardProvider;
import com.setecs.mobile.wallet.market.utility.QRCodeEncoder;
import com.setecs.mobile.wallet.market.utility.SafeReplyHandler;


public class GiftcardActivity extends Activity implements SafeReplyHandler {

	private static final String TAG = "m-Merchant";
	private long id;
	private ImageLoader imageLoader;
	private QRCodeEncoder qrCodeEncoder;
	private String description = null, amount = null;
	private String merchantAccount = null;
	private String merchantName = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.coupon_activity);
		TextView header = (TextView) findViewById(R.id.widget236);
		header.setText("View / Delete Gift Card");

		Intent i = getIntent();
		id = i.getLongExtra("id", 0);
		merchantAccount = i.getStringExtra("merchant_account");
		merchantName = i.getStringExtra("merchant_name");
		description = i.getStringExtra("description");
		amount = i.getStringExtra("amount");
		imageLoader = new ImageLoader(this);

		TextView descriptionTV = (TextView) findViewById(R.id.description);
		TextView start_date = (TextView) findViewById(R.id.start_date);
		TextView end_date = (TextView) findViewById(R.id.end_date);
		TextView amountTV = (TextView) findViewById(R.id.amount);
		ImageView icon = (ImageView) findViewById(R.id.testView1);

		descriptionTV.setText(description);
		start_date.setVisibility(View.GONE);
		end_date.setVisibility(View.GONE);
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

		String data = "GIFTCARD|" + merchantAccount + "|" + merchantName + "|" + description + "|" + amount;

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
		Intent i = new Intent().setClass(GiftcardActivity.this, GiftHome.class);
		startActivity(i);
		finish();
	}

	public void delete(View v) {
		confirmDelete();
	}

	public void deleteGiftcard() {
		String where = Constants.KEY_ID + "=" + id;
		String whereArgs[] = null;

		ContentResolver cr = getContentResolver();

		int deletedRowCount = cr.delete(GiftcardProvider.CONTENT_URI, where, whereArgs);
		if (deletedRowCount > 0)
			Toast.makeText(this, "m-Gift Card deleted successfully", Toast.LENGTH_SHORT).show();
	}

	private void confirmDelete() {

		final AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage("Are you sure you want to delete?").setCancelable(false)
				.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(final DialogInterface dialog, final int id) {
						deleteGiftcard();
						Intent i = new Intent().setClass(GiftcardActivity.this, GiftHome.class);
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
		showResponse(message, "m-Gift Card");
	}

	private void showResponse(String result, String msgtitle) {
		DisplaySAFEResponse responseScreen = new DisplaySAFEResponse();
		responseScreen.displaySAFErespons(this, result, msgtitle);
	}

	@Override
	public void onBackPressed() {
		Intent i = new Intent().setClass(GiftcardActivity.this, GiftHome.class);
		startActivity(i);
		finish();
		return;
	}

}
