package androidlab.fotografando;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidlab.DB.Objects.Utente;
import androidlab.fotografando.assets.SignUpTask;

public class signup_activity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_activity);

        Button btn = (Button)findViewById(R.id.button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new SignUpTask(getApplicationContext()).execute(new Utente("miki426811@gmail.comma","googleKey1"));
            }
        });
    }
}
