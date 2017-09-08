package androidlab.fotografando.assets.Camera;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;

/**
 * Created by miki4 on 16/05/2017.
 */

public class cameraOnClickListener implements View.OnClickListener {
    Intent intent;
    Activity activity;
    int requestcode;
    public cameraOnClickListener(Intent intent,int requestCode, Activity activity){
        this.intent = intent;
        this.requestcode = requestCode;
        this.activity = activity;
    }
    @Override
    public void onClick(View v) {
        activity.startActivityForResult(intent,requestcode);
    }
}
