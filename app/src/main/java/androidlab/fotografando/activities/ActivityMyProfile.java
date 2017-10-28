package androidlab.fotografando.activities;


import android.os.Bundle;
import android.support.v4.content.Loader;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import androidlab.DB.Objects.Medal;
import androidlab.fotografando.R;
import androidlab.fotografando.assets.objects.AppInfo;
import androidlab.fotografando.assets.objects.MyApp;
import androidlab.fotografando.assets.tasks.AsyncTaskLoaderMedalsProfile;

/**
 * Created by Cate on 22/05/2017.
 */

public class ActivityMyProfile extends MedalsActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_my_profile);
        super.onCreate(savedInstanceState);


        TextView tvUsername = (TextView) findViewById(R.id.username);
        // nome utente
        tvUsername.setText(AppInfo.getUtente().getUsername());

        TextView tvStars = (TextView) findViewById(R.id.stars);
        // stelle possedute dall'utente
        tvStars.setText(Integer.toString(AppInfo.getUtente().getScore()));

        TextView tvCurrency = (TextView) findViewById(R.id.currency);
        // monete possedute dall'utente
        tvCurrency.setText(Integer.toString(AppInfo.getUtente().getMoney()));

        ImageView iv = (ImageView) findViewById(R.id.profilePicCircle);
        // corrisponde al colore scelto dall'utente (oppure implementare immagine profilo)
        iv.setColorFilter(R.color.materialBlue500);

        TextView tvPic = (TextView) findViewById(R.id.profilePicText);
        // la lettera deve corrispondere al primo carattere del nome utente
        tvPic.setText(String.valueOf(Character.toUpperCase(AppInfo.getUtente().getUsername().charAt(0))));

        // Set layout manager to position the items
        getSupportLoaderManager().initLoader(0, null, this).forceLoad();
    }

    @Override
    public Loader<ArrayList<Medal>> onCreateLoader(int id, Bundle args) {
        return new AsyncTaskLoaderMedalsProfile(MyApp.getAppContext(), AppInfo.getUtente());
    }

}
