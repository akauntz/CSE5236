package com.example.mike9.cse_app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.mike9.cse_app.ui.main.HomeFragment;
import com.example.mike9.cse_app.ui.main.MainFragment;
import com.example.mike9.cse_app.ui.main.MatchFragment;
import com.example.mike9.cse_app.ui.main.MatchesFragment;

public class MatchesActivity extends AppCompatActivity {
    String email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = new Bundle();
        email = getIntent().getExtras().getString("EMAIL");
        bundle.putString("EMAIL", email);
        MatchesFragment fragment = new MatchesFragment();
        fragment.setArguments(bundle);
        setContentView(R.layout.main_activity);
        Log.d("onCreate", "Log the onCreate Matches");
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, fragment)
                    .commitNow();
            showMatches(email);

        }
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

    protected void showMatches(String userEmail){
        //CHANGE TO GET ACTUAL NUMBER OF MATCHES
        int num_matches = 3; //TODO: change to find total number of potential matches with userEmail
        String first_name;
        int percent_match;
        Bundle bundle;
        MatchFragment fragment;
        //setUp bundle of name and match %
        //fragment.setArguments(bundle);
        for(int i = 0; i<num_matches; i++){
            fragment = new MatchFragment();
            bundle = new Bundle();
            //get actual name and percent
            first_name = "first"; //TODO: change to get firstname of match #i
            percent_match = 76; //TODO: change to get match % of match #i
            bundle.putString("FIRSTNAME", first_name);
            bundle.putInt("PERCENT", percent_match);
            fragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction().add(R.id.matches, fragment).commitNow();
        }
    }
}
