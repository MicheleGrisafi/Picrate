package picrate.app.assets.listeners;

import android.app.Activity;
import android.content.Intent;
import android.view.View;

import picrate.app.DB.Objects.Utente;
import picrate.app.activities.ActivityMyProfile;
import picrate.app.assets.objects.AppInfo;

/**
 * Created by Michele Grisafi on 15/09/2017.
 */

public class UserOnClickListener implements View.OnClickListener {
    private Intent intent;
    private Activity activity;
    private Utente user;

    public UserOnClickListener(Intent intent, Activity activity, Utente user) {
        this.intent = intent;
        this.activity = activity;
        this.user = user;
    }

    @Override
    public void onClick(View v) {
        intent.putExtra("id",user.getId());
        intent.putExtra("score",user.getScore());
        intent.putExtra("username",user.getUsername());
        if(intent.getIntExtra("id",0) == AppInfo.getUtente().getId())
            activity.startActivity(new Intent(activity, ActivityMyProfile.class));
        else
            activity.startActivity(intent);
    }
}
