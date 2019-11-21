package com.example.mike9.cse_app;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mike9.cse_app.ui.main.LoadingFragment;
import com.example.mike9.cse_app.ui.main.NoConnectionFragment;

public class NoConnectionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        NoConnectionFragment fragment = new NoConnectionFragment();
        setContentView(R.layout.main_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, fragment)
                    .commitNow();
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("onPause", "Log the onPause");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("onResume", "Log the onResume");
    }
}
