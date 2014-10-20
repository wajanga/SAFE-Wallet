package com.setecs.mobile.wallet.market.main;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HttpContext;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.setecs.mobile.safe.apps.shared.CustomExceptionHandler;
import com.setecs.mobile.walletDev.R;
import com.setecs.mobile.wallet.market.database.MyDB;
import com.setecs.mobile.wallet.paymobile.OTAPayWalletGiftcard;


public class MarketVoucher extends ListActivity implements LocationListener {

	public double LAT, LNG;
	public Location currentLocation;
	public Handler progress_handler;
	public LinearLayout loadingLayout, feedbackLayout;
	public LocationManager locationManager;
	public Criteria criteria;
	public String bestProvider;
	public MyDB dba;
	public String location_results;
	public ArrayList<HashMap<String, String>> res;
	//public ServicesAdapter2 adapter;
	public HashMap<String, String> data;
	public int requestCode = 0;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Thread.setDefaultUncaughtExceptionHandler(new CustomExceptionHandler(MarketVoucher.this));

		constructGUI();
		openDatabase();
		handleGUIUpdates();
		//getLocationManager();

		Thread workthread = new Thread(new GetVoucherList());
		workthread.start();
	}

	/** Register for the updates when Activity is in foreground */
	/*@Override
	protected void onResume() {
		super.onResume();
		locationManager.requestLocationUpdates(bestProvider, 0, 0, this);
	}*/

	/** Stop the updates when Activity is paused */
	/*@Override
	protected void onPause() {
		super.onPause();
		locationManager.removeUpdates(this);
	}*/

	public void getLocationThroughAndroid() {
		new Thread() {
			@Override
			public void run() {
				// Check if longitude and latitude have not been set
				if ((LAT == 0) && (LNG == 0)) {
					currentLocation = locationManager.getLastKnownLocation(bestProvider);
					LAT = currentLocation.getLatitude();
					LNG = currentLocation.getLongitude();
				}
				Message msg = progress_handler.obtainMessage();
				msg.what = 3;
				msg.obj = ("Location received");
				progress_handler.sendMessage(msg);
			}
		}.start();
	}

	public void constructGUI() {
		setContentView(R.layout.prom_view);
		//setContentView(R.layout.test2);
		loadingLayout = (LinearLayout) findViewById(R.id.loading_layout);
		loadingLayout.setVisibility(View.VISIBLE); // Show the loading screen
	}

	public void getLocationManager() {
		locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
		criteria = new Criteria();
		bestProvider = locationManager.getBestProvider(criteria, false);
		locationManager.requestLocationUpdates(bestProvider, 0, 0, this);
	}

	public void openDatabase() {
		dba = new MyDB(this);
		dba.open();
	}

	public List<NameValuePair> setRequestParameters() {
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("lat", Double.toString(LAT)));
		nameValuePairs.add(new BasicNameValuePair("lng", Double.toString(LNG)));
		return nameValuePairs;
	}

	public String setPath() {
		String path = WalletConstants.PROMOTION_LIST_PATH;
		return path;
	}

	public void in_market(View v) {
		// Do nothing
	}

	@Override
	public void onBackPressed() {
		// do something on back.
		Intent i = new Intent(this, HomeActivity.class);
		startActivity(i);
		return;
	}

	// Handler for processing gui update messages
	private void handleGUIUpdates() {
		final TextView load_text = (TextView) findViewById(R.id.loading_text);
		progress_handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				// process incoming messages here
				switch (msg.what) {
				case 0: // update progress bar during processing
					load_text.setText((String) msg.obj);
					break;
				case 1: // Results from server came back
					loadingLayout.setVisibility(View.GONE); // Hide the loading
															// screen

					location_results = (String) msg.obj;
					if (location_results.contains("LOCATION_RESTRICTION")) {
						getListView().setEmptyView(findViewById(R.id.restriction));
					}
					else if (location_results.equals("")) {
						getListView().setEmptyView(getListView().getEmptyView());
					}
					else {
						res = convertJSONtoStringArray(location_results);
						setAdapter(res);
					}
					break;
				case 2: // Location data came back
					load_text.setText((String) msg.obj);
					break;
				case 3: // Location data came back
					Thread workthread = new Thread(new GetVoucherList());
					workthread.start();
					break;
				}
			}
		};
	}

	public void setAdapter(ArrayList<HashMap<String, String>> data) {
		//ServicesAdapter2 adapter = new ServicesAdapter2(MarketVoucher.this, data);
		//setListAdapter(adapter);
	}

	public String parseHTTPResponseBody(HttpEntity http_entity) {
		String result = null;
		try {
			InputStream in = http_entity.getContent();
			BufferedReader reader = new BufferedReader(new InputStreamReader(in));
			StringBuilder str = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				str.append(line);
			}
			in.close();
			result = str.toString();

		}
		catch (Exception ex) {
			result = "Error";
		}
		return result;
	}

	public ArrayList<HashMap<String, String>> convertJSONtoStringArray(String result) {
		ArrayList<HashMap<String, String>> mylist = new ArrayList<HashMap<String, String>>();
		HashMap<String, String> map;

		try {
			JSONArray entries = new JSONArray(result);
			for (int i = 0; i < entries.length(); i++) {
				JSONObject location = entries.getJSONObject(i);
				map = new HashMap<String, String>();
				map.put("id", location.getString("id"));
				map.put("merchant", location.getString("merchant"));
				map.put("merchant_name", location.getString("merchant_name"));
				map.put("description", location.getString("description"));
				map.put("startdate", location.getString("start_date"));
				map.put("enddate", location.getString("end_date"));
				map.put("image_name", location.getString("image_name"));
				mylist.add(map);
			}
		}
		catch (JSONException e) {
			Log.e("MarketVoucher", "JSON Error: " + e.getMessage());
		}
		return mylist;
	}

	public void displayWithoutIntent(String title, String text_to_display) {
		new AlertDialog.Builder(MarketVoucher.this).setTitle(title).setMessage(text_to_display)
				.setPositiveButton("OK", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int arg1) {
						dialog.cancel();
					}
				}).show();
	}

	@SuppressWarnings("unchecked")
	public void prompt(String text_to_display, String title, final int position) {
		data = (HashMap<String, String>) res.toArray()[position];
		new AlertDialog.Builder(MarketVoucher.this).setTitle(title).setMessage(text_to_display)
				.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int arg1) {
						dialog.cancel();
						Intent i = new Intent(MarketVoucher.this, OTAPayWalletGiftcard.class);
						i.putExtra("amount", data.get("amount"));
						i.putExtra("account", data.get("merchant_account"));
						i.putExtra("position", position);
						startActivityForResult(i, requestCode);
					}
				}).setNegativeButton("No", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int arg1) {
						dialog.cancel();
						return;
					}
				}).show();
	}

	@Override
	public void onLocationChanged(Location loc) {

		if (isBetterLocation(loc, currentLocation)) {
			currentLocation = loc;
			LAT = currentLocation.getLatitude();
			LNG = currentLocation.getLongitude();
			getLocationThroughAndroid();
		}
	}

	@Override
	public void onProviderDisabled(String arg0) {

	}

	@Override
	public void onProviderEnabled(String provider) {

	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {

	}

	protected boolean isBetterLocation(Location location, Location currentBestLocation) {
		if (currentBestLocation == null) {
			// A new location is always better than no location
			return true;
		}

		// Check whether the new location fix is newer or older
		long timeDelta = location.getTime() - currentBestLocation.getTime();
		boolean isSignificantlyNewer = timeDelta > WalletConstants.TWO_MINUTES;
		boolean isSignificantlyOlder = timeDelta < -WalletConstants.TWO_MINUTES;
		boolean isNewer = timeDelta > 0;

		// If it's been more than two minutes since the current location, use
		// the new location
		// because the user has likely moved
		if (isSignificantlyNewer) {
			return true;
			// If the new location is more than two minutes older, it must be
			// worse
		}
		else if (isSignificantlyOlder) {
			return false;
		}

		// Check whether the new location fix is more or less accurate
		int accuracyDelta = (int) (location.getAccuracy() - currentBestLocation.getAccuracy());
		boolean isLessAccurate = accuracyDelta > 0;
		boolean isMoreAccurate = accuracyDelta < 0;
		boolean isSignificantlyLessAccurate = accuracyDelta > 200;

		// Check if the old and new location are from the same provider
		boolean isFromSameProvider = isSameProvider(location.getProvider(), currentBestLocation.getProvider());

		// Determine location quality using a combination of timeliness and
		// accuracy
		if (isMoreAccurate) {
			return true;
		}
		else if (isNewer && !isLessAccurate) {
			return true;
		}
		else if (isNewer && !isSignificantlyLessAccurate && isFromSameProvider) {
			return true;
		}
		return false;
	}

	/** Checks whether two providers are the same */
	private boolean isSameProvider(String provider1, String provider2) {
		if (provider1 == null) {
			return provider2 == null;
		}
		return provider1.equals(provider2);
	}

	class GetVoucherList implements Runnable {

		Message msg;
		private ClientConnectionManager clientConnectionManager;
		private HttpContext context;
		private HttpParams params;

		final ResponseHandler<String> responseHandler = new ResponseHandler<String>() {
			@Override
			public String handleResponse(HttpResponse response) {
				HttpEntity entity = response.getEntity();
				String result = null;

				msg = new Message();
				msg.what = 0;
				msg.obj = ("Parsing ...");
				progress_handler.sendMessage(msg);
				result = parseHTTPResponseBody(entity);

				// Send nearby location results to GUI handler
				msg = new Message();
				msg.what = 1;
				msg.obj = result;
				progress_handler.sendMessage(msg);
				return result;
			}
		};

		@Override
		public void run() {

			try {
				msg = new Message();
				msg.what = 0;
				msg.obj = ("Connecting ...");
				progress_handler.sendMessage(msg);

				// Create a new HttpClient and Post Header
				HttpClient httpclient = new DefaultHttpClient(clientConnectionManager, params);
				HttpPost httppost = new HttpPost("http://" + WalletConstants.SERVER_ADDRESS + setPath());

				// Add your data
				List<NameValuePair> nameValuePairs = setRequestParameters();
				httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

				// Execute HTTP Post Request
				httpclient.execute(httppost, responseHandler, context);

			}
			catch (Exception e) {
				Log.d("ShowLocations", "Exception: " + e.getMessage());
				msg = new Message();
				msg.what = 2; // error occured
				msg.obj = ("Connection Error. Please try again");
				progress_handler.sendMessage(msg);

			}
		}
	}

}
