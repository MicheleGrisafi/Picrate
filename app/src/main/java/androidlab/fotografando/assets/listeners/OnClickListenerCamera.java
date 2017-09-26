package androidlab.fotografando.assets.listeners;

import android.app.Dialog;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.view.View;

/**
 * Created by miki4 on 16/05/2017.
 */

/** Listener per fotocamera sulla challenge **/
public class OnClickListenerCamera implements View.OnClickListener {
    private Intent intent;
    private FragmentActivity activity;
    private int requestcode;
    private Dialog dialog;
    public OnClickListenerCamera(Intent intent, int requestCode, FragmentActivity activity){
        this.intent = intent;
        this.requestcode = requestCode;
        this.activity = activity;
    }
    public OnClickListenerCamera(Intent intent, int requestCode, FragmentActivity activity, Dialog dialog) {
        this(intent,requestCode,activity);
        this.dialog = dialog;
    }

        @Override
    public void onClick(View v) {
        activity.startActivityForResult(intent,requestcode);
        if(dialog != null)
            dialog.dismiss();
    }
}
