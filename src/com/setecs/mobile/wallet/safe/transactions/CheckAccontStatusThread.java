package com.setecs.mobile.wallet.safe.transactions;

import static com.setecs.mobile.safe.apps.shared.Constants.SERVERPORT;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.concurrent.Callable;

import android.util.Log;

import com.setecs.mobile.safe.apps.shared.SAFEMessage;


public class CheckAccontStatusThread implements Callable<String> {

	private Object clientMsg;
	private String walletMsg;
	private String processGWMessage;
	private StringBuffer sb;
	private String receivedData;
	private final String userMobNo;
	private String serverIpAddress = "";
	private boolean connected = false;

	public CheckAccontStatusThread(String userMobNo, String serverIpAddress) {
		this.serverIpAddress = serverIpAddress;
		this.userMobNo = userMobNo;
	}

	@Override
	public String call() throws Exception {
		InetAddress serverAddr = InetAddress.getByName(serverIpAddress);

		Log.d("ClientActivity", "C: Connecting...");

		Socket socket = new Socket(serverAddr, SERVERPORT);

		connected = true;
		try {

			SAFEMessage gwmsg = new SAFEMessage();
			String safemsg = "";
			clientMsg = "as";
			walletMsg = "(" + userMobNo + ";" + clientMsg + ")";
			safemsg = gwmsg.createGWMessage("0", walletMsg);

			if (connected) {

				try {

					Log.d("ClientActivity", "C: Sending command.");

					DataOutputStream out = new DataOutputStream(socket.getOutputStream());
					DataInputStream in = new DataInputStream(socket.getInputStream());
					// WHERE YOU ISSUE THE COMMANDS

					out.write(safemsg.getBytes());
					out.flush();

					String serverGWMessage = readStringData(in);
					processGWMessage = gwmsg.processGWMessage(serverGWMessage);

					Log.d("ClientActivity", "C: Sent.");

				}
				catch (Exception e) {

					Log.e("ClientActivity", "S: Error", e);

				}

			}

			socket.close();
			connected = false;

			Log.d("ClientActivity", "C: Closed.");

		}
		catch (Exception e) {

			Log.e("ClientActivity", "C: Error", e);
			processGWMessage = "SocketException";
		}
		return processGWMessage;
	}

	private String readStringData(DataInputStream in) {
		
		try {
			int c = 0;
			int i = 0;
			sb = new StringBuffer();
			while ((c = in.read()) != -1) {
				sb.append((char) c);
				if (c == '\n')
					break;
				System.out.print(i + "\t" + c);
				i++;
			}
			receivedData = sb.toString();
		}
		catch (Exception e1) {
		}
		return receivedData;

	}
}
