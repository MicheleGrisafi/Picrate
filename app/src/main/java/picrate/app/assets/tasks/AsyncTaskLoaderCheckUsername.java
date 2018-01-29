package picrate.app.assets.tasks;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import picrate.app.DB.DAO.UtenteDAO;
import picrate.app.DB.DAO.implementations.UtenteDAO_DB_impl;
import picrate.app.DB.Objects.Utente;

/**
 * Created by miki4 on 29/01/2018.
 */

public class AsyncTaskLoaderCheckUsername extends AsyncTaskLoader<Utente> {
    private String username;

    public AsyncTaskLoaderCheckUsername(Context context, String username) {
        super(context);
        this.username = username;
    }

    @Override
    public Utente loadInBackground() {
        UtenteDAO dao = new UtenteDAO_DB_impl();
        dao.open();
        Utente user = dao.checkUsername(username);
        dao.close();
        return user;
    }
}
