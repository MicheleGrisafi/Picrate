package picrate.app.DB.DAO;

import java.util.List;

import picrate.app.DB.Objects.Rating;

/**
 * Created by Michele Grisafi on 12/05/2017.
 */

public interface RatingDAO {
    void open();
    void close();
    void insertRating(Rating rating);
    List<Rating> getRatings();
}
