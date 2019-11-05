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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import static android.content.ContentValues.TAG;

public class SignUpFragment extends Fragment implements View.OnClickListener  {

    public EditText email, password, age, name;
    public String gender;

    public static SignUpFragment newInstance() {
        return new SignUpFragment();
    }

    // Access a Cloud Firestore instance from your Activity
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    public Map<String, String> user = new HashMap<>();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.signup_fragment,container,false);
        Button createAccountButton = v.findViewById(R.id.createAccount_button);
        createAccountButton.setOnClickListener(this);
        email = v.findViewById(R.id.signUp_email);
        password = v.findViewById(R.id.signUp_password);
        age = v.findViewById(R.id.signUp_age);
        name = v.findViewById(R.id.signUp_name);
        //gender = v.findViewById(R.id.signUp_name);
        /*RadioGroup rg = (R.layout.signup_fragment,container,false);
        boolean checked = ((RadioButton) rg).isChecked();

        // Check which radio button was clicked
        switch(rg.getId()) {
            case R.id.radioFemale:
                if (checked)
                    gender="Female";
                    break;
            case R.id.radioMale:
                if (checked)
                    gender="Male";
                    break;
            case R.id.radioOther:
                if (checked)
                    gender="Other";
                    break;
        }*/

        return v;
    }
    public void onRadioButtonClicked(View v){

    }

    @Override
    public void onClick(View v){
        Editable fb_email = email.getText();
        Editable fb_password = password.getText();
        Editable fb_age = age.getText();
        Editable fb_name = name.getText();
        //Editable fb_gender = gender.getText();
        Log.d("data",fb_email.toString());
        Log.d("data",fb_password.toString());
        Log.d("data",fb_name.toString());
        Log.d("data",fb_age.toString());
       // Log.d("data",gender.toString());

        user.put("email", fb_email.toString());
        user.put("password", fb_password.toString());
        user.put("name", fb_name.toString());
        user.put("age", fb_age.toString());
       // user.put("gender", gender);
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
}
