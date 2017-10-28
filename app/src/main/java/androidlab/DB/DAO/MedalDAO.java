package androidlab.DB.DAO;

import java.util.ArrayList;

import androidlab.DB.Objects.ChallengeSession;
import androidlab.DB.Objects.Medal;
import androidlab.DB.Objects.Utente;

/**
 * Created by miki4 on 15/10/2017.
 */

public interface MedalDAO {
    void open();
    void close();

    ArrayList<Medal> getUserMedals(Utente user);
    ArrayList<Medal> getSessionMedals(ChallengeSession session);
}
