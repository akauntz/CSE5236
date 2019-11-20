package com.example.mike9.cse_app.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.mike9.cse_app.MatchesActivity;
import com.example.mike9.cse_app.R;
import com.example.mike9.cse_app.UpdateMatches;

public class MatchFragment extends Fragment implements View.OnClickListener  {

    private String email, email2, name;



    public static MatchFragment newInstance() {
        return new MatchFragment();
    }

    TextView nameText;
    TextView percentText;
    Button noMatchButton;
    Button yesMatchButton;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.match_fragment,container,false);;
        name = getArguments().getString("FIRSTNAME");
        int percent = getArguments().getInt("PERCENT");
        email = getArguments().getString("EMAIL");
        email2 = getArguments().getString("EMAIL2");
        nameText = v.findViewById(R.id.first_name);
        percentText = v.findViewById(R.id.match_percent);
        noMatchButton = v.findViewById(R.id.no_match_button);
        yesMatchButton = v.findViewById(R.id.matched_button);
        noMatchButton.setOnClickListener(this);
        yesMatchButton.setOnClickListener(this);
        setMatch(name,percent);
        return v;
    }

    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.no_match_button:

                UpdateMatches.NoMatch(email,email2);
                startActivity(new Intent(getActivity(), MatchesActivity.class));
                break;
            case R.id.matched_button:
                UpdateMatches.YesMatch(email,email2,name);
                startActivity(new Intent(getActivity(), MatchesActivity.class));


                break;
        }
    }

    public void setMatch(String name, int percent) {
        nameText.setText(name);
        percentText.setText(Integer.toString(percent));
    }
}
