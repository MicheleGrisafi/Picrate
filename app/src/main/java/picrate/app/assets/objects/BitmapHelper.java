package androidlab.app.assets.objects;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by miki4 on 30/09/2017.
 */

public abstract class BitmapHelper {

    static public Bitmap fileToBitmap(File file){
        Bitmap result = null;
        if(file.exists()){
            final BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPreferredConfig = Bitmap.Config.RGB_565;
            result = BitmapFactory.decodeFile(file.getAbsolutePath(),options);
        }
        return result;
    }
    static public boolean deleteFile(File file){
        boolean res;
        if(!file.delete()){
            res = false;
        }else
            res = true;
        return  res;
    }
    static public void bitmapToFile(Bitmap bitmap, String name){
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(name);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 50, out);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //mImage.close();
            try {
                if (out != null) {
                    out.close();
                    out = null;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
