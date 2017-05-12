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
    private static final String urlRating = "/rating";

    private static final String urlInsertUtente = "/insertUtente.php";
    private static final String urlGetUtente = "/getUtente.php";
    private static final String urlSetUsername = "/setUsername.php";
    private static final String urlSetMoney = "/setMoney.php";
    private static final String urlGetMoney = "/getMoney.php";
    private static final String urlSetScore = "/setScore.php";
    private static final String urlGetScore = "/getScore.php";

    private static final String urlInsertPhoto = "/insertPhoto.php";

    private static final String urlGetSessions = "/getSessions.php";

    private static final String urlInsertRating = "/insertRating.php";


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
    public String insertPhoto(String file_path, String owner, String session, String latitudine, String longitudine){
        String reponse_data = "";
        httpURLConnection = null;
        DataOutputStream dos = null;
        DataInputStream inStream = null;

        String existingFileName = file_path;
        String lineEnd = "\r\n";
        String twoHyphens = "--";
        String boundary =  "*****";
        int bytesRead, bytesAvailable, bufferSize;
        byte[] buffer;
        int maxBufferSize = 1*1024*1024;
        try{
            //------------------ CLIENT REQUEST
            File filetoupload = new File(existingFileName);
            FileInputStream fileInputStream = new FileInputStream(filetoupload.getPath());
            // open a URL connection to the Servlet
            URL url = getUrl(INSERT_PHOTO);
            // Open a HTTP connection to the URL
            httpURLConnection = (HttpURLConnection) url.openConnection();
            // Allow Inputs
            httpURLConnection.setDoInput(true);
            // Allow Outputs
            httpURLConnection.setDoOutput(true);
            // Don't use a cached copy.
            httpURLConnection.setUseCaches(false);
            // Use a post method.
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setRequestProperty("Connection", "Keep-Alive");
            httpURLConnection.setRequestProperty("Content-Type", "multipart/form-data;boundary="+boundary);
            dos = new DataOutputStream( httpURLConnection.getOutputStream() );

            dos.writeBytes(twoHyphens + boundary + lineEnd);
            dos.writeBytes("Content-Disposition: form-data; name=\"owner\""+ lineEnd);
            dos.writeBytes(lineEnd);
            dos.writeBytes(owner);
            dos.writeBytes(lineEnd);
            dos.writeBytes(twoHyphens + boundary + lineEnd);
            dos.writeBytes("Content-Disposition: form-data; name=\"challenge\""+ lineEnd);
            dos.writeBytes(lineEnd);
            dos.writeBytes(session);
            dos.writeBytes(lineEnd);
            if (latitudine != ""){
                dos.writeBytes(twoHyphens + boundary + lineEnd);
                dos.writeBytes("Content-Disposition: form-data; name=\"latitudine\""+ lineEnd);
                dos.writeBytes(lineEnd);
                dos.writeBytes(latitudine);
                dos.writeBytes(lineEnd);
                dos.writeBytes(twoHyphens + boundary + lineEnd);
                dos.writeBytes("Content-Disposition: form-data; name=\"longitudine\""+ lineEnd);
                dos.writeBytes(lineEnd);
                dos.writeBytes(longitudine);
                dos.writeBytes(lineEnd);
            }


            dos.writeBytes(twoHyphens + boundary + lineEnd);
            dos.writeBytes("Content-Disposition: form-data; name=\"image\";filename=\""+filetoupload.getName()+"\"" + lineEnd); // uploaded_file_name is the Name of the File to be uploaded
            dos.writeBytes(twoHyphens + boundary + lineEnd);


            bytesAvailable = fileInputStream.available();
            bufferSize = Math.min(bytesAvailable, maxBufferSize);
            buffer = new byte[bufferSize];
            bytesRead = fileInputStream.read(buffer, 0, bufferSize);
            while (bytesRead > 0){
                dos.write(buffer, 0, bufferSize);
                bytesAvailable = fileInputStream.available();
                bufferSize = Math.min(bytesAvailable, maxBufferSize);
                bytesRead = fileInputStream.read(buffer, 0, bufferSize);
            }
            dos.writeBytes(lineEnd);
            dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);
            fileInputStream.close();
            dos.flush();
            dos.close();
        }
        catch (MalformedURLException e){
            e.printStackTrace();
        }
        catch (IOException e){
            e.printStackTrace();
        }
        //------------------ read the SERVER RESPONSE
        try {
            inputStream = httpURLConnection.getInputStream();
            bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));
            String line;
            while((line = bufferedReader.readLine()) != null){
                reponse_data += line;
            }
            bufferedReader.close();
            inputStream.close();
            httpURLConnection.disconnect();
        }
        catch (IOException e){
            e.printStackTrace();
        }
        return reponse_data;
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

    /********************** CONNESSIONE  ******************************/
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
                case INSERT_RATING:
                    url = new URL(url_name+urlRating+urlInsertRating);
                    break;
            }
            return url;
        }catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }

    /*
     public String insertPhoto(String file_path, String owner, String session, String latitudine, String longitudine){
        String reponse_data = "";
        HttpURLConnection conn = null;
        DataOutputStream dos = null;
        DataInputStream inStream = null;
        String existingFileName = file_path;
        String lineEnd = "\r\n";
        String twoHyphens = "--";
        String boundary =  "*****";
        int bytesRead, bytesAvailable, bufferSize;
        byte[] buffer;
        int maxBufferSize = 1*1024*1024;
        try{
            //------------------ CLIENT REQUEST
            File filetoupload = new File(existingFileName);
            FileInputStream fileInputStream = new FileInputStream(filetoupload.getPath());
            // open a URL connection to the Servlet
            URL url = getUrl(INSERT_PHOTO);
            // Open a HTTP connection to the URL
            conn = (HttpURLConnection) url.openConnection();
            // Allow Inputs
            conn.setDoInput(true);
            // Allow Outputs
            conn.setDoOutput(true);
            // Don't use a cached copy.
            conn.setUseCaches(false);
            // Use a post method.
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Connection", "Keep-Alive");
            conn.setRequestProperty("Content-Type", "multipart/form-data;boundary="+boundary);
            dos = new DataOutputStream( conn.getOutputStream() );

            dos.writeBytes(twoHyphens + boundary + lineEnd);
            dos.writeBytes("Content-Disposition: form-data; name=\"owner\""+ lineEnd);
            dos.writeBytes(lineEnd);
            dos.writeBytes(owner);
            dos.writeBytes(lineEnd);
            dos.writeBytes(twoHyphens + boundary + lineEnd);
            dos.writeBytes("Content-Disposition: form-data; name=\"challenge\""+ lineEnd);
            dos.writeBytes(lineEnd);
            dos.writeBytes(session);
            dos.writeBytes(lineEnd);
            if (latitudine != ""){
                dos.writeBytes(twoHyphens + boundary + lineEnd);
                dos.writeBytes("Content-Disposition: form-data; name=\"latitudine\""+ lineEnd);
                dos.writeBytes(lineEnd);
                dos.writeBytes(latitudine);
                dos.writeBytes(lineEnd);
                dos.writeBytes(twoHyphens + boundary + lineEnd);
                dos.writeBytes("Content-Disposition: form-data; name=\"longitudine\""+ lineEnd);
                dos.writeBytes(lineEnd);
                dos.writeBytes(longitudine);
                dos.writeBytes(lineEnd);
            }


            dos.writeBytes(twoHyphens + boundary + lineEnd);
            dos.writeBytes("Content-Disposition: form-data; name=\"image\";filename=\""+filetoupload.getName()+"\"" + lineEnd); // uploaded_file_name is the Name of the File to be uploaded
            dos.writeBytes(twoHyphens + boundary + lineEnd);


            bytesAvailable = fileInputStream.available();
            bufferSize = Math.min(bytesAvailable, maxBufferSize);
            buffer = new byte[bufferSize];
            bytesRead = fileInputStream.read(buffer, 0, bufferSize);
            while (bytesRead > 0){
                dos.write(buffer, 0, bufferSize);
                bytesAvailable = fileInputStream.available();
                bufferSize = Math.min(bytesAvailable, maxBufferSize);
                bytesRead = fileInputStream.read(buffer, 0, bufferSize);
            }
            dos.writeBytes(lineEnd);
            dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);
            fileInputStream.close();
            dos.flush();
            dos.close();
        }
        catch (MalformedURLException e){
            e.printStackTrace();
        }
        catch (IOException e){
            e.printStackTrace();
        }
        //------------------ read the SERVER RESPONSE
        try {
            inStream = new DataInputStream ( conn.getInputStream() );
            String str;

            while (( str = inStream.readLine()) != null){

                reponse_data+=str;
            }
            inStream.close();
        }
        catch (IOException e){
            e.printStackTrace();
        }
        return reponse_data;
    }
     */

}
