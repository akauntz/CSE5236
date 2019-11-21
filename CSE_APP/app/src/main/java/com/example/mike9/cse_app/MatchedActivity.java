package com.example.mike9.cse_app;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mike9.cse_app.ui.main.MatchedFragment;
import com.example.mike9.cse_app.ui.main.MatchedPairFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class MatchedActivity extends AppCompatActivity {
    String email;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        email = DataCache.getEmail();
        Bundle bundle = new Bundle();
        MatchedFragment fragment = new MatchedFragment();
        fragment.setArguments(bundle);
        setContentView(R.layout.main_activity);
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
        db.collection("users").document(email).collection("matched").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        String first_name = document.get("name").toString();
                        String email1 = document.get("email").toString();
                        int percent_match = Integer.parseInt(UpdateMatches.makeInt(document.get("percent").toString()));
                        MatchedPairFragment fragment = new MatchedPairFragment();
                        Bundle bundle = new Bundle();
                        bundle.putString("EMAIL", email1);
                        bundle.putString("FIRSTNAME", first_name);
                        bundle.putInt("PERCENT", percent_match);
                        fragment.setArguments(bundle);
                        getSupportFragmentManager().beginTransaction().add(R.id.matched_list, fragment).commitNow();

                    }
                } else {

                }
            }
        });


    }
}
