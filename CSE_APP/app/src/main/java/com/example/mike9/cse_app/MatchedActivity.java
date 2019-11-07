package com.example.mike9.cse_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.mike9.cse_app.ui.main.HomeFragment;
import com.example.mike9.cse_app.ui.main.MainFragment;
import com.example.mike9.cse_app.ui.main.MatchFragment;
import com.example.mike9.cse_app.ui.main.MatchedFragment;
import com.example.mike9.cse_app.ui.main.MatchedPairFragment;
import com.example.mike9.cse_app.ui.main.MatchesFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import static android.content.ContentValues.TAG;

public class MatchedActivity extends AppCompatActivity {
    String email;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        email = getIntent().getExtras().getString("EMAIL");
        Bundle bundle = new Bundle();
        bundle.putString("EMAIL", getIntent().getExtras().getString("EMAIL"));
        MatchedFragment fragment = new MatchedFragment();
        fragment.setArguments(bundle);
        setContentView(R.layout.main_activity);
        Log.d("onCreate", "Log the onCreate Matches");
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, fragment)
                    .commitNow();
            showMatches(email);

        }
    }

    @Override
    protected void onPause(){
        super.onPause();
        Log.d("onPause", "Log the onPause");
    }

    @Override
    protected void onResume(){
        super.onResume();
        Log.d("onResume", "Log the onResume");
    }

    protected void showMatches(String userEmail){
        //CHANGE TO GET ACTUAL NUMBER OF MATCHES
        Log.d("Match", "in showMatches");


        DocumentReference docRef = db.collection("users").document(email);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>(){
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                        String sizeStr = document.get("matchSize").toString();
                        int num_matches= Integer.parseInt(sizeStr);

                        String first_name;
                        String matchedEmail;
                        int percent_match;
                        Bundle bundle;
                        MatchedPairFragment fragment;
                        //setUp bundle of name and match %
                        //fragment.setArguments(bundle);
                        for(int i = 0; i<num_matches; i++){
                            fragment = new MatchedPairFragment();
                            bundle = new Bundle();
                            //get actual name and percent
                            first_name = "first"; //TODO: change to get the first name of match i
                            percent_match = 76; //TODO: change to get the match % of match i
                            matchedEmail = "email@osu.edu"; //TODO: change to get the (other) email of match i
                            bundle.putString("FIRSTNAME", first_name);
                            bundle.putInt("PERCENT", percent_match);
                            bundle.putString("EMAIL", matchedEmail);
                            fragment.setArguments(bundle);
                            getSupportFragmentManager().beginTransaction().add(R.id.matched_list, fragment).commitNow();
                        }

                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });

    }
}
