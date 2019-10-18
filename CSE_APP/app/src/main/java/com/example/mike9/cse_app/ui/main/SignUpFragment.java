package com.example.mike9.cse_app.ui.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.mike9.cse_app.MainActivity;
import com.example.mike9.cse_app.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import static android.content.ContentValues.TAG;

public class SignUpFragment extends Fragment implements View.OnClickListener  {

    public EditText email, password;

    public static SignUpFragment newInstance() {
        return new SignUpFragment();
    }

    // Access a Cloud Firestore instance from your Activity
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.signup_fragment,container,false);
        Button createAccountButton = v.findViewById(R.id.createAccount_button);
        createAccountButton.setOnClickListener(this);
        email = v.findViewById(R.id.signUp_email);
        password = v.findViewById(R.id.signUp_password);

        return v;
    }

    @Override
    public void onClick(View v){
        Editable fb_email = email.getText();
        Editable fb_password = password.getText();
        Log.d("data",fb_email.toString());
        Log.d("data",fb_password.toString());
        
        Map<Object, Object> user = new HashMap<>();
        user.put(fb_email, fb_password);
        db.collection("users")
                .add(user)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                    }
                });

        Activity activity = getActivity();
        startActivity(new Intent(activity, MainActivity.class));
    }
}
