package com.example.mike9.cse_app.ui.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.mike9.cse_app.HomeActivity;
import com.example.mike9.cse_app.MatchedActivity;
import com.example.mike9.cse_app.R;

public class MatchesFragment extends Fragment implements View.OnClickListener  {



    public static MatchesFragment newInstance() {
        return new MatchesFragment();
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.matches_fragment,container,false);
        Button returnHomeButton = v.findViewById(R.id.return_home_button);
        Button seeMatchedButton = v.findViewById(R.id.check_matched_button);
        returnHomeButton.setOnClickListener(this);
        seeMatchedButton.setOnClickListener(this);
        return v;
    }

    @Override
    public void onClick(View v){
        Activity activity = getActivity();
        switch (v.getId()){
            case R.id.return_home_button:
                startActivity(new Intent(activity, HomeActivity.class));
                break;
            case R.id.check_matched_button:
                Intent matchedIntent = new Intent(activity, MatchedActivity.class);
                startActivity(matchedIntent);
                break;
        }
    }
}
