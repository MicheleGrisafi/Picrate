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
    private ChallengeSession session;
    private URL image;
    private double latitudine;
    private double longitudine;


    public Photo(){
        latitudine = 0;
        longitudine = 0;
        id = ownerID = sessionID = 0;
        image = null;
    }
    public Photo(int ownerID, int sessionID){
        this.ownerID = ownerID;
        this.sessionID = sessionID;
    }
    public Photo(Utente user,ChallengeSession session){
        this(user.getId(),session.getIDSession());
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
        int res = 0;
        if(sessionID != 0)
            res = sessionID;
        else if(session.getIDSession() != 0)
            res = session.getIDSession();
        return  res;
    }
    public void setSessionID(int sessionID) {
        this.sessionID = sessionID;
    }
    public ChallengeSession getSession() {
        return session;
    }
    public void setSession(ChallengeSession session) {
        this.session = session;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeInt(this.ownerID);
        dest.writeInt(this.sessionID);
        dest.writeString(this.image.toString());
        dest.writeDouble(latitudine);
        dest.writeDouble(longitudine);
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
