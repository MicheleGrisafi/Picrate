package picrate.app.assets.adapters;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import org.joda.time.DateTime;
import org.w3c.dom.Text;

import java.net.URL;
import java.util.ArrayList;

import picrate.app.DB.Objects.Medal;
import picrate.app.R;
import picrate.app.assets.objects.MyApp;

/**
 * Created by miki4 on 15/10/2017.
 */

public class AdapterMedalsProfile extends RecyclerView.Adapter<AdapterMedalsProfile.ViewHolder> {


    private ArrayList<Medal> medals;
    private Context context;

    public AdapterMedalsProfile(Context context) {
        this.context = context;
        this.medals = new ArrayList<>();
    }

    public ArrayList<Medal> getMedals() {
        return medals;
    }

    public void setMedals(ArrayList<Medal> medals) {
        if(medals != null) {
            this.medals = medals;
            notifyDataSetChanged();
        }
    }
    @Override
    public AdapterMedalsProfile.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View challengeView = inflater.inflate(R.layout.item_medal_profile, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(challengeView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Medal medal = medals.get(position);
        DateTime data = new DateTime(medal.getSession().getExpiration());
        String dataString = data.getDayOfMonth() + "/" + data.getMonthOfYear() + "/" +data.getYear();
        // Set item views based on your views and data model
        TextView session = holder.session;
        session.setText(medal.getSession().getTitle() + " " + dataString);
        TextView medalPosition = holder.position;
        medalPosition.setVisibility(View.INVISIBLE);
        final ProgressBar bar = holder.bar;
        ImageView imgMedal = holder.medal;
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
                medalPosition.setText(medal.getPosition());
                medalPosition.setVisibility(View.VISIBLE);
        }
        if(medal.getPosition() > 3 && medal.getPosition() <= 10)
            imgMedal.setImageResource(R.drawable.circle_medal_top10);
        ImageView img = holder.img;
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
        }).into(img);
    }

    @Override
    public int getItemCount() {
        return medals.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public TextView session;
        public ImageView medal,img;
        public ConstraintLayout box;
        public ProgressBar bar;
        public TextView position;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);
            session = (TextView) itemView.findViewById(R.id.textView_item_medal_profile_session);
            img = (ImageView) itemView.findViewById(R.id.imageView_item_medal_profile_img);
            medal = (ImageView) itemView.findViewById(R.id.imageView_item_medal_profile_medal);
            box = (ConstraintLayout) itemView.findViewById(R.id.constraintLayout_item_medal_profile);
            bar = (ProgressBar) itemView.findViewById(R.id.progressBar_item_medal);
            position = (TextView) itemView.findViewById(R.id.textView_medalPosition);
        }
    }
}
