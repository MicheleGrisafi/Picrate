package androidlab.fotografando.tabFragments;


import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ListView;

import androidlab.fotografando.MainActivity;
import androidlab.fotografando.R;
import androidlab.fotografando.assets.AsyncResponse;
import androidlab.fotografando.assets.sessionList.ChallengeSessionAdapter;
import androidlab.fotografando.assets.sessionList.LoadSessionsTask;

/**
 * Created by miki4 on 11/09/2017.
 */

public class ChallengeFragment extends Fragment implements AsyncResponse{
    private SwipeRefreshLayout swipeRefreshLayoutChallenges;
    private ChallengeSessionAdapter adapter;
    public int REQUEST_CODE_CAMERA = 0;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //TODO: sostituire ImageButton con TouchHighlightImageButton
        //TODO Implementare zoom sulle foto delle challenge

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_challenge_tab, container, false);

        final ListView listView = (ListView) view.findViewById(R.id.listViewSessions);

        //Gestisco lo swipe to refresh
        swipeRefreshLayoutChallenges = (SwipeRefreshLayout)view.findViewById(R.id.swipeRefreshSessions);
        swipeRefreshLayoutChallenges.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                adapter.updateList();
            }
        });
        //Avvio cariamento delle sessioni
        LoadSessionsTask loadSessionsTask = new LoadSessionsTask(getActivity().getApplicationContext(),listView,REQUEST_CODE_CAMERA,getActivity(),swipeRefreshLayoutChallenges);
        loadSessionsTask.delegate = this;
        loadSessionsTask.execute();

        return view;
    }

    /** Comunico l'adapter **/
    @Override
    public void processFinish(Adapter output) {
        adapter = (ChallengeSessionAdapter)output;
        ((MainActivity)getActivity()).challengeSessionAdapter = adapter;
    }


}
