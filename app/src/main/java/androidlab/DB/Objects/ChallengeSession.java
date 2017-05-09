package androidlab.DB.Objects;

import java.net.URL;
import java.util.Date;

/**
 * Created by miki4 on 08/05/2017.
 */

public class ChallengeSession extends Challenge {
    private int IDSession;
    private Date expiration;
    private int stato;

    static public final int STATO_EXPIRED = 0;
    static public final int STATO_RATING = 1;
    static public final int STATO_ACTIVE = 2;
    static public final int STATO_UPCOMING = 3;

    public ChallengeSession(){
        super();
    }
    public ChallengeSession(Date expiration){
        this();
        this.expiration = expiration;
    }

}
