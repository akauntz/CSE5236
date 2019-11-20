package com.example.mike9.cse_app.ui.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.mike9.cse_app.InterestedActivity;
import com.example.mike9.cse_app.QuestionsActivity;
import com.example.mike9.cse_app.R;
import com.example.mike9.cse_app.ShowMessage;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import static android.content.ContentValues.TAG;

public class QuestionsFragment extends Fragment implements View.OnClickListener  {

    private String[] questions;
    private int questionNum, currentScore;
    TextView questionText1;
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
        View v = inflater.inflate(R.layout.questions2_fragment,container,false);
        questions  = getResources().getStringArray(R.array.matching_questions);
        email = getArguments().getString("EMAIL");
        questionNum = getArguments().getInt("NUMQUESTIONS");
        questionText1 = v.findViewById(R.id.questionText1);
        questionText1.setText(questions[questionNum]);
        Button submitButton = v.findViewById(R.id.submitQuestion_button);
        submitButton.setOnClickListener(this);

        radioFalse1 = v.findViewById(R.id.radioFalse1);
        radioTrue1 = v.findViewById(R.id.radioTrue1);
        radioGroup1 = v.findViewById(R.id.radioGroup1);

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

        return v;
    }

    @Override
    public void onClick(View v) {

        if (!answer1.equals("")) {
            Activity activity = getActivity();
            final DocumentReference docRef = db.collection("users").document(email);
            if (answer1.equals("t1")) {
                currentScore = 0;
                final int power = questionNum;
                docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                                updateCurScore(Integer.parseInt(document.get("score").toString()));
                                Log.d("QuestionsVal:", "questionNum3: " + questionNum);
                                int updatedScore = currentScore+exponent(2,power);
                                docRef.update("score", updatedScore);

                            } else {
                                Log.d(TAG, "No such document");
                            }
                        } else {
                            Log.d(TAG, "get failed with ", task.getException());
                        }
                    }
                });
            }
            questionNum++;
            if(questionNum < questions.length) {
                Intent questionIntent = new Intent(activity, QuestionsActivity.class);
                questionIntent.putExtra("EMAIL", email);
                questionIntent.putExtra("NUMQUESTIONS", questionNum);
                startActivity(questionIntent);
            }else{
                docRef.update("answered?", true);
                Intent interestIntent = new Intent(getActivity(), InterestedActivity.class);
                interestIntent.putExtra("EMAIL", email);
                if(answer1 == "t1") {
                    Log.d("SUPERLOG: ", "Current Score: " + currentScore + " ... " + exponent(2, questionNum));
                    interestIntent.putExtra("POINTS", currentScore + exponent(2, questionNum));
                } else {
                    Log.d("SUPERLOG: ", "Current Score: " + currentScore);
                    interestIntent.putExtra("POINTS", currentScore);
                }
                startActivity(interestIntent);

            }

        } else {
            ShowMessage.show(getActivity(), "Select an option.");
        }
    }


    private void updateCurScore(int score){
        currentScore = score;
    }

    private int exponent(int a, int b){
        int temp = 1;
        for(int i=0; i<b; i++){
            temp *= a;
        }
        return temp;
    }

    private String makeInt(String str){
        int strEnd = 0;
        while (strEnd<str.length() && str.charAt(strEnd)!='.'){
            strEnd++;
        }
        return str.substring(0,strEnd);
    }
}
