package androidlab.fotografando.assets;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidlab.DB.DAO.PhotoDAO;
import androidlab.DB.DAO.implementations.PhotoDAO_DB_impl;
import androidlab.DB.Objects.ChallengeSession;
import androidlab.DB.Objects.Photo;
import androidlab.DB.Objects.Utente;
import androidlab.fotografando.MainActivity;
import androidlab.fotografando.R;
import androidlab.fotografando.cameraActivity;

/**
 * Created by miki4 on 15/05/2017.
 */

public class LoadSessionsImages extends AsyncTask<Void,Void,SparseArray<ArrayList<Photo>>> {

    private List<ChallengeSession> sessionList;
    //List containing all the imageViews' ids: at picturesMap.get(i) there is the list of avaible pictures, expressed as an array of ids
    private SparseArray<ArrayList<Integer>> picturesMap;

    private Context context;
    private View layoutRoot;
    private Utente user;
    private PhotoDAO dao;
    private int requestCode;

    public LoadSessionsImages(Context context, View layoutRoot, List<ChallengeSession> sessionList, SparseArray<ArrayList<Integer>> picturesMap,int requestCode){
        this.sessionList = sessionList;
        this.context = context;
        this.picturesMap = picturesMap;
        this.layoutRoot = layoutRoot;
        user = AppInfo.getUtente();
        this.requestCode = requestCode;
    }
    @Override
    protected void onPreExecute() {
        dao = new PhotoDAO_DB_impl();
        dao.open();
    }
    @Override
    protected SparseArray<ArrayList<Photo>> doInBackground(Void... params) {
        SparseArray<ArrayList<Photo>> allPics = new SparseArray<>();
        ArrayList<Photo> pictures;
        for (ChallengeSession session:sessionList){
            pictures = dao.getPhotosSession(user,session);
            allPics.put(session.getIDSession(),pictures);
        }
        return allPics;
    }

    @Override
    protected void onPostExecute(SparseArray<ArrayList<Photo>> allPics) {
        Photo foto = null;
        ArrayList<Photo> lista;
        ArrayList<Integer> imageViewIds;
        ImageView image;
        Intent intent;
        for (ChallengeSession session:sessionList){
            lista = allPics.get(session.getIDSession());
            imageViewIds = picturesMap.get(session.getIDSession());

            if(lista != null) {
                for (int i = 0; i < lista.size(); i++){
                    foto = lista.get(i);

                    image = (ImageView) layoutRoot.findViewById(imageViewIds.get(i));
                    if (foto != null) {
                        Glide.with(context)
                                .load(foto.getImage())
                                .into(image);

                    }
                }
            }
            ArrayList<Boolean> tags = new ArrayList<>(imageViewIds.size());
            for (int i = 0; i<imageViewIds.size();i++){
                image = (ImageView) layoutRoot.findViewById(picturesMap.get(session.getIDSession()).get(i));
                if(lista != null && !lista.isEmpty()){
                    //Zoom immagine!!!
                    image.setOnClickListener(null);
                    lista.remove(0);
                    tags.add(new Boolean(true));
                }else{
                    tags.add(new Boolean(false));
                    if ( i> 0 && tags.get(i-1) == false){
                        image.setBackgroundColor(ContextCompat.getColor(context, R.color.colorRed));
                        image.setOnClickListener(null);
                    }else{
                        intent = new Intent(context,cameraActivity.class);
                        intent.putExtra("imageView",picturesMap.get(session.getIDSession()).get(i));
                        intent.putExtra("sessionID",session.getIDSession());
                        image.setOnClickListener(new cameraOnClickListener(intent,requestCode,(Activity)context));
                    }
                }
            }
        }
    }
}
/*

 */