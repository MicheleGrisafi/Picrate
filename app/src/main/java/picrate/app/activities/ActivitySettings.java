package picrate.app.activities;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import picrate.app.R;
import picrate.app.fragments.FragmentSetting;

/**
 * Created by miki4 on 23/09/2017.
 */

public class ActivitySettings extends Activity {
    public static final String KEY_SAVE_PICTURES = "save_photos";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        getFragmentManager().beginTransaction()
                .replace(android.R.id.content, new FragmentSetting())
                .commit();
    }
}
