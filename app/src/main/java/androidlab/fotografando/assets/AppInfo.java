package androidlab.fotografando.assets;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import androidlab.DB.DAO.UtenteDAO;
import androidlab.DB.DAO.implementations.UtenteDAO_DB_impl;
import androidlab.DB.MySqlDatabase;
import androidlab.DB.Objects.Utente;
import androidlab.fotografando.assets.generalTasks.TaskLogInUtente;
import androidlab.fotografando.assets.generalTasks.TaskUpdateUtente;
import androidlab.fotografando.assets.settings.ActivitySettings;

/**
 * Created by miki4 on 13/05/2017.
 */

/** Classe usata per gestire dati comuni a tutte le attività **/
abstract public class AppInfo {
    static public final String user_shared_preferences = "user-preferences";
    static public final String settings_shared_preferences = "settings-preferences";

    /** COSTI VARI **/
    static public final int costo_seconda_foto = 40;
    static public final int retribuzione_votazione = 1;

    static public final int filter_grayscale = 20;
    static public final int filter_gotham = 10;
    static public final int filter_oil = 30;
    static public final int filter_block = 10;
    static public final int filter_blur = 10;
    static public final int filter_hdr = 30;
    static public final int filter_seppia = 30;
    static public final int filter_sharpen = 20;
    static public final int filter_sketch = 30;
    static public final int filter_glow = 20;

    /** SETTINGS **/
    static public final int SAVE_PHOTOS = 0;


    /**Inizializzo utente prendendolo dal database **/
    static public void loginUser(String googleKey, Intent intent, Activity activity){
        TaskLogInUtente logIn = new TaskLogInUtente(googleKey,intent,activity);
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
    static public Utente getUtente(int id){
        Utente user = null;
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

    static public Object getSetting(int setting){
        Object result = null;
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(MyApp.getAppContext());
        switch (setting){
            case SAVE_PHOTOS:
                result = sharedPref.getBoolean(ActivitySettings.KEY_SAVE_PICTURES, false);
                break;
        }
        return result;
    }
}
