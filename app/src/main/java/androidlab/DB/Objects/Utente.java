package androidlab.DB.Objects;

/**
 * Created by miki4 on 07/05/2017.
 */

public class Utente {
    private String username;
    private String email;
    private String googleKey;
    private int score;
    private int money;

    Utente(){}
    Utente(String email, String googleKey){
        this();
        this.email = email;
        this.googleKey = googleKey;
    }
    Utente(String username, String email, String googleKey){
        this(email,googleKey);
        this.username = username;
    }
    Utente(String username, String email, String googleKey, int money, int score){
        this(username,email,googleKey);
        this.money = money;
        this.score = score;
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
    public void setScore(int score) {
        this.score = score;
    }
    public void setMoney(int money) {
        this.money = money;
    }
    public void setUsername(String username) {
        this.username = username;
    }
}
