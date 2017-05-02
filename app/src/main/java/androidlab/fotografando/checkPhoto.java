package androidlab.fotografando;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;

public class checkPhoto extends Activity {
    private ImageView mImageView;
    private ImageButton btnOk;
    private ImageButton btnCancel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_photo);

        mImageView = (ImageView)findViewById(R.id.imageView);
        mImageView.setImageResource(android.R.color.transparent);
        final Intent in = getIntent();
        setImage(in);
        final Intent out = new Intent();
        btnOk = (ImageButton)findViewById(R.id.btnOk);
        btnCancel = (ImageButton)findViewById(R.id.btnCancel);

        btnOk.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                setResult(1,out);
                finish();
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                setResult(0,out);
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
    }
    private boolean setImage(Intent intent){
        String name = intent.getExtras().get("fileName").toString();
        File imgFile = new File(name);
        if(imgFile.exists()){

                /*ExifInterface exif = new ExifInterface(name);

                int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION,1);
                switch (orientation){
                    case ExifInterface.ORIENTATION_NORMAL:

                        break;
                    case ExifInterface.ORIENTATION_ROTATE_90:

                        break;
                    case ExifInterface.ORIENTATION_ROTATE_180:

                        break;
                    case ExifInterface.ORIENTATION_ROTATE_270:

                        break;
                }
                Matrix matrix = new Matrix();
                matrix.postRotate(90);*/
                Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                //Bitmap rotatedBitmap = Bitmap.createBitmap(myBitmap, 0, 0, myBitmap.getWidth(), myBitmap.getHeight(), matrix, true);
                mImageView.setImageBitmap(myBitmap);
                myBitmap = null;
                return true;

        }else{
            Toast.makeText(getApplicationContext(),"image not valid: " + name,Toast.LENGTH_SHORT).show();
        }
        return false;
    }
}




