package androidlab.fotografando.assets.Leaderboards;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import androidlab.DB.Objects.Utente;
import androidlab.fotografando.R;
import androidlab.fotografando.assets.AsyncResponse;
import androidlab.fotografando.assets.listeners.UserOnClickListener;

/**
 * Created by miki4 on 10/09/2017.
 */

public class TopUsersLeaderboardAdapter extends RecyclerView.Adapter<TopUsersLeaderboardAdapter.ViewHolder>{
    private List<Utente> items;
    private Context mContext;
    public AsyncResponse delegate = null;
    private Activity activity;
    private Intent intent;

    public TopUsersLeaderboardAdapter( Context mContext,List<Utente> items, Activity activity, Intent intent) {
        this.items = items;
        this.mContext = mContext;
        this.activity = activity;
        this.intent = intent;
    }
    public Context getContext(){
        return mContext;
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public ConstraintLayout layoutParent;
        public RelativeLayout layoutUser;
        public RelativeLayout layoutScore;
        public TextView textViewPosition;
        public ImageView imageViewUser;
        public TextView textViewInitial;
        public TextView textViewUser;
        public TextView textViewScore;
        public ImageView imageViewScore;

        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);
            layoutParent = (ConstraintLayout) itemView.findViewById(R.id.topUsersLeaderboardItem_layout);
            layoutUser = (RelativeLayout) itemView.findViewById(R.id.topUsersLeaderboardItem_userLayout);
            layoutScore = (RelativeLayout) itemView.findViewById(R.id.topUsersLeaderboardItem_scoreLayout);
            textViewPosition = (TextView) itemView.findViewById(R.id.topUsersLeaderboardItem_position);
            imageViewUser = (ImageView) itemView.findViewById(R.id.topUsersLeaderboardItem_userIcon);
            textViewInitial = (TextView) itemView.findViewById(R.id.topUsersLeaderboardItem_userInitial);
            textViewUser = (TextView) itemView.findViewById(R.id.topUsersLeaderboardItem_username);
            textViewScore = (TextView) itemView.findViewById(R.id.topUsersLeaderboardItem_score);
            imageViewScore = (ImageView) itemView.findViewById(R.id.topUsersLeaderboardItem_starImage);
        }

    }
    @Override
    public TopUsersLeaderboardAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //inflate a viewholder
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View leaderboardTopUsers = inflater.inflate(R.layout.item_topusersleaderboard, parent, false);
        ViewHolder viewHolder = new ViewHolder(leaderboardTopUsers);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(TopUsersLeaderboardAdapter.ViewHolder holder, int position) {
        // Get the data model based on position
        final Utente user = items.get(position);

        // Set item views based on your views and data model
        TextView textViewPosition = holder.textViewPosition;
        textViewPosition.setText(Integer.toString(position+1)+".");

        ImageView imageViewUser = holder.imageViewUser;
        //TODO: agganciare immagine utente

        TextView textViewUser = holder.textViewUser;
        textViewUser.setText(user.getUsername());

        TextView textViewInitial = holder.textViewInitial;
        textViewInitial.setText(String.valueOf(Character.toUpperCase(user.getUsername().charAt(0))));

        TextView textViewScore = holder.textViewScore;
        textViewScore.setText(Integer.toString(user.getScore()));

        RelativeLayout rlUser = holder.layoutUser;
        rlUser.setOnClickListener(new UserOnClickListener(intent,activity,user));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

}
