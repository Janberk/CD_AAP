package de.canberk.uni.cd_aap.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class DateUtil {

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

}