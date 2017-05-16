package androidlab.fotografando.assets;

import android.app.AlertDialog;
import android.content.Context;
import android.os.AsyncTask;

import java.io.File;

import androidlab.DB.DAO.PhotoDAO;
import androidlab.DB.DAO.implementations.PhotoDAO_DB_impl;
import androidlab.DB.Objects.Photo;

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
        photoDAO.close();
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
