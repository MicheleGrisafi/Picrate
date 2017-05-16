package androidlab.fotografando.assets;

import android.content.SharedPreferences;
import android.content.res.Resources;
import android.util.TypedValue;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import androidlab.DB.MySqlDatabase;
import androidlab.DB.Objects.ChallengeSession;
import androidlab.DB.Objects.Utente;

/**
 * Created by miki4 on 13/05/2017.
 */

abstract public class AppInfo {
    static public final String user_shared_preferences = "user-preferences";

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
    static public void updateUtente(Utente utente, boolean full){
        SharedPreferences settings = MyApp.getAppContext().getSharedPreferences(user_shared_preferences, 0);
        SharedPreferences.Editor editor = settings.edit();
        if (full) {
            editor.putString("name", utente.getUsername());
            editor.putString("email", utente.getEmail());
            editor.putString("google", utente.getGoogleKey());
        }
        editor.putInt("id",utente.getId());
        editor.putInt("money",utente.getMoney());
        editor.putInt("score",utente.getScore());
        editor.commit();
    }
    static public int dpToPixel(Integer dp){
        final float scale = MyApp.getAppContext().getResources().getDisplayMetrics().density;
        int px  = (int) (dp * scale + 0.5f);
        return px;
    }
    static public Date getDate(){
        MySqlDatabase database = new MySqlDatabase();
        String response = database.getDate();
        JSONObject obj = null;
        Date date = null;
        try {
            obj = new JSONObject(response);
            DateFormat format = new SimpleDateFormat("YYYY-MM-DD HH:MM:SS");
            date = format.parse(obj.getString("date"));
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

}
