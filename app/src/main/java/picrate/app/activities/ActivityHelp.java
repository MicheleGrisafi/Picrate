package picrate.app.activities;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import picrate.app.R;

/**
 * Created by Michele Grisafi on 16/12/2017.
 */

public class ActivityHelp extends Activity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        ImageButton btnBack = (ImageButton) findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        Button sendemail = (Button)findViewById(R.id.button_activity_help);
        sendemail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                        "mailto","michele.grisafi@studenti.unitn.it", null));
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Help and Feedback");
                startActivity(Intent.createChooser(emailIntent, "Send email..."));
            }
        });
    }

}
