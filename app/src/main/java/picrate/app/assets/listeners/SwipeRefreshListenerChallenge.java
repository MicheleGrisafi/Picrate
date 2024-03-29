package picrate.app.assets.listeners;


import android.support.v4.widget.SwipeRefreshLayout;

import picrate.app.fragments.FragmentTabChallenge;

/**
 * Created by miki4 on 24/09/2017.
 */

public class SwipeRefreshListenerChallenge implements SwipeRefreshLayout.OnRefreshListener {

    private FragmentTabChallenge activity;


    public SwipeRefreshListenerChallenge(FragmentTabChallenge activity) {
        this.activity = activity;
    }

    @Override
    public void onRefresh() {
        activity.getLoaderManager().restartLoader(0,null,activity).forceLoad();
    }
}
