package picrate.app.DB.Objects;

/**
 * Created by miki4 on 15/10/2017.
 */

public class Medal extends Photo{
    private int IDMedal;
    private int position;


    public Medal(int IDMedal, int position, Photo foto) {
        super(foto);
        this.IDMedal = IDMedal;
        this.position = position;
    }

    public int getIDMedal() {
        return IDMedal;
    }

    public void setIDMedal(int IDMedal) {
        this.IDMedal = IDMedal;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

}
