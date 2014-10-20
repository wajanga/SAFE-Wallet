package com.setecs.mobile.wallet.paymobile;

import static com.setecs.mobile.wallet.safe.wallet.Constants.ACCOUNTLIST;
import static com.setecs.mobile.wallet.safe.wallet.Constants.ACCOUNTLISTFILENAME;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.setecs.mobile.safe.apps.shared.SharedMethods;
import com.setecs.mobile.walletDev.R;


public class OTCPayWallet extends Activity {
	// Debugging
	private static final String TAG = "OTCPayWallet";
	private static final boolean D = true;

	private BluetoothAdapter mBluetoothAdapter = null;
	private BluetoothService btService = null;

	// Message types sent from the BluetoothChatService Handler
	public static final int MESSAGE_STATE_CHANGE = 1;
	public static final int MESSAGE_READ = 2;
	public static final int MESSAGE_WRITE = 3;
	public static final int MESSAGE_DEVICE_NAME = 4;
	public static final int MESSAGE_TOAST = 5;

	// Intent request codes
	private static final int REQUEST_CONNECT_DEVICE_SECURE = 1;
	private static final int REQUEST_CONNECT_DEVICE_INSECURE = 2;
	private static final int REQUEST_ENABLE_BT = 3;

	// Key names received from the BluetoothService Handler
	public static final String DEVICE_NAME = "device_name";
	public static final String TOAST = "toast";

	private TextView safeAccountTV, status;
	private Button btnConnectBT, btnSendAcc;
	private String btConnectedDeviceName = null;

	private final SharedMethods cv = new SharedMethods();
	private String accountNo = "";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.otc_pay_wallet);

		mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
		if (mBluetoothAdapter == null) {
			Toast.makeText(this, "Bluetooth is not available", Toast.LENGTH_LONG).show();
			finish();
			return;
		}
	}

	@Override
	public void onStart() {
		super.onStart();
		if (!mBluetoothAdapter.isEnabled()) {
			Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
			startActivityForResult(enableIntent, REQUEST_ENABLE_BT);
		}
		else {
			if (btService == null)
				initGUI();
		}
	}

	@Override
	public synchronized void onResume() {
		super.onResume();
		if (btService != null) {
			if (btService.getState() == BluetoothService.STATE_NONE) {
				btService.start();
			}
		}
	}

	@Override
	public synchronized void onPause() {
		super.onPause();
		if (D)
			Log.e(TAG, "- ON PAUSE -");
	}

	@Override
	public void onStop() {
		super.onStop();
		if (D)
			Log.e(TAG, "-- ON STOP --");
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		// Stop the Bluetooth chat services
		if (btService != null)
			btService.stop();
		if (D)
			Log.e(TAG, "--- ON DESTROY ---");
	}

	@Override
	public void onBackPressed() {
		if (btService != null)
			btService.stop();
		finish();
	}

	private void initGUI() {
		safeAccountTV = (TextView) findViewById(R.id.safeAccount);
		accountNo = cv.fetchAccountList(OTCPayWallet.this, ACCOUNTLIST, ACCOUNTLISTFILENAME);
		safeAccountTV.setText("SAFE Account: " + accountNo);

		status = (TextView) findViewById(R.id.btStatus);
		btnConnectBT = (Button) findViewById(R.id.btnConnectMerchant);
		btnSendAcc = (Button) findViewById(R.id.btnSendAcc);
		btService = new BluetoothService(this, mHandler);
	}

	public void connectMerchant(View v) {
		Intent serverIntent = null;
		serverIntent = new Intent(this, DeviceListActivity.class);
		startActivityForResult(serverIntent, REQUEST_CONNECT_DEVICE_SECURE);
	}

	public void sendAcc(View v) {
		if (!accountNo.equals(""))
			sendMessage(accountNo);
	}

	private void sendMessage(String message) {
		if (btService.getState() != BluetoothService.STATE_CONNECTED) {
			Toast.makeText(this, R.string.not_connected, Toast.LENGTH_SHORT).show();
			return;
		}
		if (message.length() > 0) {
			byte[] send = message.getBytes();
			btService.write(send);
		}
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (D)
			Log.d(TAG, "onActivityResult " + resultCode);
		switch (requestCode) {
		case REQUEST_CONNECT_DEVICE_SECURE:
			if (resultCode == Activity.RESULT_OK) {
				connectDevice(data, true);
			}
			break;
		case REQUEST_CONNECT_DEVICE_INSECURE:
			if (resultCode == Activity.RESULT_OK) {
				connectDevice(data, false);
			}
			break;
		case REQUEST_ENABLE_BT:
			// When the request to enable Bluetooth returns
			if (resultCode == Activity.RESULT_OK) {
				initGUI();
			}
			else {
				// User did not enable Bluetooth or an error occurred
				Log.d(TAG, "BT not enabled");
				Toast.makeText(this, R.string.bt_not_enabled_leaving, Toast.LENGTH_SHORT).show();
				finish();
			}
		}
	}

	private void connectDevice(Intent data, boolean secure) {
		String address = data.getExtras().getString(DeviceListActivityOriginal.EXTRA_DEVICE_ADDRESS);
		BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(address);
		btService.connect(device, secure);
	}

	private final Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case MESSAGE_STATE_CHANGE:
				if (D)
					Log.i(TAG, "MESSAGE_STATE_CHANGE: " + msg.arg1);
				switch (msg.arg1) {
				case BluetoothService.STATE_CONNECTED:
					status.setText("BT Status: Connected to " + btConnectedDeviceName);
					break;
				case BluetoothService.STATE_CONNECTING:
					status.setText("BT Status: Connecting...");
					btnConnectBT.setVisibility(View.GONE);
					btnSendAcc.setVisibility(View.VISIBLE);
					break;
				case BluetoothService.STATE_LISTEN:
					status.setText("BT Status: Started...");
					break;
				case BluetoothService.STATE_NONE:
					status.setText("BT Status: None");
					break;
				}
				break;
			case MESSAGE_WRITE:
				byte[] writeBuf = (byte[]) msg.obj;
				// construct a string from the buffer
				String writeMessage = new String(writeBuf);
				break;
			case MESSAGE_READ:
				byte[] readBuf = (byte[]) msg.obj;
				// construct a string from the valid bytes in the buffer
				String readMessage = new String(readBuf, 0, msg.arg1);
				if (readMessage.equals("end")) {
					if (btService != null)
						btService.stop();
					finish();
				}
				break;
			case MESSAGE_DEVICE_NAME:
				// save the connected device's name
				btConnectedDeviceName = msg.getData().getString(DEVICE_NAME);
				Toast.makeText(getApplicationContext(), "Connected to " + btConnectedDeviceName, Toast.LENGTH_SHORT)
						.show();
				break;
			case MESSAGE_TOAST:
				Toast.makeText(getApplicationContext(), msg.getData().getString(TOAST), Toast.LENGTH_SHORT).show();
				break;
			}
		}
	};

}
