package androidlab.fotografando.assets.Camera;

import android.content.Context;
import android.os.AsyncTask;
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
    private ImageView imageView;
    private ArrayList<ImageView> imageViews;
    private int requestCode;
    private ChallengeSession session;

    public InsertThePhotoTask(Photo fotografia, String namefile, Context context, ImageView imageView){
        this.fotografia = fotografia;
        this.nameFile = namefile;
        this.context = context;
        this.imageView = imageView;
        this.imageViews = null;
        this.requestCode = -1;
        this.session = null;
    }
    public InsertThePhotoTask(Photo fotografia, String namefile, Context context, ArrayList<ImageView> imageViews, int requestCode, ChallengeSession session){
        this.fotografia = fotografia;
        this.nameFile = namefile;
        this.context = context;
        this.imageViews = imageViews;
        this.session = session;
        this.imageView = null;
        this.requestCode = requestCode;
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
        //TODO: ripulire metodo inutile una volta deciso quale sistema utilizzare
        if(photo != null && fotografia != null) {
            if(requestCode == -1)
                Glide.with(context).load(fotografia.getImage()).into(imageView);
            else{
                LoadSessionImageTask loadSessionImageTask = new LoadSessionImageTask(context,session,imageViews,requestCode);
                loadSessionImageTask.execute();
            }

            File file = new File(nameFile);
            boolean deleted = file.delete();
        }
    }
}
