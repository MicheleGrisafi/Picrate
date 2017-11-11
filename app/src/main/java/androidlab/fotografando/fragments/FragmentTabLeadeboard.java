package androidlab.fotografando.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.FragmentTabHost;

import androidlab.fotografando.R;
import androidlab.fotografando.assets.objects.MyApp;

/**
 * Created by miki4 on 11/09/2017.
 *
 * Terza pagina della homepage, a suo volta ospite di due altre pagine
 */

public class FragmentTabLeadeboard extends Fragment {
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
        final FragmentTabHost tabHostLeaderboard = (FragmentTabHost) view.findViewById(R.id.fragmentTabHost_fragment_leaderboard);
        tabHostLeaderboard.setup(MyApp.getAppContext(),getChildFragmentManager(),R.id.tabcontent);

        FragmentTabHost.TabSpec spec = tabHostLeaderboard.newTabSpec("Leaderboard Top Users Tab");
        spec.setIndicator(getString(R.string.tab_leaderboard_topusers));
        tabHostLeaderboard.addTab(spec,FragmentTabLeaderboard_users.class,null);

        spec = tabHostLeaderboard.newTabSpec("Leaderboard Challenges Tab");
        spec.setIndicator(getString(R.string.tab_leaderboard_challenges));
        tabHostLeaderboard.addTab(spec,FragmentTabLeaderboard_sessions.class,null);

        return view;
    }
}
