package picrate.app.assets.listeners;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.load.resource.bitmap.GlideBitmapDrawable;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import picrate.app.R;
import picrate.app.assets.objects.BasicImageDownloader;

/**
 * Created by miki4 on 20/12/2017.
 */

public class OnClickListenerDownload implements View.OnClickListener {
    private Activity activity;
    private int REQUEST_WRITE_EXTERNAL_STORAGE_PERMISSION_RESULT;
    private String id;
    private File mPhotoFolder,mPhotoFile;
    private ImageView image;
    public OnClickListenerDownload(Activity activity, int requestCode, String id,ImageView image) {
        this.activity = activity;
        this.REQUEST_WRITE_EXTERNAL_STORAGE_PERMISSION_RESULT = requestCode;
        this.id = id;
        this.image = image;
        createPhotoFolder();
        checkWriteStoragePermission();
    }
    @Override
    public void onClick(View v) {
        if(mPhotoFile != null)
            BasicImageDownloader.writeToDisk(mPhotoFile,((GlideBitmapDrawable)image.getDrawable()).getBitmap(),new OnBitmapSaved(), Bitmap.CompressFormat.JPEG,true);
    }
    private void checkWriteStoragePermission(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if(ContextCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
                try {
                    createPhotoFileName();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }else{
                if(activity.shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)){
                    Toast.makeText(activity, R.string.ask_storage_permission,Toast.LENGTH_LONG).show();
                }
                ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_WRITE_EXTERNAL_STORAGE_PERMISSION_RESULT);
            }
        }else{
            try {
                createPhotoFileName();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    private void createPhotoFolder(){
        File photoFile = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        mPhotoFolder = new File(photoFile,activity.getString(R.string.photo_folder_name));
        if(!mPhotoFolder.exists()){
            if(!mPhotoFolder.mkdirs())
                Toast.makeText(activity, R.string.photo_folder_not_created, Toast.LENGTH_SHORT).show();
        }
    }
    private File createPhotoFileName() throws IOException{
        //Creo foto con la data come nome
        String prepend = "SavedPhoto_" + id;
        mPhotoFile = File.createTempFile(prepend,".jpg",mPhotoFolder);
        //mPhotoFileName = photoFile.getAbsolutePath();
        return mPhotoFile;
    }
    public class OnBitmapSaved implements BasicImageDownloader.OnBitmapSaveListener{
        @Override
        public void onBitmapSaved() {
            Toast.makeText(activity, "Saved!", Toast.LENGTH_SHORT).show();
        }
        @Override
        public void onBitmapSaveError(BasicImageDownloader.ImageError error) {
            Toast.makeText(activity, R.string.image_storage_error, Toast.LENGTH_SHORT).show();
        }
    }
}
