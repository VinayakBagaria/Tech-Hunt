package workvinayak.techhunt;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

/**
 * Created by bagariavinayak on 15-11-2016.
 */

public class GPSActivity extends AppCompatActivity {
    Location currentLoc;
    LocationListener locationListener;
    double firstLat,firstLong;
    private static final int code=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Round 3");
        setSupportActionBar(toolbar);
        getDistance();
    }

    void getDistance() {
        LocationManager locationManager = (LocationManager) getBaseContext().getSystemService(Context.LOCATION_SERVICE);
        boolean off = false;
        try {
            off = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch (Exception e) {
            Snackbar.make((CoordinatorLayout) findViewById(R.id.coordLayout), "Off hai", Snackbar.LENGTH_SHORT).show();
        }

        if (off == false) {
            startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
        }

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.

            if(ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.ACCESS_FINE_LOCATION))
            {
                Snackbar.make((CoordinatorLayout)findViewById(R.id.coordLayout),"Phaado English Here",Snackbar.LENGTH_SHORT)
                        .setAction("Settings Open", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                            }
                        });
            }
            else
            {
                ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},code);
            }

        }

        Location firstLoc = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        //firstLat=firstLoc.getLatitude();
        //firstLong=firstLoc.getLongitude();
        firstLat=12.859739;
        firstLong=77.438938;

        locationListener=new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                currentLoc=location;
                getLastBestLocation();
                Log.d("Loc","Here");
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
        };

        if(isOnline())
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,0,0,locationListener);
        else
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0,locationListener);
    }

    public void getLastBestLocation()
    {
        double latitude=currentLoc.getLatitude();
        double longitude=currentLoc.getLongitude();

        double latA=Math.toRadians(firstLat);
        double lonA=Math.toRadians(firstLong);
        double latB=Math.toRadians(latitude);
        double lonB=Math.toRadians(longitude);

        double cosAng=(Math.cos(latA)*Math.cos(latB)*Math.cos(lonB-lonA))+(Math.sin(latA)*Math.sin(latB));
        double ang=Math.acos(cosAng);

        double dist=ang*6371;
        //Snackbar.make((CoordinatorLayout)findViewById(R.id.coordLayout),String.valueOf(dist),Snackbar.LENGTH_SHORT).show();

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch(requestCode)
        {
            case code:
                if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED && grantResults[1]==PackageManager.PERMISSION_GRANTED)
                {
                    Toast.makeText(getApplicationContext(),"Grnted",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Denied",Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    public boolean isOnline()
    {
        try{
            ConnectivityManager con=(ConnectivityManager)getBaseContext().getSystemService(Context.CONNECTIVITY_SERVICE);
            if(con.getActiveNetworkInfo()!=null)
            {
                if(con.getActiveNetworkInfo().isConnected())
                    return true;
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return false;
    }
}
