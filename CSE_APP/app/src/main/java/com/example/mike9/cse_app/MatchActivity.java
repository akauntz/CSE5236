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

public class MatchActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Bundle bundle = new Bundle();
        //bundle.putString("EMAIL", getIntent().getExtras().getString("EMAIL"));
        //HomeFragment fragment = new HomeFragment();
        //fragment.setArguments(bundle);
        setContentView(R.layout.matches_fragment);
        Log.d("onCreate", "Log the onCreate Matches");
        if (savedInstanceState == null) { //HomeFragment.newInstance()
            Log.d("here", "hereMatch");
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.matches, MatchFragment.newInstance())
                    .commitNow();

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
}
