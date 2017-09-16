package androidlab.fotografando;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

/**
 * Created by Michele Grisafi on 15/09/2017.
 */

public class ActivityProfile extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Intent intent = getIntent();

        ImageButton btnBack = (ImageButton) findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        TextView tvUsername = (TextView) findViewById(R.id.tv_activity_profile_username);
        TextView tvScore = (TextView) findViewById(R.id.tv_activity_profile_stars);
        TextView tvInitial = (TextView) findViewById(R.id.profilePicText);

       // int id = intent.getIntExtra("id");

        tvUsername.setText(intent.getStringExtra("username"));
        tvScore.setText(Integer.toString(intent.getIntExtra("score",0)));
        tvInitial.setText(String.valueOf(Character.toUpperCase(intent.getStringExtra("username").charAt(0))));

    }
}
