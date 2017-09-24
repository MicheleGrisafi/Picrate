package androidlab.fotografando.assets.sessionList;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.SwipeRefreshLayout;
import android.widget.ListView;

import java.util.List;

import androidlab.DB.DAO.ChallengeSessionDAO;
import androidlab.DB.DAO.implementations.ChallengeSessionDAO_DB_impl;
import androidlab.DB.Objects.ChallengeSession;
import androidlab.fotografando.assets.AsyncResponse;

/**
 * Created by miki4 on 29/05/2017.
 */

public class LoadSessionsTask extends AsyncTask<Void,Void,List<ChallengeSession>> {
    private Context context;
    private ListView listView;
    private List<ChallengeSession> result;
    private ChallengeSessionDAO sessionDAO;
    private FragmentActivity activity;
    private int requestCode;
    private SwipeRefreshLayout swipeRefreshLayout;

    public AsyncResponse delegate = null;

    public LoadSessionsTask(Context context, ListView listView, int requestcode, FragmentActivity activity,SwipeRefreshLayout swipeRefreshLayout){
        this.context = context;
        this.listView = listView;
        this.requestCode = requestcode;
        this.activity = activity;
        this.swipeRefreshLayout = swipeRefreshLayout;
    }

    @Override
    protected List<ChallengeSession> doInBackground(Void... params) {
        sessionDAO = new ChallengeSessionDAO_DB_impl();
        sessionDAO.open();
        result = sessionDAO.getActiveSessions();
        return result;
    }

    @Override
    protected void onPostExecute(List<ChallengeSession> challengeSessions) {

        sessionDAO.close();
    }

}
