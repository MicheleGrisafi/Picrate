package picrate.app.DB.DAO.implementations;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import picrate.app.DB.DAO.PhotoDAO;
import picrate.app.DB.MySqlDatabase;
import picrate.app.DB.Objects.ChallengeSession;
import picrate.app.DB.Objects.Photo;
import picrate.app.DB.Objects.Utente;

/**
 * Created by miki4 on 07/05/2017.
 */

public class PhotoDAO_DB_impl implements PhotoDAO {
    private MySqlDatabase database;
    private Object result;
    private String response;
    @Override
    public void open() {
        database = new MySqlDatabase();
    }
    @Override
    public void close() {
        database = null;
    }

    @Override
    public Photo insertPhoto(Photo photo,String imagePath) {
        result = null;
        response = database.insertPhoto(imagePath,Integer.toString(photo.getUtente().getId()),
                Integer.toString(photo.getSession().getIDSession()),Double.toString(photo.getLatitudine()),
                Double.toString(photo.getLongitudine()));

        if (response != "null"){
            try {
                JSONObject obj = new JSONObject(response);
                if(!obj.getBoolean("error")){
                    photo.setId(obj.getInt("idImage"));
                    photo.setImage(new URL(obj.getString("url")));
                    result = photo;
                }
            } catch (JSONException | MalformedURLException e) {
                e.printStackTrace();
            }
        }
        return (Photo)result;
    }

    @Override
    public boolean deletePhoto(Photo photo) {
        //TODO collegare il delete della photo all'azione;
        result = false;
        response = database.deletePhoto(Integer.toString(photo.getId()));

        if (response != "null"){
            try {
                JSONObject obj = new JSONObject(response);
                if(!obj.getBoolean("error")){
                    result = true;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return (boolean)result;
    }
    public ArrayList<Photo> getPhotosSession(Utente user, ChallengeSession session){
        result = null;
        response = database.getPhoto(Integer.toString(user.getId()),Integer.toString(session.getIDSession()));
        if (response != "null" && response != "" && response != null){
            List<Photo> lista = new ArrayList<Photo>();
            try {
                JSONArray arr = new JSONArray(response);
                JSONObject obj;
                Photo foto;
                for (int i = 0; i < arr.length(); i++){
                    obj = arr.getJSONObject(i);
                    foto = new Photo(user,session);
                    foto.setId(obj.getInt("IDPhoto"));
                    foto.setImage(new URL(MySqlDatabase.getUrl(MySqlDatabase.PHOTO_USER_FOLDER).toString() + "/" + Integer.toString(foto.getId()) + ".jpg"));
                    lista.add(foto);
                }
                result = lista;
            } catch (JSONException | MalformedURLException e) {
                e.printStackTrace();
            }
        }
        return (ArrayList<Photo>)result;
    }

    @Override
    public ArrayList<Photo> getRatingPhotos(Utente user) {
        result = null;
        response = database.getRatingPhotos(Integer.toString(user.getId()));
        List<Photo> lista = new ArrayList<Photo>();
        JSONArray arr = null;
        Photo photo = null;
        ChallengeSession challengeSession = null;
        JSONObject obj = null;
        URL url = null;
        try {
            arr = new JSONArray(response);
            List<String> list = new ArrayList<String>();
            for(int i = 0; i < arr.length(); i++) {
                obj = arr.getJSONObject(i);
                url = new URL(MySqlDatabase.getUrl(MySqlDatabase.PHOTO_USER_FOLDER).toString() + "/" + obj.getString("IDPhoto") + ".jpg");
                challengeSession = new ChallengeSession(obj.getInt("IDSession"));
                challengeSession.setTitle(obj.getString("title"));
                challengeSession.setDescription(obj.getString("description"));
                photo = new Photo();
                photo.setId(obj.getInt("IDPhoto"));
                photo.setSession(challengeSession);
                photo.setImage(url);
                lista.add(photo);
            }
        } catch (JSONException | MalformedURLException e) {
            e.printStackTrace();
        }
        if(!lista.isEmpty())
            result = lista;
        return (ArrayList<Photo>)result;
    }
}
