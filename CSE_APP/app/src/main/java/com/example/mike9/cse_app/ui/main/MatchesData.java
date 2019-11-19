package com.example.mike9.cse_app.ui.main;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.mike9.cse_app.MainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import static android.content.ContentValues.TAG;

public class MatchesData {
    private static FirebaseFirestore db = FirebaseFirestore.getInstance();
    private static boolean match;
    public static void CreateMatch(String email1, String name, String email2, float percent){
        if(!MatchExists(email1, email2)) {
            Map<String, Object> match = new HashMap<>();
            match.put("name", name);
            match.put("match1?", false);
            match.put("match2?", false);
            match.put("show?", false);
            db.collection("users").document(email1).collection("matches").document(email2).set(match).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.d("PLZZ", "DocumentSnapshot successfully written!");
                }
            })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.w("PLZZ", "Error writing document", e);
                        }
                    });
        }
    }

    private static boolean MatchExists(String email1, String email2){
       //db.collection("users").document(email1).collection("matches").document(email2).get();
        match = false;
        DocumentReference docRef = db.collection("users").document(email1).collection("matches").document(email2);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>(){
            //@Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                        updateMatch(); //if document found match already exists
                    }
                } else {Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });
        return match;
    }

    private static void updateMatch(){
        match = true;
    }
}
