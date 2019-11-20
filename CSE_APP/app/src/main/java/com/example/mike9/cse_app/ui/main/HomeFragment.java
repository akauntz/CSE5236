package com.example.mike9.cse_app.ui.main;

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
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.mike9.cse_app.DataCache;
import com.example.mike9.cse_app.MainActivity;
import com.example.mike9.cse_app.MatchesActivity;
import com.example.mike9.cse_app.R;
import com.example.mike9.cse_app.ShowMessage;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import static android.content.ContentValues.TAG;

public class HomeFragment extends Fragment implements View.OnClickListener {

    private String email;//, firstName;
    private EditText updatePass;
    //TextView locationText;



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
        //email = getArguments().getString("EMAIL");
        email = DataCache.getEmail();
        //firstName = getArguments().getString("FIRSTNAME");
        updatePass = v.findViewById(R.id.updatePass_text);

        return v;
    }


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
                //MatchCalculator.Calc("", email);
                Intent matchesIntent = new Intent(activity, MatchesActivity.class);
                //matchesIntent.putExtra("EMAIL", email);
                //matchesIntent.putExtra("FIRSTNAME", firstName);
                startActivity(matchesIntent);
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