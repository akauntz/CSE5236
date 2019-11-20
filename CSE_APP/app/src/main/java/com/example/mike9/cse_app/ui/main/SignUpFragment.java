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
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.mike9.cse_app.MainActivity;
import com.example.mike9.cse_app.R;
import com.example.mike9.cse_app.ShowMessage;
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

public class SignUpFragment extends Fragment implements View.OnClickListener  {

    public EditText email, password, age, name, passwordCheck;
    public RadioButton male,female, other;
    public RadioGroup genderGroup;
    public String gender;

    public static SignUpFragment newInstance() {
        return new SignUpFragment();
    }

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    public Map<String, Object> user = new HashMap<>();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.signup_fragment,container,false);
        Button createAccountButton = v.findViewById(R.id.createAccount_button);
        createAccountButton.setOnClickListener(this);
        Button returnHomeButton = v.findViewById(R.id.returnHome_button);
        returnHomeButton.setOnClickListener(this);
        email = v.findViewById(R.id.signUp_email);
        password = v.findViewById(R.id.signUp_password);
        age = v.findViewById(R.id.signUp_age);
        name = v.findViewById(R.id.signUp_name);
        passwordCheck=v.findViewById(R.id.signUp_passwordConfirmation);

        male = v.findViewById(R.id.radioMale);
        other = v.findViewById(R.id.radioOther);
        female = v.findViewById(R.id.radioFemale);
        genderGroup = v.findViewById(R.id.radioGender);

        genderGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(male.isChecked()) {
                   gender="male";
                } else if(female.isChecked()) {
                    gender="female";
                } else {
                    gender="other";
                }
            }
        });
        return v;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.createAccount_button:
            final Editable fb_email = email.getText();
            final Editable fb_password = password.getText();
            final Editable fb_age = age.getText();
            final Editable fb_name = name.getText();
            final Editable fb_passwordCheck = passwordCheck.getText();


            if (fb_name.toString().equals("") || fb_password.toString().equals("") || fb_age.toString().equals("") || fb_email.toString().equals("") || gender.equals("")) {
                ShowMessage.show(getActivity(), "Missing required fields.");
            } else if (!fb_password.toString().equals(fb_passwordCheck.toString())) {
                ShowMessage.show(getActivity(), "Error, passwords don't match.");
            } else if (!fb_email.toString().contains("@")) {
                ShowMessage.show(getActivity(), "Invalid email.");
            } else if (Integer.parseInt(fb_age.toString()) < 18) {
                ShowMessage.show(getActivity(), "Must be 18 or older");
            } else {

                DocumentReference docRef = db.collection("users").document(fb_email.toString());
                docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    //@Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                ShowMessage.show(getActivity(), "Email already used");
                                Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                            } else {
                                Log.d(TAG, "No such document");
                                user.put("email", fb_email.toString());
                                user.put("password", fb_password.toString());
                                user.put("name", fb_name.toString());
                                user.put("age", fb_age.toString());
                                user.put("gender", gender);
                                user.put("answered?", "false");
                                user.put("interest", "");
                                user.put("state", "");
                                user.put("score",0);
                                db.collection("users").document(fb_email.toString())
                                        .set(user)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                Log.d(TAG, "DocumentSnapshot successfully written!");
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Log.w(TAG, "Error writing document", e);
                                            }
                                        });
                                Activity activity = getActivity();
                                startActivity(new Intent(activity, MainActivity.class));

                            }
                        } else {
                            Log.d(TAG, "get failed with ", task.getException());
                        }
                    }
                });


            }
            break;
            case R.id.returnHome_button:
                startActivity(new Intent(getActivity(), MainActivity.class));
                break;
        }
    }

}

