package com.setecs.mobile.wallet.msettings;

import static com.setecs.mobile.safe.apps.shared.Constants.SAFEIPNO;
import static com.setecs.mobile.wallet.safe.wallet.Constants.CONFIGFILENAME;
import android.app.Activity;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.widget.TextView;

import com.setecs.mobile.safe.apps.shared.SharedMethods;
import com.setecs.mobile.walletDev.R;


public class SystemInfoActivity extends Activity {

	private TextView version, ipAddress;
	private final SharedMethods cv = new SharedMethods();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.sys_info);
		version = (TextView) findViewById(R.id.version_number);
		PackageInfo pinfo = null;
		String ver = null;
		try {
			pinfo = this.getPackageManager().getPackageInfo(getPackageName(), 0);
			ver = pinfo.versionName;
		}
		catch (NameNotFoundException e) {
			ver = "?";
		}
		version.setText("App Version no: " + ver);

		ipAddress = (TextView) findViewById(R.id.ip_address);
		ipAddress.setText("IP Address: " + cv.readConfigurationFile(this, SAFEIPNO, CONFIGFILENAME));
	}

}
