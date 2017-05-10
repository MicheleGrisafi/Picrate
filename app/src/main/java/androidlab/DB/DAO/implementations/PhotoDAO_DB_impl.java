package androidlab.DB.DAO.implementations;

import android.graphics.Bitmap;
import android.util.Base64;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

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
        result = null;
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        photo.getImage().compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
        String encodedImage = Base64.encodeToString(byteArrayOutputStream.toByteArray(),Base64.DEFAULT);
        ArrayList<String> arguments = new ArrayList<String>(5);
        arguments.add(Integer.toString(photo.getOwnerID()));
        arguments.add(Integer.toString(photo.getSessionID()));
        arguments.add(encodedImage);
        if(photo.getLatitudine() != 0) {
            arguments.add(Double.toString(photo.getLatitudine()));
            arguments.add(Double.toString(photo.getLongitudine()));
        }
        String [] params = arguments.toArray(new String[arguments.size()]);
        response = database.insertPhoto(params);
        if (response != "null"){
            photo.setId(Integer.parseInt(response));
            result = photo;
        }
        return (Photo)result;
    }

    @Override
    public void deletePhoto(Photo photo) {

    }
}
