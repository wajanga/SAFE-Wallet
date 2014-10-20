package com.setecs.mobile.wallet.net;

import static com.setecs.mobile.safe.apps.shared.Constants.SAFEIPNO;
import static com.setecs.mobile.wallet.safe.wallet.Constants.CONFIGFILENAME;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.text.format.DateUtils;

import com.setecs.mobile.safe.apps.shared.SharedMethods;
import com.setecs.mobile.wallet.market.utility.SafeReplyHandler;
import com.setecs.mobile.wallet.market.utility.SafeReplyParser;


public class HttpAsyncTask extends AsyncTask<String, Void, Integer> {

	protected ProgressDialog progressDialog;
	private final SafeReplyParser replyParser;
	private final SafeReplyHandler replyHandler;
	private final Activity activity;
	protected String safeResponse = "";
	private String ipAddress = "";
	private String resourcePath = "";
	private final SharedMethods cv = new SharedMethods();
	private final List<NameValuePair> nameValuePairs;

	private final int error = 0;
	private final int cancel = 1;
	private final int response = 2;

	private final String cancelErrorMsg = "Connection to SETECS Server has been cancelled!";
	private final String connectionErrorMsg = "Connection to SETECS Server could not be established!\nPlease check your network or contact Server Administrator.";

	public HttpAsyncTask(Activity activity, String resourcePath, List<NameValuePair> nameValuePairs,
			SafeReplyParser replyParser, SafeReplyHandler replyHandler) {
		this.activity = activity;
		this.replyParser = replyParser;
		this.resourcePath = resourcePath;
		this.nameValuePairs = nameValuePairs;
		this.replyHandler = replyHandler;
	}

	@Override
	protected void onPreExecute() {
		progressDialog = ProgressDialog.show(activity, "", "Please wait...", true);
		progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
			@Override
			public void onCancel(DialogInterface dialog) {
				HttpAsyncTask.this.cancel(true);
			}

		});
		progressDialog.setCancelable(true);
	}

	@Override
	protected Integer doInBackground(String... msg) {

		if (!isOnline()) {
			replyParser.parse(connectionErrorMsg);
			return error;
		}

		if (isCancelled()) {
			replyParser.parse(cancelErrorMsg);
			return cancel;
		}

		HttpParams httpParams = new BasicHttpParams();
		int some_reasonable_timeout = (int) (30 * DateUtils.SECOND_IN_MILLIS); // 30 seconds
		HttpConnectionParams.setConnectionTimeout(httpParams, some_reasonable_timeout);
		HttpConnectionParams.setSoTimeout(httpParams, some_reasonable_timeout);

		HttpClient httpclient = new DefaultHttpClient(httpParams);

		try {
			ipAddress = cv.readConfigurationFile(activity, SAFEIPNO, CONFIGFILENAME);
			//ipAddress = "130.237.215.188";

			HttpPost httppost = new HttpPost("http://" + ipAddress + resourcePath);

			HttpResponse httpResponse = null;
			HttpEntity entity = null;

			if (ipAddress.equals("")) {
				replyParser.parse(connectionErrorMsg);
				return error;
			}

			if (isCancelled()) {
				replyParser.parse(cancelErrorMsg);
				return cancel;
			}

			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			httpResponse = httpclient.execute(httppost);
			StatusLine status = httpResponse.getStatusLine();
			if (status.getStatusCode() == HttpStatus.SC_OK) {
				entity = httpResponse.getEntity();
				safeResponse = getResponseBody(entity);
				replyParser.parse(safeResponse);
			}
			else {
				replyParser.parse(connectionErrorMsg);
				return error;
			}
		}
		catch (IOException e) {
			replyParser.parse(connectionErrorMsg);
			return error;
		}
		finally {
			httpclient.getConnectionManager().shutdown();
		}

		return response;
	}

	@Override
	protected void onPostExecute(Integer result) {
		if (progressDialog.isShowing())
			progressDialog.dismiss();
		switch (result) {
		case error:
			replyParser.output(replyHandler, connectionErrorMsg);
			break;
		case cancel:
			replyParser.output(replyHandler, cancelErrorMsg);
			break;
		case response:
			replyParser.output(replyHandler, "");
			break;
		}
	}

	private boolean isOnline() {
		ConnectivityManager cm = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo netInfo = cm.getActiveNetworkInfo();
		if (netInfo != null && netInfo.isConnectedOrConnecting()) {
			return true;
		}
		return false;
	}

	private String getResponseBody(HttpEntity http_entity) {
		String result = null;
		try {
			InputStream in = http_entity.getContent();
			BufferedReader reader = new BufferedReader(new InputStreamReader(in));
			StringBuilder str = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				str.append(line + "\n");
			}
			in.close();
			result = str.toString();
		}
		catch (Exception ex) {
			result = "Error";
		}
		return result;
	}

}
