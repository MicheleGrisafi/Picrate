package androidlab.fotografando.assets;

import android.content.SharedPreferences;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import androidlab.DB.MySqlDatabase;
import androidlab.DB.Objects.Utente;
import androidlab.fotografando.assets.generalTasks.TaskLogInUtente;
import androidlab.fotografando.assets.generalTasks.TaskUpdateUtente;

/**
 * Created by miki4 on 13/05/2017.
 */

/** Classe usata per gestire dati comuni a tutte le attività **/
abstract public class AppInfo {
    static public final String user_shared_preferences = "user-preferences";
    static public final int costo_seconda_foto = 5;
    /**Inizializzo utente prendendolo dal database **/
    static public void loginUser(String googleKey){
        TaskLogInUtente logIn = new TaskLogInUtente(googleKey);
        logIn.execute();
    }
    /** Ottiene utente loggato nell'app **/
    static public Utente getUtente(){
        Utente user = null;
        SharedPreferences settings = MyApp.getAppContext().getSharedPreferences(user_shared_preferences, 0);
        int id = settings.getInt("id",0);
        if(id != 0){
            String name     = settings.getString("name",null);
            String email    = settings.getString("email",null);
            String google   = settings.getString("google",null);
            int score       = settings.getInt("score",0);
            int money       = settings.getInt("money",0);
            user = new Utente(id,name,email,google,money,score);
        }
        return user;
    }
    /** Aggiorna utente **/
    static public void updateUtente(Utente utente){
        SharedPreferences settings = MyApp.getAppContext().getSharedPreferences(user_shared_preferences, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("name", utente.getUsername());
        editor.putInt("money",utente.getMoney());
        editor.putInt("score",utente.getScore());
        editor.apply();
        TaskUpdateUtente task = new TaskUpdateUtente(utente);
        task.execute();
    }
    static public int dpToPixel(Integer dp){
        final float scale = MyApp.getAppContext().getResources().getDisplayMetrics().density;
        return  (int) (dp * scale + 0.5f);
    }

    /** Ottiene data giornaliera dal database **/
    static public Date getDate(){
        //TODO: creare task separato per getDate
        MySqlDatabase database = new MySqlDatabase();
        String response = database.getDate();
        JSONObject obj = null;
        Date date = null;
        try {
            obj = new JSONObject(response);
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            date = format.parse(obj.getString("date"));
        } catch (JSONException | ParseException e ) {
            e.printStackTrace();
        }
        return date;
    }

}
