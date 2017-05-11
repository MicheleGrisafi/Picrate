package androidlab.DB;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import androidlab.DB.DAO.ChallengeDAO;
import androidlab.DB.DAO.ChallengeSessionDAO;

/**
 * Created by miki4 on 07/05/2017.
 */

public class MySqlDatabase {
    private static final String url_name = "http://192.168.1.133";

    private static final String urlUtente = "/utente";
    private static final String urlFoto = "/foto";
    private static final String urlSession = "/session";

    private static final String urlInsertUtente = "/insertUtente.php";
    private static final String urlGetUtente = "/getUtente.php";
    private static final String urlSetUsername = "/setUsername.php";
    private static final String urlSetMoney = "/setMoney.php";
    private static final String urlGetMoney = "/getMoney.php";
    private static final String urlSetScore = "/setScore.php";
    private static final String urlGetScore = "/getScore.php";

    private static final String urlInsertPhoto = "/insertPhoto.php";

    private static final String urlGetSessions = "/getSessions.php";


    public static final int INSERT_UTENTE = 0;
    public static final int SET_USERNAME = 1;
    public static final int SET_MONEY = 2;
    public static final int GET_MONEY = 3;
    public static final int SET_SCORE = 4;
    public static final int GET_SCORE = 5;
    public static final int GET_UTENTE = 6;
    public static final int INSERT_PHOTO = 7;
    public static final int GET_SESSIONS = 8;

    HttpURLConnection httpURLConnection;
    OutputStream outputStream;
    BufferedWriter bufferedWriter;
    InputStream inputStream;
    BufferedReader bufferedReader;
    String data = "";
    public MySqlDatabase(){

    }

    /********************** OPERAZIONI UTENTE ************************/
    public String insertUtente(String... param){
        data = "";
        try {

            switch (param.length){
                case 2:
                    data =  URLEncoder.encode("email","UTF-8")+"="+ URLEncoder.encode(param[0],"UTF-8") +"&" +
                            URLEncoder.encode("googlekey","UTF-8")+"="+ URLEncoder.encode(param[1],"UTF-8");
                    break;
                case 3:
                    data =  URLEncoder.encode("email","UTF-8")+"="+ URLEncoder.encode(param[0],"UTF-8") +"&" +
                            URLEncoder.encode("googlekey","UTF-8")+"="+ URLEncoder.encode(param[1],"UTF-8") +"&" +
                            URLEncoder.encode("username","UTF-8")+"="+ URLEncoder.encode(param[2],"UTF-8");
                    break;
                default:
                    data =  URLEncoder.encode("email","UTF-8")+"="+ URLEncoder.encode(param[0],"UTF-8") +"&" +
                            URLEncoder.encode("googlekey","UTF-8")+"="+ URLEncoder.encode(param[1],"UTF-8") +"&" +
                            URLEncoder.encode("username","UTF-8")+"="+ URLEncoder.encode(param[2],"UTF-8") +"&" +
                            URLEncoder.encode("score","UTF-8")+"="+ URLEncoder.encode(param[3],"UTF-8") +"&" +
                            URLEncoder.encode("money","UTF-8")+"="+ URLEncoder.encode(param[4],"UTF-8");
                    break;
            }

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return openConnection(data,INSERT_UTENTE);
    }
    public String getUtente(String username){
        data="";
        try {
            data =  URLEncoder.encode("username","UTF-8")+"="+ URLEncoder.encode(username,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return openConnection(data,GET_UTENTE);
    }
    public String setUsername(String id, String username){
        data ="";
        try {
            data =  URLEncoder.encode("email","UTF-8")+"="+ URLEncoder.encode(id,"UTF-8") +"&" +
                    URLEncoder.encode("googleKey","UTF-8")+"="+ URLEncoder.encode(username,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return openConnection(data,SET_USERNAME);
    }
    public String setMoney(String id, String money, String increment){
        data="";
        try {
            data =  URLEncoder.encode("id","UTF-8")+"="+ URLEncoder.encode(id,"UTF-8") +"&" +
                    URLEncoder.encode("money","UTF-8")+"="+ URLEncoder.encode(money,"UTF-8") +"&" +
                    URLEncoder.encode("increment","UTF-8")+"="+ URLEncoder.encode(increment,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return openConnection(data,SET_MONEY);
    }
    public String getMoney(String id){
        data="";
        try {
            data =  URLEncoder.encode("id","UTF-8")+"="+ URLEncoder.encode(id,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return openConnection(data,GET_MONEY);
    }
    public String setScore(String id, String score, String increment){
        data="";
        try {
            data =  URLEncoder.encode("id","UTF-8")+"="+ URLEncoder.encode(id,"UTF-8") +"&" +
                    URLEncoder.encode("score","UTF-8")+"="+ URLEncoder.encode(score,"UTF-8") +"&" +
                    URLEncoder.encode("increment","UTF-8")+"="+ URLEncoder.encode(increment,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return openConnection(data,SET_SCORE);
    }
    public String getScore(String id){
        data="";
        try {
            data =  URLEncoder.encode("id","UTF-8")+"="+ URLEncoder.encode(id,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return openConnection(data,GET_SCORE);
    }

    /********************** OPERAZIONI FOTO ************************/
    public String insertPhoto(String... param){
        data = "";
        try {
            switch (param.length){
                case 3:
                    data =  URLEncoder.encode("owner","UTF-8")+"="+ URLEncoder.encode(param[0],"UTF-8") +"&" +
                            URLEncoder.encode("challenge","UTF-8")+"="+ URLEncoder.encode(param[1],"UTF-8") +"&" +
                            URLEncoder.encode("image","UTF-8")+"="+ URLEncoder.encode(param[2],"UTF-8");
                    break;
                default:
                    data =  URLEncoder.encode("owner","UTF-8")+"="+ URLEncoder.encode(param[0],"UTF-8") +"&" +
                            URLEncoder.encode("challenge","UTF-8")+"="+ URLEncoder.encode(param[1],"UTF-8") +"&" +
                            URLEncoder.encode("image","UTF-8")+"="+ URLEncoder.encode(param[2],"UTF-8")+"&" +
                            URLEncoder.encode("latitudine","UTF-8")+"="+ URLEncoder.encode(param[3],"UTF-8") +"&" +
                            URLEncoder.encode("longitudine","UTF-8")+"="+ URLEncoder.encode(param[4],"UTF-8");
                    break;
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return openConnection(data,INSERT_PHOTO);
    }
    //public String getPhoto(String )

    /********************** OPERAZIONI SESSION ************************/
    public String getSessions(String stato){
        data = "";
        try {
            data =  URLEncoder.encode("stato","UTF-8")+"="+ URLEncoder.encode(stato,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return openConnection(data,GET_SESSIONS);
    }

    private String openConnection(String data, int action){
        String result = "";
        try {
            httpURLConnection = (HttpURLConnection)getUrl(action).openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
            outputStream = httpURLConnection.getOutputStream();
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
            bufferedWriter.write(data);
            bufferedWriter.flush();
            bufferedWriter.close();
            outputStream.close();
            inputStream = httpURLConnection.getInputStream();
            bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));
            String line;
            while((line = bufferedReader.readLine()) != null){
                result += line;
            }
            bufferedReader.close();
            inputStream.close();
            httpURLConnection.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    static URL getUrl(int action){
        URL url = null;
        try{

            switch (action){
                case INSERT_UTENTE:
                    url = new URL(url_name+urlUtente+urlInsertUtente);
                break;
                case SET_USERNAME:
                    url = new URL(url_name+urlUtente+urlSetUsername);
                    break;
                case GET_MONEY:
                    url = new URL(url_name+urlUtente+urlGetMoney);
                    break;
                case SET_MONEY:
                    url = new URL(url_name+urlUtente+urlSetMoney);
                    break;
                case GET_SCORE:
                    url = new URL(url_name+urlUtente+urlGetScore);
                    break;
                case SET_SCORE:
                    url = new URL(url_name+urlUtente+urlSetScore);
                    break;
                case GET_UTENTE:
                    url = new URL(url_name+urlUtente+urlGetUtente);
                    break;
                case INSERT_PHOTO:
                    url = new URL(url_name+urlFoto+urlInsertPhoto);
                    break;
                case GET_SESSIONS:
                    url = new URL(url_name+urlSession+urlGetSessions);
                    break;
            }
            return url;
        }catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }

}
