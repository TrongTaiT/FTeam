package com.fteam.utilities;

import java.text.NumberFormat;
import java.util.Locale;

public class NumberFormatUtil {

	public static String formatToVietnamCurrency(Object number) {
		Locale localeVN = new Locale("vi", "VN");
	    NumberFormat vn = NumberFormat.getInstance(localeVN);
	    return vn.format(number);
	}
	
}
