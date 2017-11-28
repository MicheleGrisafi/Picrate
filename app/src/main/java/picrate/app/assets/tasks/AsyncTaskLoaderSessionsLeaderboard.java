package androidlab.app.assets.tasks;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import java.util.ArrayList;

import androidlab.app.DB.DAO.ChallengeSessionDAO;
import androidlab.app.DB.DAO.implementations.ChallengeSessionDAO_DB_impl;
import androidlab.app.DB.Objects.ChallengeSession;

/**
 * Created by miki4 on 11/11/2017.
 */

public class AsyncTaskLoaderSessionsLeaderboard extends AsyncTaskLoader<ArrayList<ChallengeSession>> {
    public AsyncTaskLoaderSessionsLeaderboard(Context context) {
        super(context);
    }

    @Override
    public ArrayList<ChallengeSession> loadInBackground() {
        ChallengeSessionDAO dao = new ChallengeSessionDAO_DB_impl();
        dao.open();
        ArrayList<ChallengeSession> list = dao.getClosedSessions();
        dao.close();
        return list;
    }
}
