package picrate.app.fragments;



import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;


import java.util.List;

import picrate.app.DB.Objects.ChallengeSession;
import picrate.app.R;
import picrate.app.assets.tasks.AsyncTaskLoaderChallenges;
import picrate.app.assets.listeners.SwipeRefreshListenerChallenge;
import picrate.app.assets.adapters.AdapterChallengeSession;

/**
 * Created by miki4 on 11/09/2017.
 */

public class FragmentTabChallenge extends Fragment implements LoaderManager.LoaderCallbacks<List<ChallengeSession>> {
    private SwipeRefreshLayout swipeRefreshLayoutChallenges;
    public AdapterChallengeSession adapter;
    public int REQUEST_CODE_CAMERA = 0;
    RecyclerView rvChallenge;

    private ProgressBar progressBar;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //TODO: sostituire ImageButton con TouchHighlightImageButton
        //TODO Implementare zoom sulle foto delle challenge
        //TODO: impedire il refresh delle immagini al cambio tab -> unire loadImageTask e LoadExpirationTask nel LoadChallengeTask
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_challenge_tab, container, false);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rvChallenge = (RecyclerView) getView().findViewById(R.id.recyclerView_fragment_challenge);


        // Set layout manager to position the items
        rvChallenge.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
        adapter = new AdapterChallengeSession(getActivity().getApplicationContext(),REQUEST_CODE_CAMERA,
                swipeRefreshLayoutChallenges,getActivity());
        rvChallenge.setAdapter(adapter);

        SwipeRefreshListenerChallenge listenerChallenge = new SwipeRefreshListenerChallenge(this);

        //Gestisco lo swipe to refresh
        swipeRefreshLayoutChallenges = (SwipeRefreshLayout)view.findViewById(R.id.swipeRefreshSessions);
        swipeRefreshLayoutChallenges.setOnRefreshListener(listenerChallenge);

        progressBar =(ProgressBar) getView().findViewById(R.id.progressBar_fragment_challenge);
        progressBar.setVisibility(ProgressBar.VISIBLE);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        /** LOADER **/
        getLoaderManager().initLoader(0, null, this).forceLoad();

    }


    @Override
    public Loader<List<ChallengeSession>> onCreateLoader(int id, Bundle args) {
        return new AsyncTaskLoaderChallenges(getContext());
    }

    @Override
    public void onLoadFinished(Loader<List<ChallengeSession>> loader, List<ChallengeSession> data) {
        adapter.setSessions(data);
        progressBar.setVisibility(ProgressBar.GONE);
        swipeRefreshLayoutChallenges.setRefreshing(false);
    }

    @Override
    public void onLoaderReset(Loader<List<ChallengeSession>> loader) {

    }
}
