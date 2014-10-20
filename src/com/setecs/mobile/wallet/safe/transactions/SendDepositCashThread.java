package com.setecs.mobile.wallet.safe.transactions;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.concurrent.Callable;

import android.util.Log;

import com.setecs.mobile.safe.apps.shared.SAFEMessage;


public class SendDepositCashThread implements Callable<String> {

	private StringBuffer sb;
	private String receivedData;
	private String processGWMessage;
	private String serverIpAddress = "";
	private String clientMsg;
	private String walletMsg;
	public static final int SERVERPORT = 9661;
	private final String dpamountmsg;
	private final String agMobNomsg;
	private final String userMobNo;
	private boolean connected = false;

	public SendDepositCashThread(boolean connected, String dpamountmsg, String agMobNomsg, String serverIpAddress,
			String userMobNo) {
		this.serverIpAddress = serverIpAddress;
		this.dpamountmsg = dpamountmsg;
		this.agMobNomsg = agMobNomsg;
		this.userMobNo = userMobNo;
	}

	@Override
	public String call() throws Exception {

		try {

			InetAddress serverAddr = InetAddress.getByName(serverIpAddress);

			Log.d("ClientActivity", "C: Connecting...");

			Socket socket = new Socket(serverAddr, SERVERPORT);

			connected = true;
			SAFEMessage gwmsg = new SAFEMessage();
			String safemsg = "";
			clientMsg = "md " + dpamountmsg + " " + agMobNomsg;
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
