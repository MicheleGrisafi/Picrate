package androidlab.DB.Objects;

/**
 * Created by Michele Grisafi on 12/05/2017.
 */

public class Rating {
    private int userID;
    private int fotoID;
    private int voto;
    private boolean segnalazione;

    public Rating(){}
    public Rating(int userID, int fotoID){
        this();
        this.userID = userID;
        this.fotoID = fotoID;
    }
    public Rating(Utente user,Photo foto){
        this(user.getId(),foto.getId());
    }
    public Rating(Utente user, Photo foto, int voto, boolean segnalazione){
        this(user,foto);
        this.voto = voto;
        this.segnalazione = segnalazione;
    }



    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getFotoID() {
        return fotoID;
    }

    public void setFotoID(int fotoID) {
        this.fotoID = fotoID;
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
}
