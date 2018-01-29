package picrate.app.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import picrate.app.DB.Objects.Utente;
import picrate.app.R;
import picrate.app.assets.objects.AppInfo;
import picrate.app.assets.tasks.AsyncTaskLoaderCheckUsername;

/**
 * Created by miki4 on 26/11/2017.
 */

public class ActivitySignUp extends FragmentActivity implements LoaderManager.LoaderCallbacks<Utente> {
    final ActivitySignUp activity = this;
    private String username;
    private Button btnSignup;
    private EditText etUsername;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        etUsername = (EditText)findViewById(R.id.editText_signup);
        etUsername.addTextChangedListener(filterTextWatcher);
        btnSignup = (Button)findViewById(R.id.button_signup);

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etUsername.setEnabled(false);
                getSupportLoaderManager().initLoader(0, null, activity).forceLoad();
            }
        });
        btnSignup.setEnabled(false);

    }

    @Override
    public Loader<Utente> onCreateLoader(int id, Bundle args) {
        return new AsyncTaskLoaderCheckUsername(this,username);
    }

    @Override
    public void onLoadFinished(Loader<Utente> loader, Utente data) {
        if(data == null){
            Intent intent = getIntent();
            AppInfo.signupUser(intent.getStringExtra("googleKey"),intent.getStringExtra("email"),username,activity);
        }else{
            Toast.makeText(activity, R.string.username_taken, Toast.LENGTH_LONG).show();
            etUsername.setEnabled(true);
        }
    }

    @Override
    public void onLoaderReset(Loader<Utente> loader) {

    }
    private TextWatcher filterTextWatcher = new TextWatcher() {

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            final Pattern pattern = Pattern.compile("[a-zA-Z0-9._-]*$");
            if (pattern.matcher(s).matches() && s.length() >= 3 && s.length() <= 20){
                username = s.toString();
                btnSignup.setEnabled(true);
            }else{
                username = null;
                btnSignup.setEnabled(false);
            }
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };
    @Override
    public void onBackPressed() {
        AppInfo.client.signOut()
                .addOnCompleteListener(activity, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        activity.startActivity(new Intent(activity,ActivityLogIn.class));
                    }
                });
        super.onBackPressed();
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)  {
        if (Integer.parseInt(android.os.Build.VERSION.SDK) > 5
                && keyCode == KeyEvent.KEYCODE_BACK
                && event.getRepeatCount() == 0) {
            onBackPressed();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
