package androidlab.DB.DAO.implementations;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import androidlab.DB.DAO.MedalDAO;
import androidlab.DB.MySqlDatabase;
import androidlab.DB.Objects.Challenge;
import androidlab.DB.Objects.ChallengeSession;
import androidlab.DB.Objects.Medal;
import androidlab.DB.Objects.Photo;
import androidlab.DB.Objects.Utente;

/**
 * Created by miki4 on 15/10/2017.
 */

public class MedalDAO_DB_impl implements MedalDAO {
    private MySqlDatabase database;
    private Object result;
    private String response;

    @Override
    public void open() {
        database = new MySqlDatabase();
    }

    @Override
    public void close() {
        database = null;
    }

    @Override
    public ArrayList<Medal> getUserMedals(Utente user) {
        result = null;
        response = database.getUserMedals(Integer.toString(user.getId()));
        if (response != "null" && response != "" && response != null){
            ArrayList<Medal> lista = new ArrayList<>();
            try {
                JSONArray arr = new JSONArray(response);
                JSONObject obj;
                Medal medal;
                ChallengeSession session;
                Challenge challenge;
                Photo photo;
                DateFormat format;
                Date date;
                for (int i = 0; i < arr.length(); i++){
                    obj = arr.getJSONObject(i);
                    format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    date = format.parse(obj.getString("expiration"));
                    challenge = new Challenge(obj.getInt("IDChallenge"),obj.getString("description"),obj.getString("title"));
                    session = new ChallengeSession(obj.getInt("IDSession"),date,challenge);
                    photo = new Photo(user,session);
                    photo.setId(obj.getInt("IDPhoto"));
                    photo.setImage(new URL(MySqlDatabase.getUrl(MySqlDatabase.PHOTO_USER_FOLDER).toString() + "/" + Integer.toString(photo.getId()) + ".jpg"));
                    medal = new Medal(obj.getInt("IDMedal"),photo,obj.getInt("position"));
                    lista.add(medal);
                }
                result = lista;
            } catch (JSONException | ParseException | MalformedURLException e) {
                e.printStackTrace();
            }
        }
        return (ArrayList<Medal>)result;
    }

    @Override
    public ArrayList<Medal> getSessionMedals(ChallengeSession session) {
        result = null;
        response = database.getSessionMedals(Integer.toString(session.getIDSession()));
        if (response != null && !response.equals("null") && !response.equals("")){
            ArrayList<Medal> lista = new ArrayList<>();
            try {
                JSONArray arr = new JSONArray(response);
                JSONObject obj;
                Medal medal;
                Utente user;
                Photo photo;
                for (int i = 0; i < arr.length(); i++){
                    obj = arr.getJSONObject(i);
                    user = new Utente(obj.getString("username"));
                    photo = new Photo(user,session);
                    medal = new Medal(obj.getInt("IDMedal"),photo,obj.getInt("position"));
                    lista.add(medal);
                }
                result = lista;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return (ArrayList<Medal>)result;
    }
}