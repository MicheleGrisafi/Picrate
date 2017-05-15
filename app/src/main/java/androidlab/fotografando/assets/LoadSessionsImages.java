package androidlab.fotografando.assets;

import android.content.Context;
import android.os.AsyncTask;
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

/**
 * Created by miki4 on 15/05/2017.
 */

public class LoadSessionsImages extends AsyncTask<Void,Void,Map<Integer,List<Photo>>> {
    List<ChallengeSession> sessionList;
    Context context;
    View layoutRoot;
    Map<Integer,Integer> picturesMap;
    Utente user;
    PhotoDAO dao;
    public LoadSessionsImages(Context context, View layoutRoot, List<ChallengeSession> sessionList, Map<Integer,Integer> picturesMap){
        this.sessionList = sessionList;
        this.context = context;
        this.picturesMap = picturesMap;
        this.layoutRoot = layoutRoot;
        user = AppInfo.getUtente();
    }
    @Override
    protected void onPreExecute() {
        dao = new PhotoDAO_DB_impl();
        dao.open();
    }
    @Override
    protected Map<Integer,List<Photo>> doInBackground(Void... params) {
        Map<Integer,List<Photo>> allPics = new HashMap<Integer,List<Photo>>();
        List<Photo> pictures;
        for (ChallengeSession session:sessionList){
            pictures = dao.getPhotosSession(user,session);
            allPics.put(session.getIDSession(),pictures);
        }
        return allPics;
    }

    @Override
    protected void onPostExecute(Map<Integer,List<Photo>> allPics) {
        Photo foto = null;
        for (ChallengeSession session:sessionList){
            if(allPics.get(session.getIDSession())!= null) {
                foto = allPics.get(session.getIDSession()).get(0);
                if (foto != null) {
                    Glide.with(context)
                            .load(foto.getImage())
                            .into((ImageView) layoutRoot.findViewById(picturesMap.get(session.getIDSession())));
                }
            }
        }
    }
}
