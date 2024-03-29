package picrate.app.activities;

import android.animation.Animator;
import android.app.Activity;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;

import picrate.app.DB.Objects.ChallengeSession;
import picrate.app.DB.Objects.Photo;
import picrate.app.DB.Objects.Utente;
import picrate.app.R;
import picrate.app.assets.listeners.OnClickListenerMedal;
import picrate.app.assets.objects.AppInfo;
import picrate.app.assets.tasks.TaskInsertThePhoto;
import picrate.app.assets.tasks.TaskLoadSessionImage;
import picrate.app.assets.views.ImageViewChallenge;
import picrate.app.fragments.FragmentTabChallenge;
import picrate.app.fragments.FragmentTabLeadeboard;
import picrate.app.fragments.FragmentTabRating;

public class ActivityMain extends FragmentActivity  {
    public int REQUEST_CODE_CAMERA = 0;
    private Toolbar mToolbar;
    private static Animator mCurrentAnimator;
    private static int mShortAnimationDuration;
    private Activity activity = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /** Inizializzo utente **/

        /*
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.materialOrange600));
        }*/

        /** Imposto titolo prima scheda **/
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle("Challenges");

        //ActivityManager.TaskDescription taskDescription = new ActivityManager.TaskDescription(getResources().getString(R.string.app_name), icon, R.color.materialOrange400);
        //this.setTaskDescription(taskDescription);

        /** Inializazione del menù laterale **/
        final DrawerLayout mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawerLayout, mToolbar, R.string.drawer_open, R.string.drawer_close);
        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView mDrawerView = (NavigationView) findViewById(R.id.nav_drawer);
        mDrawerView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                // Handle navigation view item clicks here.
                int id = item.getItemId();

                if (id == R.id.nav_profile) {
                    Intent intent = new Intent(ActivityMain.this, ActivityMyProfile.class);
                    startActivity(new Intent(intent));
                } else if (id == R.id.nav_notifications) {
                    Intent intent = new Intent(ActivityMain.this, ActivityNotifications.class);
                    startActivity(new Intent(intent));
                } else if (id == R.id.nav_logout) {
                    AppInfo.client.signOut()
                            .addOnCompleteListener(activity, new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    activity.startActivity(new Intent(activity,ActivityLogIn.class));
                                }
                            });
                    activity.finish();
                } else if (id == R.id.nav_settings) {
                    Intent intent = new Intent(ActivityMain.this, ActivitySettings.class);
                    startActivity(new Intent(intent));
                } else if (id == R.id.nav_help) {
                    Intent intent = new Intent(ActivityMain.this, ActivityHelp.class);
                    startActivity(new Intent(intent));
                }

                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);
                return true;
            }
        });
        //Imposto la scritta Utente
        View navHeaderView= mDrawerView.getHeaderView(0);
        TextView tvNavheaderUsername= (TextView) navHeaderView.findViewById(R.id.navheader_username);
        tvNavheaderUsername.setText(AppInfo.getUtente().getUsername());
        //Imposto l'immagine Utente TODO: impostare immagine utente o alias
        if(true){
            TextView tvNavheaderUserInitial = (TextView) navHeaderView.findViewById(R.id.navheader_userInitial);
            tvNavheaderUserInitial.setText(String.valueOf(Character.toUpperCase(AppInfo.getUtente().getUsername().charAt(0))));
        }

        /** Inizializzazione del tabhost principale **/
        final FragmentTabHost tabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
        tabHost.setup(this,getSupportFragmentManager(),android.R.id.tabcontent);
        //Tab 1
        TabHost.TabSpec spec = tabHost.newTabSpec("Challenges Tab");
        spec.setIndicator("", ResourcesCompat.getDrawable(getResources(), R.drawable.challenges_tab_selector, null)); //Dichiaro l'icona//aggiungo la spec al mio host
        tabHost.addTab(spec,FragmentTabChallenge.class, null);
        //Tab 2
        spec = tabHost.newTabSpec("Rating Tab");
        spec.setIndicator("",ResourcesCompat.getDrawable(getResources(), R.drawable.rating_tab_selector, null));
        tabHost.addTab(spec, FragmentTabRating.class,null);
        //Tab 3
        spec = tabHost.newTabSpec("Ranking Tab");
        spec.setIndicator("", ResourcesCompat.getDrawable(getResources(), R.drawable.leaderboards_tab_selector, null));
        tabHost.addTab(spec, FragmentTabLeadeboard.class,null);

        //Cambio titolo ad ogni tab switch
        tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                switch (tabId){
                    case "Challenges Tab":
                        mToolbar.setTitle(R.string.tab_title_challenges);
                        break;
                    case "Rating Tab":
                        mToolbar.setTitle(R.string.tab_title_rating);
                        break;
                    case "Ranking Tab":
                        mToolbar.setTitle(R.string.tab_title_leaderboards);
                        break;
                    default:
                        break;
                }
            }
        });
        Intent intent = getIntent();
        NotificationManager mNotificationManager =
                (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
        try {
            mNotificationManager.cancel(intent.getIntExtra("notification", 0));
        }catch(Exception e){
            Toast.makeText(activity, R.string.generic_camera_error, Toast.LENGTH_SHORT).show();
        }
        if(intent.getBooleanExtra("newChallenge",false)){
            tabHost.setCurrentTab(0);
        }



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
            /** Gestisco l'inserzione della foto scattata **/
            switch (resultCode){
                case 0:
                    break;
                case 1:
                    //Recupero fragment
                    FragmentTabChallenge fragment = (FragmentTabChallenge) getSupportFragmentManager().findFragmentByTag("Challenges Tab");

                    //Creo la foto
                    double lat,longi;
                    lat = data.getDoubleExtra("lat",0);
                    longi = data.getDoubleExtra("long",0);
                    Photo foto = new Photo(AppInfo.getUtente(),new ChallengeSession(data.getIntExtra("sessionID",0)));
                    if(lat != 0 && longi != 0) {
                        foto.setLatitudine(lat);
                        foto.setLongitudine(longi);
                    }
                    //Ottengo le imageView relative alla challenge dall'adapter
                    ArrayList<ImageViewChallenge> imageViews = fragment.adapter.getImageViews(data.getIntExtra("sessionID",0));
                    ArrayList<ProgressBar> progressBars = fragment.adapter.getProgressBars(data.getIntExtra("sessionID",0));
                    //Avvio il task per l'inserzione della foto



                    TaskInsertThePhoto taskInsertThePhoto = new TaskInsertThePhoto
                            (foto,data.getStringExtra("fileName"),imageViews,requestCode,
                                    fragment.adapter.getSession(data.getIntExtra("sessionID",0)),this,
                                    progressBars);
                    taskInsertThePhoto.execute();
                    if(data.getIntExtra("price",0)>0) {
                        Utente user = AppInfo.getUtente();
                        user.setMoney(data.getIntExtra("price",0) * -1, true);
                        AppInfo.updateUtente(user);
                    }
                    break;
            }
        }
        if(requestCode == OnClickListenerMedal.REQUEST_CODE){
            if(resultCode == 1){
                FragmentTabChallenge fragment = (FragmentTabChallenge) getSupportFragmentManager().findFragmentByTag("Challenges Tab");
                ChallengeSession session = ((Photo)data.getParcelableExtra("photo")).getSession();
                ArrayList<ImageViewChallenge> imageViews = fragment.adapter.getImageViews(session.getIDSession());
                int i = 0;
                for (ImageViewChallenge img:imageViews) {
                    if (img.getPhoto() != null)
                        i++;
                }
                if(i==0)
                    AppInfo.setChallengePhotoRecord(session.getIDSession(),false);
                ArrayList<ProgressBar> progressBars = fragment.adapter.getProgressBars(session.getIDSession());
                ProgressBar p1 = progressBars.get(0);
                ProgressBar p2 = progressBars.get(1);
                TaskLoadSessionImage taskLoadSessionImage = new TaskLoadSessionImage(session, imageViews,requestCode,this,p1,p2);
                taskLoadSessionImage.execute();
            }
        }
    }



/*
    public void zoomImageFromThumb(View thumbView, int imageResId) {
        if (mCurrentAnimator != null) {
            mCurrentAnimator.cancel();
        }

        final ImageView expandedImageView = (ImageView) findViewById(R.id.expanded_image);
        expandedImageView.setImageResource(imageResId);
        final ImageButton btnBack = (ImageButton) findViewById(R.id.btnBack);
        final ImageButton btnDownload = (ImageButton) findViewById(R.id.btnDownload);
        final ImageButton btnMap = (ImageButton) findViewById(R.id.btnMap);

        final Rect startBounds = new Rect();
        final Rect finalBounds = new Rect();
        final Point globalOffset = new Point();

        thumbView.getGlobalVisibleRect(startBounds);
        findViewById(R.id.container).getGlobalVisibleRect(finalBounds, globalOffset);
        startBounds.offset(-globalOffset.x, -globalOffset.y);
        finalBounds.offset(-globalOffset.x, -globalOffset.y);

        float startScale;
        if ((float) finalBounds.width() / finalBounds.height()
                > (float) startBounds.width() / startBounds.height()) {
            startScale = (float) startBounds.height() / finalBounds.height();
            float startWidth = startScale * finalBounds.width();
            float deltaWidth = (startWidth - startBounds.width()) / 2;
            startBounds.left -= deltaWidth;
            startBounds.right += deltaWidth;
        } else {
            startScale = (float) startBounds.width() / finalBounds.width();
            float startHeight = startScale * finalBounds.height();
            float deltaHeight = (startHeight - startBounds.height()) / 2;
            startBounds.top -= deltaHeight;
            startBounds.bottom += deltaHeight;
        }

        thumbView.setAlpha(0f);
        expandedImageView.setBackgroundColor(getResources().getColor(android.R.color.black));
        expandedImageView.setVisibility(View.VISIBLE);
        btnBack.setVisibility(View.VISIBLE);
        btnDownload.setVisibility(View.VISIBLE);
        btnMap.setVisibility(View.VISIBLE);
        btnBack.setClickable(true);
        btnDownload.setClickable(true);
        btnMap.setClickable(true);
        thumbView.setClickable(false);

        expandedImageView.setPivotX(0f);
        expandedImageView.setPivotY(0f);

        AnimatorSet set = new AnimatorSet();
        set
                .play(ObjectAnimator.ofFloat(expandedImageView, View.X, startBounds.left,
                        finalBounds.left))
                .with(ObjectAnimator.ofFloat(expandedImageView, View.Y, startBounds.top,
                        finalBounds.top))
                .with(ObjectAnimator.ofFloat(expandedImageView, View.SCALE_X, startScale, 1f))
                .with(ObjectAnimator.ofFloat(expandedImageView, View.SCALE_Y, startScale, 1f));
        set.setDuration(mShortAnimationDuration);
        set.setInterpolator(new DecelerateInterpolator());
        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mCurrentAnimator = null;
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                mCurrentAnimator = null;
            }
        });
        set.start();
        mCurrentAnimator = set;

        final float startScaleFinal = startScale;
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mCurrentAnimator != null) {
                    mCurrentAnimator.cancel();
                }

                AnimatorSet set = new AnimatorSet();
                expandedImageView.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                set
                        .play(ObjectAnimator.ofFloat(expandedImageView, View.X, startBounds.left))
                        .with(ObjectAnimator.ofFloat(expandedImageView, View.Y, startBounds.top))
                        .with(ObjectAnimator
                                .ofFloat(expandedImageView, View.SCALE_X, startScaleFinal))
                        .with(ObjectAnimator
                                .ofFloat(expandedImageView, View.SCALE_Y, startScaleFinal));
                set.setDuration(mShortAnimationDuration);
                set.setInterpolator(new DecelerateInterpolator());
                set.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        thumbView.setAlpha(1f);
                        expandedImageView.setVisibility(View.GONE);
                        btnBack.setVisibility(View.GONE);
                        btnDownload.setVisibility(View.GONE);
                        btnMap.setVisibility(View.GONE);
                        btnBack.setClickable(false);
                        btnDownload.setClickable(false);
                        btnMap.setClickable(false);
                        thumbView.setClickable(true);
                        mCurrentAnimator = null;
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {
                        thumbView.setAlpha(1f);
                        expandedImageView.setVisibility(View.GONE);
                        btnBack.setVisibility(View.GONE);
                        btnDownload.setVisibility(View.GONE);
                        btnMap.setVisibility(View.GONE);
                        btnBack.setClickable(false);
                        btnDownload.setClickable(false);
                        btnMap.setClickable(false);
                        thumbView.setClickable(true);
                        mCurrentAnimator = null;
                    }
                });
                set.start();
                mCurrentAnimator = set;
            }
        });
    }*/
}
