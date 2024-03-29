package picrate.app.assets.OLD;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import picrate.app.DB.DAO.PhotoDAO;
import picrate.app.DB.DAO.implementations.PhotoDAO_DB_impl;
import picrate.app.DB.Objects.ChallengeSession;
import picrate.app.DB.Objects.Photo;
import picrate.app.DB.Objects.Utente;
import picrate.app.R;
import picrate.app.activities.ActivityCamera;
import picrate.app.assets.listeners.OnClickListenerCamera;
import picrate.app.assets.objects.AppInfo;

/**
 * Created by miki4 on 15/05/2017.
 */

public class OLD_LoadSessionsImages extends AsyncTask<Void,Void,SparseArray<ArrayList<Photo>>> {

    private List<ChallengeSession> sessionList;
    //List containing all the imageViews' ids: at picturesMap.get(i) there is the list of avaible pictures, expressed as an array of ids
    private SparseArray<ArrayList<Integer>> picturesMap;

    private Context context;
    private View layoutRoot;
    private Utente user;
    private PhotoDAO dao;
    private int requestCode;

    public OLD_LoadSessionsImages(Context context, View layoutRoot, List<ChallengeSession> sessionList, SparseArray<ArrayList<Integer>> picturesMap, int requestCode){
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
        // TODO: provare a scaricare l'immagine bitmap in formato grande per implementare lo zoom
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
                        intent = new Intent(context,ActivityCamera.class);
                        intent.putExtra("imageView",picturesMap.get(session.getIDSession()).get(i));
                        intent.putExtra("sessionID",session.getIDSession());
                        image.setOnClickListener(new OnClickListenerCamera(intent,requestCode,(FragmentActivity) context,session));
                    }
                }
            }
        }
    }
}
/*

 */