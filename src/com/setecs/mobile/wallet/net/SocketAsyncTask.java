package com.setecs.mobile.wallet.net;

import static com.setecs.mobile.safe.apps.shared.Constants.SAFEIPNO;
import static com.setecs.mobile.safe.apps.shared.Constants.SERVERPORT;
import static com.setecs.mobile.wallet.safe.wallet.Constants.CONFIGFILENAME;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;

import com.setecs.mobile.safe.apps.shared.SAFEMessage;
import com.setecs.mobile.safe.apps.shared.SharedMethods;


public class SocketAsyncTask extends AsyncTask<String, Void, Integer> {

	private final Activity activity;
	private final SocketResponseHandler responseHandler;
	private final SharedMethods cv = new SharedMethods();
	private final SAFEMessage gwmsg = new SAFEMessage();
	private String serverGWMessage = "";
	private String ipAddress = "";
	protected String safeResponse = "";
	protected ProgressDialog progressDialog;

	private final int error = 0;
	private final int cancel = 1;
	private final int response = 2;

	private final String cancelErrorMsg = "Connection to SETECS Server has been cancelled!";
	private final String connectionErrorMsg = "Connection to SETECS Server could not be established!\nPlease check your network or contact Server Administrator.";

	public SocketAsyncTask(Activity activity, SocketResponseHandler responseHandler) {
		this.activity = activity;
		this.responseHandler = responseHandler;
	}

	@Override
	protected void onPreExecute() {
		progressDialog = ProgressDialog.show(activity, "", "Please wait...", true);
		progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
			@Override
			public void onCancel(DialogInterface dialog) {
				SocketAsyncTask.this.cancel(true);
			}

		});
		progressDialog.setCancelable(true);
	}

	@Override
	protected Integer doInBackground(String... msg) {

		if (!isOnline())
			return error;

		if (isCancelled())
			return cancel;

		try {
			ipAddress = cv.readConfigurationFile(activity, SAFEIPNO, CONFIGFILENAME);

			if (ipAddress.equals(""))
				return error;

			if (isCancelled())
				return cancel;

			Socket socket = new Socket();
			socket.connect(new InetSocketAddress(ipAddress, SERVERPORT), 10000);

			if (isCancelled())
				return cancel;

			String safeMsg = gwmsg.createGWMessage("0", msg[0]);

			if (isCancelled())
				return cancel;

			DataOutputStream out = new DataOutputStream(socket.getOutputStream());
			DataInputStream in = new DataInputStream(socket.getInputStream());

			if (isCancelled())
				return cancel;

			out.write(safeMsg.getBytes());
			out.flush();

			if (isCancelled())
				return cancel;

			int c = 0;
			int i = 0;
			StringBuffer sb;
			sb = new StringBuffer();

			if (isCancelled())
				return cancel;

			while ((c = in.read()) != -1) {

				if (isCancelled())
					return cancel;

				sb.append((char) c);
				if (c == '\n')
					break;
				i++;
			}

			if (isCancelled())
				return cancel;

			serverGWMessage = sb.toString();

			//serverGWMessage = readStringData(in);
			safeResponse = gwmsg.processGWMessage(serverGWMessage);

			socket.close();
		}
		catch (IOException e) {
			return error;
		}

		return response;
	}

	@Override
	protected void onPostExecute(Integer result) {
		if (progressDialog.isShowing())
			progressDialog.dismiss();
		switch (result) {
		case error:
			responseHandler.handleErrorResponse(connectionErrorMsg);
			break;
		case cancel:
			responseHandler.handleErrorResponse(cancelErrorMsg);
			break;
		case response:
			responseHandler.handleResponse(safeResponse);
			break;
		}
	}

	protected void onCancelled(Integer result) {
		responseHandler.handleErrorResponse(cancelErrorMsg);
	}

	public boolean isOnline() {
		ConnectivityManager cm = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo netInfo = cm.getActiveNetworkInfo();
		if (netInfo != null && netInfo.isConnectedOrConnecting()) {
			return true;
		}
		return false;
	}

}
