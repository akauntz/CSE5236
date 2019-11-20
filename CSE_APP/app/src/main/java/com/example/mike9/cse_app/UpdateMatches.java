package com.example.mike9.cse_app;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
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

public class UpdateMatches {
    final static FirebaseFirestore db = FirebaseFirestore.getInstance();
    private static String state;
    private static String interested ;
    private static int score;
    private static String name1;
    final static float MATCH_PERCENT = 65;

    public static void fillMatches(String email){
        Map<String, Object> user = new HashMap<>();
        state = "";
        interested = "";
        score = 0;
        name1 = "";
        final String email1 = email;
        final DocumentReference docRef = db.collection("users").document(email);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        updateState(document.get("state").toString());
                        updateInterest(document.get("interest").toString());
                        updateScore(document.get("score").toString());
                        updateName(document.get("name").toString());
                        db.collection("users").whereEqualTo("state",state).whereEqualTo("gender", interested).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>(){
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task){
                                if(task.isSuccessful()){
                                    for (QueryDocumentSnapshot document : task.getResult()){
                                        Log.d("PLZZZ: ", "yes");
                                        int points = Integer.parseInt(makeInt(document.get("score").toString()));
                                        Log.d("PLZZZ: ", "points: " + points + "  score:"+ score);
                                        float percent_match = MatchCalc.getPercent(score, points);
                                        Log.d("PLZZZ: ", "Percent_match: " + percent_match);
                                        if(percent_match > MATCH_PERCENT){
                                            Log.d("PLZZZ: ", "yep");
                                            String email2 = document.get("email").toString();
                                            String name2 = document.get("name").toString();
                                            Log.d("PLZZZ: ", "email1: " + email2 + "name2: " + name2 + "email1: " + email1);
                                            addMatches(email1, name1, email2, name2, percent_match);
                                        }
                                    }
                                } else {

                                }
                            }
                        });

                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });
    }

    static private void updateState(String st){
        state = st;
    }

    static private void updateInterest(String intrst){
        interested=intrst;
    }

    static private void updateScore(String s){
        score = Integer.parseInt(makeInt(s));
    }

    static private void updateName(String n){
        name1 = n;
    }

    static private String makeInt(String str){
        int strEnd = 0;
        while (strEnd<str.length() && str.charAt(strEnd)!='.'){
            strEnd++;
        }
        return str.substring(0,strEnd);
    }

    static private void addMatches(String e1, String n1, String e2, String n2, float per){
        CollectionReference ref1 = db.collection("users").document(e1).collection("matches");
        CollectionReference ref2 = db.collection("users").document(e2).collection("matches");
        Map<String, Object> user1 = new HashMap<>();
        user1.put("name", n1);
        user1.put("percent", per);
        user1.put("bool1", false);
        user1.put("bool2", false);
        user1.put("show", true);

        Map<String, Object> user2 = new HashMap<>();
        user2.put("name", n2);
        user2.put("percent", per);
        user2.put("bool1", false);
        user2.put("bool2", false);
        user2.put("show", true);

// Add a new document with a generated ID
        ref1.document(e2)
                .set(user2)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("PLZZ", "DocumentSnapshot successfully written! UNO");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("PLZZ", "Error writing document UNO", e);
                    }
                });
        ref2.document(e1)
                .set(user1)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("PLZZ", "DocumentSnapshot successfully written! DOS");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("PLZZ", "Error writing document DOS", e);
                    }
                });
    }
}
