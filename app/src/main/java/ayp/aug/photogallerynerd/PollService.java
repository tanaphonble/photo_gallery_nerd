package ayp.aug.photogallerynerd;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by Tanaphon on 9/16/2016.
 */
public class PollService extends IntentService {
    private static final String TAG = "PollService";

    public static Intent newIntent(Context context){
        return new Intent(context, PollService.class);
    }

    public PollService() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.i(TAG, "Receive an intent: " + intent);
    }
}
