package androidlab.fotografando.assets.OLD;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.view.View;
import android.widget.AbsListView;
import android.widget.RatingBar;
import android.widget.Toast;

import java.util.ArrayList;

import androidlab.DB.DAO.PhotoDAO;
import androidlab.DB.DAO.implementations.PhotoDAO_DB_impl;
import androidlab.DB.Objects.Photo;
import androidlab.DB.Objects.Rating;
import androidlab.DB.Objects.Utente;
import androidlab.fotografando.R;
import androidlab.fotografando.assets.objects.AppInfo;
import androidlab.fotografando.assets.interfaces.AsyncResponse;
import androidlab.fotografando.assets.adapters.AdapterRatingPhotos;
import androidlab.fotografando.assets.tasks.TaskInsertRating;

/**
 * Created by miki4 on 27/08/2017.
 */

public class TaskRatingLoadPhoto extends AsyncTask<Void,Void,ArrayList<Photo>> {
    private Context context;
    private View rootview;
    public AsyncResponse delegate = null;
    FragmentActivity activity;

    public TaskRatingLoadPhoto(Context context, View rootview, FragmentActivity activity) {
        this.context = context;
        this.rootview = rootview;
        this.activity = activity;
    }

    @Override
    protected ArrayList<Photo> doInBackground(Void... params) {
        PhotoDAO dao = new PhotoDAO_DB_impl();
        dao.open();
        ArrayList<Photo> result = dao.getRatingPhotos(AppInfo.getUtente());
        return result;
    }

    @Override
    protected void onPostExecute(final ArrayList<Photo> photos) {
        /*
        // Lookup the recyclerview in activity layout
        RecyclerView recyclerView = (RecyclerView) rootview.findViewById(R.id.recyclerViewRatings);
        // Create adapter passing in the sample user data
        final AdapterRatingPhotos adapter = new AdapterRatingPhotos(context,photos,activity);
        // Attach the adapter to the recyclerview to populate items
        recyclerView.setAdapter(adapter);
        // Set layout manager to position the items
        final LinearLayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setHasFixedSize(true);
        final SnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(recyclerView);

        //Rimozione ed invio voto
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if(newState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
                    View centerView = snapHelper.findSnapView(layoutManager);
                    int pos = layoutManager.getPosition(centerView);
                    if(pos != 0){
                        CardView card = (CardView)recyclerView.getLayoutManager().findViewByPosition(0);
                        float voto = ((RatingBar)card.findViewById(R.id.ratingBar)).getRating();
                        Rating rating = new Rating(AppInfo.getUtente(),photos.get(0),Math.round(voto),false);
                        adapter.removeItem(0);
                        TaskInsertRating insertRating = new TaskInsertRating(rating);
                        insertRating.execute();
                        //Se voto non è nullo allora dò dei soldi
                        if(rating.getVoto() != 0){
                            Utente user = AppInfo.getUtente();
                            user.setMoney(AppInfo.retribuzione_votazione,true);
                            AppInfo.updateUtente(user);
                            Toast.makeText(context, "1 dollar earned!", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });*/

//        delegate.processRatingFinish(adapter);
    }
}