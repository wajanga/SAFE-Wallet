package com.setecs.mobile.wallet.market.main;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.NameValuePair;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.params.ConnManagerPNames;
import org.apache.http.conn.params.ConnPerRouteBean;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.setecs.mobile.safe.apps.util.security.EasySSLSocketFactory;


public class BaseClass extends Activity {

	Handler progress_handler;
	double LAT, LNG;

	class RetrieveLocations implements Runnable {

		private final String resource_path = "/location/nearby_loc.php";
		Message msg;
		private ClientConnectionManager clientConnectionManager;
		private HttpContext context;
		private HttpParams params;
		Handler progress_handler = getHandler();

		final ResponseHandler<String> responseHandler = new ResponseHandler<String>() {
			@Override
			public String handleResponse(HttpResponse response) {
				HttpEntity entity = response.getEntity();
				String result = null;

				msg = new Message();
				msg.what = 0;
				msg.obj = ("Parsing ...");
				progress_handler.sendMessage(msg);
				result = parseResponseBody(entity);

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

				prepareHTTPSConnection();

				// Create a new HttpClient and Post Header
				HttpClient httpclient = new DefaultHttpClient(clientConnectionManager, params);
				//HttpPost httppost = new HttpPost("http://" + server_settings.getServerAddress() + resource_path);
				//HttpPost httppost = new HttpPost("http://130.229.153.86" + resource_path);
				HttpPost httppost = new HttpPost("https://10.0.2.2" + resource_path);

				// Add your data
				List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
				nameValuePairs.add(new BasicNameValuePair("lat", Double.toString(LAT)));
				nameValuePairs.add(new BasicNameValuePair("lng", Double.toString(LNG)));
				httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

				// Execute HTTP Post Request
				httpclient.execute(httppost, responseHandler, context);

			}
			catch (Exception e) {
				Log.d("ShowLocations", "Exception: " + e.getMessage());
				msg = new Message();
				msg.what = 2; // error occured
				msg.obj = ("Caught an error while retrieving location of nearby services");
				progress_handler.sendMessage(msg);

			}
		}

		private String parseResponseBody(HttpEntity http_entity) {
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

		private void prepareHTTPSConnection() {
			SchemeRegistry schemeRegistry = new SchemeRegistry();
			// http scheme
			schemeRegistry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
			// https scheme
			schemeRegistry.register(new Scheme("https", new EasySSLSocketFactory(), 443));

			params = new BasicHttpParams();
			params.setParameter(ConnManagerPNames.MAX_TOTAL_CONNECTIONS, 1);
			params.setParameter(ConnManagerPNames.MAX_CONNECTIONS_PER_ROUTE, new ConnPerRouteBean(1));
			params.setParameter(HttpProtocolParams.USE_EXPECT_CONTINUE, false);
			HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
			HttpProtocolParams.setContentCharset(params, "utf8");

			// ignore that the ssl cert is self signed
			CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
			credentialsProvider.setCredentials(new AuthScope("yourServerHere.com", AuthScope.ANY_PORT),
					new UsernamePasswordCredentials("YourUserNameHere", "UserPasswordHere"));
			clientConnectionManager = new ThreadSafeClientConnManager(params, schemeRegistry);

			context = new BasicHttpContext();
			context.setAttribute("http.auth.credentials-provider", credentialsProvider);
		}
	}

	private Handler getHandler() {
		return progress_handler;
	}

}
