package androidlab.fotografando.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

import androidlab.DB.Objects.Medal;
import androidlab.DB.Objects.Utente;
import androidlab.fotografando.R;
import androidlab.fotografando.assets.adapters.AdapterMedalsProfile;
import androidlab.fotografando.assets.objects.AppInfo;
import androidlab.fotografando.assets.objects.MyApp;
import androidlab.fotografando.assets.tasks.AsyncTaskLoaderMedalsProfile;

/**
 * Created by Michele Grisafi on 15/09/2017.
 */

public class ActivityProfile extends FragmentActivity implements LoaderManager.LoaderCallbacks<ArrayList<Medal>>{
    private RecyclerView rvMedals;
    private AdapterMedalsProfile adapter;
    private ProgressBar progressBar;
    private Intent inIntent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.materialOrange600));
        }

        inIntent = getIntent();
        getSupportLoaderManager().initLoader(0, null, this).forceLoad();
        ImageButton btnBack = (ImageButton) findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        TextView tvUsername = (TextView) findViewById(R.id.tv_activity_profile_username);
        TextView tvScore = (TextView) findViewById(R.id.tv_activity_profile_stars);
        TextView tvInitial = (TextView) findViewById(R.id.profilePicText);
        progressBar = (ProgressBar) findViewById(R.id.progressBar_activity_profile);

        progressBar.setVisibility(ProgressBar.VISIBLE);
        tvUsername.setText(inIntent.getStringExtra("username"));
        tvScore.setText(Integer.toString(inIntent.getIntExtra("score",0)));
        tvInitial.setText(String.valueOf(Character.toUpperCase(inIntent.getStringExtra("username").charAt(0))));

        rvMedals = (RecyclerView) findViewById(R.id.recyclerView_activity_profile);
        rvMedals.setHasFixedSize(true);

        // Set layout manager to position the items
        rvMedals.setLayoutManager(new LinearLayoutManager(MyApp.getAppContext()));
        adapter = new AdapterMedalsProfile(MyApp.getAppContext());
        rvMedals.setAdapter(adapter);

    }

    @Override
    public Loader<ArrayList<Medal>> onCreateLoader(int id, Bundle args) {
        return new AsyncTaskLoaderMedalsProfile(MyApp.getAppContext(), new Utente(inIntent.getStringExtra("username")));
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
