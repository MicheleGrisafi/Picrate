package androidlab.fotografando.assets.tasks;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import java.util.ArrayList;

import androidlab.DB.DAO.MedalDAO;
import androidlab.DB.DAO.implementations.MedalDAO_DB_impl;
import androidlab.DB.Objects.Medal;
import androidlab.DB.Objects.Utente;

/**
 * Created by miki4 on 15/10/2017.
 */

public class AsyncTaskLoaderMedalsProfile extends AsyncTaskLoader<ArrayList<Medal>>{
    private Utente user;
    public AsyncTaskLoaderMedalsProfile(Context context, Utente user){
        super(context);
        this.user = user;
    }

    @Override
    public ArrayList<Medal> loadInBackground() {
        MedalDAO medalDAO = new MedalDAO_DB_impl();
        medalDAO.open();
        ArrayList<Medal> result = medalDAO.getUserMedals(user);
        medalDAO.close();
        return result;
    }
}