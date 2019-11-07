package com.example.mike9.cse_app;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

import static android.content.ContentValues.TAG;

public class MatchCalculator {

    static public void Calc (String loc, final String email){

        final FirebaseFirestore db = FirebaseFirestore.getInstance();

        final DocumentReference docRef = db.collection("users").document(email);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>(){
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    final DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                        final String userQ1 = document.get("q1").toString();
                        final String userQ2 = document.get("q2").toString();
                        final String userQ3 = document.get("q3").toString();


                        db.collection("users")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            //document.get("top10");

                                            Map<String, Float> top10 = new HashMap<>();

                                            for (QueryDocumentSnapshot document2 : task.getResult()) {
                                                String otherEmail = document2.get("email").toString();
                                                if (document2.get("answered?").equals("true") && !otherEmail.equals(email)) {
                                                    Log.d(TAG, document2.getId() + " => " + document2.getData());

                                                    String q1 = document2.get("q1").toString();
                                                    String q2 = document2.get("q2").toString();
                                                    String q3 = document2.get("q3").toString();

                                                    float comp = 0;
                                                    if (userQ1.equals(q1)) {
                                                        comp += 100;
                                                    }
                                                    if (userQ2.equals(q2)) {
                                                        comp += 100;
                                                    }
                                                    if (userQ3.equals(q3)) {
                                                        comp += 100;
                                                    }
                                                    comp /= 300;
                                                    comp *= 100;

                                                    float minVal = 400;
                                                    String keyReplace = "";
                                                    if (top10.size() == 10) {
                                                        for (Map.Entry<String, Float> entry : top10.entrySet())
                                                            if (entry.getValue() < minVal) {
                                                                minVal = entry.getValue();
                                                                keyReplace = entry.getKey();
                                                            }
                                                        ;
                                                        if (comp > minVal) {
                                                            top10.remove(keyReplace);
                                                            top10.put(otherEmail, comp);
                                                        }
                                                    } else if (top10.size() < 10) {
                                                        top10.put(otherEmail, comp);
                                                    }
                                                    docRef.update("top10", top10);
                                                    docRef.update("matchSize", top10.size());

                                                }
                                            }
                                        } else {
                                            Log.d(TAG, "Error getting documents: ", task.getException());
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


        if(!loc.equals("")){ }
    }

}
