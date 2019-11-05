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
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.mike9.cse_app.HomeActivity;
import com.example.mike9.cse_app.MainActivity;
import com.example.mike9.cse_app.QuestionsActivity;
import com.example.mike9.cse_app.R;
import com.example.mike9.cse_app.SignUpActivity;

public class QuestionsFragment extends Fragment implements View.OnClickListener  {

    private String[] questions;
    private int questionNum;
    TextView questionText;
    private String email;

    public static QuestionsFragment newInstance() {
        return new QuestionsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.questions_fragment,container,false);
        questions  = getResources().getStringArray(R.array.matching_questions);
        email = getArguments().getString("EMAIL");
        questionNum = getArguments().getInt("NUMQUESTIONS");
        questionText = v.findViewById(R.id.questionText);
        questionText.setText(questions[questionNum]);
        Button submitButton = v.findViewById(R.id.submitQuestion_button);
        submitButton.setOnClickListener(this);
        return v;
    }

    @Override
    public void onClick(View v){
        //TODO: make sure they selected an answer
        Activity activity = getActivity();
        questionNum++;
        if(questionNum < questions.length){
            questionText.setText(questions[questionNum]);
        } else {
            Intent homeIntent = new Intent(activity, HomeActivity.class);
            homeIntent.putExtra("EMAIL", email.toString());
            startActivity(homeIntent);
        }
    }
}
