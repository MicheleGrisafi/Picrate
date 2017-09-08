package androidlab.DB.DAO;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

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
    ArrayList<Photo> getPhotosSession(Utente user, ChallengeSession session);


}
