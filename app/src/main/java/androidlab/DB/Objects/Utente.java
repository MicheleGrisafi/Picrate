package androidlab.DB.Objects;

/**
 * Created by miki4 on 07/05/2017.
 */

public class Utente {
    private String username;
    private int score;
    private int money;

    Utente(){}
    Utente(String username){
        this.username = username;
        score = 0;
        money = 0;
    }
    Utente(String username, int money, int score){
        this.username = username;
        this.money = money;
        this.score = score;
    }
}
