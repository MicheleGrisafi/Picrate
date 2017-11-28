package picrate.app.DB.DAO.implementations;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import picrate.app.DB.DAO.UtenteDAO;
import picrate.app.DB.MySqlDatabase;
import picrate.app.DB.Objects.Utente;

/**
 * Created by miki4 on 07/05/2017.
 */

public class UtenteDAO_DB_impl implements UtenteDAO {
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
    public Utente insertUtente(Utente user) {
        result = null;
        response = database.insertUtente(user.getEmail(),user.getGoogleKey(),user.getUsername());
        if (response != "null"){
            user.setId(Integer.parseInt(response));
            result = user;
        }
        return (Utente)result;
    }

    @Override
    public void deleteUtente(Utente user) {

    }


    @Override
    public Utente getUtente(String googleKey) {
        result = null;
        response = database.getUtente(googleKey);
        JSONArray arr;
        if (response != "null"){
            try {
                arr = new JSONArray(response);
                JSONObject obj = arr.getJSONObject(0);
                int IDUser = obj.getInt("IDUser");
                String username = obj.getString("username");
                String mail = obj.getString("mail");
                int money = obj.getInt("money");
                int score = obj.getInt("score");
                result = new Utente(IDUser,username,mail,googleKey,money,score);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return (Utente)result;
    }
    public Utente getUtente(int id) {
        result = null;
        response = database.getUtente(id);
        JSONArray arr;
        if (response != "null"){
            try {
                arr = new JSONArray(response);
                JSONObject obj = arr.getJSONObject(0);
                String username = obj.getString("username");
                String mail = obj.getString("mail");
                int money = obj.getInt("money");
                int score = obj.getInt("score");
                result = new Utente(id,username,null,null,money,score);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return (Utente)result;
    }

    @Override
    public ArrayList<Utente> getTopUsers(){
        result = null;
        ArrayList<Utente> lista;
        JSONArray arr;
        JSONObject obj;
        response = database.getTopUsers();
        if (response != "null"){
            lista = new ArrayList<Utente>();
            try {
                arr = new JSONArray(response);
                //List<String> list = new ArrayList<String>();
                for(int i = 0; i < arr.length(); i++) {
                    obj = arr.getJSONObject(i);
                    Utente user = new Utente(obj.getInt("IDUser"),obj.getString("username"),obj.getInt("score"));
                    lista.add(user);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            result = lista;
        }
        return (ArrayList<Utente>) result;
    }
    @Override
    public Utente updateUtente(Utente user) {
        result = null;
        response = database.updateUtente(Integer.toString(user.getId()),user.getUsername(),Integer.toString(user.getMoney()),Integer.toString(user.getScore()));
        if(response != "null") {
            try {
                JSONObject obj = new JSONObject(response);
                result = user;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return (Utente)result;
    }
}
