package com.example.mike9.cse_app;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mike9.cse_app.ui.main.LoadingFragment;

public class LoadingActivity extends AppCompatActivity {
    private String email;
    private String state;
    private int points;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = new Bundle();
        email = getIntent().getExtras().getString("EMAIL");
        state = getIntent().getExtras().getString("STATE");
        points = getIntent().getExtras().getInt("POINTS");
        bundle.putString("EMAIL", email);
        LoadingFragment fragment = new LoadingFragment();
        fragment.setArguments(bundle);
        setContentView(R.layout.main_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, fragment)
                    .commitNow();
        }
        calcAndUpdateMatches();
        Intent homeIntent = new Intent(this, HomeActivity.class);
        homeIntent.putExtra("EMAIL", email);
        startActivity(homeIntent);

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

    private void calcAndUpdateMatches(){
        UpdateMatches.fillMatches(email, points, state);
    }

}
