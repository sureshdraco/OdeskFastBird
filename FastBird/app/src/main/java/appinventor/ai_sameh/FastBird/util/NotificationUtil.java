package appinventor.ai_sameh.FastBird.util;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.apache.commons.collections4.queue.CircularFifoQueue;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

import appinventor.ai_sameh.FastBird.PreferenceUtil;
import appinventor.ai_sameh.FastBird.R;
import appinventor.ai_sameh.FastBird.view.MainActivity;

/**
 * Created by suresh on 19/10/14.
 */
public class NotificationUtil {
    private static final int NOTIFICATION_ID = 1;
    private static Gson gson = new Gson();

    public static void cacheNotification(Context context, String title, String message) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();
        NotificationItem notificationItem = new NotificationItem(title, simpleDateFormat.format(date), message);
        Type listType = new TypeToken<ArrayList<NotificationItem>>() {
        }.getType();
        ArrayList<NotificationItem> notificationItemArrayList = gson.fromJson(PreferenceUtil.getNotificationList(context), listType);
        CircularFifoQueue<NotificationItem> queue = new CircularFifoQueue<NotificationItem>(10);
        Collections.reverse(notificationItemArrayList);
        notificationItemArrayList.add(notificationItem);
        queue.addAll(notificationItemArrayList);
        notificationItemArrayList = new ArrayList<NotificationItem>(queue);
        Collections.reverse(notificationItemArrayList);
        PreferenceUtil.saveNotificationsList(context, gson.toJson(notificationItemArrayList));
        LocalBroadcastManager.getInstance(context).sendBroadcast(new Intent(PreferenceUtil.NOTIFICATIONS_UPDATED_BROADCAST));
    }

    // Put the message into a notification and post it.
    // This is just one simple example of what you might choose to do with
    // a GCM message.
    public static void sendNotification(Context context, String title, String msg) {
        NotificationManager mNotificationManager = (NotificationManager)
                context.getSystemService(Context.NOTIFICATION_SERVICE);

        Intent mainActivityIntent = new Intent(context, MainActivity.class);
        mainActivityIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        mainActivityIntent.putExtra("notifications", true);
        PendingIntent contentIntent = PendingIntent.getActivity(context, 0,
                mainActivityIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(context)
                        .setContentIntent(contentIntent)
                        .setSmallIcon(R.drawable.app_icon)
                        .setContentTitle(title)
                        .setStyle(new NotificationCompat.BigTextStyle()
                                .bigText(msg))
                        .setAutoCancel(true)
                        .setContentText(msg);
        int notifCount = PreferenceUtil.getIncrementedNotificationCount(context);
        if(notifCount > 1) {
            mBuilder.setNumber(notifCount);
        }
        mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());
    }
}