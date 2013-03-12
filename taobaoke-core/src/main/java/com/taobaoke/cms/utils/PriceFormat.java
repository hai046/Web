package com.taobaoke.cms.utils;

import java.text.DecimalFormat;

public class PriceFormat {
	public static String formatPrice(Object price) {
		if (price == null)
			return "";
		java.text.DecimalFormat df = new DecimalFormat();
		df.setMinimumFractionDigits(2);
		df.setMaximumFractionDigits(2);
		try {
			return df.format(price);
		} catch (Exception e) {
			return price.toString();
		}
	}
}
