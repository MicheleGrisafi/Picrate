package picrate.app.assets.tasks;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.job.JobParameters;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.NotificationCompat;

import java.util.ArrayList;
import java.util.List;

import picrate.app.DB.DAO.ChallengeSessionDAO;
import picrate.app.DB.DAO.implementations.ChallengeSessionDAO_DB_impl;
import picrate.app.DB.Objects.ChallengeSession;
import picrate.app.R;
import picrate.app.activities.ActivityMain;
import picrate.app.assets.adapters.AdapterChallengeSession;
import picrate.app.assets.objects.AppInfo;
import picrate.app.assets.objects.MyApp;
import picrate.app.assets.services.ServiceUpdateChallenges;

/**
 * Created by miki4 on 29/05/2017.
 */

public class TaskUpdateSessionsList extends AsyncTask<Void,Void,List<ChallengeSession>> {

    private ServiceUpdateChallenges service;
    private ChallengeSessionDAO sessionDAO;
    private List<ChallengeSession> result;
    private JobParameters jobParameters;
    private Context context;

    public TaskUpdateSessionsList(ServiceUpdateChallenges service, JobParameters jobParameters) {
        this.service = service;
        this.jobParameters = jobParameters;
        this.context = MyApp.getAppContext();
    }

    @Override
    protected List<ChallengeSession> doInBackground(Void... params) {
        result = sessionDAO.getActiveSessions();
        return result;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        sessionDAO = new ChallengeSessionDAO_DB_impl();
        sessionDAO.open();
    }

    @Override
    protected void onPostExecute(List<ChallengeSession> challengeSessions) {
        sessionDAO.close();
        sessionDAO = null;
        List<ChallengeSession> oldList = AppInfo.getChallengeList();
        if(!compareLists(oldList,challengeSessions)){
            NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
            builder.setContentTitle(context.getString(R.string.notification_new_challenge_title));
            builder.setContentInfo(context.getString(R.string.notification_new_challenge_body));
            builder.setPriority(Notification.PRIORITY_HIGH);
            builder.setSmallIcon(R.drawable.ic_new_challenge_orange_24dp);


            Intent intent = new Intent(context, ActivityMain.class);

            // The stack builder object will contain an artificial back stack for the
            // started Activity.
            // This ensures that navigating backward from the Activity leads out of
            // your app to the Home screen.
            TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
            // Adds the back stack for the Intent (but not the Intent itself)
            stackBuilder.addParentStack(ActivityMain.class);
            // Adds the Intent that starts the Activity to the top of the stack
            stackBuilder.addNextIntent(intent);
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
            intent.putExtra("notification",AppInfo.NOTIFY_NEW_CHALLENGE);
            mNotificationManager.notify(AppInfo.NOTIFY_NEW_CHALLENGE, builder.build());
            service.jobFinished(jobParameters,true);
        }
    }
    private boolean compareLists(List<ChallengeSession> listOld, List<ChallengeSession> listNew){
        boolean result = false;
        for (ChallengeSession session1 : listOld){
            result = false;
            for (ChallengeSession session2 : listNew) {
                if (session1.getIDSession() == session2.getIDSession())
                    result = true;
            }
            if(!result)
                break;
        }
        return result;
    }
}
