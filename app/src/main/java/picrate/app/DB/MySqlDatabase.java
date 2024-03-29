package picrate.app.DB;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
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


    private static final String url_name                    = "http://fotografandoapp.altervista.org";
    private static final String fotoUtente_folder           = "/picturesUsers";
    private static final String fotoChallenge_folder        = "/picturesChallenge";
    public static final String photo_extension              = ".jpg";

    private static final String urlUtente                   = "/utente";
    private static final String urlFoto                     = "/foto";
    private static final String urlSession                  = "/session";
    private static final String urlRating                   = "/ratings";
    private static final String urlUtilities                = "/utilities";
    private static final String urlMedals                   = "/medals";

    private static final String urlInsertUtente             = "/insertUtente.php";
    private static final String urlGetUtente                = "/getUtente.php";
    private static final String urlGetTopUsers              = "/getTopUsers.php";
    private static final String urlUpdateUtente             = "/updateUtente.php";
    private static final String urlCheckUsername            = "/checkUsername.php";

    private static final String urlInsertPhoto              = "/insertPhoto.php";
    private static final String urlGetPhoto                 = "/getPhoto.php";
    private static final String urlDeletePhoto              = "/deletePhoto.php";
    private static final String urlGetRatingPhotos          = "/getRatingPhotos.php";

    private static final String urlGetSessions              = "/getSessions.php";

    private static final String urlInsertRating             = "/insertRating.php";
    private static final String urlGetRatings               = "/getRatings.php";

    private static final String urlGetDate                  = "/getDate.php";

    private static final String urlgetUserMedals            = "/getUserMedals.php";
    private static final String urlgetSessionMedals         = "/getSessionMedals.php";


    public static final int INSERT_UTENTE           = 0;
    public static final int GET_UTENTE              = 1;
    public static final int INSERT_PHOTO            = 2;
    public static final int GET_SESSIONS            = 3;
    public static final int INSERT_RATING           = 4;
    public static final int GET_DATE                = 5;
    public static final int GET_PHOTO               = 6;
    public static final int PHOTO_USER_FOLDER       = 7;
    public static final int DELETE_PHOTO            = 8;
    public static final int GET_RATINGS             = 9;
    public static final int GET_RATING_PHOTOS       = 10;
    public static final int GET_TOP_USERS           = 11;
    public static final int UPDATE_UTENTE           = 12;
    public static final int PHOTO_CHALLENGE_FOLDER  = 13;
    public static final int GET_USER_MEDALS         = 14;
    public static final int GET_SESSION_MEDALS      = 15;
    public static final int CHECK_USERNAME          = 16;


    private HttpURLConnection httpURLConnection;
    private OutputStream outputStream;
    private BufferedWriter bufferedWriter;
    private InputStream inputStream;
    private BufferedReader bufferedReader;
    private String data = "";
    public MySqlDatabase(){

    }

    /********************** OPERAZIONI UTENTE ************************/
    public String insertUtente(String email,String googleKey, String username){
        data = "";
        try {
            data =  URLEncoder.encode("email","UTF-8")+"="+ URLEncoder.encode(email,"UTF-8") +"&" +
                    URLEncoder.encode("googlekey","UTF-8")+"="+ URLEncoder.encode(googleKey,"UTF-8") +"&" +
                    URLEncoder.encode("username","UTF-8")+"="+ URLEncoder.encode(username,"UTF-8");
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
    public String getUtente(int id){
        data="";
        try {
            data =  URLEncoder.encode("id","UTF-8")+"="+ URLEncoder.encode(Integer.toString(id),"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return openConnection(data,GET_UTENTE);
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
    public String checkUsername(String username){
        data = "";
        try {
            data =  URLEncoder.encode("username","UTF-8")+"="+ URLEncoder.encode(username,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return openConnection(data,CHECK_USERNAME);
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
    /********************** OPERAZIONI MEDALS ************************/
    public String getUserMedals(String user){
        data = "";
        try {
            data =  URLEncoder.encode("user","UTF-8")+"="+ URLEncoder.encode(user,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return openConnection(data,GET_USER_MEDALS);
    }
    public String getSessionMedals(String session){
        data = "";
        try {
            data =  URLEncoder.encode("session","UTF-8")+"="+ URLEncoder.encode(session,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return openConnection(data,GET_SESSION_MEDALS);
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
                case PHOTO_CHALLENGE_FOLDER:
                    url = new URL(url_name+fotoChallenge_folder);
                    break;
                case GET_TOP_USERS:
                    url = new URL(url_name+urlUtente+urlGetTopUsers);
                    break;
                case UPDATE_UTENTE:
                    url = new URL(url_name+urlUtente+urlUpdateUtente);
                    break;
                case GET_USER_MEDALS:
                    url = new URL(url_name+urlMedals+urlgetUserMedals);
                    break;
                case GET_SESSION_MEDALS:
                    url = new URL(url_name+urlMedals+urlgetSessionMedals);
                    break;
                case CHECK_USERNAME:
                    url = new URL(url_name+urlUtente+urlCheckUsername);
                    break;
            }
        }catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }


}
