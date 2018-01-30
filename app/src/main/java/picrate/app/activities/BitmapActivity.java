package picrate.app.activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;

import picrate.app.assets.objects.BitmapHelper;

/**
 * Created by miki4 on 30/09/2017.
 */

public abstract class BitmapActivity extends Activity {
    protected ImageView imageView;
    protected Intent inIntent;
    protected Intent outIntent;
    protected Bitmap bitmap;
    protected ImageButton btnOk;
    protected ImageButton btnCancel;

    protected void setBitmap(File file){
        if(bitmap != null)
            bitmap.recycle();
        bitmap = BitmapHelper.fileToBitmap(file);
    }

    protected boolean setImage(){
        boolean result = false;
        if(bitmap != null){
            imageView.setImageBitmap(bitmap);
            result = true;
        }else
            Toast.makeText(getApplicationContext(),"image not valid",Toast.LENGTH_SHORT).show();
        return result;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        inIntent = getIntent();
        outIntent = new Intent();
        bitmap = null;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(bitmap == null){
            String name = inIntent.getStringExtra("fileName");
            File imgFile = new File(name);
            setBitmap(imgFile);
        }
        setImage();
    }


    protected void freeResources() {
        imageView.setImageBitmap(null);
    }

}
