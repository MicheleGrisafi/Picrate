package androidlab.fotografando;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TabHost;

import com.google.android.gms.maps.model.LatLng;

import java.io.ByteArrayOutputStream;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.materialOrange600));
        }

        TabHost tabHost = (TabHost) findViewById(R.id.tabHost); //Tabhost = tab manager
        tabHost.setup();    //Inizializzo

        //Tab 1
        TabHost.TabSpec spec = tabHost.newTabSpec("Challenges Tab");    //Una spec è una componente del tabhost, la creo con un identificatore
        spec.setContent(R.id.tabChallenges);    //Setto la mia tab. Volendo potrei impostare una nuova INTENT invece di una tab
        spec.setIndicator("", getResources().getDrawable(R.drawable.ic_check_box_black_24dp)); //Dichiaro l'icona
        tabHost.addTab(spec);   //aggiungo la spec al mio host
        //Tab 1
        spec = tabHost.newTabSpec("Rating Tab");
        spec.setContent(R.id.tabRating);
        spec.setIndicator("", getResources().getDrawable(R.drawable.ic_star_black_24dp));
        tabHost.addTab(spec);
        //Tab 1
        spec = tabHost.newTabSpec("Ranking Tab");
        spec.setContent(R.id.tabRanking);
        spec.setIndicator("", getResources().getDrawable(R.drawable.ic_star_black_24dp));
        tabHost.addTab(spec);

        final Intent openCamera = new Intent(this,camera.class);
        Button btn2 = (Button) findViewById(R.id.button2);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(openCamera);
            }
        });

        Button btnMap = (Button) findViewById(R.id.btnMap);
        btnMap.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent mapIntent = new Intent(MainActivity.this, MapsActivity.class);
                mapIntent.putExtra("latLng", new LatLng(-34, 151));
                Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.beautiful_photography);
                ByteArrayOutputStream bs = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 50, bs);
                mapIntent.putExtra("photo", bs.toByteArray());
                startActivity(new Intent(mapIntent));
            }
        });
    }

}
