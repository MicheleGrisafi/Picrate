package androidlab.DB.Objects;

import android.graphics.Bitmap;

import java.net.URL;

/**
 * Created by miki4 on 07/05/2017.
 */

public class Photo {

    private int id;
    private URL imageUrl;
    private Bitmap image;
    private double longitudine;
    private double latitudine;
    private int ownerID;

    public Photo(){

    }
    public Photo(Utente user){
        this();
        ownerID = user.getId();
    }
    public Photo(int id, URL image, double longitudine, double latitudine, Utente user){
        this(user);
        this.id = id;
        this.image = image;
        this.longitudine = longitudine;
        this.latitudine = latitudine;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public URL getImageUrl() {
        return imageUrl;
    }
    public void setImageUrl(URL imageUrl) {
        this.imageUrl = imageUrl;
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
}
