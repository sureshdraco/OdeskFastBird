package appinventor.ai_sameh.FastBird;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import appinventor.ai_sameh.FastBird.api.model.Address;
import appinventor.ai_sameh.FastBird.api.model.DataDescription;
import appinventor.ai_sameh.FastBird.api.model.DeliveryTime;
import appinventor.ai_sameh.FastBird.api.model.MRBTransactions;
import appinventor.ai_sameh.FastBird.api.model.Order;
import appinventor.ai_sameh.FastBird.api.request.CreateOrderRequest;
import appinventor.ai_sameh.FastBird.api.request.UpdateOrderRequest;
import appinventor.ai_sameh.FastBird.api.response.OrderTrackHistoryResponse;
import appinventor.ai_sameh.FastBird.api.response.UserInfoResponse;
import appinventor.ai_sameh.FastBird.model.OpenOrder;
import appinventor.ai_sameh.FastBird.util.TimestampUtil;
import appinventor.ai_sameh.FastBird.view.MainActivity;

/**
 * Created by suresh on 13/10/14.
 */
public class PreferenceUtil {

    private static final String LOGGED_IN = "loggedIn";
    private static final String EMAIL = "email";
    private static final String PASSWORD = "password";
    public static final String PREF_STORAGE_LOCATION = "FastbirdClientApp";
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
    private static final String CASH_ON_MY_WAY = "cashOnMyWay";
    private static final String CASH_HISTORY = "cashHistory";
    private static final String SELECTED_CASH_IN_WAY = "selectedCashInWay";
    private static final String SELECTED_CASH_IN_PROGRESS = "selectedCashInProgress";
    private static final String SELECTED_CASH_HISTORY = "selectedCashHistory";
    private static final String PREF_MY_PICK_ADDRESS = "myPickAddress";
    private static final String PREF_DELIVERY_TIME = "deliveryTime";
    private static final String PREF_DELIVERY_TYPE = "delivertType";
    private static final String PREF_LOCATIONS = "location";
    private static final String PREF_PACKAGE_TYPES = "packageTypes";
    private static final String PREF_SERVICE_TYPE = "serviceTypes";
    private static final String PREF_ORDER_IN_PROGRESS = "orderInProgress";
    private static final String PENDING_CREATE_ORDER_REQUEST = "pendingCreateOrderRequest";
    private static final String DISCOUNT_PRICE = "discountPrice";
    private static final String USER_INFO = "userInfo";
    private static final String MY_HISTORY_ORDERS = "myHistoryOrders";
    private static final String SELECTED_ORDER_TRACK_HISTORY = "selectedOrderTrackHistory";
    private static final String CLIENT_MONEY = "clientMoney";
    private static final String CLIENT_CREDITS = "clientCredits";
    private static final String PENDING_UPDATE_ORDER_REQUEST = "pendingUpdateOrderRequest";
    private static final String OPEN_ORDER_FROM_NOTIFICATION = "openOrderFromNotif";

    // Preference backend access.
    private static SharedPreferences.Editor prefEditor;

    public static void saveUserLoggedIn(Context context, boolean loggedIn) {
        saveMyPendingOrders(context, "[]");
        saveFastBirdPendingOrders(context, "[]");
        saveCashHistory(context, "[]");
        saveCashOnMyWayList(context, "[]");
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

    public static String getPassword(Context context) {
        return getPref(context).getString(PASSWORD, "");
    }

    public static void saveEmail(Context context, String email) {
        getPrefEditor(context).putString(EMAIL, email).commit();
    }

    public static void saveUserInfo(Context context, UserInfoResponse userInfoResponse) {
        getPrefEditor(context).putString(USER_INFO, new Gson().toJson(userInfoResponse)).commit();
    }

    public static void savePassword(Context context, String password) {
        getPrefEditor(context).putString(PASSWORD, password).commit();
    }

    /**
     * Gets the current registration ID for application on GCM service.
     * <p/>
     * If result is empty, the app needs to register.
     *
     * @return registration ID, or empty string if there is no existing registration ID.
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
     * Stores the registration ID and app versionCode in the application's {@code SharedPreferences}.
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

    public static ArrayList<Order> getFastBirdPendingOrders(Context context) {
        String orders = getPref(context).getString(FAST_BIRD_PENDING_ORDERS, "[]");
        Type listType = new TypeToken<ArrayList<Order>>() {
        }.getType();
        ArrayList<Order> shipmentOrders = new Gson().fromJson(orders, listType);
        Collections.sort(shipmentOrders, new Comparator<Order>() {
            @Override
            public int compare(Order lhs, Order rhs) {
                try {
                    return TimestampUtil.getIso8601Date(rhs.getProgressStatusDate()).compareTo(TimestampUtil.getIso8601Date(lhs.getProgressStatusDate()));
                } catch (ParseException e) {
                    return -1;
                }
            }
        });
        return shipmentOrders;
    }

    public static void saveFastBirdPendingOrders(Context context, String fastBirdPendingOrders) {
        getPrefEditor(context).putString(FAST_BIRD_PENDING_ORDERS, fastBirdPendingOrders).commit();
    }

    public static String getCashOnMyWayList(Context context) {
        return getPref(context).getString(CASH_ON_MY_WAY, "[]");
    }

    public static void saveCashOnMyWayList(Context context, String cashOnMyWay) {
        getPrefEditor(context).putString(CASH_ON_MY_WAY, cashOnMyWay).commit();
    }

    public static String getCashHistory(Context context) {
        return getPref(context).getString(CASH_HISTORY, "[]");
    }

    public static UserInfoResponse getUserInfo(Context context) {
        return new Gson().fromJson(getPref(context).getString(USER_INFO, ""), UserInfoResponse.class);
    }

    public static void saveCashHistory(Context context, String cashHistory) {
        getPrefEditor(context).putString(CASH_HISTORY, cashHistory).commit();
    }

    public static ArrayList<Order> getMyPendingOrders(Context context) {
        String orders = getPref(context).getString(MY_PENDING_ORDERS, "[]");
        Type listType = new TypeToken<ArrayList<Order>>() {
        }.getType();
        ArrayList<Order> myPendingOrder = new Gson().fromJson(orders, listType);
        Collections.sort(myPendingOrder, new Comparator<Order>() {
            @Override
            public int compare(Order lhs, Order rhs) {
                try {
                    return TimestampUtil.getIso8601Date(rhs.getOrderDate()).compareTo(TimestampUtil.getIso8601Date(lhs.getOrderDate()));
                } catch (ParseException e) {
                    return -1;
                }
            }
        });
        return myPendingOrder;
    }

    public static void saveMyPendingOrders(Context context, String myPendingOrders) {
        getPrefEditor(context).putString(MY_PENDING_ORDERS, myPendingOrders).commit();
    }

    public static ArrayList<Order> getMyHistoryOrders(Context context) {

        String orders = getPref(context).getString(MY_HISTORY_ORDERS, "[]");
        Type listType = new TypeToken<ArrayList<Order>>() {
        }.getType();
        ArrayList<Order> myPendingOrder = new Gson().fromJson(orders, listType);
        Collections.sort(myPendingOrder, new Comparator<Order>() {
            @Override
            public int compare(Order lhs, Order rhs) {
                try {
                    return TimestampUtil.getIso8601Date(rhs.getProgressStatusDate()).compareTo(TimestampUtil.getIso8601Date(lhs.getProgressStatusDate()));
                } catch (ParseException e) {
                    return -1;
                }
            }
        });
        return myPendingOrder;
    }

    public static void saveMyHistoryOrders(Context context, String myHistoryOrders) {
        getPrefEditor(context).putString(MY_HISTORY_ORDERS, myHistoryOrders).commit();
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

    public static Order getSelectedOrder(Context context) {
        String selectedOrder = getPref(context).getString(SELECTED_ORDER, "");
        return new Gson().fromJson(selectedOrder, Order.class);
    }

    public static void saveSelectedOrder(Context context, Order order) {
        getPrefEditor(context).putString(SELECTED_ORDER, new Gson().toJson(order)).commit();
    }

    public static String getSelectedCashInWay(Context context) {
        return getPref(context).getString(SELECTED_CASH_IN_WAY, "");
    }

    public static void saveSelectedCashInWay(Context context, MRBTransactions transactions) {
        getPrefEditor(context).putString(SELECTED_CASH_IN_WAY, new Gson().toJson(transactions)).commit();
    }

    public static Order getSelectedCashInProgress(Context context) {
        String orderString = getPref(context).getString(SELECTED_CASH_IN_PROGRESS, "");
        return new Gson().fromJson(orderString, Order.class);
    }

    public static void saveSelectedCashInProgress(Context context, Order order) {
        getPrefEditor(context).putString(SELECTED_CASH_IN_PROGRESS, new Gson().toJson(order)).commit();
    }

    public static String getSelectedCashHistory(Context context) {
        return getPref(context).getString(SELECTED_CASH_HISTORY, "");
    }

    public static void saveSelectedCashHistory(Context context, MRBTransactions transactions) {
        getPrefEditor(context).putString(SELECTED_CASH_HISTORY, new Gson().toJson(transactions)).commit();
    }

    public static ArrayList<Address> getMyPickupAddress(Context context) {
        String json = getPref(context).getString(PREF_MY_PICK_ADDRESS, "[]");
        Gson gson = new Gson();
        Type listType = new TypeToken<ArrayList<Address>>() {
        }.getType();
        return gson.fromJson(json, listType);
    }

    public static void savePickupAddresses(Context context, ArrayList<Address> addresses) {
        getPrefEditor(context).putString(PREF_MY_PICK_ADDRESS, new Gson().toJson(addresses)).commit();
    }

    public static ArrayList<DeliveryTime> getDeliveryTime(Context context) {
        String json = getPref(context).getString(PREF_DELIVERY_TIME, "[]");
        Gson gson = new Gson();
        Type listType = new TypeToken<ArrayList<DeliveryTime>>() {
        }.getType();
        return gson.fromJson(json, listType);
    }

    public static void saveDeliveryTime(Context context, ArrayList<DeliveryTime> deliveryTimes) {
        getPrefEditor(context).putString(PREF_DELIVERY_TIME, new Gson().toJson(deliveryTimes)).commit();
    }

    public static ArrayList<DeliveryTime> getDeliveryType(Context context) {
        String json = getPref(context).getString(PREF_DELIVERY_TYPE, "[]");
        Gson gson = new Gson();
        Type listType = new TypeToken<ArrayList<DeliveryTime>>() {
        }.getType();
        return gson.fromJson(json, listType);
    }

    public static void saveDeliveryType(Context context, ArrayList<DeliveryTime> deliveryTypes) {
        getPrefEditor(context).putString(PREF_DELIVERY_TYPE, new Gson().toJson(deliveryTypes)).commit();
    }

    public static ArrayList<DataDescription> getLocations(Context context) {
        String json = getPref(context).getString(PREF_LOCATIONS, "[]");
        Gson gson = new Gson();
        Type listType = new TypeToken<ArrayList<DataDescription>>() {
        }.getType();
        return gson.fromJson(json, listType);
    }

    public static void saveLocations(Context context, ArrayList<DataDescription> locations) {
        getPrefEditor(context).putString(PREF_LOCATIONS, new Gson().toJson(locations)).commit();
    }

    public static ArrayList<DataDescription> getPackageTypes(Context context) {
        String json = getPref(context).getString(PREF_PACKAGE_TYPES, "[]");
        Gson gson = new Gson();
        Type listType = new TypeToken<ArrayList<DataDescription>>() {
        }.getType();
        return gson.fromJson(json, listType);
    }

    public static void savePackageTypes(Context context, ArrayList<DataDescription> locations) {
        getPrefEditor(context).putString(PREF_PACKAGE_TYPES, new Gson().toJson(locations)).commit();
    }

    public static ArrayList<DataDescription> getServiceTypes(Context context) {
        String json = getPref(context).getString(PREF_SERVICE_TYPE, "[]");
        Gson gson = new Gson();
        Type listType = new TypeToken<ArrayList<DataDescription>>() {
        }.getType();
        return gson.fromJson(json, listType);
    }

    public static void saveServiceTypes(Context context, ArrayList<DataDescription> serviceTypes) {
        getPrefEditor(context).putString(PREF_SERVICE_TYPE, new Gson().toJson(serviceTypes)).commit();
    }

    public static ArrayList<Order> getProgressOrders(Context context) {
        String json = getPref(context).getString(PREF_ORDER_IN_PROGRESS, "[]");
        Gson gson = new Gson();
        Type listType = new TypeToken<ArrayList<Order>>() {
        }.getType();
        return gson.fromJson(json, listType);
    }

    public static void saveProgressOrders(Context context, ArrayList<Order> serviceTypes) {
        getPrefEditor(context).putString(PREF_ORDER_IN_PROGRESS, new Gson().toJson(serviceTypes)).commit();
    }

    public static CreateOrderRequest getPendingCreateOrderRequest(Context context) {
        String createOrderRequest = getPref(context).getString(PENDING_CREATE_ORDER_REQUEST, "");
        return new Gson().fromJson(createOrderRequest, CreateOrderRequest.class);
    }

    public static void savePendingCreateOrderRequest(Context context, CreateOrderRequest createOrderRequest) {
        getPrefEditor(context).putString(PENDING_CREATE_ORDER_REQUEST, new Gson().toJson(createOrderRequest)).commit();
    }

    public static UpdateOrderRequest getPendingUpdateOrderRequest(Context context) {
        String createOrderRequest = getPref(context).getString(PENDING_UPDATE_ORDER_REQUEST, "");
        return new Gson().fromJson(createOrderRequest, UpdateOrderRequest.class);
    }

    public static void savePendingUpdateOrderRequest(Context context, UpdateOrderRequest updateOrderRequest) {
        getPrefEditor(context).putString(PENDING_UPDATE_ORDER_REQUEST, new Gson().toJson(updateOrderRequest)).commit();
    }

    public static void saveSelectedOrderTrackHistory(Context context, OrderTrackHistoryResponse orderTrackHistoryResponse) {
        getPrefEditor(context).putString(SELECTED_ORDER_TRACK_HISTORY, new Gson().toJson(orderTrackHistoryResponse)).commit();
    }

    public static OrderTrackHistoryResponse getSelectedOrderTrackHistory(Context context) {
        String createOrderRequest = getPref(context).getString(SELECTED_ORDER_TRACK_HISTORY, "");
        return new Gson().fromJson(createOrderRequest, OrderTrackHistoryResponse.class);
    }

    public static String getClientMoney(Context context) {
        return getPref(context).getString(CLIENT_MONEY, "-");
    }

    public static void saveClientMoney(Context context, String clientMoney) {
        getPrefEditor(context).putString(CLIENT_MONEY, clientMoney).commit();
    }

    public static String getClientCredits(Context context) {
        return getPref(context).getString(CLIENT_CREDITS, "-");
    }

    public static void saveClientCredits(Context context, String clientCredits) {
        getPrefEditor(context).putString(CLIENT_CREDITS, clientCredits).commit();
    }

    public static void saveOpenOrder(Context context, OpenOrder openOrder) {
        getPrefEditor(context).putString(OPEN_ORDER_FROM_NOTIFICATION, new Gson().toJson(openOrder)).commit();
    }

    public static OpenOrder getOpenOrder(Context context) {
        String createOrderRequest = getPref(context).getString(OPEN_ORDER_FROM_NOTIFICATION, "");
        return new Gson().fromJson(createOrderRequest, OpenOrder.class);
    }
}
