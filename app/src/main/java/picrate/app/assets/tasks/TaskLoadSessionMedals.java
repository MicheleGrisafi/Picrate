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
import picrate.app.assets.adapters.AdapterLeaderboardSessionMedals;

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
    private Intent intentUser;
    private Intent intentPhoto;

    public TaskLoadSessionMedals(RecyclerView rv, Context ctx, ChallengeSession session, ProgressBar progressBar,Activity activity,Intent intentUser,Intent intentPhoto) {
        this.rv = rv;
        this.ctx = ctx;
        this.session = session;
        this.progressBar = progressBar;
        this.activity = activity;
        this.intentUser = intentUser;
        this.intentPhoto = intentPhoto;
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
        AdapterLeaderboardSessionMedals adapter = new AdapterLeaderboardSessionMedals(data,activity,intentUser,intentPhoto);
        rv.setAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(ctx, LinearLayoutManager.HORIZONTAL, false);
        rv.setLayoutManager(layoutManager);
        dao.close();
    }
}
