package androidlab.DB.DAO;

import java.util.List;

import androidlab.DB.Objects.Challenge;
import androidlab.DB.Objects.ChallengeSession;

/**
 * Created by Michele Grisafi on 10/05/2017.
 */

public interface ChallengeSessionDAO {
    void open();
    void close();
    int STATO_ATTIVO = 1;
    int STATO_RATING = 0;
    int STATO_SCADUTO = -1;


    List<ChallengeSession> getActiveSessions();
    List<ChallengeSession> getRatingSessions();
}
