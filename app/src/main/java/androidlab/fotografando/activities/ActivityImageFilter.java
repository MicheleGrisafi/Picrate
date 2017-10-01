package androidlab.fotografando.activities;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;


import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import androidlab.fotografando.R;
import androidlab.fotografando.assets.objects.AppInfo;
import androidlab.fotografando.assets.adapters.AdapterFilters;
import androidlab.fotografando.assets.objects.BitmapHelper;
import androidlab.fotografando.assets.objects.Filter;

/**
 * Created by miki4 on 17/09/2017.
 */

public class ActivityImageFilter extends BitmapActivity {

    public Bitmap filterBitmap;
    private AdapterFilters adapter;
    private boolean set_result = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filters);
        imageView = (ImageView)findViewById(R.id.imageView_activity_filter);
        btnOk = (ImageButton)findViewById(R.id.btnOk);
        btnCancel = (ImageButton)findViewById(R.id.btnCancel);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView_activity_filters);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);

        //Create filters
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

        //Add filters to an adapter list
        ArrayList<Filter> filterList = new ArrayList<>();

        filterList.add(none);
        filterList.add(grayscale);
        filterList.add(gotham);
        filterList.add(oil);
        filterList.add(block);
        filterList.add(blur);
        filterList.add(hdr);
        filterList.add(old);
        filterList.add(sharpen);
        filterList.add(sketch);
        filterList.add(glow);


        // Create adapter passing in the sample user data
        adapter = new AdapterFilters(getApplicationContext(),filterList,imageView,outIntent,this);

        // Attach the adapter to the recyclerview to populate items
        recyclerView.setAdapter(adapter);



        btnOk.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                int price = inIntent.getIntExtra("price",0);
                int price2 = outIntent.getIntExtra("price",0);
                if (AppInfo.getUtente().getMoney() >= price + price2) {
                    set_result = true;
                    if(outIntent.getBooleanExtra("filter",false)){
                        String fileName = inIntent.getStringExtra("fileName");
                        String nameFilter = fileName.substring(0,fileName.indexOf('.')) + "-filter" + fileName.substring(fileName.indexOf('.'),fileName.length());
                        BitmapHelper.bitmapToFile(filterBitmap,nameFilter);
                        outIntent.putExtra("fileNameFilter",nameFilter);
                    }
                    outIntent.putExtra("price",price + price2);
                    setResult(1, outIntent);
                    finish();
                }
                else
                    Toast.makeText(ActivityImageFilter.this, R.string.not_enough_money, Toast.LENGTH_LONG).show();
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                outIntent.putExtra("price",inIntent.getIntExtra("price",0));
                set_result = true;
                setResult(0,outIntent);
                finish();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        setImage();
        adapter.setBitmap(bitmap);
    }

    @Override
    protected void onDestroy() {
        freeResources();
        if(!set_result) {
            outIntent.putExtra("price", 0);
            setResult(0, outIntent);
        }
        super.onDestroy();
    }

    @Override
    protected void freeResources(){
        super.freeResources();
        bitmap.recycle();
        bitmap = null;
        if(filterBitmap != null) {
            filterBitmap.recycle();
            filterBitmap = null;
        }
    }

}
