package androidlab.DB.DAO.implementations;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import androidlab.DB.DAO.RatingDAO;
import androidlab.DB.MySqlDatabase;
import androidlab.DB.Objects.Rating;
import androidlab.fotografando.assets.AppInfo;

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
        response = database.insertRating(Integer.toString(rating.getUserID()),Integer.toString(rating.getFotoID()),Integer.toString(rating.getVoto()),Boolean.toString(rating.isSegnalazione()));
    }

    @Override
    public List<Rating> getRatings() {
        return null;
    }


}
