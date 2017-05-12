package androidlab.DB.DAO;

import androidlab.DB.Objects.Rating;

/**
 * Created by Michele Grisafi on 12/05/2017.
 */

public interface RatingDAO {
    void open();
    void close();
    void insertRating(Rating rating);
}
