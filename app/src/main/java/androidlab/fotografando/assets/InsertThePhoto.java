package androidlab.fotografando.assets;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.widget.Toast;

import java.util.UUID;

import androidlab.DB.DAO.PhotoDAO;
import androidlab.DB.DAO.UtenteDAO;
import androidlab.DB.DAO.implementations.PhotoDAO_DB_impl;
import androidlab.DB.DAO.implementations.UtenteDAO_DB_impl;
import androidlab.DB.MySqlDatabase;
import androidlab.DB.Objects.Challenge;
import androidlab.DB.Objects.ChallengeSession;
import androidlab.DB.Objects.Photo;
import androidlab.DB.Objects.Utente;
import androidlab.fotografando.checkPhoto;

/**
 * Created by Michele Grisafi on 11/05/2017.
 */

public class InsertThePhoto extends AsyncTask<Photo, Void, Photo> {
    Photo fotografia;
    String nameFile;
    Context context;
    String response;
    public InsertThePhoto(Photo fotografia, String namefile, Context ctx){
        super();
        this.fotografia = fotografia;
        this.nameFile = namefile;
        context = ctx;
    }

    @Override
    protected Photo doInBackground(Photo... params) {
        //Toast.makeText(context, "Beginning", Toast.LENGTH_LONG).show();
        MySqlDatabase database = new MySqlDatabase();
        response = database.uploadPic(nameFile);

        return fotografia;
    }

    @Override
    protected void onPostExecute(Photo photo) {
        super.onPostExecute(photo);
        //Toast.makeText(context, response, Toast.LENGTH_LONG).show();
    }
}
