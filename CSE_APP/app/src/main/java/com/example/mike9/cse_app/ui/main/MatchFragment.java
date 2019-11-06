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
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.mike9.cse_app.HomeActivity;
import com.example.mike9.cse_app.MainActivity;
import com.example.mike9.cse_app.MatchActivity;
import com.example.mike9.cse_app.R;
import com.example.mike9.cse_app.SignUpActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import static android.content.ContentValues.TAG;

public class MatchFragment extends Fragment implements View.OnClickListener  {

    private String email;
    private EditText updatePass;
    private Map<Object, Object> user;

    public static MatchFragment newInstance() {
        return new MatchFragment();
    }

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    TextView nameText;
    TextView percentText;
    Button noMatchButton;
    Button yesMatchButton;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.match_fragment,container,false);;
        String name = getArguments().getString("FIRSTNAME");
        int percent = getArguments().getInt("PERCENT");
        nameText = v.findViewById(R.id.first_name);
        percentText = v.findViewById(R.id.match_percent);
        //noMatchButton = v.findViewById(R.id.no_match_button);
        yesMatchButton = v.findViewById(R.id.matched_button);
        //noMatchButton.setOnClickListener(this);
        yesMatchButton.setOnClickListener(this);
        setMatch(name,percent);
        return v;
    }

    @Override
    public void onClick(View v){
        Activity activity = getActivity();
        switch (v.getId()){
            //case R.id.no_match_button:
                //startActivity(new Intent(getActivity(), MatchActivity.class));
                //delete/flag potential match as nada
                //break;
            case R.id.matched_button:
                //flag as lovebirds and move to different match

                break;
        }
    }

    public void setMatch(String name, int percent) {
        nameText.setText(name);
        percentText.setText(Integer.toString(percent));
    }
}
