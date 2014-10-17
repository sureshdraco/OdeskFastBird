package appinventor.ai_sameh.FastBird.receiver;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.WakefulBroadcastReceiver;

import appinventor.ai_sameh.FastBird.service.GcmIntentService;

public class GcmBroadcastReceiver extends WakefulBroadcastReceiver {
    @Override 
    public void onReceive(Context context, Intent intent) {
        // Explicitly specify that GcmIntentService will handle the intent. 
        ComponentName comp = new ComponentName(context.getPackageName(),
                GcmIntentService.class.getName());
        // Start the service, keeping the device awake while it is launching. 
        startWakefulService(context, (intent.setComponent(comp)));
        setResultCode(Activity.RESULT_OK);
    } 
} 