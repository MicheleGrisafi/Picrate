package picrate.app.assets.OLD;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;

import picrate.app.DB.DAO.UtenteDAO;
import picrate.app.DB.DAO.implementations.UtenteDAO_DB_impl;
import picrate.app.DB.Objects.Utente;
import picrate.app.R;

/**
 * Created by miki4 on 10/09/2017.
 */

public class TaskLoadTopUsers extends AsyncTask<Void,Void,ArrayList<Utente>> {
    private Context context;
    private View rootview;
    private Activity activity;
    private Intent intent;

    public TaskLoadTopUsers(Context context, View rootview, Activity activity, Intent intent) {
        this.context = context;
        this.rootview = rootview;
        this.activity = activity;
        this.intent = intent;
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
        //AdapterLeaderboardTopUsers adapter = new AdapterLeaderboardTopUsers(context,items,activity,intent);
        // Attach the adapter to the recyclerview to populate items
        //recyclerView.setAdapter(adapter);
        // Set layout manager to position the items
        final LinearLayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(context, DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(itemDecoration);
    }
}
