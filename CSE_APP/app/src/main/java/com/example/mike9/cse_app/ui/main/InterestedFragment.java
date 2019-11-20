package com.example.mike9.cse_app.ui.main;

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
import androidx.fragment.app.Fragment;

import com.example.mike9.cse_app.DataCache;
import com.example.mike9.cse_app.LocationActivity;
import com.example.mike9.cse_app.R;
import com.example.mike9.cse_app.ShowMessage;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import static android.content.ContentValues.TAG;

public class InterestedFragment extends Fragment implements View.OnClickListener {

    private String email;
    private int points;
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
        View v = inflater.inflate(R.layout.interested_fragment, container, false);
        Button submitButton = v.findViewById(R.id.submitInterest_button);
        submitButton.setOnClickListener(this);
        //email = getArguments().getString("EMAIL");
        email = DataCache.getEmail();
        points = getArguments().getInt("POINTS");
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
                    interest="female";
                } else if(radioOther.isChecked()){
                    interest="other";
                }
            }
        });


        return v;
    }


    @Override
    public void onClick(View v) {
        final DocumentReference docRef = db.collection("users").document(email);
        switch (v.getId()) {
            case R.id.submitInterest_button:


                if(interest!=""){

                    docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {
                                DocumentSnapshot document = task.getResult();
                                if (document.exists()) {
                                    docRef.update("interest", interest);
                                    Intent locationIntent = new Intent(getActivity(), LocationActivity.class);
                                    //locationIntent.putExtra("EMAIL", email);
                                    locationIntent.putExtra("INTEREST", interest);
                                    locationIntent.putExtra("POINTS", points);
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


}
