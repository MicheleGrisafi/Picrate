package androidlab.app.assets.tasks;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.widget.Toast;

import androidlab.app.DB.DAO.UtenteDAO;
import androidlab.app.DB.DAO.implementations.UtenteDAO_DB_impl;
import androidlab.app.DB.Objects.Utente;
import androidlab.app.assets.objects.AppInfo;
import androidlab.app.assets.objects.MyApp;

/**
 * Created by miki4 on 14/09/2017.
 */

public class TaskLogInUtente extends AsyncTask<Void,Void,Utente> {
    private UtenteDAO dao;
    private String googleKey;
    private Intent intent;
    private Activity activity;

    public TaskLogInUtente(String googleKey, Intent intent, Activity activity) {
        this.googleKey = googleKey;
        this.intent = intent;
        this.activity = activity;
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
        if(utente != null) {
            SharedPreferences settings = MyApp.getAppContext().getSharedPreferences(AppInfo.user_shared_preferences, 0);
            SharedPreferences.Editor editor = settings.edit();
            editor.putString("name", utente.getUsername());
            editor.putString("email", utente.getEmail());
            editor.putString("google", utente.getGoogleKey());
            editor.putInt("id", utente.getId());
            editor.putInt("money", utente.getMoney());
            editor.putInt("score", utente.getScore());
            editor.apply();
            activity.getApplicationContext().startActivity(intent);
            activity.finish();
        }else{
            Toast.makeText(activity, "Errore nel login", Toast.LENGTH_SHORT).show();
        }

    }

}
