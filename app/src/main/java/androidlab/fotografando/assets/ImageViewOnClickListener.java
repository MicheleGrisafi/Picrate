package androidlab.fotografando.assets;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;

import androidlab.fotografando.MainActivity;
import androidlab.fotografando.MapsActivity;
import androidlab.fotografando.R;

/**
 * Created by Cate on 26/05/2017.
 */

public class ImageViewOnClickListener implements View.OnClickListener {
    Intent intent;
    MainActivity activity;
    int requestcode;
    Context ctx;
    ImageView thumbImg;
    Bitmap bitmap;
    public ImageViewOnClickListener(Intent intent, int requestCode, MainActivity activity, Context ctx, ImageView thumbImg, Bitmap bitmap){
        this.intent = intent;
        this.requestcode = requestCode;
        this.activity = activity;
        this.ctx = ctx;
        this.thumbImg = thumbImg;
        this.bitmap = bitmap;
    }

    @Override
    public void onClick(View v) {
        //activity.zoomImageFromThumb(thumbImg, bitmap);
    }
}
