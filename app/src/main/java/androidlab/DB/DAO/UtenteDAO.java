package androidlab.DB.DAO;

import java.util.ArrayList;
import java.util.List;

import androidlab.DB.Objects.Utente;

/**
 * Created by miki4 on 07/05/2017.
 */

public interface UtenteDAO {
    void open();
    void close();

    Utente insertUtente(Utente user);
    void deleteUtente(Utente user);
    Utente getUtente(String googleKey);
    Utente getUtente(int id);
    ArrayList<Utente> getTopUsers();
    Utente updateUtente(Utente user);


}
