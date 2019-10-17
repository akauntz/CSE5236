package com.example.mike9.cse_app.ui.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.mike9.cse_app.MainActivity;
import com.example.mike9.cse_app.R;

public class SignUpFragment extends Fragment implements View.OnClickListener  {

    public EditText email, password;

    public static SignUpFragment newInstance() {
        return new SignUpFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.signup_fragment,container,false);
        Button createAccountButton = v.findViewById(R.id.createAccount_button);
        createAccountButton.setOnClickListener(this);
        email = v.findViewById(R.id.signUp_email);
        password = v.findViewById(R.id.signUp_password);
        return v;
    }

    @Override
    public void onClick(View v){
        Editable fb_email = email.getText();
        Editable fb_password = password.getText();
        Log.d("data",fb_email.toString());
        Log.d("data",fb_password.toString());
        //Would create account on firebase here
        Activity activity = getActivity();
        startActivity(new Intent(activity, MainActivity.class));
    }
}
