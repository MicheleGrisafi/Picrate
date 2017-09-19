package androidlab.fotografando.assets.camera;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;


import java.io.File;
import java.util.ArrayList;
import java.util.Collection;

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
    AdapterFilters adapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filters);
        imageView = (ImageView)findViewById(R.id.imageView_activity_filter);
        inIntent = getIntent();

        ImageButton btnOk = (ImageButton)findViewById(R.id.btnOk);
        ImageButton btnCancel = (ImageButton)findViewById(R.id.btnCancel);

        Filter grayscale = new Filter("Grayscale",20);
        Filter gotham = new Filter("Gotham",30);
        Filter oil = new Filter("Oil",40);
        Filter block = new Filter("Block",20);
        Filter blur = new Filter("Blur",20);
        Filter hdr = new Filter("HDR",20);
        Filter invert = new Filter("Invert",20);
        Filter light = new Filter("Light",20);
        Filter lomo = new Filter("Lomo",20);
        Filter neon = new Filter("Neon",20);
        Filter old = new Filter("Old",20);
        Filter pixel = new Filter("Pixel",20);
        Filter relief = new Filter("Relief",20);
        Filter sharpen = new Filter("Sharpen",20);
        Filter sketch = new Filter("Sketch",20);
        Filter glow = new Filter("Glow",20);
        Filter tv = new Filter("Tv",20);


        ArrayList<Filter> filterList = new ArrayList<>();

        filterList.add(grayscale);
        filterList.add(gotham);
        filterList.add(oil);filterList.add(block);filterList.add(blur);
        filterList.add(hdr);filterList.add(invert);filterList.add(light);filterList.add(lomo);filterList.add(neon
        );filterList.add(old);filterList.add(pixel);filterList.add(relief);filterList.add(sharpen);filterList.add(sketch);
        filterList.add(glow);filterList.add(tv);


        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView_activity_filters);
        // Create adapter passing in the sample user data
        adapter = new AdapterFilters(getApplicationContext(),filterList,imageView);
        // Attach the adapter to the recyclerview to populate items
        recyclerView.setAdapter(adapter);
        // Set layout manager to position the items
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);



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
        adapter.setBitmap(bitmap);
        adapter.setFilterBitmap(filterBitmap);
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
            imageView.setImageBitmap(bitmap);
        }else{
            Toast.makeText(getApplicationContext(),"image not valid: " + name,Toast.LENGTH_SHORT).show();
        }
    }
    private void freeResources(){
        imageView.setImageBitmap(null);
        bitmap.recycle();
        if(filterBitmap != null)
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
