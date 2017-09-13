package androidlab.fotografando.assets.Camera;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.io.File;
import java.util.ArrayList;

import androidlab.DB.DAO.PhotoDAO;
import androidlab.DB.DAO.implementations.PhotoDAO_DB_impl;
import androidlab.DB.Objects.Challenge;
import androidlab.DB.Objects.ChallengeSession;
import androidlab.DB.Objects.Photo;
import androidlab.fotografando.assets.sessionList.LoadSessionImageTask;

/**
 * Created by Michele Grisafi on 11/05/2017.
 */

public class InsertThePhotoTask extends AsyncTask<Void, Void, Photo> {
    private Photo fotografia;
    private String nameFile;
    private Context context;
    private ArrayList<ImageView> imageViews;
    private int requestCode;
    private ChallengeSession session;
    private FragmentActivity activity;

    public InsertThePhotoTask(Photo fotografia, String namefile, Context context, ArrayList<ImageView> imageViews, int requestCode, ChallengeSession session, FragmentActivity activity){
        this.fotografia = fotografia;
        this.nameFile = namefile;
        this.context = context;
        this.imageViews = imageViews;
        this.session = session;
        this.requestCode = requestCode;
        this.activity = activity;
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
    protected void onPostExecute(Photo photo) {
        if(photo != null && fotografia != null) {
            //Avvio caricamento foto
            LoadSessionImageTask loadSessionImageTask = new LoadSessionImageTask(context,session,imageViews,requestCode,activity);
            loadSessionImageTask.execute();

            //Cancello file locale
            File file = new File(nameFile);
            file.delete();
        }
    }
}
