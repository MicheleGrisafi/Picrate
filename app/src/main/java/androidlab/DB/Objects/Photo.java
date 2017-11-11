package androidlab.DB.Objects;


import android.os.Parcel;
import android.os.Parcelable;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by miki4 on 07/05/2017.
 */

public class Photo implements Parcelable{

    private int id;
    private ChallengeSession session;
    private Utente utente;
    private URL image;
    private double latitudine;
    private double longitudine;


    public Photo(){
        latitudine = 0;
        longitudine = 0;
        id = 0;
        image = null;
        utente = null;
        session = null;
    }
    public Photo(Utente user,ChallengeSession session){
        this();
        this.session = session;
        this.setUtente(user);
    }
    public Photo(Utente user,ChallengeSession session, URL image){
        this(user,session);
        this.image = image;
    }
    public Photo(Utente user,ChallengeSession session, URL image, double latitudine, double longitudine){
        this(user,session,image);
        this.longitudine = longitudine;
        this.latitudine = latitudine;
    }
    public Photo(Photo foto){
        this(foto.getUtente(),foto.getSession(),foto.getImage(),foto.getLatitudine(),foto.getLongitudine());
        id = foto.getId();
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
    public URL getImage() {
        return image;
    }
    public void setImage(URL image) {
        this.image = image;
    }
    public ChallengeSession getSession() {
        return session;
    }
    public void setSession(ChallengeSession session) {
        this.session = session;
    }
    public Utente getUtente() {
        return utente;
    }
    public void setUtente(Utente utente) {
        this.utente = utente;
    }
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
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
        try {
            this.image = new URL(in.readString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        this.latitudine = in.readDouble();
        this.longitudine = in.readDouble();
    }
}
