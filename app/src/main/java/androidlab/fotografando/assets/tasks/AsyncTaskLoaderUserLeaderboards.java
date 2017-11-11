package androidlab.fotografando.assets.tasks;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import java.util.ArrayList;

import androidlab.DB.DAO.UtenteDAO;
import androidlab.DB.DAO.implementations.UtenteDAO_DB_impl;
import androidlab.DB.Objects.Utente;

/**
 * Created by miki4 on 10/11/2017.
 */

public class AsyncTaskLoaderUserLeaderboards extends AsyncTaskLoader<ArrayList<Utente>> {
    public AsyncTaskLoaderUserLeaderboards(Context context) {
        super(context);
    }

    @Override
    public ArrayList<Utente> loadInBackground() {
        UtenteDAO dao = new UtenteDAO_DB_impl();
        dao.open();
        ArrayList<Utente> list = dao.getTopUsers();
        dao.close();
        return list;
    }
}
