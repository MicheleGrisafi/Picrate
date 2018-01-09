package picrate.app.DB.Objects;

import android.os.Parcel;
import android.os.Parcelable;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by miki4 on 08/05/2017.
 */

public class Challenge implements Parcelable{
    protected int id;
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
    public Challenge(int id,String description, String title){
        this(id);
        this.description = description;
        this.title = title;
    }
    public Challenge(int id, String description, String title, URL image){
        this(id,description,title);
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
    public URL getImage() {
        return image;
    }
    public void setImage(URL image) {
        this.image = image;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(title);
        dest.writeString(description);
        dest.writeString(image.toString());
    }
    public static final Parcelable.Creator<Challenge> CREATOR = new Parcelable.Creator<Challenge>() {
        public Challenge createFromParcel(Parcel in) {
            return new Challenge(in);
        }

        public Challenge[] newArray(int size) {
            return new Challenge[size];
        }
    };

    private Challenge(Parcel in) {
        this.id = in.readInt();
        this.title = in.readString();
        this.description = in.readString();
        try {
            this.image = new URL(in.readString());
        }catch (MalformedURLException e){

        }
    }
}
