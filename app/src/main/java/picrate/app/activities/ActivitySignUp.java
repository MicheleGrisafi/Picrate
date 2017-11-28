package picrate.app.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import picrate.app.R;
import picrate.app.assets.objects.AppInfo;

/**
 * Created by miki4 on 26/11/2017.
 */

public class ActivitySignUp extends Activity {
    final Activity activity = this;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        final Intent intent = getIntent();
        final EditText etUsername = (EditText)findViewById(R.id.editText_signup);
        Button btnSignup = (Button)findViewById(R.id.button_signup);
        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = etUsername.getText().toString();
                Boolean valid = true;
                //TODO: username univoco? Allora va controllata l'esistenza dello stesso nel DB
                //TODO: escaping del username
                if(username.length() < 3)
                    valid = false;
                if(valid)
                    AppInfo.signupUser(intent.getStringExtra("googleKey"),intent.getStringExtra("email"),username,activity);
                else
                    Toast.makeText(activity, R.string.username_error, Toast.LENGTH_SHORT).show();
            }
        });

    }
}
