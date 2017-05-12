package androidlab.fotografando.assets;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Camera;
import android.os.AsyncTask;
import android.widget.Toast;

import java.io.File;
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

public class InsertThePhoto extends AsyncTask<Void, Void, Photo> {
    Photo fotografia;
    String nameFile;
    AlertDialog alertDialog;
    Context context;
    public InsertThePhoto(Photo fotografia, String namefile, Context context){
        super();
        this.fotografia = fotografia;
        this.nameFile = namefile;
        this.context = context;
    }
    @Override
    protected Photo doInBackground(Void... params) {
        PhotoDAO photoDAO = new PhotoDAO_DB_impl();
        photoDAO.open();
        fotografia = photoDAO.insertPhoto(fotografia,nameFile);
        return fotografia;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        alertDialog = new AlertDialog.Builder(context).create();
    }

    @Override
    protected void onPostExecute(Photo photo) {
        alertDialog.setTitle("Result");
        if(fotografia == null) {
            File file = new File(nameFile);
            boolean deleted = file.delete();
            if (!deleted) {
                alertDialog.setMessage("Error in the uploading! Local image deleted");
            }else
                alertDialog.setMessage("Error in the uploading! Local image cannot be deleted");
        }else{
            alertDialog.setMessage("Image was uploaded");
        }
        alertDialog.show();
    }
}
