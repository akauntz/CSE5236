package com.example.mike9.cse_app.ui.main;

import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProviders;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.mike9.cse_app.HomeActivity;
import com.example.mike9.cse_app.MainActivity;
import com.example.mike9.cse_app.QuestionsActivity;
import com.example.mike9.cse_app.R;
import com.example.mike9.cse_app.SignUpActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Map;

import static android.content.ContentValues.TAG;

public class MainFragment extends Fragment implements View.OnClickListener {

    private MainViewModel mViewModel;
    private EditText email, password;

    public static MainFragment newInstance() {
        return new MainFragment();
    }
    public Map<String, String> user;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.main_fragment, container, false);
        Button matchesButton = v.findViewById(R.id.matches_button);
        matchesButton.setOnClickListener((View.OnClickListener) this);
        Button signUpButton = v.findViewById(R.id.signUp_button);
        signUpButton.setOnClickListener(this);
        Button logInButton = v.findViewById(R.id.login_button);
        logInButton.setOnClickListener(this);
        email = v.findViewById(R.id.userLogin);
        password = v.findViewById(R.id.userPassword);
        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        // TODO: Use the ViewModel? -> not my TODO but leaving in case one of you two
        //I think this was just part of the template, so don't think we need anything here
    }

    @Override
    public void onClick(View v){
        final Activity activity = getActivity();
        switch (v.getId()){
            case R.id.login_button:
                final String userEmail = email.getText().toString();
                DocumentReference docRef = db.collection("users").document(userEmail);
                docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>(){
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                                String storedPassword = document.get("password").toString();

                                if(storedPassword.equals(password.getText().toString())) {
                                    Log.d(TAG, "Correct password");
                                    //String questions[] = getResources().getStringArray(R.array.matching_questions);
                                    //int questionNum = 1;

                                    if (document.get("answered?").toString().equals("false")) {
                                        Intent questionsIntent = new Intent(activity, QuestionsActivity.class);
                                        questionsIntent.putExtra("EMAIL", email.getText().toString());
                                        //questionsIntent.putExtra("NUMQUESTIONS", questionNum);
                                        startActivity(questionsIntent);
                                    } else {
                                        //logIn
                                        Intent logInIntent = new Intent(activity, HomeActivity.class);
                                        logInIntent.putExtra("EMAIL", email.getText().toString());
                                        startActivity(logInIntent);
                                    }
                                } else{
                                    Log.d(TAG, "Password Mismatch");
                                }

                            } else {
                                Log.d(TAG, "No such document");
                            }
                        } else {Log.d(TAG, "get failed with ", task.getException());
                        }
                    }
                });

                break;
            case R.id.signUp_button:
                //signUp
                startActivity(new Intent(activity, SignUpActivity.class));
//                FragmentManager fm = getFragmentManager();
//                Fragment fragment = new SignUpFragment();
//                fm.beginTransaction().replace(R.id.container,fragment).commit(); //addToBackStack("signup_fragment")?
                break;
            case R.id.matches_button:
                Log.d("buttonClick", "clicked Button");
                int test_int = 7;
                Log.d("checkpoint3", "Look at the step through here 1");
                test_int = 12;
                Log.d("checkpoint3", "Now we step here to 2");
                test_int = 5;
                Log.d("checkpoint3", "Now we step here to 3");
                break;
        }

    }

}
