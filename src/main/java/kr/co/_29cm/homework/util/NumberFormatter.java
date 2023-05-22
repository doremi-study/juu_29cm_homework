package kr.co._29cm.homework.util;

import java.text.DecimalFormat;

public class NumberFormatter {
	public static String formatNumberWithCommas(int number) {
		DecimalFormat decimalFormat = new DecimalFormat("#,###");
		return decimalFormat.format(number);
	}

	public static String lpadNumber(int number) {
		return String.format("%06d", number);
	}
}
