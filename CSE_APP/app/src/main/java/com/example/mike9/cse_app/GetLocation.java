package com.example.mike9.cse_app;

import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.util.Log;

import androidx.core.content.ContextCompat;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import static android.content.Context.LOCATION_SERVICE;

public class GetLocation {
    public static String getCityState(Context context){
        LocationManager locationManager = (LocationManager) context.getSystemService(LOCATION_SERVICE);
        /*if(locationAllowed()){
            return;
        }*/
        Criteria criteria = new Criteria();
        String bestProvider = locationManager.getBestProvider(criteria,true);
        if(ContextCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            Log.d("GPS", "Name GPS_PRovider:" + LocationManager.GPS_PROVIDER + "isEnabled? " +locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER));
            if(location == null){
                Log.d("GPS","No Location Found");
                return "Location Not Found";
                //locationText.setText("Location Not Found");
            }
            if (location != null){
                try {
                    Log.d("GPS", "Location found" + location);
                    Geocoder geocoder;
                    List<Address> addresses;
                    geocoder = new Geocoder(context, Locale.getDefault());
                    addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                    //locationText.setText(addresses.get(0).getLocality()+" ,"+addresses.get(0).getAdminArea());
                    return addresses.get(0).getLocality() + ", " + addresses.get(0).getAdminArea();
                    //TODO: Add state/city/whatever to database
                    //addresses.get(0).getLocality() -> returns city
                    //addresses.get(0).getAdminArea() -> returns state
                }catch (IOException e){
                    //locationText.setText("GPS failed. Try again.");
                    return "GPS failed. Please try again.";
                }
            }
        }
        return "Error setting up GPS";
    }
}
