package androidlab.DB.DAO.implementations;

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
    public Photo insertPhoto(Photo photo) {
        result = null;/*
        response = database.insertPhoto(user.getEmail(),user.getGoogleKey());
        if (response != "null"){
            user.setId(Integer.parseInt(response));
            result = user;
        }*/
        return (Photo)result;
    }

    @Override
    public void deletePhoto(Photo photo) {

    }
}
