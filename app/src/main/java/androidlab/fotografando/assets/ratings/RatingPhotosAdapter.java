package androidlab.fotografando.assets.ratings;

import android.app.Dialog;
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
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import androidlab.DB.Objects.Photo;
import androidlab.DB.Objects.Rating;
import androidlab.fotografando.R;
import androidlab.fotografando.assets.AppInfo;
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
        //Crea una foto farlocca per fine lista
        Photo foto = new Photo(-1,-1);
        if(this.items != null)
            this.items.add(foto);
        else {
            this.items = new ArrayList<Photo>();
            this.items.add(foto);
        }

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
        public ImageButton imageButtonInfo;
        public ImageButton imageButtonReport;

        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);
            constraintLayout = (ConstraintLayout) itemView.findViewById(R.id.activeRatingPhotoLayout);
            constraintLayoutTitle = (ConstraintLayout) itemView.findViewById(R.id.activeRatingPhotoTitleLayout);
            challengeTextView = (TextView) itemView.findViewById(R.id.activeRatingPhotoChallenge);
            ratingBar = (RatingBar) itemView.findViewById(R.id.ratingBar);
            imageView = (ImageView) itemView.findViewById(R.id.activeRatingPhoto);
            imageButtonInfo = (ImageButton) itemView.findViewById(R.id.activeRatingPhotoChallengeInfo);
            imageButtonReport = (ImageButton) itemView.findViewById(R.id.activeRatingPhotoReportButon);

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
        final Photo photo = items.get(position);
        //check if it's the last item
        if(photo.getOwnerID() == -1){
            RatingBar ratingBar = holder.ratingBar;
            ratingBar.setVisibility(View.GONE);

            ImageView imageView = holder.imageView;
            imageView.setImageResource(R.drawable.ic_block_black_300dp);

            TextView textView = holder.challengeTextView;
            textView.setText(R.string.no_more_photos);

            ImageButton imageButtonInfo = holder.imageButtonInfo;
            imageButtonInfo.setVisibility(View.GONE);

            ImageButton imageButtonReport = holder.imageButtonReport;
            imageButtonReport.setVisibility(View.GONE);
        }else {
            // Set item views based on your views and data model
            ConstraintLayout constraintLayout = holder.constraintLayout;
            ConstraintLayout constraintLayoutTitle = holder.constraintLayoutTitle;
            final RatingBar ratingBar = holder.ratingBar;
            ImageView imageView = holder.imageView;
            TextView textView = holder.challengeTextView;
            textView.setText(photo.getSession().getTitle());
            //TODO: implementare anche il messaggio di descrizione della challenge
            ImageButton imageButtonInfo = holder.imageButtonInfo;
            imageButtonInfo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Toast.makeText(mContext, "ciao", Toast.LENGTH_SHORT).show();
                    // custom dialog
                    final Dialog dialog = new Dialog(mContext);
                    dialog.setContentView(R.layout.challengesession_info_dialog);

                    // set the custom dialog components - text, image and button
                    TextView text = (TextView) dialog.findViewById(R.id.challengeSessionInfoDialogText);
                    text.setText(photo.getSession().getDescription());


                    ImageButton dialogButton = (ImageButton) dialog.findViewById(R.id.challengeSessionInfoDialogCloseButton);
                    // if button is clicked, close the custom dialog
                    dialogButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });
                    dialog.show();
                }
            });

            ImageButton imageButtonReport = holder.imageButtonReport;
            imageButtonReport.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final Dialog dialog = new Dialog(mContext);
                    dialog.setContentView(R.layout.report_photo_dialog);

                    // set the custom dialog components - text, image and button
                    TextView text = (TextView) dialog.findViewById(R.id.reportPhotoDialogText);

                    ImageButton dialogCloseButton = (ImageButton) dialog.findViewById(R.id.reportPhotoDialogCloseButton);
                    // if button is clicked, close the custom dialog
                    dialogCloseButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });
                    Button dialogReportButton = (Button) dialog.findViewById(R.id.reportPhotoDialogReportButton);
                    // if button is clicked, close the custom dialog
                    dialogReportButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            float voto = ratingBar.getRating();
                            Rating rating = new Rating(AppInfo.getUtente(),items.get(0),Math.round(voto),true);
                            items.remove(0);
                            notifyItemRemoved(0);
                            InsertRatingTask insertRating = new InsertRatingTask(rating);
                            insertRating.execute();
                            dialog.dismiss();
                            Toast.makeText(mContext, R.string.report_thanks, Toast.LENGTH_LONG).show();
                        }
                    });
                    dialog.show();
                }
            });

            Glide.with(mContext)
                    .load(photo.getImage())
                    .into(imageView);
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }


}
