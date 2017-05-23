package androidlab.fotografando.assets;

import android.app.AlertDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.io.File;

import androidlab.DB.DAO.PhotoDAO;
import androidlab.DB.DAO.implementations.PhotoDAO_DB_impl;
import androidlab.DB.Objects.Photo;

/**
 * Created by Michele Grisafi on 11/05/2017.
 */

public class InsertThePhoto extends AsyncTask<Void, Void, Photo> {
    private Photo fotografia;
    private String nameFile;
    private Context context;
    private ImageView imageView;

    public InsertThePhoto(Photo fotografia, String namefile, Context context, ImageView imageView){
        super();
        this.fotografia = fotografia;
        this.nameFile = namefile;
        this.context = context;
        this.imageView = imageView;

    }
    @Override
    protected Photo doInBackground(Void... params) {
        PhotoDAO photoDAO = new PhotoDAO_DB_impl();
        photoDAO.open();
        fotografia = photoDAO.insertPhoto(fotografia,nameFile);
        photoDAO.close();
        if (fotografia != null){
            Glide.with(context).load(fotografia.getImage()).into(imageView);
        }
        return fotografia;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(Photo photo) {
        if(photo == null) {
            File file = new File(nameFile);
            boolean deleted = file.delete();
        }
    }
}
