package picrate.app.DB.DAO;

import java.util.ArrayList;

import picrate.app.DB.Objects.ChallengeSession;
import picrate.app.DB.Objects.Medal;
import picrate.app.DB.Objects.Utente;

/**
 * Created by miki4 on 15/10/2017.
 */

public interface MedalDAO {
    void open();
    void close();

    ArrayList<Medal> getUserMedals(Utente user);
    ArrayList<Medal> getSessionMedals(ChallengeSession session);
}
