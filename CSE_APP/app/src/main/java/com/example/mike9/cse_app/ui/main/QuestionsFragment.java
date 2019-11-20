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
import com.example.mike9.cse_app.InterestedActivity;
import com.example.mike9.cse_app.MainActivity;
import com.example.mike9.cse_app.QuestionsActivity;
import com.example.mike9.cse_app.R;
import com.example.mike9.cse_app.ShowMessage;
import com.example.mike9.cse_app.SignUpActivity;
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
        Log.d("Questions 0:", questions[0]);
        Log.d("TestParse: ", makeInt("1.0"));
        Log.d("TestParse: ", makeInt("546.8834134"));
        Log.d("TestParse: ", makeInt("4321"));
        email = getArguments().getString("EMAIL");
        questionNum = getArguments().getInt("NUMQUESTIONS");
        questionText1 = v.findViewById(R.id.questionText1);
        questionText1.setText(questions[questionNum]);
        Button submitButton = v.findViewById(R.id.submitQuestion_button);
        submitButton.setOnClickListener(this);

        radioFalse1 = v.findViewById(R.id.radioFalse1);
        //radioFalse2 = v.findViewById(R.id.radioFalse2);
        //radioFalse3 = v.findViewById(R.id.radioFalse3);
        radioTrue1 = v.findViewById(R.id.radioTrue1);
        //radioTrue2 = v.findViewById(R.id.radioTrue2);
        //radioTrue3 = v.findViewById(R.id.radioTrue3);
        radioGroup1 = v.findViewById(R.id.radioGroup1);
        //radioGroup2 = v.findViewById(R.id.radioGroup2);
        //radioGroup3 = v.findViewById(R.id.radioGroup3);

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

        /*radioGroup2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
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
        });*/

        return v;
    }

    @Override
    public void onClick(View v) {

        //if(!answer1.equals("") && !answer2.equals("") && !answer3.equals("")){
        if (!answer1.equals("")) {
            Activity activity = getActivity();
            //questionNum++;
            //if(questionNum < questions.length){
            //   questionText.setText(questions[questionNum]);
            //} else {
            final DocumentReference docRef = db.collection("users").document(email);
            if (answer1.equals("t1")) {
                currentScore = 0;
                final int power = questionNum;
                Log.d("QuestionsVal:", "currentScore: " + currentScore);
                Log.d("QuestionsVal:", "questionNum1: " + questionNum);
                docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                                //String storedPassword = document.get("password").toString();
                                Log.d("QuestionsVal:", "questionNum2: " + questionNum);
                                String scoreStr = document.get("score").toString();
                                updateCurScore(Integer.parseInt(document.get("score").toString()));
                                Log.d("QuestionsVal:", "questionNum3: " + questionNum);
                                int updatedScore = currentScore+exponent(2,power);
                                float testScore = 2^1;
                                float testScore2 = 2+2^1;
                                Log.d("QuestionsVal:", "power: " + power);
                                Log.d("QuestionsVal:", "questionNum4: " + questionNum);
                                Log.d("QuestionsVal:", "CurrentScore: " + currentScore +"updatedScore: " + updatedScore);
                                docRef.update("score", updatedScore);

                            } else {
                                Log.d(TAG, "No such document");
                            }
                        } else {
                            Log.d(TAG, "get failed with ", task.getException());
                        }
                    }
                });
                //Log.d("Questions:", "currentScore: "  + currentScore);
                //float updateScore = currentScore+2^questionNum;
                //Log.d("Questions:", "updatedScore: " + updateScore);
                //Log.d("Questions: ", "QuestionNum: " + questionNum);
                //docRef.update("score", currentScore + 2 ^questionNum);

                //docRef.update("q1", answer1);
                //docRef.update("q2", answer2);
                //docRef.update("q3", answer3);
            }
            questionNum++;
            if(questionNum < questions.length) {
                Intent questionIntent = new Intent(activity, QuestionsActivity.class);
                questionIntent.putExtra("EMAIL", email);
                questionIntent.putExtra("NUMQUESTIONS", questionNum);
                startActivity(questionIntent);
            }else{
                docRef.update("answered?", true);
                /*Intent homeIntent = new Intent(activity, HomeActivity.class);
                homeIntent.putExtra("EMAIL", email.toString());
                startActivity(homeIntent);*/
                Intent interestIntent = new Intent(getActivity(), InterestedActivity.class);
                interestIntent.putExtra("EMAIL", email);
                startActivity(interestIntent);

            }

        } else {
            ShowMessage.show(getActivity(), "Select an option.");
        }
    }

        /*Intent homeIntent = new Intent(activity, HomeActivity.class);
            homeIntent.putExtra("EMAIL", email.toString());
            startActivity(homeIntent);
        }*/

    private void updateCurScore(int score){
        Log.d("QuestionsVal2:", "currentScore: " + currentScore +"   score: "+score);
        currentScore = score;
        Log.d("QuestionsVal2:", "currentScore: " + currentScore +"   score: "+score);
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
