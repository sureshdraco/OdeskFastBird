package appinventor.ai_sameh.FastBird;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.app.FragmentActivity;

/**
 * Created by suresh on 13/10/14.
 */
public class PreferenceUtil {

    private static final String LOGGED_IN = "loggedIn";
    private static final String EMAIL = "email";
    private static final String PASSWORD = "password";
    public static final String PREF_STORAGE_LOCATION = "FastbirdClientApp";
    private static final String FIRST_NAME = "firstName";
    private static final String LAST_NAME = "lastName";
    private static final String COUNTRY = "country";
    private static final String PHONE = "phone";
    private static final String BANK_NAME = "bankName";
    private static final String CREDITS = "credits";

    // Preference backend access.
    private static SharedPreferences.Editor prefEditor;

    public static void saveUserLoggedIn(Context context, boolean loggedIn) {
        getPrefEditor(context).putBoolean(LOGGED_IN, loggedIn).commit();
    }

    /**
     * Gets the shared preferences to use
     */
    public static SharedPreferences getPref(Context context) {
        return context.getSharedPreferences(PREF_STORAGE_LOCATION, 0);
    }

    private static SharedPreferences.Editor getPrefEditor(Context context) {
        return getPref(context).edit();
    }

    public static boolean getUserLoggedIn(Context context) {
        return getPref(context).getBoolean(LOGGED_IN, false);
    }

    public static String getEmail(Context context) {
        return getPref(context).getString(EMAIL, "");
    }
    public static String getFirstName(Context context) {
        return getPref(context).getString(FIRST_NAME, "");
    }
    public static String getLastName(Context context) {
        return getPref(context).getString(LAST_NAME, "");
    }
    public static String getCountry(Context context) {
        return getPref(context).getString(COUNTRY, "");
    }

    public static String getPhone(Context context) {
        return getPref(context).getString(PHONE, "");
    }

    public static String getPassword(Context context) {
        return getPref(context).getString(PASSWORD, "");
    }

    public static void saveEmail(Context context, String email) {
        getPrefEditor(context).putString(EMAIL, email).commit();
    }

    public static void savePhone(Context context, String email) {
        getPrefEditor(context).putString(PHONE, email).commit();
    }

    public static void saveLastName(Context context, String email) {
        getPrefEditor(context).putString(LAST_NAME, email).commit();
    }

    public static void saveFirstName(Context context, String email) {
        getPrefEditor(context).putString(FIRST_NAME, email).commit();
    }

    public static void saveCountry(Context context, String email) {
        getPrefEditor(context).putString(COUNTRY, email).commit();
    }


    public static void saveCredits(Context context, String credits) {
        getPrefEditor(context).putString(CREDITS, credits).commit();
    }


    public static void saveBankName(Context context, String bankName) {
        getPrefEditor(context).putString(BANK_NAME, bankName).commit();
    }

    public static void savePassword(Context context, String password) {
        getPrefEditor(context).putString(PASSWORD, password).commit();
    }

    public static String getBankName(Context context) {
        return getPref(context).getString(BANK_NAME, "");
    }

    public static String getCredits(Context context) {
        return getPref(context).getString(CREDITS, "");
    }
}
