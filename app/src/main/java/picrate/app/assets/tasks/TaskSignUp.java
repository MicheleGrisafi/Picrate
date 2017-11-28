package picrate.app.assets.tasks;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

import picrate.app.DB.DAO.UtenteDAO;
import picrate.app.DB.DAO.implementations.UtenteDAO_DB_impl;
import picrate.app.DB.Objects.Utente;
import picrate.app.R;
import picrate.app.activities.ActivityLogIn;


/**
 * Created by miki4 on 26/11/2017.
 */

public class TaskSignUp extends AsyncTask<Void,Void,Boolean> {
    private String username,email,googleKey;
    private Activity activity;
    private Utente user;
    private UtenteDAO dao;

    public TaskSignUp(String username, String email,Activity activity,String googleKey) {
        this.username = username;
        this.googleKey = googleKey;
        this.email = email;
        this.activity = activity;
    }

    @Override
    protected Boolean doInBackground(Void... voids) {
        Boolean result = (dao.insertUtente(user).getId() != 0);
        return result;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        dao = new UtenteDAO_DB_impl();
        dao.open();
        user = new Utente(username,email,googleKey);
    }

    @Override
    protected void onPostExecute(Boolean result) {
        super.onPostExecute(result);
        dao.close();
        Intent intent = new Intent(activity, ActivityLogIn.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if(result){
            activity.startActivity(intent);
            activity.finish();
        }else{
            Toast.makeText(activity, R.string.signup_error, Toast.LENGTH_SHORT).show();
        }

    }
}
