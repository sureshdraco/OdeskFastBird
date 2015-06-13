package appinventor.ai_sameh.FastBird.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.telephony.SmsMessage;
import android.util.Log;

import appinventor.ai_sameh.FastBird.view.RegisterActivity;

public class SmsReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        Log.i("cs.fsu", "smsReceiver: SMS Received");

        Bundle bundle = intent.getExtras();
        if (bundle != null) {

            Object[] pdus = (Object[]) bundle.get("pdus");
            SmsMessage sms = SmsMessage.createFromPdu((byte[]) pdus[0]);

            if (sms.getMessageBody().contains("code")) {
                Intent myIntent = new Intent(context, RegisterActivity.class);
                myIntent.putExtra("mySMS", bundle);
                myIntent.setAction(RegisterActivity.SMS_CODE_RECEIVER);
                LocalBroadcastManager.getInstance(context).sendBroadcast(myIntent);
            }
        }
    }
}
