package picrate.app.activities;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import picrate.app.R;
import picrate.app.fragments.FragmentNotifications;

/**
 * Created by miki4 on 28/11/2017.
 */

public class ActivityNotifications extends Activity {
    public static final String KEY_NOTIFY_NEW_CHALLENGE = "notify_new_challenge";
    public static final String KEY_NOTIFY_CHALLENGE_EXPIRATION = "notify_challenge_expiration";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        getFragmentManager().beginTransaction()
                .replace(android.R.id.content, new FragmentNotifications())
                .commit();
    }
}
