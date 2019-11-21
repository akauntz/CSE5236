package com.example.mike9.cse_app.ui.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.mike9.cse_app.R;

public class MatchedPairFragment extends Fragment  {


    public static MatchedPairFragment newInstance() {
        return new MatchedPairFragment();
    }

    TextView nameText;
    TextView percentText;
    TextView emailText;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.matched_pair_fragment,container,false);;
        String name = getArguments().getString("FIRSTNAME");
        int percent = getArguments().getInt("PERCENT");
        String email = getArguments().getString("EMAIL");
        nameText = v.findViewById(R.id.matched_first_name);
        percentText = v.findViewById(R.id.matched_match_percent);
        emailText = v.findViewById(R.id.matched_contact);
        setMatch(name,email,percent);
        return v;
    }


    public void setMatch(String name, String email, int percent) {
        nameText.setText(name);
        emailText.setText(email);
        percentText.setText(Integer.toString(percent));
    }
}
