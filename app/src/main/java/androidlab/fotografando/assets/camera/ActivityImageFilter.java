package androidlab.fotografando.assets.camera;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;

import androidlab.fotografando.R;
import cn.Ragnarok.BitmapFilter;

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
    }

    @Override
    protected void onResume() {
        super.onResume();
        setImage(inIntent);
    }

    @Override
    protected void onPause() {
        super.onPause();
        freeResources();
    }

    private boolean setImage(Intent intent){
        String name = intent.getStringExtra("fileName");
        File imgFile = new File(name);
        if(imgFile.exists()){

            bitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            filterBitmap = BitmapFilter.changeStyle(bitmap, BitmapFilter.GOTHAM_STYLE);
            imageView.setImageBitmap(filterBitmap);

            return true;
        }else{
            Toast.makeText(getApplicationContext(),"image not valid: " + name,Toast.LENGTH_SHORT).show();
        }
        return false;
    }
    private void freeResources(){
        imageView.setImageBitmap(null);
        bitmap.recycle();
        filterBitmap.recycle();
    }
}
