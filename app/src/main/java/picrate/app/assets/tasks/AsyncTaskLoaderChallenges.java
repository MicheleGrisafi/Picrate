package picrate.app.assets.tasks;

import android.support.v4.content.AsyncTaskLoader;
import android.content.Context;


import java.util.List;

import picrate.app.DB.DAO.ChallengeSessionDAO;
import picrate.app.DB.DAO.implementations.ChallengeSessionDAO_DB_impl;
import picrate.app.DB.Objects.ChallengeSession;

/**
 * Created by miki4 on 24/09/2017.
 */

public class AsyncTaskLoaderChallenges extends AsyncTaskLoader<List<ChallengeSession>> {

    public AsyncTaskLoaderChallenges(Context context) {
        super(context);
    }

    @Override
    public List<ChallengeSession> loadInBackground() {
        ChallengeSessionDAO sessionDAO = new ChallengeSessionDAO_DB_impl();
        sessionDAO.open();
        List<ChallengeSession> result = sessionDAO.getActiveSessions();
        sessionDAO.close();
        return result;
    }
}
