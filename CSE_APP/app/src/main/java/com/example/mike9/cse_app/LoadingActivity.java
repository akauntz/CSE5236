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
        email = DataCache.getEmail();
        state = getIntent().getExtras().getString("STATE");
        points = getIntent().getExtras().getInt("POINTS");
        LoadingFragment fragment = new LoadingFragment();
        setContentView(R.layout.main_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, fragment)
                    .commitNow();
        }
        calcAndUpdateMatches();
        startActivity(new Intent(this, HomeActivity.class));

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
