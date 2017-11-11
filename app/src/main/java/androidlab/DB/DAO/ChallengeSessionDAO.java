package androidlab.DB.DAO;

import java.util.ArrayList;
import java.util.List;

import androidlab.DB.Objects.Challenge;
import androidlab.DB.Objects.ChallengeSession;
import androidlab.DB.Objects.Photo;

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
