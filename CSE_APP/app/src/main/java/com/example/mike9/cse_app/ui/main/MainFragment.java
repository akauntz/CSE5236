package com.example.mike9.cse_app.ui.main;

import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProviders;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.mike9.cse_app.HomeActivity;
import com.example.mike9.cse_app.MainActivity;
import com.example.mike9.cse_app.QuestionsActivity;
import com.example.mike9.cse_app.R;
import com.example.mike9.cse_app.SignUpActivity;

public class MainFragment extends Fragment implements View.OnClickListener {

    private MainViewModel mViewModel;
    private EditText email;

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.main_fragment, container, false);
        Button matchesButton = v.findViewById(R.id.matches_button);
        matchesButton.setOnClickListener((View.OnClickListener) this);
        Button signUpButton = v.findViewById(R.id.signUp_button);
        signUpButton.setOnClickListener(this);
        Button logInButton = v.findViewById(R.id.login_button);
        logInButton.setOnClickListener(this);
        email = v.findViewById(R.id.userLogin);
        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        // TODO: Use the ViewModel? -> not my TODO but leaving in case one of you two
        //I think this was just part of the template, so don't think we need anything here
    }

    @Override
    public void onClick(View v){
        //TODO: need to verify that the user has acct, right password before signing in
        Activity activity = getActivity();
        switch (v.getId()){
            case R.id.login_button:
                String userEmail = email.getText().toString();
                String questions[]  = getResources().getStringArray(R.array.matching_questions);
                int questionNum = 3; //TODO: change this to the num questions answered by the user
                if(questionNum < questions.length){
                    Intent questionsIntent = new Intent(activity, QuestionsActivity.class);
                    questionsIntent.putExtra("EMAIL", email.getText().toString());
                    questionsIntent.putExtra("NUMQUESTIONS", questionNum);
                    startActivity(questionsIntent);
                } else {
                    //logIn
                Intent logInIntent = new Intent(activity, HomeActivity.class);
                logInIntent.putExtra("EMAIL", email.getText().toString());
                startActivity(logInIntent);
                }
                break;
            case R.id.signUp_button:
                //signUp
                startActivity(new Intent(activity, SignUpActivity.class));
//                FragmentManager fm = getFragmentManager();
//                Fragment fragment = new SignUpFragment();
//                fm.beginTransaction().replace(R.id.container,fragment).commit(); //addToBackStack("signup_fragment")?
                break;
            case R.id.matches_button:
                Log.d("buttonClick", "clicked Button");
                int test_int = 7;
                Log.d("checkpoint3", "Look at the step through here 1");
                test_int = 12;
                Log.d("checkpoint3", "Now we step here to 2");
                test_int = 5;
                Log.d("checkpoint3", "Now we step here to 3");
                break;
        }

    }

}
