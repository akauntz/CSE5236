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

import com.example.mike9.cse_app.HomeActivity;
import com.example.mike9.cse_app.MainActivity;
import com.example.mike9.cse_app.MatchActivity;
import com.example.mike9.cse_app.MatchedActivity;
import com.example.mike9.cse_app.R;
import com.example.mike9.cse_app.SignUpActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import static android.content.ContentValues.TAG;

public class MatchesFragment extends Fragment implements View.OnClickListener  {

    private String email;


    public static MatchesFragment newInstance() {
        return new MatchesFragment();
    }

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    Button returnHomeButton;
    Button seeMatchedButton;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.matches_fragment,container,false);
        returnHomeButton = v.findViewById(R.id.return_home_button);
        seeMatchedButton = v.findViewById(R.id.check_matched_button);
        returnHomeButton.setOnClickListener(this);
        seeMatchedButton.setOnClickListener(this);
        email = getArguments().getString("EMAIL");
        return v;
    }

    @Override
    public void onClick(View v){
        Activity activity = getActivity();
        switch (v.getId()){
            case R.id.return_home_button:
                Intent homeIntent = new Intent(activity, HomeActivity.class);
                homeIntent.putExtra("EMAIL", email);
                startActivity(homeIntent);
                break;
            case R.id.check_matched_button:
                Intent matchedIntent = new Intent(activity, MatchedActivity.class);
                matchedIntent.putExtra("EMAIL", email);
                startActivity(matchedIntent);
                break;
        }
    }
}
