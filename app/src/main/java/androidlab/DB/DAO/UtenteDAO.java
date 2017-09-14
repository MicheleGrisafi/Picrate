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
    Utente setUsername(Utente user,String username);
    Utente setMoney(Utente user,int money,boolean increment);
    Utente getMoney(Utente user);
    Utente setScore(Utente user,int score,boolean increment);
    Utente getScore(Utente user);
    Utente getUtente(String googleKey);
    ArrayList<Utente> getTopUsers();
    Utente updateUtente(Utente user);


}
