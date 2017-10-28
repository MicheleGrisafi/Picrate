package androidlab.fotografando.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;

import androidlab.fotografando.R;
import androidlab.fotografando.assets.objects.AppInfo;

/**
 * Created by Michele Grisafi on 15/09/2017.
 */

public class ActivityLogIn extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_activity);

        Button btnLogin = (Button) findViewById(R.id.btnLogIn);

        final Intent intent = new Intent(this, ActivityMain.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        final Activity activity = this;

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppInfo.loginUser("12345678",intent,activity);
            }
        });

        //Inizializzo le impostazioni
        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);
    }
}
