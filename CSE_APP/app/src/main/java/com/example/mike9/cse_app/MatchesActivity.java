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
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

import static android.content.ContentValues.TAG;
import static java.lang.Float.parseFloat;

public class MatchesActivity extends AppCompatActivity {
    String email;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private String emails[];
    private int emailCount = 0;

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
            showMatches2(email);

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


    protected void showMatches2(String userEmail) {


        db.collection("users").document(email).collection("matches").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Log.d("PLZZZ: ", "yes " + document.get("name"));
                        String first_name = document.get("name").toString();
                        String email1 = document.getId().toString();
                        int percent_match = Integer.parseInt(makeInt(document.get("percent").toString()));
                        MatchFragment fragment = new MatchFragment();
                        Bundle bundle = new Bundle();
                        Log.d("PLZZZ: ", "Doc Snap: " + document.getData());
                        Log.d("PLZZ ", "email1: " + email1+ "first_name: " +first_name + "percent_match: " + percent_match);
                        bundle.putString("EMAIL", email1);
                        bundle.putString("FIRSTNAME", first_name);
                        bundle.putInt("PERCENT", percent_match);
                        fragment.setArguments(bundle);
                        getSupportFragmentManager().beginTransaction().add(R.id.matches, fragment).commitNow();

                    }
                } else {

                }
            }
        });
    }

    private String makeInt(String str){
        int strEnd = 0;
        while (strEnd<str.length() && str.charAt(strEnd)!='.'){
            strEnd++;
        }
        return str.substring(0,strEnd);
    }

    private void addEmail(String e1){
        emails[emailCount++] = e1;

    }
}