package androidlab.fotografando.assets.camera;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;


import java.io.File;

import androidlab.fotografando.R;
import cn.Ragnarok.GrayFilter;

/**
 * Created by miki4 on 17/09/2017.
 */

public class ActivityImageFilter extends Activity {
    private ImageView imageView;
    private Intent inIntent;
    private Bitmap bitmap;
    private Bitmap filterBitmap;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filters);
        imageView = (ImageView)findViewById(R.id.imageView_activity_filter);
        inIntent = getIntent();

        ImageButton btnOk = (ImageButton)findViewById(R.id.btnOk);
        ImageButton btnCancel = (ImageButton)findViewById(R.id.btnCancel);


        btnOk.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //setResult(1,outIntent);
                finish();
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //setResult(0,outIntent);
                finish();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        setImage(inIntent);
    }

    @Override
    protected void onPause() {
        freeResources();
        super.onPause();
    }

    private void setImage(Intent intent){
        String name = intent.getStringExtra("fileName");
        File imgFile = new File(name);
        if(imgFile.exists()){
            final BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPreferredConfig = Bitmap.Config.RGB_565;
            bitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath(),options);
            filterBitmap = GrayFilter.changeToGray(bitmap);
            imageView.setImageBitmap(filterBitmap);
        }else{
            Toast.makeText(getApplicationContext(),"image not valid: " + name,Toast.LENGTH_SHORT).show();
        }
    }
    private void freeResources(){
        imageView.setImageBitmap(null);
        bitmap.recycle();
        filterBitmap.recycle();
        bitmap = null;
        filterBitmap = null;
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
    }
}
