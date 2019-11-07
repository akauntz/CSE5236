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
import com.example.mike9.cse_app.MatchCalculator;
import com.example.mike9.cse_app.ui.main.MatchesFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import static android.content.ContentValues.TAG;
import static java.lang.Float.parseFloat;

public class MatchesActivity extends AppCompatActivity {
    String email;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = new Bundle();
        email = getIntent().getExtras().getString("EMAIL");
        bundle.putString("EMAIL", email);
        MatchesFragment fragment = new MatchesFragment();
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

        DocumentReference docRef = db.collection("users").document(email);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>(){
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                        //String sizeStr = document.get("matchSize").toString();
                        //int num_matches= Integer.parseInt(sizeStr);
                        String top10Str = document.get("top10").toString();

                        HashMap<String,Float> top10 = new HashMap<>();
                        int num_matches= top10.size();
                        String em="";
                        String emAdd="";
                        String com="";
                        String comAdd="";
                        for(int i=1;i<top10Str.length()-1;i++){
                            if(top10Str.charAt(i)== '=') {
                                emAdd=em;
                                em="";
                                com="";
                            } else if (top10Str.charAt(i) == ',' || top10Str.charAt(i) == '}') {
                                comAdd=com;
                                top10.put(emAdd, Float.parseFloat(comAdd));
                                em="";
                                com="";
                            } else if(top10Str.charAt(i) != ' '){
                                em+= top10Str.charAt(i);
                                com += top10Str.charAt(i);
                            }


                        }

                       // Log.d("data", top10);



                        //setUp bundle of name and match %
                        //fragment.setArguments(bundle);

                        String topEmail="";
                        float compFloat= 0;
                        for (Map.Entry<String, Float> entry : top10.entrySet()) {
                            //for(int i = 0; i<num_matches; i++){
                            topEmail = entry.getKey();
                            compFloat = entry.getValue();

                            final float compFloat2=compFloat;

                            DocumentReference docRef = db.collection("users").document(topEmail);
                            docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>(){
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                    if (task.isSuccessful()) {
                                        DocumentSnapshot document = task.getResult();
                                        if (document.exists()) {
                                            Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                                            String first_name = document.get("name").toString();

                                            MatchFragment fragment = new MatchFragment();
                                            Bundle bundle = new Bundle();
                                            //get actual name and percent
                                            int percent_match =  Math.round(compFloat2);
                                            bundle.putString("FIRSTNAME", first_name);
                                            bundle.putInt("PERCENT", percent_match);
                                            fragment.setArguments(bundle);
                                            getSupportFragmentManager().beginTransaction().add(R.id.matches, fragment).commitNow();



                                        } else {
                                            Log.d(TAG, "No such document");
                                        }
                                    } else {
                                        Log.d(TAG, "get failed with ", task.getException());
                                    }

                                }
                            });



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