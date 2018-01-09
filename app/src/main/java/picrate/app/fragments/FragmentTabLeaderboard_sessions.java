package picrate.app.fragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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

import picrate.app.DB.Objects.ChallengeSession;
import picrate.app.R;
import picrate.app.activities.ActivityPhotoZoom;
import picrate.app.activities.ActivityProfile;
import picrate.app.activities.ActivityZoom;
import picrate.app.assets.adapters.AdapterLeaderboardSessionList;
import picrate.app.assets.tasks.AsyncTaskLoaderSessionsLeaderboard;

/**
 * Created by miki4 on 05/11/2017.
 */

public class FragmentTabLeaderboard_sessions extends Fragment implements LoaderManager.LoaderCallbacks<ArrayList<ChallengeSession>>{
    private RecyclerView recyclerView;
    private AdapterLeaderboardSessionList adapter;
    private ProgressBar progressBar;
    private Context context;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        context = getContext();
        //Collego il frammento al layout
        View view = inflater.inflate(R.layout.fragment_tab_leaderboard_sessions, container, false);
        RelativeLayout tabLeaderboardTopSessions = (RelativeLayout) view.findViewById(R.id.relativeLayout_tabLeaderboardSessions);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerViewLeaderboardSessions);

        Intent linkUser = new Intent(context, ActivityProfile.class);
        Intent linkPhoto = new Intent(context, ActivityPhotoZoom.class);
        adapter = new AdapterLeaderboardSessionList(getContext(),getActivity(),linkUser,linkPhoto);
        recyclerView.setAdapter(adapter);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(context, DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(itemDecoration);

        progressBar =(ProgressBar) view.findViewById(R.id.progressBar_fragment_tab_sessionsLeadeboard);
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
    public Loader<ArrayList<ChallengeSession>> onCreateLoader(int id, Bundle args) {
        return new AsyncTaskLoaderSessionsLeaderboard(getContext());
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<ChallengeSession>> loader, ArrayList<ChallengeSession> data) {
        adapter.setItems(data);
        progressBar.setVisibility(ProgressBar.GONE);
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<ChallengeSession>> loader) {

    }
}
