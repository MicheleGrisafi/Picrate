package picrate.app.assets.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.net.URL;
import java.util.ArrayList;

import picrate.app.DB.Objects.Medal;
import picrate.app.R;
import picrate.app.assets.listeners.UserOnClickListener;
import picrate.app.assets.objects.MyApp;

/**
 * Created by miki4 on 22/10/2017.
 */

public class AdapterLeaderboardSession  extends RecyclerView.Adapter<AdapterLeaderboardSession.ViewHolder> {
    private ArrayList<Medal> items;
    private Activity activity;
    private Intent intent;

    public AdapterLeaderboardSession(ArrayList<Medal> items, Activity activity, Intent intent) {
        this.items = items;
        this.activity = activity;
        this.intent = intent;
    }

    @Override
    public AdapterLeaderboardSession.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View challengeView = inflater.inflate(R.layout.item_photo_leaderboard, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(challengeView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Medal medal = items.get(position);

        final ProgressBar bar = holder.bar;
        UserOnClickListener userClick = new UserOnClickListener(intent,activity,medal.getUtente());
        TextView username = holder.username;
        username.setText(medal.getUtente().getUsername());
        username.setOnClickListener(userClick);

        RelativeLayout userIcon = holder.userIcon;
        userIcon.setOnClickListener(userClick);

        TextView userInitial = holder.userInitial;
        userInitial.setText(String.valueOf(Character.toUpperCase(medal.getUtente().getUsername().charAt(0))));


        ImageView imgMedal = holder.medalBackground;
        ImageView foto = holder.photo;
        //TODO: decidere colori per medaglie dopo il podio
        TextView tvMedal = holder.medalPosition;
        switch (medal.getPosition()){
            case 1:
                imgMedal.setImageResource(R.drawable.ic_medal_gold_24dp);
                break;
            case 2:
                imgMedal.setImageResource(R.drawable.ic_medal_silver_24dp);
                break;
            case 3:
                imgMedal.setImageResource(R.drawable.ic_medal_bronze_24dp);
                break;
            default:
                tvMedal.setText(Integer.toString(medal.getPosition()));
        }
        URL url = medal.getImage();
        Glide.with(MyApp.getAppContext()).load(url).listener(new RequestListener<URL, GlideDrawable>() {
            @Override
            public boolean onException(Exception e, URL model, Target<GlideDrawable> target, boolean isFirstResource) {
                bar.setVisibility(View.GONE);
                return false;
            }
            @Override
            public boolean onResourceReady(GlideDrawable resource, URL model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                bar.setVisibility(View.GONE);
                return false;
            }
        }).into(foto);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public TextView userInitial,username,medalPosition;
        public ImageView imgUser,photo,medalBackground;
        public RelativeLayout userIcon;
        public ProgressBar bar;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);
            userInitial = (TextView) itemView.findViewById(R.id.textView_userInitial);
            username = (TextView) itemView.findViewById(R.id.textView_username);
            medalPosition = (TextView) itemView.findViewById(R.id.textView_medalPosition);
            medalBackground = (ImageView) itemView.findViewById(R.id.imageView_medalBackground);
            imgUser = (ImageView) itemView.findViewById(R.id.imageView_userIcon);
            photo = (ImageView) itemView.findViewById(R.id.imageView_item_photoleaderboard_photo);
            bar = (ProgressBar) itemView.findViewById(R.id.progressBar_item_photoLeaderboard);
            userIcon = (RelativeLayout) itemView.findViewById(R.id.relativeLayout_userIcon);
        }
    }
}
