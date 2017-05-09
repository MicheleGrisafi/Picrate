package androidlab.DB.Objects;

import android.graphics.Bitmap;

import java.net.URL;

/**
 * Created by miki4 on 08/05/2017.
 */

public class Challenge {
    private int id;
    private String description;
    private String title;
    private URL imageUrl;
    private Bitmap image;

    public Challenge(){}

    public Challenge(String description, String title, URL imageUrl, Bitmap image){
        this();
        this.description = description;
        this.title = title;
        this.imageUrl = imageUrl;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public URL getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(URL imageUrl) {
        this.imageUrl = imageUrl;
    }
    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }
}
