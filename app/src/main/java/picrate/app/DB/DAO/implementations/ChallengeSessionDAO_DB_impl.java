package picrate.app.DB.DAO.implementations;

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

import picrate.app.DB.DAO.ChallengeSessionDAO;
import picrate.app.DB.MySqlDatabase;
import picrate.app.DB.Objects.Challenge;
import picrate.app.DB.Objects.ChallengeSession;
import picrate.app.R;
import picrate.app.assets.objects.MyApp;


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
    public ArrayList<ChallengeSession> getActiveSessions() {
        ArrayList<ChallengeSession> lista = null;
        response = database.getSessions(Integer.toString(ChallengeSession.STATO_ACTIVE));
        if (!response.equals("null")){
            lista = getSessions(response);
        }
        return lista;
    }

    @Override
    public ArrayList<ChallengeSession> getRatingSessions() {
        ArrayList<ChallengeSession> lista = null;
        response = database.getSessions(Integer.toString(ChallengeSession.STATO_RATING));
        if (!response.equals("null")){
            lista = getSessions(response);
        }
        return lista;
    }

    @Override
    public ArrayList<ChallengeSession> getClosedSessions() {
        ArrayList<ChallengeSession> lista = null;
        response = database.getSessions(Integer.toString(ChallengeSession.STATO_EXPIRED));
        if (!response.equals("null")){
            lista = getSessions(response);
        }
        return lista;
    }


    private ArrayList<ChallengeSession> getSessions(String response){
        ArrayList<ChallengeSession> lista = new ArrayList<ChallengeSession>();
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
                DateFormat format = new SimpleDateFormat(MyApp.getAppContext().getString(R.string.date_pattern));
                Date date = format.parse(obj.getString("expiration"));
                challege = new Challenge(obj.getInt("IDChallenge"),obj.getString("description"),obj.getString("title"),url);
                session = new ChallengeSession(obj.getInt("IDSession"),date,challege);
                session.setStato(ChallengeSession.STATO_ACTIVE);
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
