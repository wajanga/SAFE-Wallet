package com.setecs.mobile.wallet.msettings;

import static com.setecs.mobile.wallet.safe.wallet.Constants.ACCOUNTLIST;
import static com.setecs.mobile.wallet.safe.wallet.Constants.ACCOUNTLISTFILENAME;
import static com.setecs.mobile.wallet.safe.wallet.Constants.CONFIGFILENAME;
import static com.setecs.mobile.wallet.safe.wallet.Constants.MOBILENO;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.setecs.mobile.safe.apps.shared.SharedMethods;
import com.setecs.mobile.walletDev.R;


public class WalletParser {

	private final UserRegistration activity;
	private final SharedMethods cv = new SharedMethods();
	private boolean openSystem = true;

	public WalletParser(UserRegistration activity) {
		this.activity = activity;
	}

	public void parseUserRegistration(String serverResponse) {
		if (serverResponse.contains("Unknown customer")) {
			if (!serverResponse.contains("pre-registration is required")) {
				activity.setContentView(R.layout.user_reg);
				activity.fnametxt = (EditText) activity.findViewById(R.id.fnametxt);
				activity.lnametxt = (EditText) activity.findViewById(R.id.lnametxt);
				((Button) activity.findViewById(R.id.btn_usereg)).setOnClickListener(activity.userRegButtonListener);
			}
			else {
				openSystem = false;
				showMessage(serverResponse);
			}
		}
		else {
			showMessage(serverResponse);
		}
	}

	public void parseAndClose(String title, String message) {
		activity.setContentView(R.layout.system_msg);

		TextView msg_title = (TextView) activity.findViewById(R.id.title);
		msg_title.setText(title);

		TextView alert_message = (TextView) activity.findViewById(R.id.message);
		alert_message.setText(message);
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

	private void showMessage(String result) {

		activity.setContentView(R.layout.system_msg);

		TextView msg_title = (TextView) activity.findViewById(R.id.title);
		msg_title.setText("User Registration");

		TextView alert_message = (TextView) activity.findViewById(R.id.message);

		RelativeLayout Rlayout = (RelativeLayout) activity.findViewById(R.id.SingleLayout);
		Rlayout.setVisibility(View.VISIBLE);

		final EditText safePin = (EditText) activity.findViewById(R.id.enterSafePin);

		Button pos_button = (Button) activity.findViewById(R.id.btnSingle);

		if (result.contains("PENDING")) {
			if (result.contains("PIN:"))
				activity.pinmmsg = result.substring(result.indexOf("PIN") + 5, result.indexOf("PIN:") + 9);
			else if (result.contains("PIN is"))
				activity.pinmmsg = result.substring(result.indexOf("PIN is") + 7, result.indexOf("PIN is") + 11);
			if (result.contains("Name:")) {
				activity.userName = result.substring(result.indexOf("Name:") + 6, result.indexOf("PIN:"));
			}
			alert_message.setVisibility(View.VISIBLE);

			if (!openSystem) {
				alert_message.setText("Registration Pending!" + "\n" + "Please confirm");
				LinearLayout safePinLayout = (LinearLayout) activity.findViewById(R.id.safePinLayout);
				safePinLayout.setVisibility(View.VISIBLE);
			}
			else {
				alert_message.setText("Registration Pending!" + "\n"
						+ "Your PIN is:"
						+ activity.pinmmsg
						+ "\n"
						+ "Please confirm");
			}

			pos_button.setText("Confirm");
			pos_button.setVisibility(View.VISIBLE);
			pos_button.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					// confirm safe pin first
					if (!openSystem) {
						String safePinText = safePin.getText().toString();
						if (safePinText != null) {
							activity.pinmmsg = safePinText;
							activity.sendUserRegConf();
						}
						else {
							Toast.makeText(activity, "You must enter PIN", Toast.LENGTH_SHORT).show();
						}
					}
					else {
						activity.sendUserRegConf();
					}
				}
			});
		}
		else if (result.contains("SUCCESS")) {
			/*String successmsg = "Registration Successful\n\n" + "Customer ID: "
					+ result.substring(result.indexOf("SUCCESS") + 23, result.indexOf("SAFE Acc") - 1)
					+ "\nSAFE Account Number:\n"
					+ result.substring(result.indexOf("SAFE Acc. No:") + 14);*/

			alert_message.setText(result);
			alert_message.setVisibility(View.VISIBLE);
			pos_button.setText("	OK	");
			pos_button.setVisibility(View.VISIBLE);
			pos_button.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					if (SharedMethods.initFlag) {
						activity.saveRegistrationStatus(activity);
						cv.fetchSaveAccountList(activity, MOBILENO, CONFIGFILENAME, ACCOUNTLIST, ACCOUNTLISTFILENAME);
						activity.saveSafePIN(activity.pinmmsg, activity);
						if (activity.fnamemsg != null && activity.fnamemsg.length() > 0
								&& activity.lnamemsg.length() > 0
								&& activity.lnamemsg != null) {
							activity.saveUserName(activity.fnamemsg + " " + activity.lnamemsg);
						}
						else if (activity.userName != null && activity.userName.length() > 0) {
							activity.saveUserName(activity.userName);
						}
						activity.showMainScreen();
					}
					else {
						cv.fetchSaveAccountList(activity, MOBILENO, CONFIGFILENAME, ACCOUNTLIST, ACCOUNTLISTFILENAME);
						activity.finish();
					}
				}
			});

		}
		else if (result.contains("CONFIRMED")) {
			if (result.contains("Name:")) {
				if (result.contains("PIN:")) {
					activity.userName = result.substring(result.indexOf("Name:") + 6, result.indexOf("PIN:"));
				}
				else {
					openSystem = false;
					activity.userName = result.substring(result.indexOf("Name:") + 6, result.indexOf("Agent:"));
				}
			}
			if (result.contains("PIN:")) {
				activity.pinmmsg = result.replaceAll("\n", "").substring(
						result.replaceAll("\n", "").indexOf("PIN:") + 5, result.replaceAll("\n", "").indexOf("Agent:"));
			}
			alert_message.setVisibility(View.VISIBLE);

			if (!openSystem) {
				alert_message.setText("User is already registered successfully!" + "\n"
						+ "\n"
						+ "Name: "
						+ activity.userName
						+ "\n"
						+ "Please confirm");
				LinearLayout safePinLayout = (LinearLayout) activity.findViewById(R.id.safePinLayout);
				safePinLayout.setVisibility(View.VISIBLE);
			}
			else {
				alert_message.setText("User is already registered successfully!" + "\n"
						+ "\n"
						+ "Name: "
						+ activity.userName
						+ "\n"
						+ "PIN: "
						+ activity.pinmmsg);
			}

			pos_button.setText("	OK	");
			pos_button.setVisibility(View.VISIBLE);
			pos_button.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					// confirm safe pin first
					if (!openSystem) {
						String safePinText = safePin.getText().toString();
						if (safePinText != null) {
							activity.pinmmsg = safePinText;
							activity.sendUserRegConf();
						}
						else {
							Toast.makeText(activity, "You must enter PIN", Toast.LENGTH_SHORT).show();
						}
					}
					else {
						activity.saveSafeInformation(activity);
					}
				}
			});

		}
		else if (result.contains("SocketException")) {
			alert_message.setText("Connection to SETECS Server could not be established!" + "\n"
					+ "Please check your network or contact Server Administrator.");
			alert_message.setVisibility(View.VISIBLE);
			pos_button.setText("	OK	");
			pos_button.setVisibility(View.VISIBLE);
			pos_button.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					activity.finish();
				}
			});
		}
		else if (result.contains("pre-registration is required") && !result.contains("Unknown customer")) {
			alert_message.setVisibility(View.VISIBLE);
			alert_message.setText(result);

			LinearLayout safePinLayout = (LinearLayout) activity.findViewById(R.id.safePinLayout);
			safePinLayout.setVisibility(View.VISIBLE);

			pos_button.setText("Confirm");
			pos_button.setVisibility(View.VISIBLE);
			pos_button.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					// confirm safe pin first
					String safePinText = safePin.getText().toString();
					if (safePinText != null && safePinText.length() > 0) {
						activity.sendUserRegConfClosedSystem(safePinText);
					}
					else {
						Toast.makeText(activity, "Please enter PIN", Toast.LENGTH_SHORT).show();
					}
				}
			});
		}
		else if (result.contains("ERROR (112)")) {
			alert_message.setText("SETECS SYSTEM\n\nCustomer has been approved");
			alert_message.setVisibility(View.VISIBLE);
			pos_button.setText("	OK	");
			pos_button.setVisibility(View.VISIBLE);
			pos_button.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					activity.saveSafeInformation(activity);
				}
			});
		}
		else {
			alert_message.setText(result);
			alert_message.setVisibility(View.VISIBLE);
			pos_button.setText("	OK	");
			pos_button.setVisibility(View.VISIBLE);
			pos_button.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					activity.setRunned(true);
					activity.finish();
				}
			});
		}
	}

}
