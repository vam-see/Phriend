package vamsee.phriend;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Telephony;
import android.provider.Telephony.*;
import android.telephony.TelephonyManager;
import android.widget.Toast;

/**
 * Created by Vamsee on 3/12/2016.
 */
public class IncomingSms extends BroadcastReceiver {
    String state;
    final String inSMS = "android.provider.Telephony.SMS_RECEIVED";
    final String inCall = "android.intent.action.PHONE_STATE";
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(inSMS)){
            intent = new Intent(context, OverlayService.class);
            intent.putExtra("BR", "1");
            context.startService(intent);
        }
        else if (intent.getAction().equals(inCall)){
            state = intent.getStringExtra(TelephonyManager.EXTRA_STATE);

            if (state.equals(TelephonyManager.EXTRA_STATE_RINGING)){
                intent = new Intent(context, OverlayService.class);
                intent.putExtra("BR", "2");
                context.startService(intent);
            }

        }
    }
}
