package com.example.mike9.cse_app.ui.main;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.example.mike9.cse_app.GetLocation;
import com.example.mike9.cse_app.LoadingActivity;
import com.example.mike9.cse_app.MatchCalculator;
import com.example.mike9.cse_app.R;
import com.example.mike9.cse_app.ShowMessage;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import static android.content.ContentValues.TAG;

public class LocationFragment extends Fragment implements View.OnClickListener{
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    private String email;
    private EditText state;
    TextView locationText;



    public static LocationFragment newInstance() {
        return new LocationFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        //TODO: fix the screen + check the default preferences for the user/move those to separate screen
        View v = inflater.inflate(R.layout.location_fragment, container, false);
        Button getGPSButton = v.findViewById(R.id.FindLocation_button);
        getGPSButton.setOnClickListener(this);
        Button confirmButton = v.findViewById(R.id.ConfirmLocation_button);
        confirmButton.setOnClickListener(this);
        state = v.findViewById(R.id.Location_editText);
        email = getArguments().getString("EMAIL");

        return v;
    }

    @Override
    public void onClick(View v){

        switch(v.getId()){
            case R.id.FindLocation_button:
                if(locationAllowed()){
                    Log.d("GPS", "Allowed");

                    locationText.setText(GetLocation.getCityState(getActivity()));
                } else {
                    Log.d("GPS", "Denied");
                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION}, 1);
                }
                break;

            case R.id.ConfirmLocation_button:
                if(state.getText().toString() == ""){ //add more confirmation
                    ShowMessage.show(getActivity(), "Please enter a state");
                } else {
                    //add state to document
                    final DocumentReference docRef = db.collection("users").document(email);
                    docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {
                                DocumentSnapshot document = task.getResult();
                                if (document.exists()) {
                                    Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                                    //String storedPassword = document.get("password").toString();
                                    docRef.update("state", state.getText().toString().toLowerCase());
                                } else {
                                    Log.d(TAG, "No such document");
                                }
                            } else {
                                Log.d(TAG, "get failed with ", task.getException());
                            }
                        }
                    });
                    Intent loadIntent = new Intent(getActivity(), LoadingActivity.class);
                    loadIntent.putExtra("EMAIL", email);
                    startActivity(loadIntent);
                    //TODO: MOVE TO LOADING INTENT
                }
                break;
        }
    }

    public boolean locationAllowed(){
        return (getActivity().checkCallingOrSelfPermission("android.permission.ACCESS_COARSE_LOCATION") == PackageManager.PERMISSION_GRANTED && getActivity().checkCallingOrSelfPermission("android.permission.ACCESS_FINE_LOCATION") == PackageManager.PERMISSION_GRANTED);
    }

}
