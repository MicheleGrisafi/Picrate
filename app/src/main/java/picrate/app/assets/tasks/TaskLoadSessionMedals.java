package picrate.app.assets.tasks;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import java.util.ArrayList;

import picrate.app.DB.DAO.MedalDAO;
import picrate.app.DB.DAO.implementations.MedalDAO_DB_impl;
import picrate.app.DB.Objects.ChallengeSession;
import picrate.app.DB.Objects.Medal;
import picrate.app.assets.adapters.AdapterLeaderboardSession;

/**
 * Created by miki4 on 05/11/2017.
 */

public class TaskLoadSessionMedals extends AsyncTask<Void,Void,ArrayList<Medal>> {
    private RecyclerView rv;
    private Context ctx;
    private MedalDAO dao;
    private ChallengeSession session;
    private ProgressBar progressBar;
    private Activity activity;
    private Intent intent;

    public TaskLoadSessionMedals(RecyclerView rv, Context ctx, ChallengeSession session, ProgressBar progressBar,Activity activity,Intent intent) {
        this.rv = rv;
        this.ctx = ctx;
        this.session = session;
        this.progressBar = progressBar;
        this.activity = activity;
        this.intent = intent;
    }

    @Override
    protected ArrayList<Medal> doInBackground(Void... voids) {
        ArrayList<Medal> items = dao.getSessionMedals(session);
        return items;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        dao = new MedalDAO_DB_impl();
        dao.open();
    }

    @Override
    protected void onPostExecute(ArrayList<Medal> data) {
        progressBar.setVisibility(View.GONE);
        AdapterLeaderboardSession adapter = new AdapterLeaderboardSession(data,activity,intent);
        rv.setAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(ctx, LinearLayoutManager.HORIZONTAL, false);
        rv.setLayoutManager(layoutManager);
        dao.close();
    }
}
