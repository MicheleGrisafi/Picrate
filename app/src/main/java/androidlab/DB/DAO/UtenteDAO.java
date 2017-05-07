package androidlab.DB.DAO;

import androidlab.DB.Objects.Utente;

/**
 * Created by miki4 on 07/05/2017.
 */

public interface UtenteDAO {
    void open();
    void close();

    Utente insertUtente(Utente user);
    void deleteUtente(Utente user);
}
