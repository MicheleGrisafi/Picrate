package androidlab.fotografando.assets.sessionList;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.ListView;

import java.util.List;

import androidlab.DB.DAO.ChallengeSessionDAO;
import androidlab.DB.DAO.implementations.ChallengeSessionDAO_DB_impl;
import androidlab.DB.Objects.ChallengeSession;

/**
 * Created by miki4 on 13/08/2017.
 */

public class UpdateSessionsTask extends AsyncTask<Void,Void,List<ChallengeSession>> {


    private ChallengeSessionAdapter adapter;


    public UpdateSessionsTask( ChallengeSessionAdapter adapter){
        this.adapter = adapter;
    }

    @Override
    protected List<ChallengeSession> doInBackground(Void... params) {
        ChallengeSessionDAO sessionDAO = new ChallengeSessionDAO_DB_impl();
        sessionDAO.open();
        List<ChallengeSession> result = sessionDAO.getActiveSessions();
        return result;
    }

    @Override
    protected void onPostExecute(List<ChallengeSession> challengeSessions) {
        adapter.taskResponse(challengeSessions);
    }
}
