package androidlab.fotografando.fragments;

import android.content.Context;
import android.graphics.PorterDuff;
import android.support.v4.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.Loader;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import java.util.ArrayList;

import androidlab.DB.Objects.Utente;
import androidlab.fotografando.R;
import androidlab.fotografando.activities.ActivityProfile;
import androidlab.fotografando.assets.adapters.AdapterLeaderboardTopUsers;
import androidlab.fotografando.assets.tasks.AsyncTaskLoaderUserLeaderboards;


/**
 * Created by miki4 on 05/11/2017.
 *
 * Prima pagina delle classifiche, contenente le classifiche degli utenti
 */

public class FragmentTabLeaderboard_users extends Fragment implements LoaderManager.LoaderCallbacks<ArrayList<Utente>>{
    private RecyclerView recyclerView;
    private AdapterLeaderboardTopUsers adapter;
    private ProgressBar progressBar;
    private Context context;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        context = getActivity().getApplicationContext();
        //Collego il frammento al layout
        View view = inflater.inflate(R.layout.fragment_tab_leaderboard_users, container, false);
        RelativeLayout tabLeaderboardTopUsers = (RelativeLayout) view.findViewById(R.id.relativeLayout_tabLeaderboardUsers);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerViewLeaderboardUsers);

        Intent intent = new Intent(getActivity(), ActivityProfile.class);
        adapter = new AdapterLeaderboardTopUsers(context,getActivity(),intent);
        recyclerView.setAdapter(adapter);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(context, DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(itemDecoration);

        progressBar =(ProgressBar) view.findViewById(R.id.progressBar_fragment_tab_userLeadeboard);
        progressBar.setVisibility(ProgressBar.VISIBLE);
        progressBar.getIndeterminateDrawable().setColorFilter(ContextCompat.getColor(context, R.color.materialOrange600), PorterDuff.Mode.MULTIPLY);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getLoaderManager().initLoader(0, null, this).forceLoad();
    }

    @Override
    public Loader<ArrayList<Utente>> onCreateLoader(int id, Bundle args) {
        return new AsyncTaskLoaderUserLeaderboards(getContext());
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<Utente>> loader, ArrayList<Utente> data) {
        adapter.setItems(data);
        progressBar.setVisibility(ProgressBar.GONE);
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<Utente>> loader) {
    }
}
