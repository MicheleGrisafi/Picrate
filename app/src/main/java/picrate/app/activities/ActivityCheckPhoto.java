package androidlab.app.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import java.io.File;

import androidlab.app.R;
import androidlab.app.assets.objects.BitmapHelper;

public class ActivityCheckPhoto extends BitmapActivity {

    public int CODE_FILTERS = 5;
    private boolean result_sent = false;
    private String nameFilter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_photo);

        imageView = (ImageView)findViewById(R.id.imageView);
        //mImageView.setImageResource(android.R.color.transparent);
        outIntent.putExtra("price", inIntent.getIntExtra("price",0));
        btnOk = (ImageButton)findViewById(R.id.btnOk);
        btnCancel = (ImageButton)findViewById(R.id.btnCancel);
        outIntent.putExtra("fileName",inIntent.getStringExtra("fileName"));

        btnOk.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                result_sent =true;
                if(nameFilter != null){
                    BitmapHelper.deleteFile(new File(inIntent.getStringExtra("fileName")));
                }
                setResult(1,outIntent);
                finish();
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                result_sent = true;
                setResult(0,outIntent);
                finish();
            }
        });

        ImageButton imageButtonfilter = (ImageButton) findViewById(R.id.btnFilter);
        final Intent filterIntent = new Intent(this,ActivityImageFilter.class);
        filterIntent.putExtra("fileName",inIntent.getStringExtra("fileName"));
        filterIntent.putExtra("price",inIntent.getIntExtra("price",0));
        imageButtonfilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(filterIntent,CODE_FILTERS);
            }
        });
    }



    @Override
    protected void onDestroy() {
        freeResources();
        if (!result_sent)
            setResult(0,outIntent);
        super.onDestroy();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == CODE_FILTERS){
            if(resultCode == 1){
                boolean res = false;
                outIntent.putExtra("price",data.getIntExtra("price",0));
                nameFilter = data.getStringExtra("fileNameFilter");
                if(nameFilter != null){
                    File file = new File(nameFilter);
                    setBitmap(file);
                    outIntent.putExtra("fileName",nameFilter);
                }else{
                    outIntent.putExtra("fileName",inIntent.getStringExtra("fileName"));
                }
            }
        }
    }
    @Override
    protected void freeResources(){
        super.freeResources();
        bitmap.recycle();
        bitmap = null;
    }
}




