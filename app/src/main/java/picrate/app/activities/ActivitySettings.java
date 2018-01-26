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

        TextView title = (TextView) findViewById(R.id.textView_setting_title);
        title.setText(R.string.settings);
        ImageButton btnBack = (ImageButton) findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               finish();
            }
        });

        getFragmentManager().beginTransaction()
                .add(R.id.frameLayout_settings, new FragmentSetting())
                .commit();
    }
}
