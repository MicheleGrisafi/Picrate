package androidlab.DB.Objects;


import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import java.net.URL;
import java.util.Date;

/**
 * Created by miki4 on 08/05/2017.
 */

public class ChallengeSession extends Challenge implements Comparable<ChallengeSession> {
    private int IDSession;
    private Date expiration;
    private int stato;


    static public final int STATO_EXPIRED = 0;
    static public final int STATO_RATING = 1;
    static public final int STATO_ACTIVE = 2;
    static public final int STATO_UPCOMING = 3;

    public ChallengeSession(){}
    public ChallengeSession(int idSession, int idChallenge){
        super(idChallenge);
        this.IDSession = idSession;
    }
    public ChallengeSession(Challenge challenge){
        super(challenge.getDescription(),challenge.getTitle(),challenge.getImage());
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


}
