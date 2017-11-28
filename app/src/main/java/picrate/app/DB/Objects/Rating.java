package picrate.app.DB.Objects;

/**
 * Created by Michele Grisafi on 12/05/2017.
 */

public class Rating extends Photo{
    int IDRating;
    private Utente voter;
    private int voto;
    private boolean segnalazione;

    public Rating(Photo foto, Utente voter){
        super(foto);
        this.voter = voter;
    }
    public Rating(Photo foto, Utente voter, int voto, boolean segnalazione){
        this(foto,voter);
        this.voto = voto;
        this.segnalazione = segnalazione;
    }

    public int getVoto() {
        return voto;
    }

    public void setVoto(int voto) {
        this.voto = voto;
    }

    public boolean isSegnalazione() {
        return segnalazione;
    }

    public void setSegnalazione(boolean segnalazione) {
        this.segnalazione = segnalazione;
    }

    public Utente getVoter() {
        return voter;
    }

    public void setVoter(Utente voter) {
        this.voter = voter;
    }

    public int getIDRating() {
        return IDRating;
    }

    public void setIDRating(int IDRating) {
        this.IDRating = IDRating;
    }
}
