package picrate.app.assets.objects;

import android.app.Activity;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.widget.Adapter;

import com.google.android.gms.auth.api.signin.GoogleSignInClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Dictionary;
import java.util.HashMap;

import picrate.app.DB.MySqlDatabase;
import picrate.app.DB.Objects.Utente;
import picrate.app.activities.ActivityNotifications;
import picrate.app.activities.ActivitySettings;
import picrate.app.assets.tasks.TaskLogInUtente;
import picrate.app.assets.tasks.TaskSignUp;
import picrate.app.assets.tasks.TaskUpdateUtente;

/**
 * Created by miki4 on 13/05/2017.
 * Classe statica per la gestione di impostazioni, variabili globaili e dati utili a tutte le activities
 */

/** Classe usata per gestire dati comuni a tutte le attività **/
abstract public class AppInfo {
    /** Shared preferences per il salvataggio dell'utente **/
    static public final String user_shared_preferences = "user-preferences";

    /** Costo per l'aggiunta di una seconda foto alla challenge **/
    static public final int costo_seconda_foto = 40;
    /** Retribuzione per una votazione **/
    static public final int retribuzione_votazione = 1;
    /** Costo per il filtro grayscale **/
    static public final int filter_grayscale = 20;
    /** Costo per il filtro gotham **/
    static public final int filter_gotham = 10;
    /** Costo per il filtro olio **/
    static public final int filter_oil = 30;
    /** Costo per il filtro block **/
    static public final int filter_block = 10;
    /** Costo per il filtro sfocatura **/
    static public final int filter_blur = 10;
    /** Costo per il filtro HDR **/
    static public final int filter_hdr = 30;
    /** Costo per il filtro seppia **/
    static public final int filter_seppia = 30;
    /** Costo per il filtro sharpen **/
    static public final int filter_sharpen = 20;
    /** Costo per il filtro disegno **/
    static public final int filter_sketch = 30;
    /** Costo per il filtro bagliore **/
    static public final int filter_glow = 20;

    /** Impostazione per il salvataggio delle foto in galleria **/
    static public final int SAVE_PHOTOS = 0;
    /** Impostazione per la notifica delle nuove challenge **/
    static public final int NOTIFY_NEW_CHALLENGE = 1;
    /** Impostazione per la notifica della scedenza delle challenge **/
    static public final int NOTIFY_CHALLENGE_EXPIRATION = 2;
    /** Impostazione per l'uso esclusivo del WIFI nelle notifiche **/
    static public final int NOTIFICATION_WIFI = 3;
    /** Intervallo di tempo per l'update delle challenge **/
    static public final int NOTIFY_NEW_CHALLENGE_TIMER = 3600000;

    /** Account google, necessario per il logout **/
    static public GoogleSignInClient client;

    /** Id del Job per l'update delle challenge disponibili **/
    static public final int JOB_REFRESH_CHALLENGES = 0;
    /** Id del Job per la notifica in caso di scadenza della challenge**/
    static public final int JOB_EXPIRATION_CHALLENGES = 1;

    /** Array degli adapters dell'applicazione, usato per aggiornare i dati all'interno dei jobservices**/
    static public SparseArray<RecyclerView.Adapter> adapters = new SparseArray<>();
    /** Adapter delle challenges nella FragmentChallenge **/
    static public final int ADAPTER_CHALLENGES = 0;


    /**
     * Effettua il login dell'utente specificato
     * @param activity activity chiamante
     * @param googleKey chiave google ricevuta dai servizi google
     * @param email email dell'utente in questione
     */
    static public void loginUser( Activity activity, String googleKey,String email){
        TaskLogInUtente logIn = new TaskLogInUtente(activity,googleKey,email);
        logIn.execute();
    }

    /**
     * Effettua la registrazione dell'utente nel DB
     * @param googleKey chiave ottenuta tramite i servizi google
     * @param email email di google dell'utente
     * @param username username scelto dall'utente
     * @param activity attività chiamante
     */
    static public void signupUser(String googleKey,String email,String username, Activity activity){
        TaskSignUp task = new TaskSignUp(username,email,activity,googleKey);
        task.execute();
    }

    /**
     * Ottiene l'utente loggato nell'app
     * @return utente loggato
     */
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

    /**
     * DA IMPLEMENTARE: Ottiene l'utente desiderato.
     * @param id id dell'utente desiderato
     * @return utente desiderato
     */
    static public Utente getUtente(int id){
        Utente user = null;
        return user;
    }

    /**
     * Aggiorna l'utente nelle shared Preferences
     * @param utente utente aggiornato
     */
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

    /**
     * Converte i dp a px
     * @param dp grandezza in dp da convertire
     * @return px corrispondenti ai dp specificati
     */
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

    /**
     * Metodo per ottenere le impostazioni dell'applicazione.
     * @param setting setting desiderata, identificata tramite una costante
     * @return setting desiderata; va effettuato il casting a seconda del tipo aspettato
     */
    static public Object getSetting(int setting){
        Object result = null;
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(MyApp.getAppContext());
        switch (setting){
            case SAVE_PHOTOS:
                result = sharedPref.getBoolean(ActivitySettings.KEY_SAVE_PICTURES, false);
                break;
            case NOTIFY_NEW_CHALLENGE:
                result = sharedPref.getBoolean(ActivityNotifications.KEY_NOTIFY_NEW_CHALLENGE, false);
                break;
            case NOTIFY_CHALLENGE_EXPIRATION:
                result = sharedPref.getBoolean(ActivityNotifications.KEY_NOTIFY_CHALLENGE_EXPIRATION, false);
                break;
            case NOTIFICATION_WIFI:
                result = sharedPref.getBoolean(ActivityNotifications.KEY_NOTIFICATION_WIFI, false);
                break;
        }
        return result;
    }

}
