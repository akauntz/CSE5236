package com.example.mike9.cse_app.ui.main;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.example.mike9.cse_app.GetLocation;
import com.example.mike9.cse_app.LocationActivity;
import com.example.mike9.cse_app.MainActivity;
import com.example.mike9.cse_app.MatchCalculator;
import com.example.mike9.cse_app.MatchesActivity;
import com.example.mike9.cse_app.R;
import com.example.mike9.cse_app.ShowMessage;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import static android.content.ContentValues.TAG;

public class InterestedFragment extends Fragment implements View.OnClickListener {

    private String email;
    private EditText updatePass;
    TextView locationText;
    public RadioButton radioMen, radioWomen, radioOther;
    public RadioGroup radioGroup;
    private String interest ="";



    public static InterestedFragment newInstance() {
        return new InterestedFragment();
    }

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        //TODO: fix the screen + check the default preferences for the user/move those to separate screen
        View v = inflater.inflate(R.layout.interested_fragment, container, false);
        Button submitButton = v.findViewById(R.id.submitInterest_button);
        submitButton.setOnClickListener(this);
        email = getArguments().getString("EMAIL");
        radioMen = v.findViewById(R.id.radioMen);
        radioWomen = v.findViewById(R.id.radioWomen);
        radioOther = v.findViewById(R.id.radioOther);
        radioGroup = v.findViewById(R.id.radioGroupInterest);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(radioMen.isChecked()) {
                    interest="male";
                } else if(radioWomen.isChecked()) {
                    interest="women";
                } else if(radioOther.isChecked()){
                    interest="other";
                }
            }
        });


        return v;
    }

   /* @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }*/

    @Override
    public void onClick(View v) {
        Activity activity = getActivity();
        final DocumentReference docRef = db.collection("users").document(email);
        switch (v.getId()) {
            case R.id.submitInterest_button:


                if(interest!=""){

                    docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {
                                DocumentSnapshot document = task.getResult();
                                if (document.exists()) {
                                    Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                                    //String storedPassword = document.get("password").toString();
                                    docRef.update("interest", interest);
                                    Intent locationIntent = new Intent(getActivity(), LocationActivity.class);
                                    locationIntent.putExtra("EMAIL", email);
                                    startActivity(locationIntent);
                                } else {
                                    Log.d(TAG, "No such document");
                                }
                            } else {
                                Log.d(TAG, "get failed with ", task.getException());
                            }
                        }
                    });
                } else {
                    ShowMessage.show(getActivity(), "Please select an interest.");
                }

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
