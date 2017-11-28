package androidlab.app.assets.tasks;

import android.content.SharedPreferences;
import android.os.AsyncTask;


import androidlab.app.DB.DAO.UtenteDAO;
import androidlab.app.DB.DAO.implementations.UtenteDAO_DB_impl;
import androidlab.app.DB.Objects.Utente;
import androidlab.app.assets.objects.AppInfo;
import androidlab.app.assets.objects.MyApp;


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
        //Applico le modifiche anche nelle sharedPreferences
        SharedPreferences settings = MyApp.getAppContext().getSharedPreferences(AppInfo.user_shared_preferences, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("name", user.getUsername());
        editor.putInt("money",user.getMoney());
        editor.putInt("score",user.getScore());
        editor.apply();
    }
}
