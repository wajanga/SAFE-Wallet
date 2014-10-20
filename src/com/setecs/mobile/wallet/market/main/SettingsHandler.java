package com.setecs.mobile.wallet.market.main;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;


public class SettingsHandler { // Class to handle application settings

	private SharedPreferences _prefs = null;
	private Editor _editor = null;
	private String _serveraddress = "10.0.2.2";

	// Class constructor
	public SettingsHandler(Context context) {
		_prefs = context.getSharedPreferences("PREFS_PRIVATE", Context.MODE_PRIVATE);
		_editor = _prefs.edit();
	}

	// Extract server address
	public String getServerAddress() {
		if (_prefs == null)
			return "10.0.2.2";
		_serveraddress = _prefs.getString("server_address", "10.0.2.2");
		return _serveraddress;
	}

	// Set server address
	public void setServerAddress(String new_server_address) {
		if (_editor == null)
			return;
		_editor.putString("server_address", new_server_address);
	}

	// Extract C2DM Registration ID
	public String getC2DMRegID() {
		if (_prefs == null)
			return "No C2DM ID";
		_serveraddress = _prefs.getString("c2dm_id", "No C2DM ID");
		return _serveraddress;
	}

	// Set C2DM Registration ID
	public void setC2DMRegID(String new_c2dm_id) {
		if (_editor == null)
			return;
		_editor.putString("c2dm_id", new_c2dm_id);
	}

	// Save settings
	public void save() {
		if (_editor == null)
			return;
		_editor.commit();
	}

}
