package de.canberk.uni.cd_aap.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class UtilMethods {
	
	public static String dateToFormattedStringConverter(Date date) {
		String timestampAsString = null;

		try {
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",
					Locale.GERMAN);
			dateFormat.setTimeZone(TimeZone.getTimeZone("CET"));
			timestampAsString = dateFormat.format(date).toString();
		} catch (NullPointerException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
		return timestampAsString;
	}

	public static Date setCreationDateFromString(String timestamp) {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",
				Locale.GERMAN);
		dateFormat.setTimeZone(TimeZone.getTimeZone("CET"));
		Date newDate = new Date();

		try {
			newDate = dateFormat.parse(timestamp);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return newDate;
	}

	public static String md5(String password) {

		try {
			MessageDigest digest = java.security.MessageDigest
					.getInstance("MD5");
			digest.update(password.getBytes());
			byte messageDigest[] = digest.digest();

			StringBuffer hexString = new StringBuffer();
			for (int i = 0; i < messageDigest.length; i++)
				hexString.append(Integer.toHexString(0xFF & messageDigest[i]));
			return hexString.toString();
		} catch (NoSuchAlgorithmException e) {
			return password;
		}
	}

	public static boolean isTrue(int valueToCheck) {
		if (valueToCheck == 1) {
			return true;
		}
		return false;
	}

	public static int isTrueAsInt(boolean isTrue) {
		if (isTrue) {
			return 1;
		}
		return 0;
	}

}