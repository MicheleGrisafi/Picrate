package androidlab.fotografando.assets;

import android.content.Context;
import android.os.AsyncTask;
import android.support.constraint.ConstraintLayout;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import androidlab.DB.DAO.ChallengeDAO;
import androidlab.DB.DAO.ChallengeSessionDAO;
import androidlab.DB.DAO.implementations.ChallengeSessionDAO_DB_impl;
import androidlab.DB.Objects.ChallengeSession;

/**
 * Created by Michele Grisafi on 14/05/2017.
 */

public class LoadChallengeSessions extends AsyncTask<Void,Void,List<ChallengeSession>> {
    Context context;
    RelativeLayout layout;
    List<ChallengeSession> result;
    public LoadChallengeSessions(Context context,RelativeLayout layout){
        this.context = context;
        result = null;
        this.layout = layout;
    }
    @Override
    protected List<ChallengeSession> doInBackground(Void... params) {
        ChallengeSessionDAO sessionDAO = new ChallengeSessionDAO_DB_impl();
        sessionDAO.open();
        result = sessionDAO.getActiveSessions();
        return result;
    }

    @Override
    protected void onPostExecute(List<ChallengeSession> challengeSessions) {
        super.onPostExecute(challengeSessions);

        TextView data = new TextView(context);
        data.setText("data");
        RelativeLayout.LayoutParams dataParam = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        data.setLayoutParams(dataParam);


        LinearLayout block = new LinearLayout(context);
        block.setOrientation(LinearLayout.HORIZONTAL);
        RelativeLayout.LayoutParams linearLayoutParam = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        ConstraintLayout firstHalf = new ConstraintLayout(context);
        LinearLayout.LayoutParams firstHalfLayoutParam = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT,1);
        firstHalf.setLayoutParams(firstHalfLayoutParam);

    }
}
