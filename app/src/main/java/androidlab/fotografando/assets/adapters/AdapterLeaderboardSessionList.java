package androidlab.fotografando.assets.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

import androidlab.DB.Objects.ChallengeSession;
import androidlab.fotografando.R;
import androidlab.fotografando.assets.tasks.TaskLoadSessionMedals;

/**
 * Created by miki4 on 04/11/2017.
 */

public class AdapterLeaderboardSessionList extends RecyclerView.Adapter<AdapterLeaderboardSessionList.ViewHolder> {
    private ArrayList<ChallengeSession> items;
    private Context ctx;

    public AdapterLeaderboardSessionList(ArrayList<ChallengeSession> items, Context ctx) {
        this.items = new ArrayList<>();
        this.ctx = ctx;
    }
    public ArrayList<ChallengeSession> getItems() {
        return items;
    }

    public void setItems(ArrayList<ChallengeSession> items) {
        this.items = items;
        notifyDataSetChanged();
    }
    @Override
    public AdapterLeaderboardSessionList.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View challengeView = inflater.inflate(R.layout.item_photo_leaderboard, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(challengeView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(AdapterLeaderboardSessionList.ViewHolder holder, int position) {
        ChallengeSession session = items.get(position);
        TextView title = holder.sessionTitle;
        String tmp = session.getTitle() + title.getText() + session.getExpiration();
        title.setText(tmp);
        RecyclerView rv = holder.recyclerView;
        TaskLoadSessionMedals task = new TaskLoadSessionMedals(rv,ctx,session);

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
        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);
            sessionTitle = (TextView) itemView.findViewById(R.id.textView_sessionName);
            recyclerView = (RecyclerView) itemView.findViewById(R.id.recyclerView_medalList);
        }
    }
}
