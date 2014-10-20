package com.setecs.mobile.wallet.safe.wallet;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.setecs.mobile.safe.apps.shared.SharedMethods;
import com.setecs.mobile.walletDev.R;
import com.setecs.mobile.wallet.msettings.WalletConfiguration;
import com.setecs.mobile.wallet.safe.wallet.ui.CirclePageIndicator;


public class IntroScreen extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.initial_layout);

		MyPagerAdapter adapter = new MyPagerAdapter();
		ViewPager myPager = (ViewPager) findViewById(R.id.panel_pager);
		myPager.setAdapter(adapter);
		myPager.setCurrentItem(0);

		//Bind the circle indicator to the adapter
		CirclePageIndicator circleIndicator = (CirclePageIndicator) findViewById(R.id.indicator);
		circleIndicator.setViewPager(myPager);
	}

	public void createAccount(View v) {
		setInitFlag(true);
		Intent i = new Intent().setClass(this, WalletConfiguration.class);
		Bundle bundle = new Bundle();
		bundle.putBoolean("regstatus", true);
		i.putExtras(bundle);
		startActivity(i);
		finish();
	}

	public static void setInitFlag(boolean initFlag) {
		SharedMethods.initFlag = initFlag;
	}

	private class MyPagerAdapter extends PagerAdapter {
		@Override
		public int getCount() {
			return 4;
		}

		@Override
		public Object instantiateItem(View collection, int position) {
			LayoutInflater inflater = (LayoutInflater) collection.getContext().getSystemService(
					Context.LAYOUT_INFLATER_SERVICE);
			View view = inflater.inflate(R.layout.initial_layout_contents, null);
			ImageView introImage = (ImageView) view.findViewById(R.id.intro_image);
			TextView introText1 = (TextView) view.findViewById(R.id.intro_text1);
			TextView introText2 = (TextView) view.findViewById(R.id.intro_text2);

			switch (position) {
			case 0:
				introImage.setImageResource(R.drawable.intro1);
				break;
			case 1:
				introImage.setImageResource(R.drawable.intro2);
				introText1.setText("Send Money.");
				introText2
						.setText("Use your phone to send money to family, or use your phone to make payments at your local merchant");
				break;
			case 2:
				introImage.setImageResource(R.drawable.intro3);
				introText1.setText("Instant Promotions.");
				introText2.setText("Use your phone to receive the latest promotions in your area free!");
				break;
			case 3:
				introImage.setImageResource(R.drawable.intro4);
				introText1.setText("Buy airtime and gift cards.");
				introText2.setText("Use your phone to purchase prepaid airtime and giftcards!");
				break;
			}

			((ViewPager) collection).addView(view, 0);
			return view;
		}

		@Override
		public void destroyItem(View arg0, int arg1, Object arg2) {
			((ViewPager) arg0).removeView((View) arg2);
		}

		@Override
		public void finishUpdate(View arg0) {
			// TODO Auto-generated method stub
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == ((View) arg1);
		}

		@Override
		public void restoreState(Parcelable arg0, ClassLoader arg1) {
			// TODO Auto-generated method stub
		}

		@Override
		public Parcelable saveState() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public void startUpdate(View arg0) {
			// TODO Auto-generated method stub
		}

	}

}
