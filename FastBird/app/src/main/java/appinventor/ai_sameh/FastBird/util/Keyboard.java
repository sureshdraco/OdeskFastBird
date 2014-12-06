package appinventor.ai_sameh.FastBird.util;

import android.app.Activity;
import android.content.Context;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

/**
 * Created by suresh.kumar on 2013-11-26.
 */

public class Keyboard {

	public static void hideKeyboard(Activity activity, EditText editText) {
		if (activity == null || editText == null) {
			return;
		}
		activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		InputMethodManager imm = (InputMethodManager) activity.getSystemService(
				Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
	}

	public static void clearKeyboardAndErrors(Activity activity) {
		if (activity == null) {
			return;
		}
		InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
		try {
			imm.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
			((EditText) activity.getCurrentFocus()).setError(null);
		} catch (Exception ignored) {
		}
	}
}
