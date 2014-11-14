package appinventor.ai_sameh.FastBird.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by fouad.yaseen on 8/21/2014. Code taken and modified from: http://stackoverflow.com/questions/2201925/converting-iso-8601-compliant-string-to-java-util-date
 */
public class TimestampUtil {

	public static final String ISO_8601_FORMAT = "yyyy-MM-dd'T'HH:mm:ss";
	public static final String FAST_BIRD_DATE_FORMAT = "dd/MM/yyyy HH:mm:ss";
	private static final int DAY_IN_MILLIS = 1000 * 60 * 60 * 24;

	/**
	 * Helper class for handling ISO 8601 strings of the following format: "2008-03-01T13:00:00+01:00". It also supports parsing the "Z" timezone.
	 */
	public static class ISO8601 {
		/** Transform ISO 8601 string to Date. */
		public static Date toDate(final String iso8601string)
				throws ParseException {
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(ISO_8601_FORMAT);
			simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
			return simpleDateFormat.parse(iso8601string);
		}
	}

	public static String getFastBirdDateString(String date) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(FAST_BIRD_DATE_FORMAT);
		try {
			return simpleDateFormat.format(ISO8601.toDate(date));
		} catch (ParseException e) {
			return "";
		}
	}

	public static Date getFastBirdDate(String date) throws ParseException {
		return ISO8601.toDate(date);
	}

	public static int getDaysBetween(Date date1, Date date2) {
		return (int) ((date1.getTime() - date2.getTime()) / DAY_IN_MILLIS);
	}
}
