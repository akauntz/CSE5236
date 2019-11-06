package com.example.mike9.cse_app.ui.main;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.mike9.cse_app.HomeActivity;
import com.example.mike9.cse_app.MainActivity;
import com.example.mike9.cse_app.QuestionsActivity;
import com.example.mike9.cse_app.R;
import com.example.mike9.cse_app.SignUpActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import static android.content.ContentValues.TAG;

public class QuestionsFragment extends Fragment implements View.OnClickListener  {

    //private String[] questions;
    //private int questionNum;
    //extView questionText1;
    private String email, answer1, answer2, answer3;
    public RadioButton radioFalse1,radioFalse2, radioFalse3, radioTrue1, radioTrue2, radioTrue3;
    public RadioGroup radioGroup1, radioGroup2, radioGroup3;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    public static QuestionsFragment newInstance() {
        return new QuestionsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.questions_fragment,container,false);
        //questions  = getResources().getStringArray(R.array.matching_questions);
        email = getArguments().getString("EMAIL");
        //questionNum = getArguments().getInt("NUMQUESTIONS");
        //questionText1 = v.findViewById(R.id.questionText1);
        //questionText1.setText(questions[questionNum]);
        Button submitButton = v.findViewById(R.id.submitQuestion_button);
        submitButton.setOnClickListener(this);

        radioFalse1 = v.findViewById(R.id.radioFalse1);
        radioFalse2 = v.findViewById(R.id.radioFalse2);
        radioFalse3 = v.findViewById(R.id.radioFalse3);
        radioTrue1 = v.findViewById(R.id.radioTrue1);
        radioTrue2 = v.findViewById(R.id.radioTrue2);
        radioTrue3 = v.findViewById(R.id.radioTrue3);
        radioGroup1 = v.findViewById(R.id.radioGroup1);
        radioGroup2 = v.findViewById(R.id.radioGroup2);
        radioGroup3 = v.findViewById(R.id.radioGroup3);

        radioGroup1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(radioFalse1.isChecked()) {
                    answer1="f1";
                } else if(radioTrue1.isChecked()) {
                    answer1="t1";
                }
            }
        });

        radioGroup2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(radioFalse2.isChecked()) {
                    answer2="f2";
                } else if(radioTrue2.isChecked()) {
                    answer2="t2";
                }
            }
        });

        radioGroup3.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(radioFalse3.isChecked()) {
                    answer3="f3";
                } else if(radioTrue3.isChecked()) {
                    answer3="t3";
                }
            }
        });

        return v;
    }

    @Override
    public void onClick(View v){
        //TODO: make sure they selected an answer
        Activity activity = getActivity();
        //questionNum++;
        //if(questionNum < questions.length){
         //   questionText.setText(questions[questionNum]);
        //} else {

        DocumentReference docRef = db.collection("users").document(email);
        docRef.update("q1", answer1);
        docRef.update("q2", answer2);
        docRef.update("q3", answer3);


        Intent homeIntent = new Intent(activity, HomeActivity.class);
            homeIntent.putExtra("EMAIL", email.toString());
            startActivity(homeIntent);
        //}
    }
}
