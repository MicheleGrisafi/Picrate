package androidlab.fotografando.assets.ratings;

import android.os.AsyncTask;

import androidlab.DB.DAO.implementations.RatingDAO_DB_impl;
import androidlab.DB.Objects.Rating;

/**
 * Created by miki4 on 09/09/2017.
 */

public class InsertRatingTask extends AsyncTask<Void,Void,Void> {
    private Rating rating;

    public InsertRatingTask(Rating rating) {
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
