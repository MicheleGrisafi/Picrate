package androidlab.fotografando;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidlab.fotografando.assets.AppInfo;

/**
 * Created by Cate on 22/05/2017.
 */

public class MyProfileActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.materialOrange600));
        }

        ImageButton btnBack = (ImageButton) findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        TextView tvUsername = (TextView) findViewById(R.id.username);
        // nome utente
        tvUsername.setText(AppInfo.getUtente().getUsername());

        TextView tvStars = (TextView) findViewById(R.id.stars);
        // stelle possedute dall'utente
        tvStars.setText(Integer.toString(AppInfo.getUtente().getScore()));

        TextView tvCurrency = (TextView) findViewById(R.id.currency);
        // monete possedute dall'utente
        tvCurrency.setText(Integer.toString(AppInfo.getUtente().getMoney()));

        ImageView iv = (ImageView) findViewById(R.id.profilePicCircle);
        // corrisponde al colore scelto dall'utente (oppure implementare immagine profilo)
        iv.setColorFilter(R.color.materialBlue500);

        TextView tvPic = (TextView) findViewById(R.id.profilePicText);
        // la lettera deve corrispondere al primo carattere del nome utente
        tvPic.setText(String.valueOf(Character.toUpperCase(AppInfo.getUtente().getUsername().charAt(0))));
    }
}
