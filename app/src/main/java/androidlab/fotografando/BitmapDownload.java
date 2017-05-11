package androidlab.fotografando;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Michele Grisafi on 11/05/2017.
 */

public abstract class BitmapDownload {
    static public Bitmap getBitmapFromURL(String src) {
        URL url = null;
        try {
            url = new URL(src);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return getImage(url);
    }
    static public Bitmap getBitmapFromURL(URL url) {
        return getImage(url);
    }
    static private Bitmap getImage(URL url){
        Bitmap myBitmap = null;
        try {
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (IOException e) {
            e.printStackTrace();
            return myBitmap;
        }
    }
}
