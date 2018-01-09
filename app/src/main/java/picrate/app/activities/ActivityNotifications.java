package picrate.app.activities;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.TextView;

import picrate.app.R;
import picrate.app.fragments.FragmentNotifications;
import picrate.app.fragments.FragmentSetting;

/**
 * Created by miki4 on 28/11/2017.
 */

public class ActivityNotifications extends Activity {
    public static final String KEY_NOTIFY_NEW_CHALLENGE = "notify_new_challenge";
    public static final String KEY_NOTIFY_CHALLENGE_EXPIRATION = "notify_challenge_expiration";
    public static final String KEY_NOTIFICATION_WIFI = "notification_wifi";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.materialOrange600));
        }
        TextView title = (TextView) findViewById(R.id.textView_setting_title);
        title.setText(R.string.notifications);
        ImageButton btnBack = (ImageButton) findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        getFragmentManager().beginTransaction()
                .add(R.id.frameLayout_settings, new FragmentNotifications())
                .commit();
    }
}
