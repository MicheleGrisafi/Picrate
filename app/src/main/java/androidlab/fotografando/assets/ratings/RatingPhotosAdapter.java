package androidlab.fotografando.assets.ratings;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import androidlab.DB.Objects.Photo;
import androidlab.fotografando.R;
import androidlab.fotografando.assets.AsyncResponse;

/**
 * Created by miki4 on 27/08/2017.
 */

public class RatingPhotosAdapter extends RecyclerView.Adapter<RatingPhotosAdapter.ViewHolder> {

    private List<Photo> items;
    private Context mContext;
    public AsyncResponse delegate = null;

    public RatingPhotosAdapter( Context mContext,List<Photo> items) {
        this.items = items;
        this.mContext = mContext;
    }
    public Context getContext(){
        return mContext;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public ConstraintLayout constraintLayout;
        public ConstraintLayout constraintLayoutTitle;
        public TextView challengeTextView;
        public RatingBar ratingBar;
        public ImageView imageView;
        public ImageButton imageButton;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);
            constraintLayout = (ConstraintLayout) itemView.findViewById(R.id.activeRatingPhotoLayout);
            constraintLayoutTitle = (ConstraintLayout) itemView.findViewById(R.id.activeRatingPhotoTitleLayout);
            challengeTextView = (TextView) itemView.findViewById(R.id.activeRatingPhotoChallenge);
            ratingBar = (RatingBar) itemView.findViewById(R.id.ratingBar);
            imageView = (ImageView) itemView.findViewById(R.id.activeRatingPhoto);
            imageButton = (ImageButton) itemView.findViewById(R.id.activeRatingPhotoChallengeInfo);
        }
    }
    @Override
    public RatingPhotosAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //inflate a viewholder
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View ratingPhotoView = inflater.inflate(R.layout.rating_photo_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(ratingPhotoView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RatingPhotosAdapter.ViewHolder holder, int position) {
        // Get the data model based on position
        Photo photo = items.get(position);

        // Set item views based on your views and data model
        ConstraintLayout constraintLayout = holder.constraintLayout;
        ConstraintLayout constraintLayoutTitle = holder.constraintLayoutTitle;
        RatingBar ratingBar = holder.ratingBar;
        ImageView imageView = holder.imageView;
        TextView textView = holder.challengeTextView;
        textView.setText(photo.getSession().getTitle());
        //TODO: implementare anche il messaggio di descrizione della challenge
        ImageButton imageButton = holder.imageButton;

        Glide.with(mContext)
                .load(photo.getImage())
                .into(imageView);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
