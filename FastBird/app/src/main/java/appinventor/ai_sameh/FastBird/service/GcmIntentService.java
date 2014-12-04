package appinventor.ai_sameh.FastBird.service;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.os.SystemClock;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.util.Log;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import appinventor.ai_sameh.FastBird.PreferenceUtil;
import appinventor.ai_sameh.FastBird.R;
import appinventor.ai_sameh.FastBird.model.OpenOrder;
import appinventor.ai_sameh.FastBird.receiver.GcmBroadcastReceiver;
import appinventor.ai_sameh.FastBird.util.NotificationUtil;
import appinventor.ai_sameh.FastBird.view.MainActivity;
import appinventor.ai_sameh.FastBird.util.NotificationItem;

public class GcmIntentService extends IntentService {
	private static final String TAG = GcmIntentService.class.getSimpleName();
	private static final java.lang.String NOTIFICATION_EXTRA_TITLE = "nottitle";
	private static final java.lang.String NOTIFICATION_EXTRA_ICON = "noticon";
	private static final java.lang.String NOTIFICATION_EXTRA_MSG = "notmsg";
	private static final java.lang.String NOTIFICATION_EXTRA_PAGE = "page";
	private static final java.lang.String NOTIFICATION_EXTRA_ORDER = "order";
	private static final java.lang.String NOTIFICATION_EXTRA_ORDER_TAB = "ordertab";

	public GcmIntentService() {
		super("GcmIntentService");
	}

	@Override
	protected void onHandleIntent(final Intent intent) {
		Log.d(TAG, "received gcm");
		final Bundle extras = intent.getExtras();
		GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(this);
		// The getMessageType() intent parameter must be the intent you received
		// in your BroadcastReceiver.
		String messageType = gcm.getMessageType(intent);

		if (!extras.isEmpty()) { // has effect of unparcelling Bundle
			/*
			 * Filter messages based on message type. Since it is likely that GCM will be extended in the future with new message types, just ignore any message types you're not
			 * interested in, or that you don't recognize.
			 */
			if (GoogleCloudMessaging.MESSAGE_TYPE_SEND_ERROR.equals(messageType)) {
				GcmBroadcastReceiver.completeWakefulIntent(intent);
			} else if (GoogleCloudMessaging.MESSAGE_TYPE_DELETED.equals(messageType)) {
				GcmBroadcastReceiver.completeWakefulIntent(intent);
				// If it's a regular GCM message, do some work.
			} else if (GoogleCloudMessaging.MESSAGE_TYPE_MESSAGE.equals(messageType)) {
				final String title = extras.getString(NOTIFICATION_EXTRA_TITLE, getApplicationContext().getResources().getString(R.string.app_name));
				final String message = extras.getString(NOTIFICATION_EXTRA_MSG, "");
				final String iconUrl = extras.getString(NOTIFICATION_EXTRA_ICON, "");

				final String order = extras.getString(NOTIFICATION_EXTRA_ORDER, "");
				if (!TextUtils.isEmpty(order)) {
					final String page = extras.getString(NOTIFICATION_EXTRA_PAGE, "");
					final String orderTab = extras.getString(NOTIFICATION_EXTRA_ORDER_TAB, "");
					PreferenceUtil.saveOpenOrder(getApplicationContext(), new OpenOrder(order, page, orderTab));
				}
				new Thread(new Runnable() {
					@Override
					public void run() {
						Bitmap bitmap = getBitmapFromURL(iconUrl);
                       // String fileName = savebitmap(String.valueOf(bitmap.hashCode()), bitmap);
                        NotificationUtil.cacheNotification(getApplicationContext(), title, message, iconUrl);
						Log.i(TAG, "Completed work @ " + SystemClock.elapsedRealtime());
						// Post notification of received message.
						NotificationUtil.sendNotification(getApplicationContext(), title, message, bitmap);
						Log.i(TAG, "Received: " + extras.toString());
						GcmBroadcastReceiver.completeWakefulIntent(intent);
					}
				}).start();

			}
		}
		// Release the wake lock provided by the WakefulBroadcastReceiver.
	}

    private static String savebitmap(String filename, Bitmap bmp) {
        String file_path = Environment.getExternalStorageDirectory().getAbsolutePath() +
                "/Fastbird";
        File dir = new File(file_path);
        if (!dir.exists())
            dir.mkdirs();
        File file = new File(dir, filename + ".png");
        FileOutputStream fOut = null;
        try {
            fOut = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.PNG, 85, fOut);
            fOut.flush();
            fOut.close();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
        return file.getAbsolutePath();
    }

	public Bitmap getBitmapFromURL(String strURL) {
		try {
			URL url = new URL(strURL);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setDoInput(true);
			connection.connect();
			InputStream input = connection.getInputStream();
			Bitmap myBitmap = BitmapFactory.decodeStream(input);
			return myBitmap;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
}
