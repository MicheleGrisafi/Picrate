package androidlab.fotografando.assets;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
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
import androidlab.fotografando.cameraActivity;

/**
 * Created by miki4 on 15/05/2017.
 */

public class LoadSessionsImages extends AsyncTask<Void,Void,SparseArray<ArrayList<Photo>>> {
    private List<ChallengeSession> sessionList;
    private Context context;
    private View layoutRoot;
    private SparseArray<ArrayList<Integer>> picturesMap;
    private Utente user;
    private PhotoDAO dao;
    private int requestCode;
    private Activity activity;

    public LoadSessionsImages(Context context, View layoutRoot, List<ChallengeSession> sessionList, SparseArray<ArrayList<Integer>> picturesMap,int requestCode,Activity activity){
        this.sessionList = sessionList;
        this.context = context;
        this.picturesMap = picturesMap;
        this.layoutRoot = layoutRoot;
        user = AppInfo.getUtente();
        this.requestCode = requestCode;
        this.activity = activity;
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
        List<Photo> lista;

        boolean[] tags;
        for (ChallengeSession session:sessionList){
            tags = new boolean[]{false,false};
            lista = allPics.get(session.getIDSession());
            if(lista != null) {
                for (int i = 0; i < lista.size(); i++){
                    foto = lista.get(i);
                    ImageView image = (ImageView) layoutRoot.findViewById(picturesMap.get(session.getIDSession()).get(i));
                    if (foto != null) {
                        Glide.with(context)
                                .load(foto.getImage())
                                .into(image);
                        tags[i] = true;
                    }
                }
            }
            for (int i = 0; i<tags.length;i++){
                if (!tags[i]){
                    ImageView image = (ImageView) layoutRoot.findViewById(picturesMap.get(session.getIDSession()).get(i));
                    image.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(context,cameraActivity.class);
                            activity.startActivityForResult(intent,requestCode);
                        }
                    });
                }else {
                    ImageView image = (ImageView) layoutRoot.findViewById(picturesMap.get(session.getIDSession()).get(i));
                    image.setOnClickListener(null);
                }
            }
        }
    }
}
/*

 */