package picrate.app.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.Loader;
import android.widget.TextView;

import java.util.ArrayList;

import picrate.app.DB.Objects.Medal;
import picrate.app.DB.Objects.Utente;
import picrate.app.R;
import picrate.app.assets.objects.MyApp;
import picrate.app.assets.tasks.AsyncTaskLoaderMedalsProfile;

/**
 * Created by Michele Grisafi on 15/09/2017.
 */

public class ActivityProfile extends ActivityMedals {
    private Intent inIntent;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_profile);
        super.onCreate(savedInstanceState);
        TextView tvUsername = (TextView) findViewById(R.id.tv_activity_profile_username);
        TextView tvScore = (TextView) findViewById(R.id.tv_activity_profile_stars);
        TextView tvInitial = (TextView) findViewById(R.id.profilePicText);
        inIntent = getIntent();
        tvUsername.setText(inIntent.getStringExtra("username"));
        tvScore.setText(Integer.toString(inIntent.getIntExtra("score",0)));
        tvInitial.setText(String.valueOf(Character.toUpperCase(inIntent.getStringExtra("username").charAt(0))));


        getSupportLoaderManager().initLoader(0, null, this).forceLoad();
    }
    @Override
    public Loader<ArrayList<Medal>> onCreateLoader(int id, Bundle args) {
        return new AsyncTaskLoaderMedalsProfile(MyApp.getAppContext(), new Utente(inIntent.getIntExtra("id",0),inIntent.getStringExtra("username"),inIntent.getIntExtra("score",0)));
    }
}
