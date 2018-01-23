package picrate.app.assets.listeners;

import android.app.Dialog;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Toast;

import picrate.app.DB.Objects.ChallengeSession;
import picrate.app.R;

/**
 * Created by miki4 on 16/05/2017.
 */

/** Listener per fotocamera sulla challenge **/
public class OnClickListenerCamera implements View.OnClickListener {
    private Intent intent;
    private FragmentActivity activity;
    private int requestcode;
    private Dialog dialog;
    private ChallengeSession session;
    public OnClickListenerCamera(Intent intent, int requestCode, FragmentActivity activity, ChallengeSession session){
        this.intent = intent;
        this.requestcode = requestCode;
        this.activity = activity;
        this.session = session;
    }
    public OnClickListenerCamera(Intent intent, int requestCode, FragmentActivity activity, Dialog dialog, ChallengeSession session) {
        this(intent,requestCode,activity,session);
        this.dialog = dialog;
    }

    @Override
    public void onClick(View v) {
        if(session.getStato() == ChallengeSession.STATO_ACTIVE)
            activity.startActivityForResult(intent,requestcode);
        else
            Toast.makeText(activity, R.string.challenge_expired, Toast.LENGTH_SHORT).show();
        if(dialog != null)
            dialog.dismiss();
    }
}
