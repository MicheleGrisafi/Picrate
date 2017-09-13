package androidlab.fotografando.assets.Camera;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.view.View;

/**
 * Created by miki4 on 16/05/2017.
 */

/** Listener per fotocamera sulla challenge **/
public class cameraOnClickListener implements View.OnClickListener {
    private Intent intent;
    private FragmentActivity activity;
    private int requestcode;
    public cameraOnClickListener(Intent intent,int requestCode, FragmentActivity activity){
        this.intent = intent;
        this.requestcode = requestCode;
        this.activity = activity;
    }
    @Override
    public void onClick(View v) {
        activity.startActivityForResult(intent,requestcode);
    }
}
