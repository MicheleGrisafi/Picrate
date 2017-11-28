package picrate.app.DB.DAO;

import java.util.ArrayList;

import picrate.app.DB.Objects.ChallengeSession;
import picrate.app.DB.Objects.Photo;
import picrate.app.DB.Objects.Utente;

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
