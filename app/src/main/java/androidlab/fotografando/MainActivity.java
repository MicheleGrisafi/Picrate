package androidlab.fotografando;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TabHost;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidlab.DB.DAO.UtenteDAO;
import androidlab.DB.DAO.implementations.UtenteDAO_DB_impl;
import androidlab.DB.Objects.ChallengeSession;
import androidlab.DB.Objects.Utente;
import androidlab.fotografando.assets.AppInfo;
import androidlab.fotografando.assets.LoadChallengeSessions;
import androidlab.fotografando.assets.LoadSessionsExpiration;

public class MainActivity extends Activity {
    private int REQUEST_CODE = 0;
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


        /********************* FIRST TAB ************************************/

        final Intent openCamera = new Intent(this,cameraActivity.class);
        /*Button btn2 = (Button) findViewById(R.id.button2);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(openCamera);
            }
        });*/


        Utente michele = new Utente(10,"michele","miki426811@gmail.com","12345678",0,0);
        AppInfo.updateUtente(michele,true);



        Map<Integer,Integer> picturesMap = new HashMap<Integer, Integer>();
        Map<Integer,Integer> expirationMap = new HashMap<Integer, Integer>();
        List<ChallengeSession> challengeSessions = new ArrayList<ChallengeSession>();

        LoadChallengeSessions task = new LoadChallengeSessions(this,(RelativeLayout)findViewById(R.id.relativeLayoutChallenges),challengeSessions,picturesMap,expirationMap);
        task.execute();



        /************************* THIRD TAB ********************************/
        Button btnZoom = (Button) findViewById(R.id.btnZoom);
        btnZoom.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent zoomIntent = new Intent(MainActivity.this, ZoomActivity.class);
                startActivity(new Intent(zoomIntent));
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode ==  REQUEST_CODE){
            switch (requestCode){
                case 0:
                    break;
                case 1:

                    break;
            }
        }
    }

}
