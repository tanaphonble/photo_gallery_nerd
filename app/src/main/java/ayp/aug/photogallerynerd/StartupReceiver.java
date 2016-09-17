package ayp.aug.photogallerynerd;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by Tanaphon on 9/17/2016.
 */
public class StartupReceiver extends BroadcastReceiver{
    private static final String TAG = "StartupReceiver";
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i(TAG, "Receive broadcast intent: " + intent.getAction());
        boolean isOn = QueryPreferences.isAlarmOn(context);
        PollService.setServiceAlarm(context, isOn);
    }
}
