package appinventor.ai_sameh.FastBird.util;

import android.text.TextUtils;

/**
 * Created by suresh.kumar on 2015-01-09.
 */
public class DecimalUtil {
	public static String formatDecimal(String decimalString) {
		if (TextUtils.isEmpty(decimalString)) {
			return "";
		}
		try {
			return String.format("%.3f", Float.parseFloat(decimalString));
		} catch (NumberFormatException ex) {
			return decimalString;
		}
	}
}
