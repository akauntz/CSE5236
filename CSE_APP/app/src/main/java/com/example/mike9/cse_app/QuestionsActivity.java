package com.example.mike9.cse_app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.mike9.cse_app.ui.main.HomeFragment;
import com.example.mike9.cse_app.ui.main.MainFragment;
import com.example.mike9.cse_app.ui.main.QuestionsFragment;
import com.example.mike9.cse_app.ui.main.SignUpFragment;

public class QuestionsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("myDebug","before super");
        super.onCreate(savedInstanceState);
        Log.d("myDebug","after super");
        Bundle bundle = new Bundle();
        bundle.putString("EMAIL", getIntent().getExtras().getString("EMAIL"));
        QuestionsFragment fragment = new QuestionsFragment();
        fragment.setArguments(bundle);
//        Bundle bundle = new Bundle();
//        bundle.putString("EMAIL", getIntent().getExtras().getString("EMAIL"));
//        HomeFragment fragment = new HomeFragment();
//        fragment.setArguments(bundle);
        setContentView(R.layout.main_activity);
        Log.d("myDebug", "Starting questionsFragment");
        if (savedInstanceState == null) { //HomeFragment.newInstance()
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
