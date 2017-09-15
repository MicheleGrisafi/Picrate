package androidlab.fotografando.tabFragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TabHost;

import androidlab.fotografando.ActivityProfile;
import androidlab.fotografando.R;
import androidlab.fotografando.assets.Leaderboards.LoadTopUsersTask;

/**
 * Created by miki4 on 11/09/2017.
 */

public class LeadeboardFragment extends Fragment {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //Collego il frammento al layout
        View view = inflater.inflate(R.layout.fragment_leaderboard_tab, container, false);

        //Creo un tabhost per le due classifiche
        final TabHost tabHostLeaderboard = (TabHost) view.findViewById(R.id.tabHostLeaderboard);
        tabHostLeaderboard.setup();

        TabHost.TabSpec spec = tabHostLeaderboard.newTabSpec("Leaderboard Top Users Tab");
        spec.setContent(R.id.tabLeaderboardUsers);
        spec.setIndicator(getString(R.string.tab_leaderboard_topusers));
        tabHostLeaderboard.addTab(spec);

        spec = tabHostLeaderboard.newTabSpec("Leaderboard Challenges Tab");
        spec.setContent(R.id.tabLeaderboardChallenges);
        spec.setIndicator(getString(R.string.tab_leaderboard_challenges));
        tabHostLeaderboard.addTab(spec);

        //Avvio task per caricamento classifiche
        RelativeLayout tabLeaderboardTopUsers = (RelativeLayout) view.findViewById(R.id.tabLeaderboardUsers);
        Intent intent = new Intent(getActivity(), ActivityProfile.class);
        LoadTopUsersTask loadTopUsersTask = new LoadTopUsersTask(getActivity().getApplicationContext(),tabLeaderboardTopUsers,getActivity(),intent);
        loadTopUsersTask.execute();
        return view;
    }
}
