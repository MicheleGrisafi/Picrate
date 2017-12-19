package picrate.app.activities;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.maps.model.LatLng;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

import picrate.app.DB.Objects.Photo;
import picrate.app.R;
import picrate.app.assets.objects.BasicImageDownloader;
import picrate.app.assets.objects.MyApp;

/**
 * Created by Cate on 25/05/2017.
 */

public class ActivityPhotoZoom extends Activity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_zoom);

        final ImageButton btnBack = (ImageButton) findViewById(R.id.btnBack);
        final ImageButton btnDownload = (ImageButton) findViewById(R.id.btnDownload);
        final ImageButton btnMap = (ImageButton) findViewById(R.id.btnMap);
        ImageView img = (ImageView) findViewById(R.id.expanded_image);
        Intent inIntent = getIntent();
        final Photo image = inIntent.getParcelableExtra("image");

        URL photo =image.getImage();
        Glide.with(this).load(photo).into(img);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        if(image.getLatitudine() == 0){
            //btnMap.setVisibility(View.INVISIBLE);
        }
            btnMap.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    Intent mapIntent = new Intent(ActivityPhotoZoom.this, ActivityMaps.class);
                    mapIntent.putExtra("latLng", new LatLng(image.getLatitudine(), image.getLongitudine()));
                    Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.beautiful_photography);
                    ByteArrayOutputStream bs = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 50, bs);
                    mapIntent.putExtra("photo", bs.toByteArray());
                    startActivity(new Intent(mapIntent));
                }
            });




        btnDownload.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                BasicImageDownloader.writeToDisk();
            }
            private void checkWriteStoragePermission(){
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                    if(ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
                        try {
                            createPhotoFileName();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }else{
                        if(shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)){
                            Toast.makeText(this,R.string.ask_storage_permission,Toast.LENGTH_LONG).show();
                        }
                        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_WRITE_EXTERNAL_STORAGE_PERMISSION_RESULT);
                        //requestPermissions(new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE},REQUEST_WRITE_EXTERNAL_STORAGE_PERMISSION_RESULT);
                    }
                }else{
                    try {
                        createPhotoFileName();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            private File createPhotoFileName() throws IOException{
                //Creo foto con la data come nome
                String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                String prepend = "SavedPhoto_" + timestamp + "_";
                File photoFile = File.createTempFile(prepend,".jpg",mPhotoFolder);
                mPhotoFileName = photoFile.getAbsolutePath();
                mPhotoFile = photoFile;
                return photoFile;
            }
        });
    }
}
