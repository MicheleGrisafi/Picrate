package androidlab.fotografando;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.io.File;

import androidlab.DB.DAO.PhotoDAO;
import androidlab.DB.DAO.UtenteDAO;
import androidlab.DB.DAO.implementations.PhotoDAO_DB_impl;
import androidlab.DB.DAO.implementations.UtenteDAO_DB_impl;
import androidlab.DB.Objects.Challenge;
import androidlab.DB.Objects.ChallengeSession;
import androidlab.DB.Objects.Photo;
import androidlab.DB.Objects.Utente;
import androidlab.fotografando.assets.AppInfo;
import androidlab.fotografando.assets.InsertThePhoto;

public class checkPhotoActivity extends Activity {
    private ImageView mImageView;
    private ImageButton btnOk;
    private ImageButton btnCancel;
    private RelativeLayout topOverlay,bottomOverlay;
    private ConstraintLayout layout;
    private Bitmap imageBitmap;
    private Intent inIntent;
    private Intent outIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_photo);

        mImageView = (ImageView)findViewById(R.id.imageView);
        mImageView.setImageResource(android.R.color.transparent);
        inIntent = getIntent();
        setImage(inIntent);
        outIntent = new Intent();
        btnOk = (ImageButton)findViewById(R.id.btnOk);
        btnCancel = (ImageButton)findViewById(R.id.btnCancel);

        topOverlay = (RelativeLayout)findViewById(R.id.topRelative);
        bottomOverlay = (RelativeLayout)findViewById(R.id.bottomRelative);
        layout = (ConstraintLayout)findViewById(R.id.layout);

        btnOk.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                insertPhoto();
                setResult(1,outIntent);
                freeResources();
                finish();
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                setResult(0,outIntent);
                freeResources();
                finish();
            }
        });

    }
    @Override
    public void onWindowFocusChanged(boolean hasFocus){
        super.onWindowFocusChanged(hasFocus);
        View decorView = getWindow().getDecorView();
        if(hasFocus){
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    |View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    |View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    |View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    |View.SYSTEM_UI_FLAG_FULLSCREEN
                    |View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        }
        // Get the preview size
        int imageWidth = mImageView.getMeasuredWidth();
        int imageHeight = mImageView.getMeasuredHeight();
        int btnDimension = btnOk.getMeasuredHeight();
        // Set the height of the overlay so that it makes the preview a square
        int topLayerHeight = 0, bottomLayerHeight = 0;

        if (imageHeight-imageWidth >= btnDimension) {
            if ((imageHeight - imageWidth) / 2 >= btnDimension) {
                topLayerHeight = (imageHeight - imageWidth) / 2;
                bottomLayerHeight = (imageHeight - imageWidth) / 2;
            } else {
                bottomLayerHeight = imageHeight - imageWidth;
                topLayerHeight = 0;
            }
        }else{
            /*
            btnDimension = imageHeight - imageWidth;
            mTakePhotoButton.getLayoutParams().height = btnDimension;
            mTakePhotoButton.getLayoutParams().width = btnDimension;*/
        }


        ConstraintLayout.LayoutParams overlayTopParams = (ConstraintLayout.LayoutParams) topOverlay.getLayoutParams();
        overlayTopParams.height = topLayerHeight;

        ConstraintLayout.LayoutParams overlayBottomParams = (ConstraintLayout.LayoutParams) bottomOverlay.getLayoutParams();
        overlayBottomParams.height = bottomLayerHeight;

        topOverlay.setLayoutParams(overlayTopParams);
        bottomOverlay.setLayoutParams(overlayBottomParams);
    }
    private boolean setImage(Intent intent){
        String name = intent.getExtras().get("fileName").toString();
        File imgFile = new File(name);
        if(imgFile.exists()){
            imageBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            mImageView.setImageBitmap(imageBitmap);
            return true;
        }else{
            Toast.makeText(getApplicationContext(),"image not valid: " + name,Toast.LENGTH_SHORT).show();
        }
        return false;
    }
    private void freeResources(){
        imageBitmap.recycle();
        imageBitmap = null;
    }
    private void insertPhoto(){
        Utente user = AppInfo.getUtente();
        ChallengeSession session = new ChallengeSession(inIntent.getExtras().getInt("session"),(Integer)inIntent.getExtras().getInt("challenge"));
        Photo foto = new Photo(user,session);
        String fileName = inIntent.getExtras().get("fileName").toString();
        InsertThePhoto insert = new InsertThePhoto(foto,fileName,this);
        insert.execute();
        outIntent.putExtra("foto",foto);
    }
}




