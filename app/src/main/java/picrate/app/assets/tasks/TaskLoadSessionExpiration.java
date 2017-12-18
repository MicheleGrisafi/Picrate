package picrate.app.assets.tasks;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.CountDownTimer;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.concurrent.TimeUnit;

import picrate.app.DB.Objects.ChallengeSession;
import picrate.app.R;
import picrate.app.assets.objects.AppInfo;
import picrate.app.assets.services.ServiceChallengeExpiration;

/**
 * Created by miki4 on 29/05/2017.
 */

/** Caricamento asincorno delle scadenze per le varie sfide **/
public class TaskLoadSessionExpiration extends AsyncTask<Void,Void,Date> {
    private Context context;
    private ChallengeSession session;
    private TextView expiration;

    public TaskLoadSessionExpiration(Context context, ChallengeSession session, TextView expiration){
        this.context = context;
        this.session = session;
        this.expiration = expiration;
    }
    @Override
    protected Date doInBackground(Void... params) {
        return AppInfo.getDate();
    }
    @Override
    protected void onPostExecute(Date data) {
        //Calcolo differenza tra scadenza e data attuale
        long diffHours = getDateDiff(data,session.getExpiration(), TimeUnit.MILLISECONDS);
        Calendar cal = Calendar.getInstance();

        long window = 1000 * 60 *70;
        int window_length = 1000 * 60 * 20;
        if(diffHours > window && (boolean)AppInfo.getSetting(AppInfo.NOTIFY_CHALLENGE_EXPIRATION)){
            //TODO: non creare sempre un nuovo oggetto notifica
            long schedule = cal.getTimeInMillis() + diffHours - window;
            Intent intent = new Intent(context, ServiceChallengeExpiration.class);
            intent.putExtra("sessionID",session.getIDSession());
            PendingIntent pintent = PendingIntent.getService(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);


            alarmManager.setWindow(AlarmManager.RTC_WAKEUP,schedule,window_length,pintent);
        }


        new CountDownTimer(diffHours, 60000) {
            public void onTick(long millisUntilFinished) {
                long minutes,hours,days;
                String exp = "";
                days = millisUntilFinished/1000/60/60/24;
                hours = millisUntilFinished/1000/60/60 - days * 24;
                minutes = millisUntilFinished/60000 - hours * 60;

                if(days >= 1){
                    if(hours >= 1)
                        exp = Long.toString(days) + context.getString(R.string.day_abbre) + " " + Long.toString(hours) + context.getString(R.string.hour_abbre);
                    else
                        exp = Long.toString(days) + context.getString(R.string.day_abbre);
                }else{
                    if(hours >= 1)
                        exp = Long.toString(hours) + context.getString(R.string.hour_abbre) + " " + Long.toString(minutes) + context.getString(R.string.minutes_abbre);
                    else
                        exp = Long.toString(minutes) + context.getString(R.string.minutes_abbre);
                }
                expiration.setText(context.getString(R.string.expiresIn) + " " + exp);
            }

            public void onFinish() {
                expiration.setText(context.getString(R.string.challenge_expired));
            }
        }.start();

    }

    private static long getDateDiff(Date date1, Date date2, TimeUnit timeUnit) {
        long diffInMillies = date2.getTime() - date1.getTime();
        return timeUnit.convert(diffInMillies,TimeUnit.MILLISECONDS);
    }
}

