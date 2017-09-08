package androidlab.DB.DAO.implementations;


import org.json.*;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import androidlab.DB.DAO.PhotoDAO;
import androidlab.DB.MySqlDatabase;
import androidlab.DB.Objects.ChallengeSession;
import androidlab.DB.Objects.Photo;
import androidlab.DB.Objects.Utente;

/**
 * Created by miki4 on 07/05/2017.
 */

public class PhotoDAO_DB_impl implements PhotoDAO {
    MySqlDatabase database;
    Object result;
    String response;
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
        response = database.insertPhoto(imagePath,Integer.toString(photo.getOwnerID()),
                Integer.toString(photo.getSessionID()),Double.toString(photo.getLatitudine()),
                Double.toString(photo.getLongitudine()));

        if (response != "null"){
            try {
                JSONObject obj = new JSONObject(response);
                if(!obj.getBoolean("error")){
                    photo.setId(obj.getInt("idImage"));
                    photo.setImage(new URL(obj.getString("url")));
                    result = photo;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (MalformedURLException e) {
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
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
        return (ArrayList<Photo>)result;
    }
}
