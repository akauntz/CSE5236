package com.example.mike9.cse_app;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;

import androidx.core.content.ContextCompat;

public class GetLocation extends Activity implements LocationListener {
    LocationManager mLocationManager;
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_fragment);

        mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
    }

    @Override
    public void onLocationChanged(Location location){
        Log.d("Location Changes", location.toString());
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras){
        Log.d("Status Changed", String.valueOf(status));
    }

    @Override
    public void onProviderEnabled(String provider) {
        Log.d("Provider Enabled", provider);
    }

    @Override
    public void onProviderDisabled(String provider) {
        Log.d("Provider Disabled", provider);
    }

    public void getLocation(Criteria criteria, Looper looper, Activity activity){
        if(ContextCompat.checkSelfPermission(activity, android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            Log.d("GPSTest", "Here");
            mLocationManager.requestSingleUpdate(criteria, this, looper);
            Log.d("GPSTest", "Here Again");
        } else {
            Log.d("GPSTest", "Nah");
        }
    }

    public boolean locationAllowed(Activity activity){
        return (activity.checkCallingOrSelfPermission("android.permission.ACCESS_FINE_LOCATION") == PackageManager.PERMISSION_GRANTED);
    }
}
