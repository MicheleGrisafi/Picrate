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
import androidlab.fotografando.BitmapDownload;

/**
 * Created by Michele Grisafi on 10/05/2017.
 */

public class ChallengeSessionDAO_DB_impl implements ChallengeSessionDAO {
    MySqlDatabase database;
    Object result;
    String response;
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
        JSONArray arr = null;
        ChallengeSession session = null;
        Challenge challege = null;
        JSONObject obj = null;
        URL url = null;
        try {
            arr = new JSONArray(response);
            List<String> list = new ArrayList<String>();
            for(int i = 0; i < arr.length(); i++) {
                obj = arr.getJSONObject(i);
                url = new URL(obj.getString("cImage"));
                DateFormat format = new SimpleDateFormat("YYYY-MM-DD HH:MM:SS");
                Date date = format.parse(obj.getString("expiration"));
                challege = new Challenge(obj.getInt("IDChallenge"),obj.getString("description"),obj.getString("title"),url, BitmapDownload.getBitmapFromURL(url));
                session = new ChallengeSession(obj.getInt("IDSession"),date,challege);
                lista.add(session);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return lista;
    }
}
