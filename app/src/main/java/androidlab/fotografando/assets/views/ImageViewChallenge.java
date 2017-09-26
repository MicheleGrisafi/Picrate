package androidlab.fotografando.assets.views;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

import androidlab.DB.Objects.Photo;

/**
 * Created by miki4 on 23/09/2017.
 */

/** Image view containing a photo reference! **/
public class ImageViewChallenge extends AppCompatImageView {

    private Photo photo;

    public ImageViewChallenge(Context context) {
        super(context);
    }

    public ImageViewChallenge(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ImageViewChallenge(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public Photo getPhoto() {
        return photo;
    }

    public void setPhoto(Photo photo) {
        this.photo = photo;
    }
}
