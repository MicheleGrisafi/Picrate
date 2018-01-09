package picrate.app.assets.services;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.TaskStackBuilder;

import java.util.ArrayList;

import picrate.app.DB.Objects.ChallengeSession;
import picrate.app.R;
import picrate.app.activities.ActivityMain;
import picrate.app.assets.adapters.AdapterChallengeSession;
import picrate.app.assets.objects.AppInfo;
import picrate.app.assets.objects.MyApp;
import picrate.app.assets.views.ImageViewChallenge;

/**
 * Created by miki4 on 10/12/2017.
 */

public class ServiceChallengeExpiration extends Service {


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Context context = MyApp.getAppContext();
        AdapterChallengeSession adapter = (AdapterChallengeSession)AppInfo.adapters.get(AppInfo.ADAPTER_CHALLENGES);
        int sessionID = intent.getIntExtra("sessionID",0);
        ArrayList<ImageViewChallenge> images = adapter.getImageViews(sessionID);
        if(images.get(0).getPhoto() == null){
            Notification.Builder builder = new Notification.Builder(context);
            builder.setContentTitle(context.getString(R.string.notification_expiration_title));
            builder.setContentText(context.getString(R.string.notification_expiration_body));
            builder.setSmallIcon(R.drawable.ic_launcher);
            builder.setPriority(Notification.PRIORITY_HIGH);

            Intent intent2 = new Intent(context, ActivityMain.class);

            // The stack builder object will contain an artificial back stack for the
            // started Activity.
            // This ensures that navigating backward from the Activity leads out of
            // your app to the Home screen.
            TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
            // Adds the back stack for the Intent (but not the Intent itself)
            stackBuilder.addParentStack(ActivityMain.class);
            // Adds the Intent that starts the Activity to the top of the stack
            stackBuilder.addNextIntent(intent2);
            PendingIntent resultPendingIntent =
                    stackBuilder.getPendingIntent(
                            0,
                            PendingIntent.FLAG_UPDATE_CURRENT
                    );
            builder.setContentIntent(resultPendingIntent);
            NotificationManager mNotificationManager =
                    (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

            // mNotificationId is a unique integer your app uses to identify the
            // notification. For example, to cancel the notification, you can pass its ID
            // number to NotificationManager.cancel().
            mNotificationManager.notify(AppInfo.NOTIFY_CHALLENGE_EXPIRATION, builder.build());
        }
        return START_NOT_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

}
