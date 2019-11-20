package com.example.mike9.cse_app;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mike9.cse_app.ui.main.InterestedFragment;
import com.example.mike9.cse_app.ui.main.LoadingFragment;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class LoadingActivity extends AppCompatActivity {
    private String email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = new Bundle();
        email = getIntent().getExtras().getString("EMAIL");
        Log.d("LOADINGACT: ", email);
        bundle.putString("EMAIL", email);
        LoadingFragment fragment = new LoadingFragment();
        fragment.setArguments(bundle);
        setContentView(R.layout.main_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, fragment)
                    .commitNow();
        }
        calcAndUpdateMatches();
        Intent homeIntent = new Intent(this, HomeActivity.class);
        homeIntent.putExtra("EMAIL", email);
        startActivity(homeIntent);

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

    //TODO: QUERY MATCHES WHERE USERS GENDER AND STATE MATCH EMAILS INTEREST AND STATE
    //GET POINTS AND RUN THROUGH MATCH CALC
    //IF PERCENT > X ADD TO TWO TABLES
    private void calcAndUpdateMatches(){
        UpdateMatches.fillMatches(email);
        /*Log.d("LOADINGACT: ", "HEREEE");
        try{
            Thread.sleep(5000);
        } catch (InterruptedException e){

        }
        Log.d("LOADINGACT: ", "HEREEE1");
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference test = db.collection("users").document(email).collection("test");
        Map<String, Object> user = new HashMap<>();
        for(int c=0; c<2;c++) {
            Log.d("LOADINGACT: ", "HEREEE2");
            user.put("first", "test");
            user.put("email", c);
            user.put("bool1", true);

// Add a new document with a generated ID
            test.document("test")
                    .set(user)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.d("LOADINGACT", "DocumentSnapshot successfully written!");
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.w("LOADINGACT", "Error writing document", e);
                        }
                    });
        }*/
    }

}
