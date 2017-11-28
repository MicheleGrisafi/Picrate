package picrate.app.assets.tasks;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;

import picrate.app.DB.DAO.UtenteDAO;
import picrate.app.DB.DAO.implementations.UtenteDAO_DB_impl;
import picrate.app.DB.Objects.Utente;
import picrate.app.activities.ActivitySignUp;
import picrate.app.assets.objects.AppInfo;
import picrate.app.assets.objects.MyApp;

/**
 * Created by miki4 on 14/09/2017.
 */

public class TaskLogInUtente extends AsyncTask<Void,Void,Utente> {
    private UtenteDAO dao;
    private Intent intent;
    private Activity activity;
    String googleKey,email;
    public TaskLogInUtente(Intent intent, Activity activity,String googleKey,String email) {
        this.intent = intent;
        this.activity = activity;
        this.googleKey = googleKey;
        this.email = email;
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
            Intent intent = new Intent(activity, ActivitySignUp.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("googleKey",googleKey);
            intent.putExtra("email",email);
            activity.getApplicationContext().startActivity(intent);
        }

    }

}
