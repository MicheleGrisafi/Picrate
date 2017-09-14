package androidlab.fotografando.assets.generalTasks;

import android.content.SharedPreferences;
import android.os.AsyncTask;

import androidlab.DB.DAO.UtenteDAO;
import androidlab.DB.DAO.implementations.UtenteDAO_DB_impl;
import androidlab.DB.Objects.Utente;
import androidlab.fotografando.assets.AppInfo;
import androidlab.fotografando.assets.MyApp;

/**
 * Created by miki4 on 14/09/2017.
 */

public class TaskLogInUtente extends AsyncTask<Void,Void,Utente> {
    private UtenteDAO dao;
    private String googleKey;

    public TaskLogInUtente(String googleKey) {
        this.googleKey = googleKey;
    }


    @Override
    protected Utente doInBackground(Void... params) {
        Utente user = dao.getUtente(googleKey);
        return user;
    }

    @Override
    protected void onPreExecute() {
        dao = new UtenteDAO_DB_impl();
        dao.open();
    }

    @Override
    protected void onPostExecute(Utente utente) {
        //Scrivo nelle preferenze locali i dati dell'utente per rendere disponibile getUtente();
        dao.close();
        SharedPreferences settings = MyApp.getAppContext().getSharedPreferences(AppInfo.user_shared_preferences, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("name", utente.getUsername());
        editor.putString("email", utente.getEmail());
        editor.putString("google", utente.getGoogleKey());
        editor.putInt("id",utente.getId());
        editor.putInt("money",utente.getMoney());
        editor.putInt("score",utente.getScore());
        editor.apply();
    }

}
