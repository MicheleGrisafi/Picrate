package androidlab.fotografando.assets;


import android.support.v4.widget.SwipeRefreshLayout;

import androidlab.fotografando.tabFragments.ChallengeFragment;

/**
 * Created by miki4 on 24/09/2017.
 */

public class SwipeRefreshListenerChallenge implements SwipeRefreshLayout.OnRefreshListener {

    private ChallengeFragment activity;


    public SwipeRefreshListenerChallenge(ChallengeFragment activity) {
        this.activity = activity;
    }

    @Override
    public void onRefresh() {
        activity.getLoaderManager().restartLoader(0,null,activity).forceLoad();
    }
}
