package androidlab.fotografando.assets.generalTasks;

import android.os.AsyncTask;


import androidlab.DB.DAO.UtenteDAO;
import androidlab.DB.DAO.implementations.UtenteDAO_DB_impl;
import androidlab.DB.Objects.Utente;


/**
 * Created by miki4 on 13/09/2017.
 */

public class TaskUpdateUtente extends AsyncTask<Void,Void,Void> {

    private UtenteDAO dao;
    private Utente user;

    public TaskUpdateUtente(Utente user) {
        this.user = user;
    }

    @Override
    protected void onPreExecute() {
        dao = new UtenteDAO_DB_impl();
        dao.open();
    }

    @Override
    protected Void doInBackground(Void... params) {
        dao.updateUtente(user);
        return null;
    }
    @Override
    protected void onPostExecute(Void aVoid) {
        dao.close();
    }
}
