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
import com.example.mike9.cse_app.MatchesActivity;
import com.example.mike9.cse_app.R;
import com.example.mike9.cse_app.SignUpActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import static android.content.ContentValues.TAG;

public class HomeFragment extends Fragment implements View.OnClickListener  {

    private String email;
    private EditText updatePass;
    public Map<String, String> user;

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState){
        //TODO: fix the screen + check the default preferences for the user/move those to separate screen
        View v = inflater.inflate(R.layout.home_fragment,container,false);
        Button updatePasswordButton = v.findViewById(R.id.updatePass_button);
        updatePasswordButton.setOnClickListener(this);
        Button deleteAccountButton= v.findViewById(R.id.deleteAcct_button);
        deleteAccountButton.setOnClickListener(this);
        Button signOutButton = v.findViewById(R.id.signOut_button);
        signOutButton.setOnClickListener(this);
        Button getMatchesButton = v.findViewById(R.id.check_matches_button);
        getMatchesButton.setOnClickListener(this);
        email = getArguments().getString("EMAIL");
        updatePass = v.findViewById(R.id.updatePass_text);
        return v;
    }

    @Override
    public void onClick(View v){
        Activity activity = getActivity();
        switch (v.getId()){
            case R.id.updatePass_button:

                //String newPass = updatePass.getText().toString();
                String newPass = "hello";

                user.put("password", newPass);
                db.collection("users").document("g")
                        .delete();
                db.collection("users").document("g").set(user);



                break;
            case R.id.deleteAcct_button:
                //user.remove(v.getId());
                db.collection("users").document("g")
                        .delete();


                startActivity(new Intent(activity, MainActivity.class));
                break;
            case R.id.signOut_button:
                startActivity(new Intent(activity, MainActivity.class));
                break;

            case R.id.check_matches_button:
                Intent matchesIntent = new Intent(activity, MatchesActivity.class);
                matchesIntent.putExtra("EMAIL", email);
                startActivity(matchesIntent);
                break;

        }
    }
}
