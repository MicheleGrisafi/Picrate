package picrate.app.activities;

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

import java.util.ArrayList;

import picrate.app.DB.Objects.Medal;
import picrate.app.R;
import picrate.app.assets.adapters.AdapterMedalsProfile;
import picrate.app.assets.objects.MyApp;

/**
 * Created by miki4 on 21/10/2017.
 */

public class ActivityMedals extends FragmentActivity implements LoaderManager.LoaderCallbacks<ArrayList<Medal>>{
    private RecyclerView rvMedals;
    private AdapterMedalsProfile adapter;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ImageButton btnBack = (ImageButton) findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        progressBar = (ProgressBar) findViewById(R.id.progressBar_activity_profile);

        progressBar.setVisibility(ProgressBar.VISIBLE);

        rvMedals = (RecyclerView) findViewById(R.id.recyclerView_activity_profile);
        rvMedals.setHasFixedSize(true);

        // Set layout manager to position the items
        rvMedals.setLayoutManager(new LinearLayoutManager(MyApp.getAppContext()));
        adapter = new AdapterMedalsProfile(MyApp.getAppContext(),this,new Intent(this,ActivityPhotoZoom.class));
        rvMedals.setAdapter(adapter);

    }

    @Override
    public Loader<ArrayList<Medal>> onCreateLoader(int id, Bundle args) {
        return null;
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
