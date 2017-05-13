package androidlab.DB.Objects;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Parcel;
import android.os.Parcelable;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by miki4 on 07/05/2017.
 */

public class Photo implements Parcelable{

    private int id;
    private int ownerID;
    private int sessionID;
    private URL image;
    private double latitudine;
    private double longitudine;


    public Photo(){
        latitudine = 0;
        longitudine = 0;
        id = ownerID = sessionID = 0;
        image = null;
    }
    public Photo(Utente user,ChallengeSession session){
        this();
        ownerID = user.getId();
        sessionID = session.getIDSession();
    }
    public Photo( double longitudine, double latitudine, Utente user,ChallengeSession session, URL image){
        this(user,session);
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
    public URL getImage() {
        return image;
    }
    public void setImage(URL image) {
        this.image = image;
    }
    public int getSessionID() {
        return sessionID;
    }
    public void setSessionID(int sessionID) {
        this.sessionID = sessionID;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }
    public static final Parcelable.Creator<Photo> CREATOR = new Parcelable.Creator<Photo>() {
        public Photo createFromParcel(Parcel in) {
            return new Photo(in);
        }

        public Photo[] newArray(int size) {
            return new Photo[size];
        }
    };

    // example constructor that takes a Parcel and gives you an object populated with it's values
    private Photo(Parcel in) {
        this.id = in.readInt();
        this.ownerID = in.readInt();
        this.sessionID = in.readInt();
        try {
            this.image = new URL(in.readString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        this.latitudine = in.readDouble();
        this.longitudine = in.readDouble();
    }

}
