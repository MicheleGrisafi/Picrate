package picrate.app.assets.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.Date;

import picrate.app.DB.Objects.ChallengeSession;
import picrate.app.R;
import picrate.app.assets.tasks.TaskLoadSessionMedals;

/**
 * Created by miki4 on 04/11/2017.
 */

public class AdapterLeaderboardSessionList extends RecyclerView.Adapter<AdapterLeaderboardSessionList.ViewHolder> {
    private ArrayList<ChallengeSession> items;
    private Context ctx;
    private Activity activity;
    private Intent intentUser;
    private Intent intentPhoto;

    public AdapterLeaderboardSessionList(Context ctx,Activity activity,Intent intentUser,Intent intentPhoto) {
        this.items = new ArrayList<>();
        this.ctx = ctx;
        this.activity = activity;
        this.intentUser = intentUser;
        this.intentPhoto = intentPhoto;
    }
    public ArrayList<ChallengeSession> getItems() {
        return items;
    }

    public void setItems(ArrayList<ChallengeSession> items) {
        if(items != null) {
            this.items = items;
            notifyDataSetChanged();
        }
    }
    @Override
    public AdapterLeaderboardSessionList.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View challengeView = inflater.inflate(R.layout.item_leaderboard_session_list, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(challengeView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(AdapterLeaderboardSessionList.ViewHolder holder, int position) {
        ChallengeSession session = items.get(position);
        TextView title = holder.sessionTitle;
        TextView sessionData = holder.sessionData;

        Date dateUtil = session.getExpiration();
        DateTime data = new DateTime(dateUtil);
        DateTimeFormatter fmt = DateTimeFormat.forPattern("e MMMM");
        String tmp = fmt.print(data);

        sessionData.setText(tmp);
        title.setText(session.getTitle());

        RecyclerView rv = holder.recyclerView;
        ProgressBar progressBar = holder.progressBar;
        progressBar.setVisibility(View.VISIBLE);
        progressBar.getIndeterminateDrawable().setColorFilter(ContextCompat.getColor(ctx, R.color.materialOrange600), PorterDuff.Mode.MULTIPLY);
        TaskLoadSessionMedals task = new TaskLoadSessionMedals(rv,ctx,session,progressBar,activity,intentUser,intentPhoto);
        task.execute();
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public RecyclerView recyclerView;
        public TextView sessionTitle;
        public TextView sessionData;
        public ProgressBar progressBar;
        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);
            sessionTitle = (TextView) itemView.findViewById(R.id.textView_sessionName);
            recyclerView = (RecyclerView) itemView.findViewById(R.id.recyclerView_medalList);
            sessionData = (TextView) itemView.findViewById(R.id.textView_sessionData);
            progressBar = (ProgressBar) itemView.findViewById(R.id.progressBar_item_leaderboardSessionList);
        }
    }
}
