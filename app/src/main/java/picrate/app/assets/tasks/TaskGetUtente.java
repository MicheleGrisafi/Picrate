package picrate.app.assets.tasks;

import android.os.AsyncTask;

import picrate.app.DB.DAO.UtenteDAO;
import picrate.app.DB.DAO.implementations.UtenteDAO_DB_impl;
import picrate.app.DB.Objects.Utente;

/**
 * Created by Michele Grisafi on 15/09/2017.
 */

public class TaskGetUtente extends AsyncTask<Void,Void,Utente> {
    private UtenteDAO dao;
    private int id;

    public TaskGetUtente(int id) {
        this.id = id;
    }

    @Override
    protected Utente doInBackground(Void... params) {
        Utente user = dao.getUtente(id);
        return user;
    }

    @Override
    protected void onPreExecute() {
        dao = new UtenteDAO_DB_impl();
        dao.open();
    }

    @Override
    protected void onPostExecute(Utente utente) {
        dao.close();
    }
}
