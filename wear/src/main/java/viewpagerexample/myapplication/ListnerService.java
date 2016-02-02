package viewpagerexample.myapplication;

import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;

import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.WearableListenerService;

/**
 * Created by asaldanha on 1/25/2016.
 */
public class ListnerService extends WearableListenerService
{
    @Override
    public void onMessageReceived(MessageEvent messageEvent) {
        Log.v("wear", "onMessageReceived()");
        if(messageEvent.getPath().equals("/startactivity")) {
            final String message = new String(messageEvent.getData());
            NotificationCompat.Builder b = new NotificationCompat.Builder(this);
            b.setContentText(message);
            b.setSmallIcon(R.mipmap.ic_launcher);
            b.setContentTitle("Test Notification");
            b.setLocalOnly(true);
            NotificationManagerCompat man = NotificationManagerCompat.from(this);
            man.notify(0, b.build());
        } else {
            super.onMessageReceived(messageEvent);
        }
    }


}
