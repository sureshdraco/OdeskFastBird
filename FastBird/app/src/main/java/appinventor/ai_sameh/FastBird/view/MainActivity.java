package appinventor.ai_sameh.FastBird.view;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import appinventor.ai_sameh.FastBird.PreferenceUtil;
import appinventor.ai_sameh.FastBird.R;
import appinventor.ai_sameh.FastBird.adapter.CashArrayAdapter;
import appinventor.ai_sameh.FastBird.adapter.CashDetailListArrayAdapter;
import appinventor.ai_sameh.FastBird.adapter.CashInProgressArrayAdapter;
import appinventor.ai_sameh.FastBird.adapter.OrderArrayAdapter;
import appinventor.ai_sameh.FastBird.api.ApiRequests;
import appinventor.ai_sameh.FastBird.api.model.MRBTransactions;
import appinventor.ai_sameh.FastBird.api.model.MoneyDetail;
import appinventor.ai_sameh.FastBird.api.model.Order;
import appinventor.ai_sameh.FastBird.api.request.RegisterDeviceRequest;
import appinventor.ai_sameh.FastBird.api.response.RegisterDeviceResponse;
import appinventor.ai_sameh.FastBird.api.response.UserInfoResponse;
import appinventor.ai_sameh.FastBird.util.Constant;
import appinventor.ai_sameh.FastBird.util.DecimalUtil;
import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;

public class MainActivity extends FragmentActivity {
    private static final String NOTIFICATION_TAB = "Notifications";
    private static final String ORDERS = "orders";
    String SENDER_ID = "773072195235";
    private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;

    private static final String TAG = MainActivity.class.getSimpleName();
    private FragmentTabHost mTabHost;

    GoogleCloudMessaging gcm;
    AtomicInteger msgId = new AtomicInteger();
    SharedPreferences prefs;
    Context context;

    String regid;
    private TextView balance;
    private int NOTIFICATION_ID = 1;
    private Gson gson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gson = new Gson();
        context = getApplicationContext();
        if (checkPlayServices()) {
            // If this check succeeds, proceed with normal processing.
            // Otherwise, prompt user to get valid Play Services APK.
            gcm = GoogleCloudMessaging.getInstance(this);
            regid = PreferenceUtil.getRegistrationId(context);

            if (regid.isEmpty()) {
                registerInBackground();
            }
        }
        setContentView(R.layout.activity_main);
        balance = (TextView) findViewById(R.id.balance);
        mTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
        mTabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);
        Bundle b = new Bundle();
        b.putString("key", "Orders");
        mTabHost.addTab(mTabHost.newTabSpec(ORDERS).setIndicator("", getResources().getDrawable(R.drawable.orders_icon_selector)),
                OrdersTabWithFastBirdOrdersFragment.class, b);
        //
        b = new Bundle();
        b.putString("key", "Notifications");
        mTabHost.addTab(mTabHost.newTabSpec("Notifications")
                .setIndicator("", getResources().getDrawable(R.drawable.notif_icon_selector)), NotificationFragment.class, b);
        b = new Bundle();
        b.putString("key", "+");
        mTabHost.addTab(mTabHost.newTabSpec("+")
                .setIndicator("", getResources().getDrawable(R.drawable.add_order_icon_selector)), CreateOrderDummyFragment.class, b);
        b = new Bundle();
        b.putString("key", "$$");
        mTabHost.addTab(mTabHost.newTabSpec("$$").setIndicator("", getResources().getDrawable(R.drawable.money_icon_selector)),
                MoneyTabFragment.class, b);

        b = new Bundle();
        b.putString("key", "Profile");
        mTabHost.addTab(mTabHost.newTabSpec("Profile").setIndicator("", getResources().getDrawable(R.drawable.settings_icon_selector)),
                SettingsFragment.class, b);
        mTabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                if (tabId.equals(NOTIFICATION_TAB) || tabId.equals(ORDERS)) {
                    findViewById(R.id.clearNotif).setVisibility(View.VISIBLE);
                } else {
                    findViewById(R.id.clearNotif).setVisibility(View.GONE);
                }
            }
        });

        TabWidget widget = mTabHost.getTabWidget();
        for (int i = 0; i < widget.getChildCount(); i++) {
            View v = widget.getChildAt(i);
            widget.getChildAt(i).getLayoutParams().height = 180;
            if (i == 2) {
                v.setBackgroundResource(R.drawable.apptheme_tab_unselected_pressed_holo);
            } else {
                v.setBackgroundResource(R.drawable.apptheme_tab_indicator_holo);
            }
        }
        // setContentView(mTabHost);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        List<Fragment> fragmentList = getSupportFragmentManager().getFragments();
        for (Fragment fragment : fragmentList) {
            if (fragment != null) {
                fragment.onActivityResult(requestCode, resultCode, data);
            }
        }
    }

    public void setCurrentTab(int index) {
        mTabHost.setCurrentTab(index);
    }

    public void updateBalance() {
        ApiRequests.getUserInformation(this, PreferenceUtil.getEmail(this), PreferenceUtil.getPassword(this), new Response.Listener<UserInfoResponse>() {
            @Override
            public void onResponse(UserInfoResponse userInfoResponse) {
                cacheResponse(userInfoResponse);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
            }
        });
        updateBalanceField();
    }

    private void cacheResponse(UserInfoResponse userInfoResponse) {
        if (userInfoResponse == null) {
            Crouton.makeText(this, "Unable to get user information.", Style.ALERT);
            return;
        }
        PreferenceUtil.saveEmail(this, userInfoResponse.getData().getEmail());
        PreferenceUtil.saveUserInfo(this, userInfoResponse);
        updateBalanceField();
    }

    public void updateBalanceField() {
        String balanceStr = "";
        try {
            balanceStr = PreferenceUtil.getUserInfo(this).getData().getCredits();
        } catch (Exception e) {

        }
        balance.setText(getString(R.string.balance, balanceStr));
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkPlayServices();
        updateBalance();
        handleIntent(getIntent());
    }

    private void handleIntent(Intent intent) {
        try {
            if (intent.getExtras().getBoolean("notifications", false)) {
                mTabHost.setCurrentTabByTag("Notifications");
            }
        } catch (Exception ignored) {
        }
    }

    public void handleNotification() {
        if (PreferenceUtil.getOpenOrder(this) != null) {
            // goto order
            mTabHost.setCurrentTabByTag("orders");
        }
    }

    public void gotoPickupOrders() {
        mTabHost.setCurrentTabByTag("orders");
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        handleIntent(intent);
    }

    /**
     * Check the device to make sure it has the Google Play Services APK. If it doesn't, display a dialog that allows users to download the APK from the Google Play Store or enable
     * it in the device's system settings.
     */
    private boolean checkPlayServices() {
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                GooglePlayServicesUtil.getErrorDialog(resultCode, this,
                        PLAY_SERVICES_RESOLUTION_REQUEST).show();
            } else {
                Log.i(TAG, "This device is not supported.");
                finish();
            }
            return false;
        }
        return true;
    }

    /**
     * Registers the application with GCM servers asynchronously.
     * <p/>
     * Stores the registration ID and app versionCode in the application's shared preferences.
     */
    private void registerInBackground() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String msg = "";
                try {
                    if (gcm == null) {
                        gcm = GoogleCloudMessaging.getInstance(context);
                    }
                    regid = gcm.register(SENDER_ID);
                    msg = "Device registered, registration ID=" + regid;

                    // You should send the registration ID to your server over HTTP,
                    // so it can use GCM/HTTP or CCS to send messages to your app.
                    // The request to your server should be authenticated if your app
                    // is using accounts.
                    sendRegistrationIdToBackend(regid);

                    // For this demo: we don't need to send it because the device
                    // will send upstream messages to a server that echo back the
                    // message using the 'from' address in the message.

                    // Persist the regID - no need to register again.
                    PreferenceUtil.storeRegistrationId(context, regid);
                } catch (IOException ex) {
                    msg = "Error :" + ex.getMessage();
                    // If there is an error, don't just keep trying to register.
                    // Require the user to click a button again, or perform
                    // exponential back-off.
                }
                final String tstMsg = msg;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (Constant.DEBUG) {
                            Toast.makeText(getApplicationContext(), tstMsg, Toast.LENGTH_SHORT).show();
                            Log.d("deviceId", tstMsg);
                        }
                    }
                });
            }
        }).start();
    }

    private void sendRegistrationIdToBackend(String regid) {
        String email = PreferenceUtil.getEmail(getApplicationContext());
        String password = PreferenceUtil.getPassword(getApplicationContext());
        ApiRequests.registerDevice(getApplicationContext(), new RegisterDeviceRequest(email, password, regid), new Response.Listener<RegisterDeviceResponse>() {
            @Override
            public void onResponse(RegisterDeviceResponse registerDeviceResponse) {
                if (TextUtils.isEmpty(registerDeviceResponse.getData().getError())) {
                    if (Constant.DEBUG)
                        Toast.makeText(getApplicationContext(), "Device Registered", Toast.LENGTH_SHORT).show();
                } else {
                    if (Constant.DEBUG)
                        Toast.makeText(getApplicationContext(), registerDeviceResponse.getData().getError(), Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                if (Constant.DEBUG)
                    Toast.makeText(getApplicationContext(), String.valueOf(volleyError.networkResponse.statusCode), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        Dialog dialog = null;

        switch (id) {
            case OrderArrayAdapter.ORDER_INFO_DIALOG:
                dialog = OrderInfoDialog.showOrderDetail(this);
                break;
            case OrderArrayAdapter.ORDER_TRACK_STATUS_DIALOG:
                dialog = OrderTrackStatusDialog.showOrderTrackStatusDetail(this);
                break;
            case CashArrayAdapter.DIALOG_CASH_IN_WAY:
            case CashArrayAdapter.DIALOG_CASH_HISTORY:
                dialog = CashDialog.showCashDialog(this);
                break;
            case CashInProgressArrayAdapter.DIALOG_CASH_IN_PROGRESS:
                dialog = CashDialog.showCashInProgressDialog(this);
                break;
            case ActivityProgressIndicator.ACTIVITY_PROGRESS_LOADER:
                dialog = new ActivityProgressIndicator(this, R.style.TransparentDialog);
                break;
        }
        return dialog;
    }

    @Override
    protected void onPrepareDialog(int id, Dialog dialog) {
        super.onPrepareDialog(id, dialog);

        switch (id) {
            case OrderArrayAdapter.ORDER_INFO_DIALOG:
                Order selectedOrder = PreferenceUtil.getSelectedOrder(this);
                if (selectedOrder != null) {
                    OrderInfoDialog.setupOrderDetailUi(getApplicationContext(), dialog.getWindow().getDecorView(), selectedOrder);
                } else {
                    dialog.dismiss();
                }
                break;
            case OrderArrayAdapter.ORDER_TRACK_STATUS_DIALOG:
                OrderTrackStatusDialog.setupOrderTrackStatusDetailUi(this, dialog, PreferenceUtil.getSelectedOrderTrackHistory(context));
                break;
            case CashArrayAdapter.DIALOG_CASH_IN_WAY:
                String selectedCashInWay = PreferenceUtil.getSelectedCashInWay(this);
                if (!TextUtils.isEmpty(selectedCashInWay)) {
                    MRBTransactions transactions = new Gson().fromJson(PreferenceUtil.getSelectedCashInWay(this), MRBTransactions.class);
                    ((TextView) dialog.findViewById(R.id.id)).setText(getResources().getString(R.string.id, transactions.getId()));
                    ((TextView) dialog.findViewById(R.id.date)).setText(getResources().getString(R.string.date, transactions.getDate()));
                    ((TextView) dialog.findViewById(R.id.totalAmounts)).setText(getResources().getString(R.string.total_amount,
                            DecimalUtil.formatDecimal(transactions.getTotalAmounts())));

                    if (transactions.getDetails() != null && transactions.getDetails().size() > 0) {
                        ListView cashList = (ListView) dialog.findViewById(R.id.moneyDetailList);
                        CashDetailListArrayAdapter cashDetailListArrayAdapter = new CashDetailListArrayAdapter(context, R.layout.cash_card_detail_item);
                        for (MoneyDetail moneyDetail : transactions.getDetails()) {
                            cashDetailListArrayAdapter.add(moneyDetail);
                        }
                        cashList.setAdapter(cashDetailListArrayAdapter);
                    }
                } else {
                    dialog.dismiss();
                }

                break;
            case CashArrayAdapter.DIALOG_CASH_HISTORY:
                String selectedCashHistory = PreferenceUtil.getSelectedCashHistory(this);
                if (!TextUtils.isEmpty(selectedCashHistory)) {
                    MRBTransactions transactions = new Gson().fromJson(PreferenceUtil.getSelectedCashHistory(this), MRBTransactions.class);
                    ((TextView) dialog.findViewById(R.id.id)).setText(getResources().getString(R.string.id, transactions.getId()));
                    ((TextView) dialog.findViewById(R.id.date)).setText(getResources().getString(R.string.date, transactions.getDate()));
                    ((TextView) dialog.findViewById(R.id.totalAmounts)).setText(getResources().getString(R.string.total_amount,
                            DecimalUtil.formatDecimal(transactions.getTotalAmounts())));

                    if (transactions.getDetails() != null && transactions.getDetails().size() > 0) {
                        ListView cashList = (ListView) dialog.findViewById(R.id.moneyDetailList);
                        CashDetailListArrayAdapter cashDetailListArrayAdapter = new CashDetailListArrayAdapter(context, R.layout.cash_card_detail_item);
                        for (MoneyDetail moneyDetail : transactions.getDetails()) {
                            cashDetailListArrayAdapter.add(moneyDetail);
                        }
                        cashList.setAdapter(cashDetailListArrayAdapter);
                    }
                } else {
                    dialog.dismiss();
                }

                break;
            case CashInProgressArrayAdapter.DIALOG_CASH_IN_PROGRESS:
                Order selectedOrder1 = PreferenceUtil.getSelectedCashInProgress(this);
                if (selectedOrder1 != null) {
                    OrderInfoDialog.setupOrderDetailUi(context, dialog.getWindow().getDecorView(), selectedOrder1);
                } else {
                    dialog.dismiss();
                }

                break;
        }

    }

    public void onClickSearchBtn(View view) {
        startActivity(new Intent(this, SearchActivity.class));
    }
}