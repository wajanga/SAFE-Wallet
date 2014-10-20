package com.setecs.mobile.wallet.market.utility;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.setecs.mobile.walletDev.R;


public class WalletActivity extends Activity {

	public void showDialog(String title, String message) {
		new AlertDialog.Builder(this).setTitle(title).setMessage(message)
				.setPositiveButton("OK", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int arg1) {
						dialog.cancel();
						return;
					}
				}).show();
	}

	public boolean isFieldEmpty(EditText e) {
		boolean result = false;
		if (e.getText().toString().equals(""))
			result = true;
		return result;
	}

	public void showDialogAndFinish(String title, String message) {
		new AlertDialog.Builder(this).setTitle(title).setMessage(message)
				.setPositiveButton("OK", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int arg1) {
						dialog.cancel();
						finish();
					}
				}).show();
	}

	public void displayResponseAndCloseActivity(final Activity activity, String title, String result) {

		activity.setContentView(R.layout.system_msg);

		TextView msg_title = (TextView) activity.findViewById(R.id.title);
		msg_title.setText(title);

		TextView alert_message = (TextView) activity.findViewById(R.id.message);
		alert_message.setText(result);
		alert_message.setVisibility(View.VISIBLE);

		RelativeLayout Rlayout = (RelativeLayout) activity.findViewById(R.id.SingleLayout);
		Rlayout.setVisibility(View.VISIBLE);

		Button pos_button = (Button) activity.findViewById(R.id.btnSingle);
		pos_button.setText("	OK	");
		pos_button.setVisibility(View.VISIBLE);
		pos_button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				activity.finish();
			}
		});
	}

}
