package com.example.mike9.cse_app.ui.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.mike9.cse_app.HomeActivity;
import com.example.mike9.cse_app.R;
import com.google.firebase.firestore.FirebaseFirestore;

public class MatchedFragment extends Fragment implements View.OnClickListener  {

    private String email;


    public static MatchedFragment newInstance() {
        return new MatchedFragment();
    }

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    Button returnHomeButton;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.matched_fragment,container,false);
        returnHomeButton = v.findViewById(R.id.return_home_button_2);
        returnHomeButton.setOnClickListener(this);
        email = getArguments().getString("EMAIL");
        return v;
    }

    @Override
    public void onClick(View v){
        Activity activity = getActivity();
        switch (v.getId()){
            case R.id.return_home_button_2:
                Intent homeIntent = new Intent(activity, HomeActivity.class);
                homeIntent.putExtra("EMAIL",email);
                startActivity(homeIntent);
                break;
        }
    }
}
