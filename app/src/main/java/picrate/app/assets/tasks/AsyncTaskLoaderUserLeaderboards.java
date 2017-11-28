package picrate.app.assets.tasks;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import java.util.ArrayList;

import picrate.app.DB.DAO.UtenteDAO;
import picrate.app.DB.DAO.implementations.UtenteDAO_DB_impl;
import picrate.app.DB.Objects.Utente;

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
