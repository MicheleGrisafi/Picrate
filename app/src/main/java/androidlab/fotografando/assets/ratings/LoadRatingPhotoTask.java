package androidlab.fotografando.assets.ratings;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.view.View;

import java.util.ArrayList;

import androidlab.DB.DAO.PhotoDAO;
import androidlab.DB.DAO.implementations.PhotoDAO_DB_impl;
import androidlab.DB.Objects.Photo;
import androidlab.fotografando.R;
import androidlab.fotografando.assets.AppInfo;
import androidlab.fotografando.assets.AsyncResponse;

/**
 * Created by miki4 on 27/08/2017.
 */

public class LoadRatingPhotoTask extends AsyncTask<Void,Void,ArrayList<Photo>> {
    private Context context;
    private View rootview;
    public AsyncResponse delegate = null;

    public LoadRatingPhotoTask(Context context, View rootview) {
        this.context = context;
        this.rootview = rootview;
    }

    @Override
    protected ArrayList<Photo> doInBackground(Void... params) {
        PhotoDAO dao = new PhotoDAO_DB_impl();
        dao.open();
        ArrayList<Photo> result = dao.getRatingPhotos(AppInfo.getUtente());
        return result;
    }

    @Override
    protected void onPostExecute(ArrayList<Photo> photos) {
        // Lookup the recyclerview in activity layout
        RecyclerView recyclerView = (RecyclerView) rootview.findViewById(R.id.recyclerViewRatings);
        // Create adapter passing in the sample user data
        RatingPhotosAdapter adapter = new RatingPhotosAdapter(context,photos);
        // Attach the adapter to the recyclerview to populate items
        recyclerView.setAdapter(adapter);
        // Set layout manager to position the items
        LinearLayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setHasFixedSize(true);
        SnapHelper snapHelper = new LinearSnapHelper();
        snapHelper.attachToRecyclerView(recyclerView);

        delegate.processRatingFinish(adapter);
    }
}
