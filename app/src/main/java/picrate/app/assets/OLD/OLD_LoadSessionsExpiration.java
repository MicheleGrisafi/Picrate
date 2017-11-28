package androidlab.app.assets.OLD;

import android.content.Context;
import android.os.AsyncTask;
import android.util.SparseIntArray;
import android.view.View;
import android.widget.TextView;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import androidlab.app.DB.Objects.ChallengeSession;
import androidlab.app.assets.objects.AppInfo;

/**
 * Created by miki4 on 15/05/2017.
 */

public class OLD_LoadSessionsExpiration extends AsyncTask<Void,Void,Date> {
    Context context;
    List<ChallengeSession> listaSession;
    SparseIntArray expirationMap;
    View rootView;
    public OLD_LoadSessionsExpiration(Context context, SparseIntArray expirationMap, List<ChallengeSession> listaSession, View rootView){
        this.context = context;
        this.expirationMap = expirationMap;
        this.listaSession = listaSession;
        this.rootView = rootView;
    }
    @Override
    protected Date doInBackground(Void... params) {
        Date data = AppInfo.getDate();
        return data;
    }
    @Override
    protected void onPostExecute(Date data) {
        TextView text;
        long diffHours;
        int tmp;
        for (int i = 0; i < listaSession.size(); i++) {
            diffHours = getDateDiff(listaSession.get(i).getExpiration(),data,TimeUnit.HOURS);
            text = (TextView)rootView.findViewById(expirationMap.get(listaSession.get(i).getIDSession()));
            if (diffHours<0)
                diffHours *= -1;
            if(diffHours >= 24) {
                tmp = (int) diffHours / 24;
                diffHours = (int)diffHours%24;
                text.setText(text.getText() + Integer.toString(tmp) + "d " + Long.toString(diffHours) + "h");
            }else
                text.setText(text.getText() + Long.toString(diffHours) + "h");
        }
    }
    private static long getDateDiff(Date date1, Date date2, TimeUnit timeUnit) {
        long diffInMillies = date2.getTime() - date1.getTime();
        return timeUnit.convert(diffInMillies,TimeUnit.MILLISECONDS);
    }
}
