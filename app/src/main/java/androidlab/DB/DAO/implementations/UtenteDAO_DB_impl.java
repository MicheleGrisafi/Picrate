package androidlab.DB.DAO.implementations;

import androidlab.DB.DAO.UtenteDAO;
import androidlab.DB.MySqlDatabase;
import androidlab.DB.Objects.Utente;

/**
 * Created by miki4 on 07/05/2017.
 */

public class UtenteDAO_DB_impl implements UtenteDAO {
    MySqlDatabase database;

    @Override
    public void open() {

    }
    @Override
    public void close() {

    }

    @Override
    public Utente insertUtente(Utente user) {
        return null;
    }

    @Override
    public void deleteUtente(Utente user) {

    }
}
