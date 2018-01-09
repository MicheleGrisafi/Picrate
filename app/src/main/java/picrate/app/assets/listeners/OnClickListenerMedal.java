package picrate.app.assets.listeners;

import android.app.Activity;
import android.content.Intent;
import android.view.View;

import picrate.app.DB.Objects.Photo;
import picrate.app.DB.Objects.Utente;
import picrate.app.activities.ActivityMyProfile;
import picrate.app.assets.objects.AppInfo;

/**
 * Created by Michele Grisafi on 19/12/2017.
 */

public class OnClickListenerMedal implements View.OnClickListener {
    private Intent intent;
    private Activity activity;
    private Photo photo;
    public static final int REQUEST_CODE = 25;

    public OnClickListenerMedal(Intent intent, Activity activity, Photo photo) {
        this.intent = intent;
        this.activity = activity;
        this.photo = photo;
    }

    @Override
    public void onClick(View v) {
        intent.putExtra("image",photo);
        activity.startActivityForResult(intent,REQUEST_CODE);
    }
}
