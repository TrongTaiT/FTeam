package com.fteam.utilities;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class FormatUtil {

	public static String formatToVietnamCurrency(Object number) {
		Locale localeVN = new Locale("vi", "VN");
	    NumberFormat vn = NumberFormat.getInstance(localeVN);
	    return vn.format(number);
	}
	
	public static String toStringNumber(Object number) {
		return String.format("%.0f", number);
	}

	public static String dateToString(Date date) {
		SimpleDateFormat fmt = new SimpleDateFormat("dd/MM/yyyy");
		return fmt.format(date);
	}
	
}
