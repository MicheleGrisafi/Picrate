package androidlab.fotografando.assets.sessionList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v4.content.ContextCompat;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import androidlab.DB.DAO.PhotoDAO;
import androidlab.DB.DAO.implementations.PhotoDAO_DB_impl;
import androidlab.DB.Objects.ChallengeSession;
import androidlab.DB.Objects.Photo;
import androidlab.DB.Objects.Utente;
import androidlab.fotografando.R;
import androidlab.fotografando.assets.AppInfo;
import androidlab.fotografando.assets.Camera.cameraOnClickListener;
import androidlab.fotografando.CameraActivity;

/**
 * Created by miki4 on 29/05/2017.
 */


public class LoadSessionImageTask extends AsyncTask<Void,Void,ArrayList<Photo>> {

    private ChallengeSession session;
    private ArrayList<ImageView> imageViews;
    private Context context;

    private Utente user;
    private PhotoDAO dao;
    private int requestCode;

    public LoadSessionImageTask(Context context, ChallengeSession session, ArrayList<ImageView> imageViews, int requestCode){
        this.session = session;
        this.context = context;
        this.imageViews = imageViews;
        this.requestCode = requestCode;
        user = AppInfo.getUtente();
    }
    @Override
    protected void onPreExecute() {
        dao = new PhotoDAO_DB_impl();
        dao.open();
    }
    @Override
    protected ArrayList<Photo> doInBackground(Void... params) {
        ArrayList<Photo> pictures;
        pictures = dao.getPhotosSession(user,session);
        return pictures;
    }

    @Override
    protected void onPostExecute(ArrayList<Photo> pictures) {
        // TODO: provare a scaricare l'immagine bitmap in formato grande per implementare lo zoom
        Photo foto = null;
        ImageView imageView;
        Intent intent;

        if(pictures != null) {
            for (int i = 0; i < pictures.size(); i++){
                foto = pictures.get(i);
                imageView = imageViews.get(i);
                if (foto != null) {
                    Glide.with(context)
                            .load(foto.getImage())
                            .into(imageView);
                }
            }
        }

        ArrayList<Boolean> tags = new ArrayList<>(imageViews.size());
        for (int i = 0; i<imageViews.size();i++){
            imageView = imageViews.get(i);
            if(pictures != null && !pictures.isEmpty()){
                //TODO: inserire zoom nel listener
                imageView.setOnClickListener(null);
                pictures.remove(0);
                tags.add(true);
            }else{
                tags.add(false);
                imageView.setImageResource(R.drawable.ic_add_a_photo_black_24dp);
                if ( i> 0 && !tags.get(i-1)){
                    imageView.setBackgroundColor(ContextCompat.getColor(context, R.color.colorRed));
                    imageView.setOnClickListener(null);
                }else{
                    imageView.setBackgroundColor(Color.TRANSPARENT);
                    intent = new Intent(context,CameraActivity.class);
                    intent.putExtra("imageView",i);
                    intent.putExtra("sessionID",session.getIDSession());
                    imageView.setOnClickListener(new cameraOnClickListener(intent,requestCode,(Activity)context));
                }
            }
        }
        dao.close();
    }
}


