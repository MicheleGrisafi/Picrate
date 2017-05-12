package androidlab.DB.Objects;

import android.graphics.Bitmap;

import java.net.URL;

/**
 * Created by miki4 on 07/05/2017.
 */

public class Photo {

    private int id;
    private Bitmap image;
    private double longitudine;
    private double latitudine;

    private int ownerID;
    private int sessionID;

    public Photo(){
        latitudine = 0;
        longitudine = 0;
        id = ownerID = sessionID = 0;
        image = null;
    }
    public Photo(Utente user,ChallengeSession session, Bitmap image){
        this();
        ownerID = user.getId();
        sessionID = session.getIDSession();
        this.image = image;
    }
    public Photo( double longitudine, double latitudine, Utente user,ChallengeSession session, Bitmap image){
        this(user,session,image);
        this.longitudine = longitudine;
        this.latitudine = latitudine;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public double getLongitudine() {
        return longitudine;
    }
    public void setLongitudine(double longitudine) {
        this.longitudine = longitudine;
    }
    public double getLatitudine() {
        return latitudine;
    }
    public void setLatitudine(double latitudine) {
        this.latitudine = latitudine;
    }
    public int getOwnerID() {
        return ownerID;
    }
    public void setOwnerID(int ownerID) {
        this.ownerID = ownerID;
    }
    public Bitmap getImage() {
        return image;
    }
    public void setImage(Bitmap image) {
        this.image = image;
    }
    public int getSessionID() {
        return sessionID;
    }
    public void setSessionID(int sessionID) {
        this.sessionID = sessionID;
    }
}
