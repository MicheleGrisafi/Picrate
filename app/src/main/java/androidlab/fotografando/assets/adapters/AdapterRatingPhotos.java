package androidlab.fotografando.assets.adapters;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import androidlab.DB.Objects.Photo;
import androidlab.DB.Objects.Rating;
import androidlab.fotografando.R;
import androidlab.fotografando.assets.listeners.RequestListenerGlideProgressBar;
import androidlab.fotografando.assets.objects.AppInfo;
import androidlab.fotografando.assets.interfaces.AsyncResponse;
import androidlab.fotografando.assets.tasks.TaskInsertRating;

/**
 * Created by miki4 on 27/08/2017.
 */

public class AdapterRatingPhotos extends RecyclerView.Adapter<AdapterRatingPhotos.ViewHolder> {
    private List<Photo> items;
    private Context mContext;
    public AsyncResponse delegate = null;
    FragmentActivity activity;

    public AdapterRatingPhotos(Context mContext, FragmentActivity activity) {
        this.mContext = mContext;
        this.activity = activity;
        this.items = new ArrayList<>();
    }
    public List<Photo> getItems() {
        return items;
    }

    public void setItems(List<Photo> items) {
        this.items = items;
        //Crea una foto farlocca per fine lista
        Photo foto = new Photo();
        if(this.items != null)
            this.items.add(foto);
        else {
            this.items = new ArrayList<Photo>();
            this.items.add(foto);
        }
        notifyDataSetChanged();
    }
    public void removeItem(int pos){
        items.remove(pos);
        notifyItemRemoved(pos);
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
        public ProgressBar progressBar;

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
            progressBar = (ProgressBar) itemView.findViewById(R.id.progressBar_item_ratingPhoto);
        }
    }
    @Override
    public AdapterRatingPhotos.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //inflate a viewholder
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View ratingPhotoView = inflater.inflate(R.layout.item_rating_photo, parent, false);
        ViewHolder viewHolder = new ViewHolder(ratingPhotoView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(AdapterRatingPhotos.ViewHolder holder, int position) {
        // Get the data model based on position
        final Photo photo = items.get(position);
        //check if it's the last item
        if(photo.getId() == 0){
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
            ProgressBar progressBar = holder.progressBar;
            progressBar.getIndeterminateDrawable().setColorFilter(ContextCompat.getColor(mContext, R.color.materialOrange600), PorterDuff.Mode.MULTIPLY);
            progressBar.setVisibility(ProgressBar.VISIBLE);
            ImageButton imageButtonInfo = holder.imageButtonInfo;
            imageButtonInfo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Toast.makeText(mContext, "ciao", Toast.LENGTH_SHORT).show();
                    // custom dialog
                    final Dialog dialog = new Dialog(activity);
                    dialog.setContentView(R.layout.dialog_challengesession_info);

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
                    final Dialog dialog = new Dialog(activity);
                    dialog.setContentView(R.layout.dialog_report_photo);

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
                            Rating rating = new Rating(items.get(0),AppInfo.getUtente(),0,true);
                            items.remove(0);
                            notifyItemRemoved(0);
                            TaskInsertRating insertRating = new TaskInsertRating(rating);
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
                    .listener(new RequestListenerGlideProgressBar(progressBar))
                    .into(imageView);
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }


}
