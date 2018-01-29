package picrate.app.activities;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.GlideBitmapDrawable;
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
import picrate.app.assets.listeners.OnClickListenerDownload;
import picrate.app.assets.listeners.OnClickListenerMedal;
import picrate.app.assets.objects.AppInfo;
import picrate.app.assets.objects.BasicImageDownloader;
import picrate.app.assets.objects.MyApp;
import picrate.app.assets.tasks.TaskDeletePhoto;

/**
 * Created by Cate on 25/05/2017.
 */

public class ActivityPhotoZoom extends Activity {
    private final int CODE_SAVE = 6;
    final Activity activity = this;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_zoom);

        final ImageButton btnBack = (ImageButton) findViewById(R.id.btnBack);
        final ImageButton btnDownload = (ImageButton) findViewById(R.id.btnDownload);
        final ImageButton btnMap = (ImageButton) findViewById(R.id.btnMap);
        final ImageView img = (ImageView) findViewById(R.id.expanded_image);
        Intent inIntent = getIntent();
        final Photo image = inIntent.getParcelableExtra("image");

        final URL photo =image.getImage();
        Glide.with(this).load(photo).into(img);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        if(image.getLatitudine() == 0 || image.getLongitudine() == 0){
            btnMap.setVisibility(View.INVISIBLE);
        }else {
            btnMap.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent mapIntent = new Intent(ActivityPhotoZoom.this, ActivityMaps.class);
                    mapIntent.putExtra("latLng", new LatLng(image.getLatitudine(), image.getLongitudine()));
                    Bitmap bitmap = ((GlideBitmapDrawable)img.getDrawable()).getBitmap();
                    //Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.circular_profile);
                    ByteArrayOutputStream bs = new ByteArrayOutputStream();

                    bitmap.compress(Bitmap.CompressFormat.JPEG, 50, bs);
                    mapIntent.putExtra("photo", bs.toByteArray());
                    startActivity(new Intent(mapIntent));
                }
            });
        }
        btnDownload.setOnClickListener(new OnClickListenerDownload(this,CODE_SAVE,Integer.toString(image.getId()),img));
        //TODO: aggiungere barra di stato download

        TextView delete = (TextView)findViewById(R.id.textView_deletePicture);
        if(inIntent.getBooleanExtra("deletable",false) && image.getUtente() != null && image.getUtente().getId() == AppInfo.getUtente().getId()){
            delete.setVisibility(View.VISIBLE);
            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    final Dialog dialog = new Dialog(activity);
                    dialog.setContentView(R.layout.dialog_report_photo);

                    // set the custom dialog components - text, image and button
                    TextView text = (TextView) dialog.findViewById(R.id.reportPhotoDialogText);
                    text.setText(R.string.delete_picture_dialog_message);
                    TextView title = (TextView) dialog.findViewById(R.id.reportPhotoDialogTitle);
                    title.setText(R.string.delete_picture);
                    ImageButton dialogCloseButton = (ImageButton) dialog.findViewById(R.id.reportPhotoDialogCloseButton);

                    // if button is clicked, close the custom dialog
                    dialogCloseButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });
                    Button dialogDeleteButton = (Button) dialog.findViewById(R.id.reportPhotoDialogReportButton);
                    dialogDeleteButton.setText(R.string.delete_picture);
                    // if button is clicked, close the custom dialog
                    dialogDeleteButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            TaskDeletePhoto task = new TaskDeletePhoto(image);
                            task.execute();
                            dialog.dismiss();
                            Intent outIntent = new Intent();
                            outIntent.putExtra("photo",image);
                            setResult(1,outIntent);
                            finish();
                        }
                    });
                    dialog.show();
                }
            });
        }
    }
}
