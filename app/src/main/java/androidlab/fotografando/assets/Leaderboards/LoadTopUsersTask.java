package androidlab.fotografando.assets.Leaderboards;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;

import androidlab.DB.DAO.UtenteDAO;
import androidlab.DB.DAO.implementations.UtenteDAO_DB_impl;
import androidlab.DB.Objects.Utente;
import androidlab.fotografando.R;

/**
 * Created by miki4 on 10/09/2017.
 */

public class LoadTopUsersTask extends AsyncTask<Void,Void,ArrayList<Utente>> {
    private Context context;
    private View rootview;

    public LoadTopUsersTask(Context context, View rootview) {
        this.context = context;
        this.rootview = rootview;
    }

    @Override
    protected ArrayList<Utente> doInBackground(Void... params) {
        UtenteDAO dao = new UtenteDAO_DB_impl();
        dao.open();
        ArrayList<Utente> list = dao.getTopUsers();
        dao.close();
        return list;
    }

    @Override
    protected void onPostExecute(ArrayList<Utente> items) {
        // Lookup the recyclerview in activity layout
        RecyclerView recyclerView = (RecyclerView) rootview.findViewById(R.id.recyclerViewLeaderboardUsers);
        // Create adapter passing in the sample user data
        TopUsersLeaderboardAdapter adapter = new TopUsersLeaderboardAdapter(context,items);
        // Attach the adapter to the recyclerview to populate items
        recyclerView.setAdapter(adapter);
        // Set layout manager to position the items
        final LinearLayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(context, DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(itemDecoration);
    }
}
