package com.example.mike9.cse_app.ui.main;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.text.Editable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.mike9.cse_app.GetLocation;
import com.example.mike9.cse_app.MainActivity;
import com.example.mike9.cse_app.MatchesActivity;
import com.example.mike9.cse_app.R;
import com.example.mike9.cse_app.SignUpActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static android.content.ContentValues.TAG;
import static android.content.Context.LOCATION_SERVICE;

public class HomeFragment extends Fragment implements View.OnClickListener {

    private String email;
    private EditText updatePass;
    TextView locationText;
    GetLocation locationListener;
    Criteria criteria;
    LocationManager locationManager;
    Looper looper;


    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        //TODO: fix the screen + check the default preferences for the user/move those to separate screen
        View v = inflater.inflate(R.layout.home_fragment, container, false);
        Button updatePasswordButton = v.findViewById(R.id.updatePass_button);
        updatePasswordButton.setOnClickListener(this);
        Button deleteAccountButton = v.findViewById(R.id.deleteAcct_button);
        deleteAccountButton.setOnClickListener(this);
        Button signOutButton = v.findViewById(R.id.signOut_button);
        signOutButton.setOnClickListener(this);
        Button getMatchesButton = v.findViewById(R.id.check_matches_button);
        getMatchesButton.setOnClickListener(this);
        Button getLocationButton = v.findViewById(R.id.location_button);
        getLocationButton.setOnClickListener(this);
        locationText = v.findViewById(R.id.locationText);
        email = getArguments().getString("EMAIL");
        updatePass = v.findViewById(R.id.updatePass_text);
        locationListener = new GetLocation();
        criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_COARSE);
        criteria.setPowerRequirement(Criteria.POWER_LOW);
        criteria.setAltitudeRequired(false);
        criteria.setBearingRequired(false);
        criteria.setSpeedRequired(false);
        criteria.setCostAllowed(true);
        criteria.setHorizontalAccuracy(Criteria.ACCURACY_HIGH);
        criteria.setVerticalAccuracy(Criteria.ACCURACY_HIGH);
        //locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        looper = null;
        return v;
    }

   /* @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }*/

    @Override
    public void onClick(View v) {
        Activity activity = getActivity();
        switch (v.getId()) {
            case R.id.updatePass_button:

                String newPass = updatePass.getText().toString();

                DocumentReference docRef = db.collection("users").document(email);
                docRef.update("password", newPass);

                break;

            case R.id.deleteAcct_button:
                db.collection("users").document(email)
                        .delete()
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.d(TAG, "DocumentSnapshot successfully deleted!");
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w(TAG, "Error deleting document", e);
                            }
                        });


                startActivity(new Intent(activity, MainActivity.class));
                break;
            case R.id.signOut_button:
                startActivity(new Intent(activity, MainActivity.class));
                break;

            case R.id.check_matches_button:
                Intent matchesIntent = new Intent(activity, MatchesActivity.class);
                matchesIntent.putExtra("EMAIL", email);
                startActivity(matchesIntent);
                break;

            case R.id.location_button:
                Log.d("test", "here");

                Log.d("test", "there");
                if(locationAllowed()){
                    Log.d("GPS", "Allowed");
                    //LocationManager myLocation = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                    //locationListener.getLocation(criteria, looper, getActivity());
                    //locationText.setText(getCityLocation());
                    setUpLocationListener();
                } else {
                    Log.d("GPS", "Denied");
                    ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION}, 1);
                }
                //gets here immediately when askign for gps
                Log.d("GPS", "here");


                //locationText.setText("location2");
                break;

        }
    }


    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.d("GPS", "accepted");
                } else {
                    Log.d("GPS", "denied");
                }
                return;
            }
        }
    }

    public boolean locationAllowed(){
        return (getActivity().checkCallingOrSelfPermission("android.permission.ACCESS_COARSE_LOCATION") == PackageManager.PERMISSION_GRANTED && getActivity().checkCallingOrSelfPermission("android.permission.ACCESS_FINE_LOCATION") == PackageManager.PERMISSION_GRANTED);
    }

    public String getCityLocation(){
        LocationManager locationManager = (LocationManager) locationListener.getSystemService(LOCATION_SERVICE);
        if(locationAllowed()){
            return "city";
        }
        return "city";
    }

    public void setUpLocationListener(){
        LocationManager locationManager = (LocationManager) getActivity().getSystemService(LOCATION_SERVICE);
        /*if(locationAllowed()){
            return;
        }*/
        Criteria criteria = new Criteria();
        String bestProvider = locationManager.getBestProvider(criteria,true);
        if(ContextCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            Log.d("GPS", "Name GPS_PRovider:" + LocationManager.GPS_PROVIDER + "isEnabled? " +locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER));
            if(location == null){
                Log.d("GPS","No Location Found");
            }
            if (location != null){
                Log.d("GPS","Location found" + location);
            }
        }

    }
    public void onLocationChanged(Location location){
        Geocoder geocoder;
        List<Address> addresses;
        geocoder = new Geocoder(getContext(), Locale.getDefault());

        double lat = location.getLatitude();
        double longitude = location.getLongitude();

        try{
            Log.d("GPS", "Latitude, Longitude"+lat+" "+ longitude);
            addresses = geocoder.getFromLocation(lat, longitude,1);
            if (addresses != null && addresses.size() > 0){
                String city = addresses.get(0).getLocality();
                String state = addresses.get(0).getAdminArea();

                Log.d("GPS", "City: " + city);
                Log.d("GPS", "State: " + state);
            }
        } catch (IOException e){
            Log.d("errrrror",e.toString());
            e.printStackTrace();
        }
    }
}