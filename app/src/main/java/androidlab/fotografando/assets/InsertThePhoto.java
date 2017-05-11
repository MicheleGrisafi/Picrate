package androidlab.fotografando.assets;

import android.graphics.Bitmap;
import android.os.AsyncTask;

import androidlab.DB.DAO.PhotoDAO;
import androidlab.DB.DAO.UtenteDAO;
import androidlab.DB.DAO.implementations.PhotoDAO_DB_impl;
import androidlab.DB.DAO.implementations.UtenteDAO_DB_impl;
import androidlab.DB.Objects.Challenge;
import androidlab.DB.Objects.ChallengeSession;
import androidlab.DB.Objects.Photo;
import androidlab.DB.Objects.Utente;

/**
 * Created by Michele Grisafi on 11/05/2017.
 */

public class InsertThePhoto extends AsyncTask<Photo, Void, Photo> {
    Photo fotografia;

    public InsertThePhoto(Photo fotografia){
        super();
        this.fotografia = fotografia;
    }

    @Override
    protected Photo doInBackground(Photo... params) {
        PhotoDAO daoPhoto = new PhotoDAO_DB_impl();
        UtenteDAO daoUser = new UtenteDAO_DB_impl();
        daoPhoto.open();
        daoUser.open();

        Utente user = new Utente("michele");
        Challenge challenge = new Challenge();
        challenge.setId(1);
        ChallengeSession session = new ChallengeSession(1,null,challenge);
        user = daoUser.getUtente(user);
        Photo thisPhoto = daoPhoto.insertPhoto(new Photo(user,session,image));
        daoPhoto.close();
        daoUser.close();
        return thisPhoto;
    }

    @Override
    protected void onPostExecute(Photo photo) {
        super.onPostExecute(photo);

    }
}
