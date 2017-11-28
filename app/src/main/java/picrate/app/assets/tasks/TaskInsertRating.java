package androidlab.app.assets.tasks;

import android.os.AsyncTask;

import androidlab.app.DB.DAO.implementations.RatingDAO_DB_impl;
import androidlab.app.DB.Objects.Rating;

/**
 * Created by miki4 on 09/09/2017.
 */

public class TaskInsertRating extends AsyncTask<Void,Void,Void> {
    private Rating rating;

    public TaskInsertRating(Rating rating) {
        this.rating = rating;
    }

    @Override
    protected Void doInBackground(Void... params) {
        RatingDAO_DB_impl dao = new RatingDAO_DB_impl();
        dao.open();
        dao.insertRating(rating);
        dao.close();
        return null;
    }
}
