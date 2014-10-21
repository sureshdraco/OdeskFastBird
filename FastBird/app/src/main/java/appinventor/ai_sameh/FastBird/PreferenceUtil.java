package appinventor.ai_sameh.FastBird;

import android.app.Activity;
import android.content.Context;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.google.gson.Gson;

import appinventor.ai_sameh.FastBird.api.Order;
import appinventor.ai_sameh.FastBird.view.MainActivity;

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
    private static final String TAG = PreferenceUtil.class.getSimpleName();
    public static final String EXTRA_MESSAGE = "message";
    public static final String PROPERTY_REG_ID = "registration_id";
    private static final String PROPERTY_APP_VERSION = "appVersion";
    private static final String NOTIFICATION_LIST = "notificationList";
    public static final String NOTIFICATIONS_UPDATED_BROADCAST = "notificationsUpdated";
    private static final String NOTIFICATION_COUNT = "notificationCount";
    private static final String FAST_BIRD_PENDING_ORDERS = "fastBirdPendingOrders";
    private static final String MY_PENDING_ORDERS = "myPendingOrders";
    private static final String SELECTED_ORDER = "selectedOrder";

    // Preference backend access.
    private static SharedPreferences.Editor prefEditor;

    public static void saveUserLoggedIn(Context context, boolean loggedIn) {
        saveMyPendingOrders(context, "");
        saveFastBirdPendingOrders(context, "");
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


    /**
     * Gets the current registration ID for application on GCM service.
     * <p/>
     * If result is empty, the app needs to register.
     *
     * @return registration ID, or empty string if there is no existing
     * registration ID.
     */
    public static String getRegistrationId(Context context) {
        final SharedPreferences prefs = getGCMPreferences(context);
        String registrationId = prefs.getString(PROPERTY_REG_ID, "");
        if (registrationId.isEmpty()) {
            Log.i(TAG, "Registration not found.");
            return "";
        }
        // Check if app was updated; if so, it must clear the registration ID
        // since the existing regID is not guaranteed to work with the new
        // app version.
        int registeredVersion = prefs.getInt(PROPERTY_APP_VERSION, Integer.MIN_VALUE);
        int currentVersion = getAppVersion(context);
        if (registeredVersion != currentVersion) {
            Log.i(TAG, "App version changed.");
            return "";
        }
        return registrationId;
    }

    /**
     * @return Application's {@code SharedPreferences}.
     */
    private static SharedPreferences getGCMPreferences(Context context) {
        // This sample app persists the registration ID in shared preferences, but
        // how you store the regID in your app is up to you.
        return context.getSharedPreferences(MainActivity.class.getSimpleName(),
                Context.MODE_PRIVATE);
    }


    /**
     * Stores the registration ID and app versionCode in the application's
     * {@code SharedPreferences}.
     *
     * @param context application's context.
     * @param regId   registration ID
     */
    public static void storeRegistrationId(Context context, String regId) {
        final SharedPreferences prefs = getGCMPreferences(context);
        int appVersion = getAppVersion(context);
        Log.i(TAG, "Saving regId on app version " + appVersion);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(PROPERTY_REG_ID, regId);
        editor.putInt(PROPERTY_APP_VERSION, appVersion);
        editor.commit();
    }

    /**
     * @return Application's version code from the {@code PackageManager}.
     */
    private static int getAppVersion(Context context) {
        try {
            PackageInfo packageInfo = context.getPackageManager()
                    .getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            // should never happen
            throw new RuntimeException("Could not get package name: " + e);
        }
    }

    public static String getNotificationList(Context context) {
        return getPref(context).getString(getNotificationName(context), "[]");
    }

    public static void saveNotificationsList(Context context, String notificationsLsit) {
        getPrefEditor(context).putString(getNotificationName(context), notificationsLsit).commit();
    }

    public static String getFastBirdPendingOrders(Context context) {
        return getPref(context).getString(FAST_BIRD_PENDING_ORDERS, "[]");
    }

    public static void saveFastBirdPendingOrders(Context context, String fastBirdPendingOrders) {
        getPrefEditor(context).putString(FAST_BIRD_PENDING_ORDERS, fastBirdPendingOrders).commit();
    }

    public static String getMyPendingOrders(Context context) {
        return getPref(context).getString(MY_PENDING_ORDERS, "[]");
    }

    public static void saveMyPendingOrders(Context context, String myPendingOrders) {
        getPrefEditor(context).putString(MY_PENDING_ORDERS, myPendingOrders).commit();
    }

    private static String getNotificationName(Context context) {
        return getPref(context).getString(EMAIL, "") + NOTIFICATION_LIST;
    }

    public static int getIncrementedNotificationCount(Context context) {
        int count = getPref(context).getInt(NOTIFICATION_COUNT, 0);
        getPrefEditor(context).putInt(NOTIFICATION_COUNT, ++count).commit();
        return count;
    }

    public static void resetNotificationCount(Context context) {
        getPrefEditor(context).putInt(NOTIFICATION_COUNT, 0).commit();
    }

    public static String getSelectedOrder(Context context) {
        return getPref(context).getString(SELECTED_ORDER, "");
    }

    public static void saveSelectedOrder(Context context, Order order) {
        getPrefEditor(context).putString(SELECTED_ORDER, new Gson().toJson(order)).commit();
    }
}
