package androidlab.DB.Objects;

/**
 * Created by miki4 on 15/10/2017.
 */

public class Medal {
    private int IDMedal;
    private int IDPhoto;
    private int position;
    private Photo photo;


    public Medal(int IDMedal, int IDPhoto, int position) {
        this.IDMedal = IDMedal;
        this.IDPhoto = IDPhoto;
        this.position = position;
    }
    public Medal(int IDMedal, Photo photo,int position) {
        this(IDMedal,photo.getId(),position);
        this.photo = photo;
    }

    public int getIDMedal() {
        return IDMedal;
    }

    public void setIDMedal(int IDMedal) {
        this.IDMedal = IDMedal;
    }

    public int getIDPhoto() {
        return IDPhoto;
    }

    public void setIDPhoto(int IDPhoto) {
        this.IDPhoto = IDPhoto;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public Photo getPhoto() {
        return photo;
    }

    public void setPhoto(Photo photo) {
        this.photo = photo;
    }
}
