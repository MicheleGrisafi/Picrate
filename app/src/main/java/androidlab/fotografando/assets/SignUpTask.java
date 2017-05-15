package androidlab.fotografando.assets;

import android.app.AlertDialog;
import android.content.Context;
import android.os.AsyncTask;

import androidlab.DB.DAO.UtenteDAO;
import androidlab.DB.DAO.implementations.UtenteDAO_DB_impl;
import androidlab.DB.Objects.Utente;

/**
 * Created by miki4 on 08/05/2017.
 */

public class SignUpTask extends AsyncTask<Utente,Void,Utente> {
    UtenteDAO utenteDAO;
    Utente nuovoUtente;
    AlertDialog alert;
    Context context;

    SignUpTask(Context context){
        this.context = context;
    }
    @Override
    protected Utente doInBackground(Utente... params) {
        nuovoUtente = utenteDAO.insertUtente(params[0]);
        return nuovoUtente;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        utenteDAO = new UtenteDAO_DB_impl();
        utenteDAO.open();
        alert = new AlertDialog.Builder(context).create();
        alert.setTitle("Login status");
    }

    @Override
    protected void onPostExecute(Utente utente) {
        super.onPostExecute(utente);
        terminate();
        alert.setMessage(utente.getId() + " - " + utente.getEmail());
    }


    @Override
    protected void onCancelled() {
        terminate();
        super.onCancelled();
    }
    private void terminate(){
        utenteDAO.close();
        utenteDAO = null;
        nuovoUtente = null;
    }
}
