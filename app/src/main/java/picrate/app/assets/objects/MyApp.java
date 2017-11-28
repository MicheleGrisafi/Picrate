package picrate.app.assets.objects;

import android.app.Application;
import android.content.Context;

/**
 * Created by miki4 on 13/05/2017.
 */

public class MyApp extends Application {
    private static Context context;
    public void onCreate(){
        super.onCreate();
        MyApp.context = getApplicationContext();
    }
    public static Context getAppContext() {
        return MyApp.context;
    }
}
