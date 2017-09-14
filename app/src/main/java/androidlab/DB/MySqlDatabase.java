package androidlab.DB;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
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
import java.nio.DoubleBuffer;

import androidlab.DB.DAO.ChallengeDAO;
import androidlab.DB.DAO.ChallengeSessionDAO;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by miki4 on 07/05/2017.
 */

public class MySqlDatabase {


    private static final String url_name = "http://fotografandoapp.altervista.org";
    private static final String fotoUtente_folder = "/picturesUsers";

    private static final String urlUtente = "/utente";
    private static final String urlFoto = "/foto";
    private static final String urlSession = "/session";
    private static final String urlRating = "/ratings";
    private static final String urlUtilities = "/utilities";

    private static final String urlInsertUtente = "/insertUtente.php";
    private static final String urlGetUtente = "/getUtente.php";
    private static final String urlSetUsername = "/setUsername.php";
    private static final String urlSetMoney = "/setMoney.php";
    private static final String urlGetMoney = "/getMoney.php";
    private static final String urlSetScore = "/setScore.php";
    private static final String urlGetScore = "/getScore.php";
    private static final String urlGetTopUsers = "/getTopUsers.php";
    private static final String urlUpdateUtente = "/updateUtente.php";

    private static final String urlInsertPhoto = "/insertPhoto.php";
    private static final String urlGetPhoto = "/getPhoto.php";
    private static final String urlDeletePhoto = "/deletePhoto.php";
    private static final String urlGetRatingPhotos = "/getRatingPhotos.php";

    private static final String urlGetSessions = "/getSessions.php";

    private static final String urlInsertRating = "/insertRating.php";
    private static final String urlGetRatings = "/getRatings.php";

    private static final String urlGetDate = "/getDate.php";


    public static final int INSERT_UTENTE = 0;
    public static final int SET_USERNAME = 1;
    public static final int SET_MONEY = 2;
    public static final int GET_MONEY = 3;
    public static final int SET_SCORE = 4;
    public static final int GET_SCORE = 5;
    public static final int GET_UTENTE = 6;
    public static final int INSERT_PHOTO = 7;
    public static final int GET_SESSIONS = 8;
    public static final int INSERT_RATING = 9;
    public static final int GET_DATE = 10;
    public static final int GET_PHOTO = 11;
    public static final int PHOTO_USER_FOLDER = 12;
    public static final int DELETE_PHOTO = 13;
    public static final int GET_RATINGS = 14;
    public static final int GET_RATING_PHOTOS = 15;
    public static final int GET_TOP_USERS = 16;
    public static final int UPDATE_UTENTE = 17;


    private HttpURLConnection httpURLConnection;
    private OutputStream outputStream;
    private BufferedWriter bufferedWriter;
    private InputStream inputStream;
    private BufferedReader bufferedReader;
    private String data = "";
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
    public String getUtente(String googleKey){
        data="";
        try {
            data =  URLEncoder.encode("googleKey","UTF-8")+"="+ URLEncoder.encode(googleKey,"UTF-8");
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
    public String getTopUsers(){
        data="";
        return openConnection(data,GET_TOP_USERS);
    }
    public String updateUtente(String id, String username, String money, String score){
        data = "";
        try {
            data =  URLEncoder.encode("id","UTF-8")+"="+ URLEncoder.encode(id,"UTF-8") +"&" +
                    URLEncoder.encode("username","UTF-8")+"="+ URLEncoder.encode(username,"UTF-8") +"&" +
                    URLEncoder.encode("score","UTF-8")+"="+ URLEncoder.encode(score,"UTF-8") +"&" +
                    URLEncoder.encode("money","UTF-8")+"="+ URLEncoder.encode(money,"UTF-8");

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return openConnection(data,UPDATE_UTENTE);
    }

    /********************** OPERAZIONI FOTO ************************/
    public String insertPhoto(String file_path, String owner, String session, String latitudine, String longitudine){
        File image = new File(file_path);
        String result = null;
        try {
            final MediaType MEDIA_TYPE_JPG = MediaType.parse("image/jpg");
            RequestBody req = null;
            if(latitudine != "0.0"){
                req = new MultipartBody.Builder().setType(MultipartBody.FORM)
                        .addFormDataPart("owner", owner)
                        .addFormDataPart("challenge", session)
                        .addFormDataPart("latitudine", latitudine)
                        .addFormDataPart("longitudine", longitudine)
                        .addFormDataPart("image","profile.jpg", RequestBody.create(MEDIA_TYPE_JPG, image))
                        .build();
            }else{
                req = new MultipartBody.Builder().setType(MultipartBody.FORM)
                        .addFormDataPart("owner", owner)
                        .addFormDataPart("challenge", session)
                        .addFormDataPart("image","profile.png", RequestBody.create(MEDIA_TYPE_JPG, image))
                        .build();
            }

            Request request = new Request.Builder()
                    .url(getUrl(INSERT_PHOTO))
                    .post(req)
                    .build();

            OkHttpClient client = new OkHttpClient();
            Response response = client.newCall(request).execute();
            result=  response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
    public String getPhoto(String utente, String session){
        data="";
        try {
            data =  URLEncoder.encode("utente","UTF-8")+"="+ URLEncoder.encode(utente,"UTF-8") + "&" +
                    URLEncoder.encode("session","UTF-8")+"="+ URLEncoder.encode(session,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return openConnection(data,GET_PHOTO);
    }
    public String deletePhoto(String photoId){
        data="";
        try {
            data =  URLEncoder.encode("id","UTF-8")+"="+ URLEncoder.encode(photoId,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return openConnection(data,DELETE_PHOTO);
    }
    public String getRatingPhotos(String utente){
        data="";
        try {
            data =  URLEncoder.encode("user","UTF-8")+"="+ URLEncoder.encode(utente,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return openConnection(data,GET_RATING_PHOTOS);
    }
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

    /********************** OPERAZIONI RATING ************************/
    public String insertRating(String user, String foto, String voto, String segnalazione){
        data="";
        try {
            data =  URLEncoder.encode("user","UTF-8")+"="+ URLEncoder.encode(user,"UTF-8") +"&" +
                    URLEncoder.encode("foto","UTF-8")+"="+ URLEncoder.encode(foto,"UTF-8") +"&" +
                    URLEncoder.encode("segnalazione","UTF-8")+"="+ URLEncoder.encode(segnalazione,"UTF-8") +"&" +
                    URLEncoder.encode("voto","UTF-8")+"="+ URLEncoder.encode(voto,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return openConnection(data,INSERT_RATING);
    }

    public String getRatings(String user){
        data = "";
        try {
            data =  URLEncoder.encode("user","UTF-8")+"="+ URLEncoder.encode(user,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return openConnection(data,GET_RATINGS);
    }
    /********************** OPERAZIONI UTILITIES ************************/
    public String getDate(){
        data = "";
        return openConnection(data,GET_DATE);
    }
    /********************** CONNESSIONE  ******************************/
    private String openConnection(String data, int action){
        String result = "";
        try {
            httpURLConnection = (HttpURLConnection)getUrl(action).openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
            if (data != "") {
                outputStream = httpURLConnection.getOutputStream();
                bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
            }
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

    static public URL getUrl(int action){
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
                case INSERT_RATING:
                    url = new URL(url_name+urlRating+urlInsertRating);
                    break;
                case GET_RATINGS:
                    url = new URL(url_name+urlRating+urlGetRatings);
                    break;
                case GET_DATE:
                    url = new URL(url_name+urlUtilities+urlGetDate);
                    break;
                case GET_PHOTO:
                    url = new URL(url_name+urlFoto+urlGetPhoto);
                    break;
                case GET_RATING_PHOTOS:
                    url = new URL(url_name+urlFoto+urlGetRatingPhotos);
                    break;
                case DELETE_PHOTO:
                    url = new URL(url_name+urlFoto+urlDeletePhoto);
                    break;
                case PHOTO_USER_FOLDER:
                    url = new URL(url_name+fotoUtente_folder);
                    break;
                case GET_TOP_USERS:
                    url = new URL(url_name+urlUtente+urlGetTopUsers);
                    break;
                case UPDATE_UTENTE:
                    url = new URL(url_name+urlUtente+urlUpdateUtente);
                    break;
            }
            return url;
        }catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }


}
