package picrate.app.DB.DAO;

import java.util.ArrayList;

import picrate.app.DB.Objects.ChallengeSession;

/**
 * Created by Michele Grisafi on 10/05/2017.
 */

public interface ChallengeSessionDAO {
    void open();
    void close();
    int STATO_ATTIVO = 1;
    int STATO_RATING = 0;
    int STATO_SCADUTO = 2;


    ArrayList<ChallengeSession> getActiveSessions();
    ArrayList<ChallengeSession> getRatingSessions();
    ArrayList<ChallengeSession> getClosedSessions();
}
