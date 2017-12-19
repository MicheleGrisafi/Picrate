package picrate.app.assets.OLD;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;

import picrate.app.activities.ActivityMain;

/**
 * Created by Cate on 26/05/2017.
 */

/** Nuovo click listener personalizzato per le imageview delle challenge **/
public class OnClickListenerImageViewChallenge implements View.OnClickListener {
    private Intent intent;
    private ActivityMain activity;
    private int requestcode;
    private Context ctx;
    private ImageView thumbImg;
    private Bitmap bitmap;
    public OnClickListenerImageViewChallenge(Intent intent, int requestCode, ActivityMain activity, Context ctx, ImageView thumbImg, Bitmap bitmap){
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
