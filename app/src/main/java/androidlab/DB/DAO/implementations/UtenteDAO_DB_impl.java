package androidlab.DB.DAO.implementations;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import androidlab.DB.DAO.UtenteDAO;
import androidlab.DB.MySqlDatabase;
import androidlab.DB.Objects.Utente;

/**
 * Created by miki4 on 07/05/2017.
 */

public class UtenteDAO_DB_impl implements UtenteDAO {
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
    }

    @Override
    public Utente insertUtente(Utente user) {
        result = null;
        response = database.insertUtente(user.getEmail(),user.getGoogleKey());
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
    public Utente setUsername(Utente user,String username) {
        result = null;
        response = database.setUsername(Integer.toString(user.getId()),username);
        if(Integer.parseInt(response) == 1){
            user.setUsername(username);
            result = user;
        }
        return (Utente)result;
    }

    @Override
    public Utente setMoney(Utente user, int money, boolean increment) {
        result = null;
        response = database.setMoney(Integer.toString(user.getId()),Integer.toString(money),Boolean.toString(increment));
        if (Integer.parseInt(response) == 1){
            user.setMoney(money,increment);
            result = user;
        }
        return (Utente)result;
    }

    @Override
    public Utente getMoney(Utente user) {
        result = null;
        response = database.getMoney(Integer.toString(user.getId()));
        if (response != "null"){
            user.setMoney(Integer.parseInt(response),false);
            result = user;
        }
        return (Utente)result;
    }

    @Override
    public Utente setScore(Utente user, int score, boolean increment) {
        result = null;
        response = database.setMoney(Integer.toString(user.getId()),Integer.toString(score),Boolean.toString(increment));
        if (Integer.parseInt(response) == 1){
            user.setScore(score,increment);
            result = user;
        }
        return (Utente)result;
    }

    @Override
    public Utente getScore(Utente user) {
        result = null;
        response = database.getMoney(Integer.toString(user.getId()));
        if (response != "null"){
            user.setScore(Integer.parseInt(response),false);
            result = user;
        }
        return (Utente)result;
    }

    @Override
    public Utente getUtente(Utente user) {
        result = null;
        response = database.getUtente(user.getUsername());
        if (response != "null"){
            try {
                JSONObject obj = new JSONObject(response);
                int IDUser = obj.getInt("IDUser");
                String username = obj.getString("username");
                String googleKey = obj.getString("googleKey");
                String mail = obj.getString("mail");
                int money = obj.getInt("money");
                int score = obj.getInt("score");
                user = new Utente(IDUser,username,mail,googleKey,money,score);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            result = user;
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

        try {
            JSONObject obj = new JSONObject(response);
            if(!obj.getBoolean("error"))
                result = user;
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return (Utente)result;
    }
}
