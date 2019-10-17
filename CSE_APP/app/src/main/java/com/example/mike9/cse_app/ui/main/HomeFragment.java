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
import com.example.mike9.cse_app.SignUpActivity;

public class HomeFragment extends Fragment implements View.OnClickListener  {

    private String email;
    private EditText updatePass;

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.home_fragment,container,false);
        Button updatePasswordButton = v.findViewById(R.id.updatePass_button);
        updatePasswordButton.setOnClickListener(this);
        Button deleteAccountButton= v.findViewById(R.id.deleteAcct_button);
        deleteAccountButton.setOnClickListener(this);
        Button signOutButton = v.findViewById(R.id.signOut_button);
        signOutButton.setOnClickListener(this);
        email = getArguments().getString("EMAIL");
        updatePass = v.findViewById(R.id.updatePass_text);
        return v;
    }

    @Override
    public void onClick(View v){
        Activity activity = getActivity();
        switch (v.getId()){
            case R.id.updatePass_button:
                //update password assoicated with email in firebase to newPass
                String newPass = updatePass.getText().toString();
                break;
            case R.id.deleteAcct_button:
                //delete account associated with email in firebase (delete row with id email)
                startActivity(new Intent(activity, MainActivity.class));
                break;
            case R.id.signOut_button:
                startActivity(new Intent(activity, MainActivity.class));
                break;
        }
    }
}
