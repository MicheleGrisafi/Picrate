package picrate.app.DB.Objects;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by miki4 on 07/05/2017.
 */

public class Utente implements Parcelable{
    private String username;
    private int id;
    private String email;
    private String googleKey;
    private int score;
    private int money;

    public Utente(){
        username = email = googleKey = "";
        id = score = money = 0;
    }
    public Utente(String username){
        this.username = username;
    }
    public Utente(int id, String username, int score){
        this(username);
        this.id = id;
        this.score = score;
    }
    public Utente(String email, String googleKey){
        this();
        this.email = email;
        this.googleKey = googleKey;
    }
    public Utente(String username, String email, String googleKey){
        this(email,googleKey);
        this.username = username;
    }
    public Utente(String username, String email, String googleKey, int money, int score){
        this(username,email,googleKey);
        this.money = money;
        this.score = score;
    }
    public Utente(int id, String username, String email, String googleKey, int money, int score){
        this(username,email,googleKey,money,score);
        this.id = id;
    }
    public String getUsername(){
        return username;
    }
    public String getEmail(){
        return email;
    }
    public String getGoogleKey() {
        return googleKey;
    }
    public int getScore() {
        return score;
    }
    public int getMoney() {
        return money;
    }
    public int getId() {
        return id;
    }
    public void setScore(int score,boolean increment) {
        if(increment)
            this.score += score;
        else
            this.score = score;
    }
    public void setMoney(int money,boolean increment) {
        if(increment)
            this.money += money;
        else
            this.money = money;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(username);
        dest.writeString(email);
        dest.writeString(googleKey);
        dest.writeInt(score);
        dest.writeInt(money);
    }
    public static final Parcelable.Creator<Utente> CREATOR = new Parcelable.Creator<Utente>() {
        public Utente createFromParcel(Parcel in) {
            return new Utente(in);
        }

        public Utente[] newArray(int size) {
            return new Utente[size];
        }
    };

    private Utente(Parcel in) {
        this(in.readInt(),in.readString(),in.readString(),in.readString(),in.readInt(),in.readInt());
    }
}
