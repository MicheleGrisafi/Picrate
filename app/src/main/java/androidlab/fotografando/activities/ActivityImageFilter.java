package androidlab.fotografando.activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;


import java.io.File;
import java.util.ArrayList;

import androidlab.fotografando.R;
import androidlab.fotografando.assets.objects.AppInfo;
import androidlab.fotografando.assets.adapters.AdapterFilters;
import androidlab.fotografando.assets.objects.Filter;

/**
 * Created by miki4 on 17/09/2017.
 */

public class ActivityImageFilter extends Activity {
    private ImageView imageView;
    private Intent inIntent;
    private Intent outIntent;
    private Bitmap bitmap;
    private Bitmap filterBitmap;
    AdapterFilters adapter;
    private boolean set_result = false;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filters);
        imageView = (ImageView)findViewById(R.id.imageView_activity_filter);
        inIntent = getIntent();

        ImageButton btnOk = (ImageButton)findViewById(R.id.btnOk);
        ImageButton btnCancel = (ImageButton)findViewById(R.id.btnCancel);

        Filter none = new Filter("None", 0);
        Filter grayscale = new Filter("Grayscale",AppInfo.filter_grayscale);
        Filter gotham = new Filter("Gotham",AppInfo.filter_gotham);
        Filter oil = new Filter("Oil",AppInfo.filter_oil);
        Filter block = new Filter("Block",AppInfo.filter_block);
        Filter blur = new Filter("Blur",AppInfo.filter_blur);
        Filter hdr = new Filter("HDR",AppInfo.filter_hdr);
        //Filter light = new Filter("Light",20);
        //Filter lomo = new Filter("Lomo",20);
        //Filter neon = new Filter("Neon",20);
        Filter old = new Filter("Seppia",AppInfo.filter_seppia);
        //Filter pixel = new Filter("Pixel",20);
        //Filter relief = new Filter("Relief",20);
        Filter sharpen = new Filter("Sharpen",AppInfo.filter_sharpen);
        Filter sketch = new Filter("Sketch",AppInfo.filter_sketch);
        Filter glow = new Filter("Glow",AppInfo.filter_glow);



        ArrayList<Filter> filterList = new ArrayList<>();

        filterList.add(none);
        filterList.add(grayscale);
        filterList.add(gotham);
        filterList.add(oil);filterList.add(block);filterList.add(blur);
        filterList.add(hdr);filterList.add(old);filterList.add(sharpen);filterList.add(sketch);
        filterList.add(glow);

        outIntent = new Intent();
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView_activity_filters);
        // Create adapter passing in the sample user data
        adapter = new AdapterFilters(getApplicationContext(),filterList,imageView,outIntent);
        // Attach the adapter to the recyclerview to populate items
        recyclerView.setAdapter(adapter);
        // Set layout manager to position the items
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);



        btnOk.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (AppInfo.getUtente().getMoney() < inIntent.getIntExtra("price",0) + outIntent.getIntExtra("price",0)) {
                    set_result = true;
                    setResult(1, outIntent);
                    finish();
                }
                else
                    Toast.makeText(ActivityImageFilter.this, Integer.toString(inIntent.getIntExtra("price",0)) + Integer.toString(outIntent.getIntExtra("price",0)), Toast.LENGTH_LONG).show();
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                outIntent.putExtra("price",0);
                set_result = true;
                setResult(0,outIntent);
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

    @Override
    protected void onDestroy() {
        if(!set_result) {
            outIntent.putExtra("price", 0);
            setResult(0, outIntent);
        }
        super.onDestroy();
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
