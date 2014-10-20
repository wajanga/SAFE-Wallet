package com.setecs.mobile.wallet.market.coupons;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.setecs.mobile.wallet.market.utility.SafeReplyHandler;
import com.setecs.mobile.wallet.market.utility.SafeReplyParser;


public class CouponParser implements SafeReplyParser {

	private ArrayList<Coupon> couponList;
	private String response = "";

	private ArrayList<Coupon> parseJSON(String reply) throws JSONException {
		ArrayList<Coupon> couponList = new ArrayList<Coupon>();
		JSONArray entries = new JSONArray(reply);
		for (int i = 0; i < entries.length(); i++) {
			JSONObject object = entries.getJSONObject(i);
			Coupon coupon = new Coupon();
			coupon.setCouponId(object.getString("id"));
			coupon.setMerchantAccount(object.getString("merchant_account"));
			coupon.setMerchantName(object.getString("merchant_name"));
			coupon.setDescription(object.getString("description"));
			coupon.setStartDate(object.getString("start_date"));
			coupon.setEndDate(object.getString("end_date"));
			coupon.setImageString(object.getString("image_name"));

			String amount = object.getString("amount");
			String discount = object.getString("discount");

			coupon.setAmount(amount);
			coupon.setDiscount(discount);
			if (amount.equals("null")) {
				float f = Float.valueOf(discount) * 100;
				discount = String.valueOf(f) + "%";
				coupon.setValue(discount);
			}
			else {
				coupon.setValue("$" + amount);
			}
			couponList.add(coupon);
		}
		return couponList;
	}

	@Override
	public void parse(String response) {
		try {
			couponList = parseJSON(response);
		}
		catch (JSONException e) {
			this.response = response;
		}
	}

	@Override
	public void output(SafeReplyHandler replyHandler, String message) {
		if (message.equals("") && response.equals(""))
			replyHandler.displayList(couponList);
		else if (!message.equals(""))
			replyHandler.displayMessage(message);
		else
			replyHandler.displayMessage(response);
	}

}
