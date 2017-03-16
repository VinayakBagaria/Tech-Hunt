package workvinayak.techhunt;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import workvinayak.techhunt.FirebaseDB.AddRemove;

/**
 * Created by bagariavinayak on 16-11-2016.
 */

public class Gps2 extends BaseActivity
{
    Location currentLoc;
    float count = 0.0f;
    double firstLat, firstLong;
    Location location;
    private static final int code = 1;
    ProgressBar progressbar;
    Handler handler = new Handler();
    TextView ShowText,tv;
    int flag=1;
    int countComplete;
    SharedPreferences sharedPreferences;
    int progressBarValue = 0;
    long childCount=-1;
    String initialCount=null;
    AddRemove addRemove;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gps);
        sharedPreferences = this.getSharedPreferences("Activity", Context.MODE_PRIVATE);
        initialise();

    }

    void initialise()
    {

        tv=(TextView)findViewById(R.id.tvShow);



        int permissionCheck = ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION);
        if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
            Log.d("XOXO", "Granted");
            loc();

        } else {
            Log.d("XOXO", "DEnied");
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        code);
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        code);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }

            return;
        }


    }

    public float getLastBestLocation() {
        double latitude = currentLoc.getLatitude();
        double longitude = currentLoc.getLongitude();

        float results[]=new float[1];

        Location.distanceBetween(firstLat,firstLong,latitude,longitude,results);
        return results[0];
    }


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case code: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                    int permissionCheck = ContextCompat.checkSelfPermission(this,
//                            Manifest.permission.ACCESS_FINE_LOCATION);
//                    if (permissionCheck == PackageManager.PERMISSION_GRANTED)
//                    {
                    loc();
//                    }
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        flag=1;
    }

    public void loc() {

        progressbar = (ProgressBar)findViewById(R.id.progressBar1);
        ShowText = (TextView)findViewById(R.id.tvPercent);


        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER, 0, 0, new LocationListener()
                {
                    @Override
                    public void onLocationChanged(Location location)
                    {
                        currentLoc = location;
                        count = getLastBestLocation();
                        countComplete=(int)(count*100/108);

                        progressBarValue=countComplete;

                        handler.post(new Runnable() {

                            @Override
                            public void run() {

                                progressbar.setProgress(progressBarValue);
                                ShowText.setText(progressBarValue +"/ 100 %");

                                if(countComplete>=100)
                                {
                                    Log.d("XOXO",countComplete+"BINGxx");
                                    progressbar.setVisibility(View.INVISIBLE);
                                        moveToNext();

                                }

                            }
                        });
                    }

                    @Override
                    public void onStatusChanged(String s, int i, Bundle bundle) {

                    }

                    @Override
                    public void onProviderEnabled(String s) {

                    }

                    @Override
                    public void onProviderDisabled(String s) {

                    }
                }
        );

        location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if(flag==1)
        {
            recheck();
        }
    }

    public void recheck()
    {
        if (location != null) {
            String message = String.format(
                    "Current Location \n Longitude: %1$s \n Latitude: %2$s",
                    location.getLongitude(), location.getLatitude()
            );
            firstLat=location.getLatitude();
            firstLong=location.getLongitude();
            Toast.makeText(getApplicationContext(), message,
                    Toast.LENGTH_LONG).show();
            flag=0;
            return;
        }
        recheck();
        Toast.makeText(getApplicationContext(), "messaged",
                Toast.LENGTH_LONG).show();
    }

    public void moveToNext()
    {
        SharedPreferences sharedPreferences=this.getSharedPreferences("Activity", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString("Intent","4");
        editor.commit();

        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Loading..");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();

        addRemove=new AddRemove();
        addRemove.addUser("stage4",androidId);

        progressDialog.dismiss();

        startActivity(new Intent(Gps2.this,TasksActivity.class));
        finish();


    }
}

