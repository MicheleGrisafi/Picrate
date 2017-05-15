package androidlab.fotografando;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
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
import android.widget.ListView;
import android.widget.TabHost;

public class MainActivity extends Activity {
    private String[] mDrawerItems;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }

        mDrawerItems = new String[]{"Profile", "Active Photos", "Logout", "Settings", "Help & Feedback"};
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);

        // TODO: HEADER LISTVIEW
        mDrawerList.setAdapter(new ArrayAdapter<>(this, R.layout.drawer_list_item, mDrawerItems));
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.drawer_open, R.string.drawer_close) {
            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };
        mDrawerLayout.addDrawerListener(mDrawerToggle);

        ImageButton btnDrawer = (ImageButton) findViewById(R.id.btnDrawer);
        btnDrawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawerLayout.openDrawer(Gravity.START);
            }
        });

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
        spec.setIndicator("", getResources().getDrawable(R.drawable.ic_leaderboard_24dp));
        tabHost.addTab(spec);

        /********************* FIRST TAB ************************************/

        final Intent openCamera = new Intent(this,cameraActivity.class);
        /*Button btn2 = (Button) findViewById(R.id.button2);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(openCamera);
            }
        });
        */

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

    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItem(position);
        }
    }

    private void selectItem(int position) {
        // Highlight the selected item, and close the drawer
        mDrawerList.setItemChecked(position, true);
        mDrawerLayout.closeDrawer(mDrawerList);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // If the nav drawer is open, hide action items related to the content view
        //boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
        //menu.findItem(R.id.action_websearch).setVisible(!drawerOpen);
        ConstraintLayout cLayout = (ConstraintLayout) findViewById(R.id.constraint_layout_bar);
        cLayout.setAlpha(0.75f);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Pass the event to ActionBarDrawerToggle, if it returns true, then it has handled the app icon touch event
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
