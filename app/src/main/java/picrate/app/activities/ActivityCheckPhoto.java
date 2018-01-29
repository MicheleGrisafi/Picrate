package picrate.app.activities;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.Toast;

import java.io.File;
import java.util.List;

import picrate.app.R;
import picrate.app.assets.objects.BitmapHelper;

public class ActivityCheckPhoto extends BitmapActivity implements CompoundButton.OnCheckedChangeListener, LocationListener {

    public int CODE_FILTERS = 5;
    private boolean result_sent = false;
    private String nameFilter;
    private boolean location = false;
    private final int MY_PERMISSIONS_REQUEST_LOCATION = 9;
    private Switch location_switch;
    private LocationManager locationManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_photo);

        imageView = (ImageView)findViewById(R.id.imageView);
        //mImageView.setImageResource(android.R.color.transparent);
        outIntent.putExtra("price", inIntent.getIntExtra("price",0));
        btnOk = (ImageButton)findViewById(R.id.btnOk);
        btnCancel = (ImageButton)findViewById(R.id.btnCancel);
        location_switch = (Switch) findViewById(R.id.switch_location);

        location_switch.setOnCheckedChangeListener(this);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if(location_switch.isChecked())
            setLocation();



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


    private void buildAlertMessageNoGps() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Your GPS seems to be disabled, do you want to enable it?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        dialog.cancel();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }
    private boolean checkLocationPermission(){
        boolean res = true;
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // Asking user if explanation is needed
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                //Prompt the user once explanation has been shown
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
            res = false;
        }
        return res;
    }
    private void setLocation(){
        if(!checkLocationPermission()){
            location_switch.setChecked(false);
        }else if (!locationManager.isProviderEnabled( LocationManager.GPS_PROVIDER ) ) {
            buildAlertMessageNoGps();
            location_switch.setChecked(false);
        }else{
            //Location loca = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            //Location loca = locationManager.requestSingleUpdate(LocationManager.GPS_PROVIDER,this,null);

            if(locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
                locationManager.requestSingleUpdate(LocationManager.GPS_PROVIDER,this,null);
            }else if(locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)){
                locationManager.requestSingleUpdate(LocationManager.NETWORK_PROVIDER,this,null);
            }else if(locationManager.isProviderEnabled(LocationManager.PASSIVE_PROVIDER)){
                locationManager.requestSingleUpdate(LocationManager.PASSIVE_PROVIDER,this,null);
            }else{
                Toast.makeText(this, R.string.gps_error_location, Toast.LENGTH_SHORT).show();
            }
        }
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
    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if(isChecked)
            setLocation();
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission was granted.
                    if (ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                        setLocation();
                    }

                } else {
                    location = false;
                    // Permission denied, Disable the functionality that depends on this permission.
                    Toast.makeText(this, R.string.gps_permission_denied, Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        //TODO: add loading screen
        outIntent.putExtra("lat",location.getLatitude());
        outIntent.putExtra("long",location.getLongitude());
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}




