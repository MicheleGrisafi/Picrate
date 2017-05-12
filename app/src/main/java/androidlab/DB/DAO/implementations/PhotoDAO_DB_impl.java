package androidlab.DB.DAO.implementations;


import org.json.*;
import androidlab.DB.DAO.PhotoDAO;
import androidlab.DB.MySqlDatabase;
import androidlab.DB.Objects.Photo;

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
                    result = photo;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return (Photo)result;
    }

    @Override
    public void deletePhoto(Photo photo) {

    }
}
