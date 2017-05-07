package androidlab.DB;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by miki4 on 07/05/2017.
 */

public class MySqlDatabase {
    public static final String url_name = "http://10.0.0.2";


    public MySqlDatabase(){

    }
    public URL insertUtente(){
        URL url = null;
        try {
            url = new URL(url_name + "/something");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }
}
