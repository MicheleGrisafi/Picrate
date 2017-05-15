package androidlab.DB.DAO;

import java.io.File;

import androidlab.DB.Objects.Photo;

/**
 * Created by miki4 on 07/05/2017.
 */

public interface PhotoDAO {
    void open();
    void close();

    Photo insertPhoto(Photo photo, String imagePath);
    void deletePhoto(Photo photo);


}
