package androidlab.fotografando.activities;

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
import androidlab.fotografando.R;
import androidlab.fotografando.assets.adapters.AdapterMedalsProfile;
import androidlab.fotografando.assets.objects.MyApp;

/**
 * Created by miki4 on 21/10/2017.
 */

public class MedalsActivity extends FragmentActivity implements LoaderManager.LoaderCallbacks<ArrayList<Medal>>{
    private RecyclerView rvMedals;
    private AdapterMedalsProfile adapter;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

        progressBar = (ProgressBar) findViewById(R.id.progressBar_activity_profile);

        progressBar.setVisibility(ProgressBar.VISIBLE);

        rvMedals = (RecyclerView) findViewById(R.id.recyclerView_activity_profile);
        rvMedals.setHasFixedSize(true);

        // Set layout manager to position the items
        rvMedals.setLayoutManager(new LinearLayoutManager(MyApp.getAppContext()));
        adapter = new AdapterMedalsProfile(MyApp.getAppContext());
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
