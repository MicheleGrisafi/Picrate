package androidlab.fotografando.assets.tasks;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.TextView;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import androidlab.DB.Objects.ChallengeSession;
import androidlab.fotografando.R;
import androidlab.fotografando.assets.objects.AppInfo;

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
        //TODO modificare get date
        return AppInfo.getDate();
    }
    @Override
    protected void onPostExecute(Date data) {
        //Calcolo differenza tra scadenza e data attuale
        long diffHours;
        int tmp;
        diffHours = getDateDiff(session.getExpiration(),data, TimeUnit.HOURS);
        if (diffHours<0)
            diffHours *= -1;
        if(diffHours >= 24) {
            tmp = (int) diffHours / 24;
            diffHours = (int)diffHours%24;
            expiration.setText(R.string.expiresIn);
            expiration.setText(expiration.getText() + Integer.toString(tmp) + "d " + Long.toString(diffHours) + "h");
        }else {
            expiration.setText(R.string.expiresIn);
            expiration.setText(expiration.getText() + Long.toString(diffHours) + "h");
        }
    }

    private static long getDateDiff(Date date1, Date date2, TimeUnit timeUnit) {
        long diffInMillies = date2.getTime() - date1.getTime();
        return timeUnit.convert(diffInMillies,TimeUnit.MILLISECONDS);
    }
}

