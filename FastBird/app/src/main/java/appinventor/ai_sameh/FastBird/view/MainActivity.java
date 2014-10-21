package appinventor.ai_sameh.FastBird.view;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.apache.commons.collections4.queue.CircularFifoQueue;

import java.io.IOException;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

import appinventor.ai_sameh.FastBird.PreferenceUtil;
import appinventor.ai_sameh.FastBird.R;
import appinventor.ai_sameh.FastBird.api.ApiRequests;
import appinventor.ai_sameh.FastBird.api.RegisterDeviceRequest;
import appinventor.ai_sameh.FastBird.api.RegisterDeviceResponse;
import appinventor.ai_sameh.FastBird.util.NotificationItem;
import appinventor.ai_sameh.FastBird.util.NotificationUtil;

public class MainActivity extends FragmentActivity {

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
        b.putString("key", "Settings");
        mTabHost.addTab(mTabHost.newTabSpec("Settings").setIndicator("Settings"),
                SettingsFragment.class, b);
        //
        b = new Bundle();
        b.putString("key", "Notifications");
        mTabHost.addTab(mTabHost.newTabSpec("Notifications")
                .setIndicator("Notifications"), NotificationFragment.class, b);
        b = new Bundle();
        b.putString("key", "Custom");
        mTabHost.addTab(mTabHost.newTabSpec("custom").setIndicator("Custom"),
                Fragment3.class, b);
        // setContentView(mTabHost);
        updateBalance();
        findViewById(R.id.withdrawBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "not implemented", Toast.LENGTH_SHORT).show();
                NotificationUtil.cacheNotification(getApplicationContext(), "FastBird", "this is test content");
                NotificationUtil.sendNotification(MainActivity.this, "FastBird", "This is test content, delivered");
            }
        });
    }

    public void updateBalance() {
        String balanceStr = PreferenceUtil.getCredits(this);
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
            if(intent.getExtras().getBoolean("notifications", false)) {
                mTabHost.setCurrentTabByTag("Notifications");
            }
        }catch (Exception ignored) {

        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        handleIntent(intent);
    }

    /**
     * Check the device to make sure it has the Google Play Services APK. If
     * it doesn't, display a dialog that allows users to download the APK from
     * the Google Play Store or enable it in the device's system settings.
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
     * Stores the registration ID and app versionCode in the application's
     * shared preferences.
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
                        Toast.makeText(getApplicationContext(), tstMsg, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }).start();
    }

    private void sendRegistrationIdToBackend(String regid) {
        String email = PreferenceUtil.getEmail(getApplicationContext());
        String password = PreferenceUtil.getPassword(getApplicationContext());
        ApiRequests.registerDevice(getApplicationContext(), new RegisterDeviceRequest(regid, email, password), new Response.Listener<RegisterDeviceResponse>() {
            @Override
            public void onResponse(RegisterDeviceResponse registerDeviceResponse) {
                if (TextUtils.isEmpty(registerDeviceResponse.getData().getError())) {
                    Toast.makeText(getApplicationContext(), "Device Registered", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), registerDeviceResponse.getData().getError(), Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(getApplicationContext(), String.valueOf(volleyError.networkResponse.statusCode), Toast.LENGTH_SHORT).show();
            }
        });
    }

}