package androidlab.fotografando;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.SparseArray;
import android.util.SparseIntArray;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TabHost;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import androidlab.DB.Objects.ChallengeSession;
import androidlab.DB.Objects.Photo;
import androidlab.DB.Objects.Utente;
import androidlab.fotografando.assets.AppInfo;
import androidlab.fotografando.assets.InsertThePhoto;
import androidlab.fotografando.assets.LoadChallengeSessions;

public class MainActivity extends Activity {

    private int REQUEST_CODE_CAMERA = 0;


    private Toolbar mToolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.materialOrange600));
        }

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle("Challenges");

        //ActivityManager.TaskDescription taskDescription = new ActivityManager.TaskDescription(getResources().getString(R.string.app_name), icon, R.color.materialOrange400);
        //this.setTaskDescription(taskDescription);

        final DrawerLayout mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawerLayout, mToolbar, R.string.drawer_open, R.string.drawer_close);
        mDrawerLayout.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView mDrawerView = (NavigationView) findViewById(R.id.nav_drawer);
        mDrawerView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                // Handle navigation view item clicks here.
                int id = item.getItemId();

                if (id == R.id.nav_profile) {
                    Intent intent = new Intent(MainActivity.this, MyProfileActivity.class);
                    startActivity(new Intent(intent));
                } else if (id == R.id.nav_photos) {

                } else if (id == R.id.nav_notifications) {

                } else if (id == R.id.nav_logout) {

                } else if (id == R.id.nav_settings) {

                } else if (id == R.id.nav_help) {

                }

                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);
                return true;
            }
        });

        final TabHost tabHost = (TabHost) findViewById(R.id.tabHost); //Tabhost = tab manager
        tabHost.setup();    //Inizializzo

        //Tab 1
        TabHost.TabSpec spec = tabHost.newTabSpec("Challenges Tab");    //Una spec è una componente del tabhost, la creo con un identificatore
        spec.setContent(R.id.tabChallenges);    //Setto la mia tab. Volendo potrei impostare una nuova INTENT invece di una tab
        spec.setIndicator("", getResources().getDrawable(R.drawable.challenges_tab_selector)); //Dichiaro l'icona
        tabHost.addTab(spec);   //aggiungo la spec al mio host
        //Tab 1
        spec = tabHost.newTabSpec("Rating Tab");
        spec.setContent(R.id.tabRating);
        spec.setIndicator("", getResources().getDrawable(R.drawable.rating_tab_selector));
        tabHost.addTab(spec);
        //Tab 1
        spec = tabHost.newTabSpec("Ranking Tab");
        spec.setContent(R.id.tabRanking);
        spec.setIndicator("", getResources().getDrawable(R.drawable.leaderboards_tab_selector));
        tabHost.addTab(spec);

        tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                switch (tabId){
                    case "Challenges Tab":
                        mToolbar.setTitle("Challenges");
                        break;
                    case "Rating Tab":
                        mToolbar.setTitle("Rate");
                        break;
                    case "Ranking Tab":
                        mToolbar.setTitle("Leaderboards");
                        break;
                    default:
                        break;
                }
            }
        });

        /********************* FIRST TAB ************************************/


        Utente michele = new Utente(10,"michele","miki426811@gmail.com","12345678",0,0);
        AppInfo.updateUtente(michele,true);


        SparseIntArray expirationMap = new SparseIntArray();
        SparseArray<ArrayList<Integer>> picturesMap = new SparseArray<>();
        List<ChallengeSession> challengeSessions = new ArrayList<ChallengeSession>();
        LoadChallengeSessions task = new LoadChallengeSessions(this,(RelativeLayout)findViewById(R.id.relativeLayoutChallenge),challengeSessions,picturesMap,expirationMap,REQUEST_CODE_CAMERA,this);
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
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    /*@Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // If the nav drawer is open, hide action items related to the content view
        //boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
        //menu.findItem(R.id.action_websearch).setVisible(!drawerOpen);
        ConstraintLayout cLayout = (ConstraintLayout) findViewById(R.id.constraint_layout_bar);
        cLayout.setAlpha(0.75f);
        return super.onPrepareOptionsMenu(menu);
    }*/

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode ==  REQUEST_CODE_CAMERA){
            switch (resultCode){
                case 0:
                    break;
                case 1:
                    Photo foto = new Photo(AppInfo.getUtente().getId(),data.getIntExtra("sessionID",0));
                    InsertThePhoto insertThePhoto = new InsertThePhoto(foto,data.getStringExtra("fileName"),
                            this,(ImageView) findViewById(data.getIntExtra("imageView",0)));
                    insertThePhoto.execute();

                    break;
            }
        }
    }
}
