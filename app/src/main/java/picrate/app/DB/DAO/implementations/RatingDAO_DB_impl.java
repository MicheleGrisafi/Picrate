package picrate.app.DB.DAO.implementations;

import java.util.List;

import picrate.app.DB.DAO.RatingDAO;
import picrate.app.DB.MySqlDatabase;
import picrate.app.DB.Objects.Rating;

/**
 * Created by Michele Grisafi on 12/05/2017.
 */

public class RatingDAO_DB_impl implements RatingDAO {
    MySqlDatabase database;
    Object result;
    String response;
    @Override
    public void open() {
        database = new MySqlDatabase();
    }

    @Override
    public void close() {
        result = null;
        response = "";
        database = null;
    }

    @Override
    public void insertRating(Rating rating) {
        response = database.insertRating(Integer.toString(rating.getVoter().getId()),Integer.toString(rating.getId()),Integer.toString(rating.getVoto()),Boolean.toString(rating.isSegnalazione()));
    }

    @Override
    public List<Rating> getRatings() {
        return null;
    }


}
