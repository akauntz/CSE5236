package com.example.mike9.cse_app;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mike9.cse_app.ui.main.LocationFragment;

public class LocationActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = new Bundle();
        bundle.putString("INTEREST", getIntent().getExtras().getString("INTEREST"));
        bundle.putInt("POINTS", getIntent().getExtras().getInt("POINTS"));
        LocationFragment fragment = new LocationFragment();
        fragment.setArguments(bundle);
        setContentView(R.layout.main_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, fragment)
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
