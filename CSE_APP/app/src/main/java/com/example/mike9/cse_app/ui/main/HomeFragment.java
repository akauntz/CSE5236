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
import com.example.mike9.cse_app.MatchCalculator;
import com.example.mike9.cse_app.MatchesActivity;
import com.example.mike9.cse_app.R;
import com.example.mike9.cse_app.ShowMessage;
import com.example.mike9.cse_app.SignUpActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
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
        Button testButton = v.findViewById(R.id.testButton);
        testButton.setOnClickListener(this);
        locationText = v.findViewById(R.id.locationText);
        email = getArguments().getString("EMAIL");
        updatePass = v.findViewById(R.id.updatePass_text);
        MatchCalculator.Calc("", email);

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

                ShowMessage.show(getActivity(), "Password updated");

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
                MatchCalculator.Calc("", email);
                Intent matchesIntent = new Intent(activity, MatchesActivity.class);
                matchesIntent.putExtra("EMAIL", email);
                startActivity(matchesIntent);
                break;

            case R.id.location_button:

                Log.d("test", "here");

                Log.d("test", "there");
                if(locationAllowed()){
                    Log.d("GPS", "Allowed");

                    locationText.setText(GetLocation.getCityState(getActivity()));
                } else {
                    Log.d("GPS", "Denied");
                    ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION}, 1);
                }
                //gets here immediately when asking for gps
                Log.d("GPS", "here");

                break;

            case R.id.testButton:
                //CollectionReference test = db.collection("users").document("@2").collection("test");
                Map<String, Object> user = new HashMap<>();
                user.put("first", "Ada");
                user.put("last", "Lovelace");
                user.put("born", 1815);

// Add a new document with a generated ID
                db.collection("test")
                        .add(user)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Log.d("PLZZZ", "DocumentSnapshot added with ID: " + documentReference.getId());
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w("PLZZZZ", "Error adding document", e);
                            }
                        });
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

}