package androidlab.DB.DAO;

import java.util.ArrayList;

import androidlab.DB.Objects.ChallengeSession;
import androidlab.DB.Objects.Photo;
import androidlab.DB.Objects.Utente;

/**
 * Created by miki4 on 07/05/2017.
 */

public interface PhotoDAO {
    void open();
    void close();

    Photo insertPhoto(Photo photo, String imagePath);
    boolean deletePhoto(Photo photo);
    //TODO: *facoltativo* creare metodo privato getPhotos usato dagli altri metodi
    ArrayList<Photo> getPhotosSession(Utente user, ChallengeSession session);
    ArrayList<Photo> getRatingPhotos(Utente user);


}
