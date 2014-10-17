
package appinventor.ai_sameh.FastBird.service;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import appinventor.ai_sameh.FastBird.PreferenceUtil;
import appinventor.ai_sameh.FastBird.R;
import appinventor.ai_sameh.FastBird.receiver.GcmBroadcastReceiver;
import appinventor.ai_sameh.FastBird.util.NotificationUtil;
import appinventor.ai_sameh.FastBird.view.MainActivity;
import appinventor.ai_sameh.FastBird.util.NotificationItem;

public class GcmIntentService extends IntentService {
    private static final String TAG = GcmIntentService.class.getSimpleName();

    public GcmIntentService() { 
        super("GcmIntentService"); 
    } 
 
    @Override 
    protected void onHandleIntent(Intent intent) {
        Log.d(TAG, "received gcm");
        Bundle extras = intent.getExtras();
        GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(this);
        // The getMessageType() intent parameter must be the intent you received 
        // in your BroadcastReceiver. 
        String messageType = gcm.getMessageType(intent);
 
        if (!extras.isEmpty()) {  // has effect of unparcelling Bundle
            /* 
             * Filter messages based on message type. Since it is likely that GCM 
             * will be extended in the future with new message types, just ignore 
             * any message types you're not interested in, or that you don't 
             * recognize. 
             */ 
            if (GoogleCloudMessaging.
                    MESSAGE_TYPE_SEND_ERROR.equals(messageType)) {
            } else if (GoogleCloudMessaging.
                    MESSAGE_TYPE_DELETED.equals(messageType)) {
            // If it's a regular GCM message, do some work.
            } else if (GoogleCloudMessaging.
                    MESSAGE_TYPE_MESSAGE.equals(messageType)) {
                String title = extras.getString("title", getApplicationContext().getResources().getString(R.string.app_name));
                String message = extras.getString("message", "");

                NotificationUtil.cacheNotification(this, title, message);

                Log.i(TAG, "Completed work @ " + SystemClock.elapsedRealtime());
                // Post notification of received message.
                NotificationUtil.sendNotification(getApplicationContext(), title, message);
                Log.i(TAG, "Received: " + extras.toString());
            } 
        } 
        // Release the wake lock provided by the WakefulBroadcastReceiver. 
        GcmBroadcastReceiver.completeWakefulIntent(intent);
    }
}
