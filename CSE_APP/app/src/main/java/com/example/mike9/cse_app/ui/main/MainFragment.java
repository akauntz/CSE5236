package com.example.mike9.cse_app.ui.main;

import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.mike9.cse_app.R;

public class MainFragment extends Fragment implements View.OnClickListener {

    private MainViewModel mViewModel;

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        Log.d("onCreate", "Log the onCreateView MainFragment");
        View v = inflater.inflate(R.layout.main_fragment, container, false);
        Button matchesButton = v.findViewById(R.id.matches_button);
        matchesButton.setOnClickListener((View.OnClickListener) this);
        Button signUpButton = v.findViewById(R.id.signUp_button);
        signUpButton.setOnClickListener(this);
        Button logInButton = v.findViewById(R.id.login_button);
        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        Log.d("onCreate", "Log the onActivityCreated MainFragment");
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        // TODO: Use the ViewModel
    }

    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.login_button:
                //logIn
                break;
            case R.id.signUp_button:
                //signUp
                FragmentManager fm = getFragmentManager();
                Fragment fragment = new SignUpFragment();
                fm.beginTransaction().replace(R.id.container,fragment).commit(); //addToBackStack("signup_fragment")?
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
