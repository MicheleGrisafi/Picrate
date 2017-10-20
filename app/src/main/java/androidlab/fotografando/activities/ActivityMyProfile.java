package androidlab.fotografando.activities;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

import androidlab.DB.Objects.Medal;
import androidlab.fotografando.R;
import androidlab.fotografando.assets.adapters.AdapterMedalsProfile;
import androidlab.fotografando.assets.objects.AppInfo;
import androidlab.fotografando.assets.objects.MyApp;
import androidlab.fotografando.assets.tasks.AsyncTaskLoaderMedalsProfile;

/**
 * Created by Cate on 22/05/2017.
 */

public class ActivityMyProfile extends FragmentActivity implements LoaderManager.LoaderCallbacks<ArrayList<Medal>>{
    private RecyclerView rvMedals;
    private AdapterMedalsProfile adapter;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.materialOrange600));
        }

        ImageButton btnBack = (ImageButton) findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

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
        rvMedals = (RecyclerView) findViewById(R.id.recyclerView_activity_myProfile);


        // Set layout manager to position the items
        rvMedals.setLayoutManager(new LinearLayoutManager(MyApp.getAppContext()));
        adapter = new AdapterMedalsProfile(MyApp.getAppContext());
        rvMedals.setAdapter(adapter);

        rvMedals.setHasFixedSize(true);

        progressBar = (ProgressBar) findViewById(R.id.progressBar_activity_myProfile);

        progressBar.setVisibility(ProgressBar.VISIBLE);

        getSupportLoaderManager().initLoader(0, null, this).forceLoad();
    }

    @Override
    public Loader<ArrayList<Medal>> onCreateLoader(int id, Bundle args) {
        return new AsyncTaskLoaderMedalsProfile(MyApp.getAppContext(), AppInfo.getUtente());
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<Medal>> loader, ArrayList<Medal> data) {
        adapter.setMedals(data);
        progressBar.setVisibility(ProgressBar.GONE);
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<Medal>> loader) {

    }
}
