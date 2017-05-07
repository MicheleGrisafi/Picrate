package androidlab.DB.DAO.implementations;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

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
        database = new MySqlDatabase();
    }
    @Override
    public void close() {

    }

    @Override
    public Utente insertUtente(Utente user) {
        Object result = null;
        if (Integer.parseInt(database.insertUtente(user.getEmail(),user.getGoogleKey())) == 1){
            result = user;
        }
        return (Utente)result;
    }

    @Override
    public void deleteUtente(Utente user) {

    }

    @Override
    public boolean setUsername(Utente user,String username) {

        return false;
    }
}
