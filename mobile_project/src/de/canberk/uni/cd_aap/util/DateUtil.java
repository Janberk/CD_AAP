package de.canberk.uni.cd_aap.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class DateUtil {

	public static String dateToFormattedStringConverter(Date creationDate) {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",
				Locale.GERMAN);
		dateFormat.setTimeZone(TimeZone.getTimeZone("CET"));
		String timestampAsString = dateFormat.format(creationDate).toString();

		return timestampAsString;
	}

}