package androidlab.app.assets.tasks;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

import androidlab.app.DB.DAO.PhotoDAO;
import androidlab.app.DB.DAO.implementations.PhotoDAO_DB_impl;
import androidlab.app.DB.Objects.ChallengeSession;
import androidlab.app.DB.Objects.Photo;
import androidlab.app.R;
import androidlab.app.assets.objects.AppInfo;
import androidlab.app.assets.objects.BitmapHelper;
import androidlab.app.assets.views.ImageViewChallenge;

/**
 * Created by Michele Grisafi on 11/05/2017.
 */

public class TaskInsertThePhoto extends AsyncTask<Void, Void, Photo> {
    private Photo fotografia;
    private String nameFile;
    private Context context;
    private ArrayList<ImageViewChallenge> imageViews;
    private ArrayList<ProgressBar> progressBars;
    private int requestCode;
    private ChallengeSession session;
    private FragmentActivity activity;

    private PhotoDAO photoDAO;

    public TaskInsertThePhoto(Photo fotografia, String namefile, Context context,
                              ArrayList<ImageViewChallenge> imageViews, int requestCode,
                              ChallengeSession session, FragmentActivity activity,ArrayList<ProgressBar> progressBars){
        this.fotografia = fotografia;
        this.nameFile = namefile;
        this.context = context;
        this.imageViews = imageViews;
        this.session = session;
        this.requestCode = requestCode;
        this.activity = activity;
        this.progressBars = progressBars;
    }

    @Override
    protected Photo doInBackground(Void... params) {
        fotografia = photoDAO.insertPhoto(fotografia,nameFile);
        return fotografia;
    }

    @Override
    protected void onPreExecute() {
        photoDAO = new PhotoDAO_DB_impl();
        photoDAO.open();
    }

    @Override
    protected void onPostExecute(Photo photo) {
        if(photo != null && fotografia != null) {
            //Avvio caricamento foto
            ProgressBar p1 = progressBars.get(0);
            ProgressBar p2 = progressBars.get(1);
            TaskLoadSessionImage taskLoadSessionImage = new TaskLoadSessionImage(context,session,
                    imageViews,requestCode,activity,p1,p2);
            taskLoadSessionImage.execute();

            //Cancello file locale
            if(!(boolean)AppInfo.getSetting(AppInfo.SAVE_PHOTOS)){
                File file = new File(nameFile);
                if(!BitmapHelper.deleteFile(file))
                    Toast.makeText(context, R.string.deletingLocalFileError, Toast.LENGTH_SHORT).show();
            }
        }
        photoDAO.close();
    }
}
