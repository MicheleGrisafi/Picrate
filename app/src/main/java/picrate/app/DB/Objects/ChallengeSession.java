package picrate.app.DB.Objects;


import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import picrate.app.R;
import picrate.app.assets.objects.MyApp;

/**
 * Created by miki4 on 08/05/2017.
 */

public class ChallengeSession extends Challenge implements Comparable<ChallengeSession>,Parcelable {
    private int IDSession;
    private Date expiration;
    private int stato;
    static public final int STATO_EXPIRED = 0;
    static public final int STATO_RATING = 1;
    static public final int STATO_ACTIVE = 2;
    static public final int STATO_UPCOMING = 3;

    public ChallengeSession(int idSession){
        this.IDSession = idSession;
    }
    public ChallengeSession(int idSession, int idChallenge){
        super(idChallenge);
        this.IDSession = idSession;
    }
    public ChallengeSession(Challenge challenge){
        super(challenge.getId(),challenge.getDescription(),challenge.getTitle(),challenge.getImage());
    }
    public ChallengeSession(int IDSession,Date expiration, Challenge challenge){
        this(challenge);
        this.IDSession = IDSession;
        this.expiration = expiration;
    }
    public int getIDSession() {
        return IDSession;
    }
    public void setIDSession(int IDSession) {
        this.IDSession = IDSession;
    }
    public Date getExpiration() {
        return expiration;
    }
    public void setExpiration(Date expiration) {
        this.expiration = expiration;
    }
    public int getStato() {
        return stato;
    }
    public void setStato(int stato) {
        this.stato = stato;
    }

    @Override
    public int compareTo(@NonNull ChallengeSession o) {
        int res = 0;
        if(this.expiration.before(o.getExpiration()))
            res = -1;
        else if (this.expiration.after(o.getExpiration()))
            res = 1;
        else{
            if(this.IDSession < o.getId())
                res = -1;
            else
                res = 1;
        }
        return res;
    }


    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(IDSession);
        if(expiration != null)
            dest.writeString(expiration.toString());
        else
            dest.writeString("");
        dest.writeInt(id);
        dest.writeString(title);
        dest.writeString(description);
        if(image != null)
            dest.writeString(image.toString());
        else
            dest.writeString("");
    }
    public static final Parcelable.Creator<ChallengeSession> CREATOR = new Parcelable.Creator<ChallengeSession>() {
        public ChallengeSession createFromParcel(Parcel in) {
            return new ChallengeSession(in);
        }

        public ChallengeSession[] newArray(int size) {
            return new ChallengeSession[size];
        }
    };

    private ChallengeSession(Parcel in) {
        this.IDSession = in.readInt();
        Date parsed = null;
        try{
            String data = in.readString();
            if(!data.equals("")) {
                SimpleDateFormat format =
                        new SimpleDateFormat(MyApp.getAppContext().getString(R.string.date_pattern));
                parsed = format.parse(data);
            }
        }catch (ParseException e){
            e.printStackTrace();
        }
        this.expiration = parsed;
        this.id = in.readInt();
        this.title = in.readString();
        this.description = in.readString();
        try {
            String urll = in.readString();
            if(!urll.equals(""))
                this.image = new URL(urll);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }
}
