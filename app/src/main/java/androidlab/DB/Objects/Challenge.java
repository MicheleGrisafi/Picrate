package androidlab.DB.Objects;

import android.graphics.Bitmap;

import java.net.URL;

/**
 * Created by miki4 on 08/05/2017.
 */

public class Challenge {
    private int id;
    protected String description;
    protected String title;
    protected URL image;

    public Challenge(){
        id = 0;
        description = title = "";
        image = null;
    }
    public Challenge(int id){
        this();
        this.id = id;
    }
    public Challenge(String description, String title, URL image){
        this();
        this.description = description;
        this.title = title;
        this.image = image;
    }
    public Challenge(int id,String description, String title, URL image){
        this(description,title,image);
        this.id = id;
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

    public URL getImage() {
        return image;
    }

    public void setImage(URL image) {
        this.image = image;
    }
}
