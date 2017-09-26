package androidlab.fotografando.activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.google.android.gms.maps.model.LatLng;

import java.io.ByteArrayOutputStream;

import androidlab.fotografando.R;

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

        img.setImageResource(R.drawable.beautiful_photography);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnMap.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent mapIntent = new Intent(ActivityPhotoZoom.this, ActivityMaps.class);
                mapIntent.putExtra("latLng", new LatLng(-34, 151));
                Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.beautiful_photography);
                ByteArrayOutputStream bs = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 50, bs);
                mapIntent.putExtra("photo", bs.toByteArray());
                startActivity(new Intent(mapIntent));
            }
        });

        /*btnDownload.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                File directory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
                File myDirectory = new File(directory, String.valueOf(R.string.app_name));
                if (!myDirectory.exists()) {
                    myDirectory.mkdirs();
                }

                File file = new File(myDirectory, fileName);
                if (file.exists()) {
                    file.delete();
                }
                try {
                    FileOutputStream out = new FileOutputStream(file);
                    imageToSave.compress(Bitmap.CompressFormat.JPEG, 100, out);
                    out.flush();
                    out.close();
                } catch (Exception e) {
                    Toast.makeText(ActivityZoom.this, R.string.image_storage_error, Toast.LENGTH_LONG).show();
                }
            }
        });*/
    }
}
