package androidlab.fotografando.assets;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.View;
import android.widget.TextView;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import androidlab.DB.Objects.ChallengeSession;
import androidlab.fotografando.R;

/**
 * Created by miki4 on 15/05/2017.
 */

public class LoadSessionsExpiration extends AsyncTask<Void,Void,Date> {
    Context context;
    List<ChallengeSession> listaSession;
    Map<Integer,Integer> expirationMap;
    View rootView;
    public LoadSessionsExpiration(Context context, Map<Integer,Integer> expirationMap, List<ChallengeSession> listaSession, View rootView){
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
