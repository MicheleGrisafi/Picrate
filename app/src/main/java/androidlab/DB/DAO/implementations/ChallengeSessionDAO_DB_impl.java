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
import java.util.List;

import androidlab.DB.DAO.ChallengeSessionDAO;
import androidlab.DB.MySqlDatabase;
import androidlab.DB.Objects.Challenge;
import androidlab.DB.Objects.ChallengeSession;


/**
 * Created by Michele Grisafi on 10/05/2017.
 */

public class ChallengeSessionDAO_DB_impl implements ChallengeSessionDAO {
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
        response = "";
        result = null;
    }

    @Override
    public List<ChallengeSession> getActiveSessions() {
        List<ChallengeSession> lista = null;
        response = database.getSessions(Integer.toString(ChallengeSessionDAO.STATO_ATTIVO));
        if (response != "null"){
            lista = getSessions(response);
        }
        return lista;
    }

    @Override
    public List<ChallengeSession> getRatingSessions() {
        List<ChallengeSession> lista = null;
        response = database.getSessions(Integer.toString(ChallengeSessionDAO.STATO_RATING));
        if (response != "null"){
            lista = getSessions(response);
        }
        return lista;
    }


    private List<ChallengeSession> getSessions(String response){
        List<ChallengeSession> lista = new ArrayList<ChallengeSession>();
        JSONArray arr;
        ChallengeSession session;
        Challenge challege;
        JSONObject obj;
        URL url;
        try {
            arr = new JSONArray(response);
            for(int i = 0; i < arr.length(); i++) {
                obj = arr.getJSONObject(i);
                URL tmp = MySqlDatabase.getUrl(MySqlDatabase.PHOTO_CHALLENGE_FOLDER);
                String tmp2 = tmp.toString() + "/" + obj.getString("cImage")+MySqlDatabase.photo_extension;
                url = new URL(tmp2);
                DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date = format.parse(obj.getString("expiration"));
                challege = new Challenge(obj.getInt("IDChallenge"),obj.getString("description"),obj.getString("title"),url);
                session = new ChallengeSession(obj.getInt("IDSession"),date,challege);
                String sImage = obj.getString("sImage");
                if(!sImage.equals("null")){
                    session.setImage(new URL(MySqlDatabase.getUrl(MySqlDatabase.PHOTO_CHALLENGE_FOLDER),obj.getString("sImage")+MySqlDatabase.photo_extension));
                }
                lista.add(session);
            }
        } catch (JSONException |MalformedURLException | ParseException  e ) {
            e.printStackTrace();
        }
        return lista;
    }
}
